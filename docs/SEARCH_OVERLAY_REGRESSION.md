# Search overlay regression checks

## Implementation

- Window events share one background snapshot for whitelist and IME evaluation.
- Only one snapshot is in flight; a newer event invalidates its result and schedules one follow-up.
- Ordinary window events retain the last verified whitelist result until the next snapshot. Revoking it on every event creates a show/hide feedback loop because the ball itself emits window events. Screen-off and whitelist configuration changes still revoke immediately; new snapshots revoke unknown or disallowed hosts.
- Search preparation and editor queries run off the main thread. Requests expire after 2500 ms and stale callbacks cannot reopen the overlay.
- Selecting the app IME while search is active closes search without requesting keyboard dismissal, then verifies the original editor handoff.
- IME finish callbacks do not restore the previous keyboard during the bounded handoff.
- Floating-ball and edge-gesture direct switches now use the same bounded handoff. Capture of the current host runs on the worker inside the cancellable request. When search owns focus, direct switching uses the saved host instead.
- Direct handoffs do not clear search state. Recovery clicks require a focused, visible, editable node in the saved window; successful IME display does not require accessibility support from a custom editor.
- Leaving the host window, turning the screen off, pressing Back delivered to the IME, or explicitly restoring the previous keyboard stops recovery. Lifecycle protection also expires by monotonic time even if the timeout callback is delayed.
- IME hide/finish and input-start callbacks reset handoff stability, including transitions entirely between polls. Completion requires 300 ms of continuous observed visibility.
- Polling a verified host reuses its window ID instead of fetching its node tree. Only fallback clicks query the editable node. A bound host IME gets up to two `requestShowSelf` retries after transient hide callbacks settle; clicks wait for these requests to take effect. Handoff-time `onStartInputView` callbacks no longer issue unconditional competing show requests.
- Explicit exit/ball switch-back uses the same bounded handoff to the saved text IME. Its visible window must belong to the selected IME package; the outgoing app IME cannot satisfy completion. Recovery clicks revalidate the selected destination in either direction.
- Lifecycle-triggered automatic restore only changes selection; it never enters the show/reclick handoff. A failed or timed-out own-IME handoff performs this selection-only restore if our IME remains selected and hidden, because suppressed finish callbacks are not replayed after timeout. Successful handoffs and manual changes to another IME are preserved.
- Switch-back captures the original focused editor's resource ID and class before replacing the IME. If the host clears input focus during hiding, recovery finds that exact visible, enabled editor by ID. A different newly focused editor, ambiguous matches, missing IDs, changed controls or changed host windows prevent a click. View-ID reporting is enabled in both service configuration and connection setup.

## Device acceptance (not yet executed on the new build)

1. Open search from a third-party keyboard. Switch to the app IME using the keyboard picker and separately using the floating ball. Search must disappear and the selected IME must remain usable in the original editor.
2. Open search while the app IME is active. The saved text keyboard must be selected before search takes focus; old IME lifecycle callbacks must not immediately close search.
3. Submit a search. Verify results and original chat focus, including slow keyboard startup.
4. Repeatedly open/close search during keyboard animations, then disable/re-enable accessibility during preparation. No old request may reopen search.
5. Test unavailable previous keyboard, lock/unlock, rotation, split-screen, and switching away from a whitelisted app.
6. Capture logcat with threadtime and system/event buffers across the entire failure, not just application logs. Compare PID and onServiceConnected records; inspect ANR/crash/exit reasons rather than assuming a redraw is a process restart.
7. With the whitelist enabled, leave the ball visible in an allowed app for 30 seconds and repeatedly show/hide the keyboard. The ball must not alternate between allowed/unknown results solely because its own window changed. Switch to a disallowed app and verify the next snapshot hides it.
8. In Douyin with the text keyboard visible, switch directly using the ball and the edge gesture. If the host issues a transient hide, logs should show suppressed automatic restore followed by a completed handoff (and at most two recovery clicks). Repeat after opening search, both by submitting and by directly switching while search owns focus.
9. During handoff, leave the page, lock the phone, press Back, and tap the IME exit button in separate runs. Recovery must stop; after successful handoff, normal keyboard dismissal must still restore the previous IME. Test gesture navigation separately because delivery of Back to the IME depends on the system.
10. Repeat direct and search switches in the same Douyin editor at least ten times each. Record actual visible success and time to show, not only the coordinator's completion log. Check that hide/restart callbacks reset completion and that slow accessibility nodes do not delay the first bound-IME recovery request. A successful build/unit test run does not satisfy this device acceptance step.
11. Switch back to the text IME using both the ball and IME exit button. If Douyin hides it during startup, a bounded host-editor click should reopen the selected text IME. Separately dismiss our IME normally: selection should return to the text IME without reopening it. For a failed forward handoff that times out while hidden, verify that the selected IME is restored even with no further finish callback.
12. In the switch-back failure state, verify the original editor can have `focused=false`. After the fix, logs must capture its ID before switching and show an accepted recovery click without a manual tap; confirm the text keyboard is actually visible afterward. Verify a different focused editor or a changed page is never clicked by an old handoff.

## Validation

Unit tests cover search generation cancellation, startup deadline, own-IME takeover policy, window bounds, existing whitelist resolution and bounded editor retries. They do not verify OEM input dispatch or Surface rendering.
