# Diary System Test Cases

| Test ID | Scenario | Expected Behavior | Edge Case |
|---|---|---|---|
| DIARY-01 | Submit a standard diary entry with mood | Entry saved successfully, timestamped | No |
| DIARY-02 | Submit an empty diary entry | Validation error, cannot save | Yes |
| DIARY-03 | Submit extremely long text (10,000+ words) | Handled gracefully or truncated with a warning | Yes |
| DIARY-04 | Voice-to-text input failure (mic offline) | Graceful error, prompt user to type | Yes |
