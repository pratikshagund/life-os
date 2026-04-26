# Authentication & User Access Test Cases

| Test ID | Scenario | Expected Behavior | Edge Case |
|---|---|---|---|
| AUTH-01 | Valid login credentials | JWT token generated, user directed to Dashboard | No |
| AUTH-02 | Invalid credentials | Error message displayed, login denied | No |
| AUTH-03 | Expired JWT token | User prompted to re-authenticate or token auto-refreshed | Yes |
