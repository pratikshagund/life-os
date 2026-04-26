# AI Reflection Engine Test Cases

| Test ID | Scenario | Expected Behavior | Edge Case |
|---|---|---|---|
| AI-01 | Standard diary and task analysis | Returns productivity score, time wasted, and insights | No |
| AI-02 | AI API timeout or unavailable | Graceful error: "AI insights temporarily unavailable," retries later | Yes |
| AI-03 | User writes gibberish in diary | AI handles it gracefully, e.g., "Not enough context to provide insights today." | Yes |
| AI-04 | Rate limit exceeded on LLM API | Fallback mechanism, delays processing | Yes |
| AI-05 | AI returns invalid JSON structure | System catches parsing error, falls back or retries | Yes |
