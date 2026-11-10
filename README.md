# GESMIO Backend

Spring Boot 4 · Java 21 · PostgreSQL (Supabase) · JWT security. Built from the GESMIO ER diagram (44 tables, 16 modules).

## Run
1. Run `database/schema.sql` in the Supabase SQL editor (skip if the tables already exist).
2. Copy `.env.example` values into your environment (IntelliJ: Run Configuration > Environment variables).
3. `./mvnw spring-boot:run`  → http://localhost:8080
   (`./mvnw package -DskipTests` — the default `contextLoads` test needs a live DB + env vars.)

On first start the app seeds roles `ADMIN, HR, MANAGER, EMPLOYEE`, permissions, and an admin user from `ADMIN_EMAIL` / `ADMIN_PASSWORD`.

## Auth
`POST /api/v1/auth/login {email,password}` → `accessToken`. Send `Authorization: Bearer <token>`.
`GET /api/v1/auth/me`, `POST /api/v1/auth/change-password`.

## Endpoint map
| Area | Path | Who |
|---|---|---|
| Users (hashed passwords) | `/api/v1/identity/users` (+ `/{id}/reset-password`) | ADMIN |
| Roles, permissions, role-permissions, user-roles | `/api/v1/authorization/{roles,permissions,role-permissions,user-roles}` | ADMIN |
| Platform settings / Audit logs (read-only) | `/api/v1/administration/platform-settings`, `/api/v1/audit/audit-logs` | ADMIN |
| Departments, designations | `/api/v1/organization/{departments,designations}` | read: all, write: ADMIN/HR |
| Employees, reporting lines, employment records | `/api/v1/employees/{employees,reporting-relationships,employment-records}` | read: all, write: ADMIN/HR |
| Recruitment (openings, candidates, documents, applications, interviews, offers, feedback) | `/api/v1/recruitment/...` | ADMIN/HR |
| Products, product teams | `/api/v1/products/{products,product-teams}` | read: all, write: ADMIN/HR/MANAGER |
| Projects, members, milestones | `/api/v1/projects/{projects,project-members,milestones}` | same |
| Tasks, assignments | `/api/v1/tasks/{tasks,task-assignments}` | same |
| Leave policies / requests / balances / approvals / transactions | `/api/v1/leave/...` | policies: read all; rest ADMIN/HR/MANAGER read, ADMIN/HR write |
| Attendance records / adjustments | `/api/v1/attendance/...` | same |
| Support categories, tickets, comments | `/api/v1/support/...` | categories: read all; rest ADMIN/HR |
| Announcements + targets | `/api/v1/announcements/...` | read: all, write: ADMIN/HR |
| Documents + permissions | `/api/v1/documents/...` | ADMIN/HR |

**Self-service (any logged-in employee)** — `/api/v1/me/...`
- `GET/POST /leave-requests`, `POST /leave-requests/{id}/cancel`, `GET /leave-balances?year=`
- `POST /attendance/check-in`, `POST /attendance/check-out`, `GET /attendance?from=&to=`, `POST /attendance/adjustments`
- `GET /tasks`, `GET /projects`, `POST /tasks/{id}/status`
- `GET/POST /support-tickets`, `GET/POST /support-tickets/{id}/comments`
- `GET /notifications`, `GET /notifications/unread-count`, `POST /notifications/{id}/read`, `POST /notifications/read-all`

**Approvals (ADMIN/HR/MANAGER)** — `/api/v1/manager/...`
- `GET /leave-requests/pending`, `POST /leave-requests/{id}/decision {decision: APPROVED|REJECTED, comments}`
- `POST /attendance-adjustments/{id}/decision {decision}`

## CRUD conventions (all generated resources)
- `GET ?page=0&size=20&sort=createdAt,desc` and any property as a filter: `/api/v1/tasks/tasks?projectId=<uuid>&status=TODO`
- `POST` create · `PUT /{id}` partial update (null/absent fields unchanged) · `DELETE /{id}`
- Errors use RFC 7807 `ProblemDetail` (400 validation, 404, 409 constraint, 422 business rule).
- `created_by / uploaded_by / assigned_by ...` are filled from the logged-in user; every write is audit-logged.
- JSONB columns (`old_values`, `new_values`, `setting_value`) are exposed as JSON **strings**.

## Not included yet
File upload/download (documents & candidate documents store `storage_key` metadata only — wire to Supabase Storage),
password-reset email flow, MFA verification, login rate limiting, email notifications.
