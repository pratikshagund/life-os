# Non-Functional & Security Test Cases

| Test ID | Scenario | Expected Behavior | Edge Case |
|---|---|---|---|
| SEC-01 | Attempt to access another user's diary (if multi-user) | 403 Forbidden / 404 Not Found | Yes |
| PERF-01 | Load dashboard with 5 years of historical data | Query optimized, pagination/lazy loading used, loads in < 2s | Yes |
| ERR-01 | Database connection drops | Global exception handler catches it, shows friendly 500 error page | Yes |
