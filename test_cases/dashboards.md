# Dashboards (Weekly & Life) Test Cases

| Test ID | Scenario | Expected Behavior | Edge Case |
|---|---|---|---|
| DASH-01 | View dashboard with 7 days of complete data | Charts render correctly showing productivity, streak, etc. | No |
| DASH-02 | View dashboard with missing days (user didn't log in) | Charts display 0 or gaps gracefully without crashing | Yes |
| DASH-03 | Timezone change (user travels) | Timestamps correctly localized, daily routines adjust or stay locked to home zone | Yes |
| DASH-04 | Extreme streak counts (e.g., 1000 days) | UI does not break, numbers format correctly | Yes |
