# Search overlay regression checks

## Implementation

- Window events share one background snapshot for whitelist and IME evaluation.
- Only one snapshot is in flight; a newer event invalidates its result and schedules one follow-up.
- Ordinary window events retain the last verified whitelist result until the next snapshot. Revoking it on every event creates a show/hide feedback loop because the ball itself emits window events. Screen-off and whitelist configuration changes still revoke immediately; new snapshots revoke unknown or disallowed hosts.
- Search preparation and editor queries run off the main thread. Requests expire after 2500 ms and stale callbacks cannot reopen the overlay.
- Selecting the app IME while search is active closes search without requesting keyboard dismissal, then verifies the original editor handoff.
- IME finish callbacks do not restore the previous keyboard during the bounded handoff.

## Device acceptance (not yet executed on the new build)

1. Open search from a third-party keyboard. Switch to the app IME using the keyboard picker and separately using the floating ball. Search must disappear and the selected IME must remain usable in the original editor.
2. Open search while the app IME is active. The saved text keyboard must be selected before search takes focus; old IME lifecycle callbacks must not immediately close search.
3. Submit a search. Verify results and original chat focus, including slow keyboard startup.
4. Repeatedly open/close search during keyboard animations, then disable/re-enable accessibility during preparation. No old request may reopen search.
5. Test unavailable previous keyboard, lock/unlock, rotation, split-screen, and switching away from a whitelisted app.
6. Capture logcat with threadtime and system/event buffers across the entire failure, not just application logs. Compare PID and onServiceConnected records; inspect ANR/crash/exit reasons rather than assuming a redraw is a process restart.
7. With the whitelist enabled, leave the ball visible in an allowed app for 30 seconds and repeatedly show/hide the keyboard. The ball must not alternate between allowed/unknown results solely because its own window changed. Switch to a disallowed app and verify the next snapshot hides it.

## Validation

Unit tests cover search generation cancellation, startup deadline, own-IME takeover policy, window bounds, existing whitelist resolution and bounded editor retries. They do not verify OEM input dispatch or Surface rendering.
