# Resource library and transfer regression checks

## Automated checks

Run `.\gradlew.bat :app:testDebugUnitTest --no-daemon "-Pkotlin.incremental=false"`.

KeywordUtilsTest covers reversed multi-word queries, whitespace/case handling, all-terms-required matching, tag/category scope boundaries, prefix ranking, and stable results over 20,000 resource records. This is a search correctness test, not a device scrolling benchmark.

## Device checks still required

- Import images and a large ZIP, rotate repeatedly during progress, then background and return. There must be one task, continuous progress and one completion result.
- Scan a large folder, rotate while scanning and again at the confirmation dialog. The selected folder and confirmation must remain usable.
- Cancel an import via Back. Already committed images must remain; original-image cleanup must not launch for a cancelled task. Re-importing the same source should deduplicate.
- Finish an ordinary import while the app is backgrounded. On returning, show the cleanup choice once. Rotate while the system trash authorization is visible and check the returned batch count.
- Export a large ZIP, rotate and background/return. The progress dialog must reconnect to the same task. Cancel and verify incomplete output is removed, or that cleanup failure is clearly reported.
- Kill a background process during a transfer and restore its activity. Show an interrupted-task message; do not silently restart or report success. This implementation does not provide durable background jobs or automatic resume after process death.
- Tap each of the six recent thumbnails on Home. Open the tapped image and allow paging among the recent images. Back should return to Home.
- Check 320dp/360dp widths and 1.3x/2.0x font scales: library actions, filter button, permission-setting buttons and dialogs should remain accessible.
- Scroll a library with many static images/GIFs, revisit categories and inspect memory/frame timing. Thumbnails remain static; recycled category pages should release their collectors, and thumbnail disk cache should avoid repeated decoding.

Device checks are not marked as passed by JVM tests. Night-mode styling is outside this change.
