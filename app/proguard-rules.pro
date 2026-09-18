# SavedStateHandle serializes ImportSummary when Android saves the import screen.
# Preserve its name and full shape for state restored across app updates.
-keep class com.suzu.test.ui.import.ImportSummary { *; }
