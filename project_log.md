# LifeOS Project Log & Context Tracker

*This document serves as the ground truth for the LifeOS project state. It must be updated continuously to track generated files, completed tasks, and architectural decisions.*

## Initial Setup & Bootstrapping (Phase 1)
- **Status:** Scaffolding Complete
- **Environment:**
  - Backend: Java 17/21, Spring Boot 3.x, Maven
  - Frontend: Angular 18+, Node.js
  - Database: PostgreSQL 15 (Dockerized)
  - AI Engine: OpenAI API (Mocked initially)
- **Generated Architecture & Files:**
  - `life_os_skill.md`: Core system directives and execution rules.
  - `test_cases/`: Directory containing separated feature-wise test cases (Authentication, Daily Planner, Diary, Long-Term, Dashboards, AI Reflection, Non-Functional).
  - `docker-compose.yml`: Local PostgreSQL container configuration.
  - `life-os-backend/pom.xml`: Spring Boot dependencies (Web, Data JPA, Postgres, Security, Lombok).
  - `life-os-backend/src/main/resources/application.yml`: Database connection and mock API keys.
  - `life-os-backend/src/main/java/com/lifeos/LifeOsApplication.java`: Main application entry point.
  - `life-os-ui/`: Full Angular application structure generated via `ng new`.
  - `architecture_and_schema.md`: Initial architecture diagram and database ERD.
  - `DiaryEntryRepository.java` & `UserRepository.java`: Spring Data JPA interfaces.
  - `DiaryEntryRequest.java`: DTO for incoming HTTP requests.
  - `DiaryService.java`: Business logic for saving diaries and mocking AI.
  - `DiaryController.java`: Exposes `POST /api/diary`.
  - `life-os-ui/src/app/models/diary-entry.ts`: TS Interfaces mapping to backend DTO.
  - `life-os-ui/src/app/services/diary.service.ts`: Angular HTTP service.
  - `life-os-ui/src/app/components/diary/`: Angular Component with Minimalist UI.
  - `WebSecurityConfig.java`: Disabled default Spring Security to allow frontend API calls.

## Terminology & Concepts Log
*(Tracking concepts explained for interview preparation)*
- **Scaffolding / Bootstrapping:** Generating the foundational skeleton of a project (folder structure, base configuration files) so that actual business logic can be written immediately.
- **Docker / Containerization:** Packaging an application (like PostgreSQL) with all its dependencies into a standardized unit for software development, ensuring it runs uniformly across environments.
- **Three-Tier Design:** Splitting the app into Presentation, Logic, and Data layers to ensure Decoupling (easy swaps of frontend or database later).
- **Stateless REST APIs with JWT:** APIs that don't store session state; user identity is passed via tokens for better scalability.
- **Relational Schema (Foreign Keys & Normalization):** Using SQL to enforce data relationships (e.g. Task belongs to User) and reduce data redundancy.
- **JPA (Java Persistence API) & Hibernate (ORM):** An ORM (Object-Relational Mapping) framework maps Java classes (`@Entity`) directly to database tables, allowing us to interact with the database using Java objects instead of raw SQL queries.
- **Lombok:** A Java library that auto-generates boilerplate code like Getters, Setters (`@Data`), and Constructors (`@NoArgsConstructor`), keeping files clean.
- **UUID vs Auto-Incrementing IDs:** We used UUIDs for Primary Keys. *Why?* UUIDs are globally unique, meaning if we merge databases or scale, IDs will never collide. They are also harder for attackers to guess in URLs.
- **Constants File (Decoupling):** Extracting hardcoded strings (like API URLs) into a centralized file (`api.constants.ts`) so that if the URL changes, we only have to update it in one place rather than in 50 different files.
- **Glassmorphism UI:** A modern web design trend using semi-transparent backgrounds with background blur (`backdrop-filter: blur()`) to create a frosted glass effect. We used Vanilla CSS instead of a heavy library like Ignite UI to keep the application fast and fully customizable.
