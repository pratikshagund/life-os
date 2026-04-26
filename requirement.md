

Build a full-stack AI-powered web application called "LifeOS" – a personal life management system that combines a diary, timetable planner, and AI productivity coach.

🔹 Core Purpose:
The application should help a working professional manage daily routines, track activities, maintain a diary, and improve productivity using AI insights.

---

🔹 User Context:

* User works 5 days a week (Monday–Friday), 10:00 AM to 6:30 PM
* Morning routine is fixed and non-negotiable
* User wants to:

  * Prepare for job interviews (Java, Spring Boot, Angular)
  * Learn AI-driven development
  * Maintain discipline and consistency

---

🔹 Core Features:

1. Daily Planner:

* Create and manage daily tasks
* Time-blocking support
* Predefined fixed routines (morning routine should always repeat)
* Office time should be auto-blocked (10 AM – 6:30 PM)

2. Monthly & Yearly Planning:

* Create long-term goals
* Break goals into actionable tasks
* Calendar view for planning

3. Smart Timetable:

* Auto-generate daily schedule based on:

  * Pending tasks
  * Priorities
  * Missed tasks from previous days

4. Diary System:

* User can write daily journal entries
* Optional voice input (convert speech to text)
* Tag entries with mood (happy, stressed, productive, etc.)

5. AI Reflection Engine:

* Analyze daily diary + tasks
* Provide insights like:

  * Productivity score
  * Time wasted
  * Most productive hours
  * Behavioral patterns

6. Weekly Review Dashboard:

* Show:

  * Tasks planned vs completed
  * Consistency score
  * Most productive day
  * Improvement suggestions

7. Life Dashboard:

* Metrics:

  * Productivity score
  * Discipline streak
  * Time spent (learning, work, distractions)
* Visual charts

---

🔹 Tech Requirements:

Frontend:

* Angular (latest version)
* Clean, modern dashboard UI

Backend:

* Java Spring Boot
* RESTful APIs
* Layered architecture (Controller, Service, Repository)

Database:

* PostgreSQL
* Proper relational schema for users, tasks, schedules, diary entries

AI Integration:

* Use OpenAI API (or similar LLM)
* Separate service/module for AI analysis

---

🔹 Advanced Features (Optional but preferred):

* Role-based access (single user for now, scalable later)
* JWT authentication
* Notifications/reminders
* Habit tracking system

---

🔹 Non-Functional Requirements:

* Scalable architecture
* Clean code with proper naming conventions
* Modular design (microservice-ready structure)
* API documentation (Swagger/OpenAPI)

---

🔹 Deliverables:

* Complete backend APIs
* Frontend UI with dashboard
* Database schema design
* AI integration layer
* Sample data for testing

---

🔹 UI Expectations:

* Dashboard similar to productivity apps
* Calendar view + charts
* Minimal, distraction-free design

---

🔹 Goal:
This should not be just a to-do app, but an intelligent system that tracks, analyzes, and improves the user's life decisions over time.

---

## 💡 Pro Tip (important)

After you run this once in Antigravity:

👉 Then give **follow-up prompts like:**

* “Add microservices architecture”
* “Improve AI insights depth”
* “Add habit tracking with streak logic”
* “Optimize database schema for performance”

---