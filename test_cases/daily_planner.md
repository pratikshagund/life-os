# Daily Planner & Smart Timetable Test Cases

| Test ID | Scenario | Expected Behavior | Edge Case |
|---|---|---|---|
| PLAN-01 | Create a standard task within available hours | Task successfully added to the daily schedule | No |
| PLAN-02 | Add a task during a generic recurring block | System strictly prevents scheduling and displays an error | Yes |
| PLAN-03 | Overlapping tasks | System strictly blocks double-booking and prevents scheduling | Yes |
| PLAN-04 | Task crosses midnight boundary | System handles multi-day span or splits the task | Yes |
| PLAN-05 | Auto-schedule with more tasks than available time | System schedules highest priority tasks first, moves rest to backlog | Yes |
| PLAN-06 | Auto-schedule with no pending tasks | Suggests learning (Java, Angular) or rest periods | Yes |
| PLAN-07 | Modify fixed morning routine | System prevents or warns user about non-negotiable routine changes | Yes |
