package com.suzu.test

import com.suzu.test.update.FailureReason
import com.suzu.test.update.GitHubRelease
import com.suzu.test.update.ReleaseVersion
import com.suzu.test.update.UpdateCheckPolicy
import com.suzu.test.update.UpdateCheckResult
import org.junit.Assert.*
import org.junit.Test

class UpdateCheckPolicyTest {
    private fun compare(current: String, latest: String) =
        UpdateCheckPolicy.compare(current, GitHubRelease(latest, ""))

    @Test fun comparesNumericComponentsRatherThanStrings() {
        assertTrue(compare("1.7.3", "v1.7.10") is UpdateCheckResult.NewVersion)
        assertTrue(compare("1.9.9", "v1.10.0") is UpdateCheckResult.NewVersion)
        assertTrue(compare("1.99.99", "v2.0.0") is UpdateCheckResult.NewVersion)
    }

    @Test fun acceptsPrefixesWhitespaceAndEquivalentTrailingZeros() {
        assertEquals(UpdateCheckResult.UpToDate, compare("1.7.3", " v1.7.3 "))
        assertEquals(UpdateCheckResult.UpToDate, compare("1.7.3", "V1.7.3.0"))
        assertEquals(UpdateCheckResult.UpToDate, compare("1.7", "1.7.0"))
    }

    @Test fun equalOrOlderReleaseNeverPromptsDowngrade() {
        assertEquals(UpdateCheckResult.UpToDate, compare("1.7.3", "v1.7.3"))
        assertEquals(UpdateCheckResult.UpToDate, compare("1.8.0", "v1.7.3"))
    }

    @Test fun stableReleaseIsNewerThanItsPrerelease() {
        assertTrue(compare("1.8.0-rc.1", "v1.8.0") is UpdateCheckResult.NewVersion)
        assertEquals(UpdateCheckResult.UpToDate, compare("1.8.0", "1.8.0-rc.2"))
    }

    @Test fun usesSemverPrereleaseOrderingAndIgnoresBuildMetadata() {
        assertTrue(compare("1.8.0-beta.9", "1.8.0-beta.10") is UpdateCheckResult.NewVersion)
        assertTrue(compare("1.8.0-beta", "1.8.0-rc.1") is UpdateCheckResult.NewVersion)
        assertEquals(UpdateCheckResult.UpToDate, compare("1.7.3+build.99", "v1.7.3+build.100"))
    }

    @Test fun rejectsMalformedTagsInsteadOfClaimingUpToDate() {
        for (tag in listOf("", "latest", "release-1.8.0", "1..8", "1.8.0/../", "1.8.0 stable")) {
            assertEquals(UpdateCheckResult.Failed(FailureReason.UNKNOWN_VERSION), compare("1.7.3", tag))
        }
        assertEquals(UpdateCheckResult.Failed(FailureReason.UNKNOWN_VERSION), compare("unknown", "1.8.0"))
        assertNull(ReleaseVersion.parse("1".repeat(201)))
    }

    @Test fun versionNumbersCannotOverflow() {
        assertTrue(compare("1.7.3", "999999999999999999999999.0.0") is UpdateCheckResult.NewVersion)
    }

    private val now = 1_800_000_000_000L

    @Test fun firstLaunchChecksImmediately() {
        assertTrue(UpdateCheckPolicy.shouldCheck(now, 0, 0))
    }

    @Test fun successfulCheckWaitsOneDay() {
        assertFalse(UpdateCheckPolicy.shouldCheck(now, now - 2 * 60 * 60 * 1000, now - 2 * 60 * 60 * 1000))
        assertTrue(UpdateCheckPolicy.shouldCheck(now, now - UpdateCheckPolicy.SUCCESS_INTERVAL_MS,
            now - UpdateCheckPolicy.SUCCESS_INTERVAL_MS))
    }

    @Test fun failuresBackOffForOneHour() {
        assertFalse(UpdateCheckPolicy.shouldCheck(now, now - 10_000, 0))
        assertTrue(UpdateCheckPolicy.shouldCheck(now, now - UpdateCheckPolicy.RETRY_INTERVAL_MS, 0))
    }

    @Test fun recentFailedAttemptDoesNotEraseSuccessfulCooldown() {
        assertFalse(UpdateCheckPolicy.shouldCheck(now, now - 2 * 60 * 60 * 1000, now - 3 * 60 * 60 * 1000))
    }

    @Test fun clockMovingBackwardsDoesNotDisableChecksForever() {
        assertTrue(UpdateCheckPolicy.shouldCheck(now, now + 1_000_000, now + 1_000_000))
    }
}
