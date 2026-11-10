-- GESMIO schema (generated from the ER diagram). Run once in the Supabase SQL editor.
-- Safe to re-run: every statement is IF NOT EXISTS.

create extension if not exists pgcrypto;

create table if not exists users (
  id uuid primary key default gen_random_uuid(),
  email varchar(255) not null unique,
  password_hash varchar(255) not null,
  status varchar(255) not null default 'ACTIVE',
  created_at timestamptz not null default now()
);

create table if not exists password_reset_tokens (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null,
  token_hash varchar(255) not null,
  expires_at timestamptz not null
);

create table if not exists mfa_credentials (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null,
  credential_type varchar(255) not null
);

create table if not exists sessions (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null,
  token_hash varchar(255) not null,
  expires_at timestamptz not null
);

create table if not exists permissions (
  id uuid primary key default gen_random_uuid(),
  code varchar(255) not null unique,
  module varchar(255) not null,
  action varchar(255) not null
);

create table if not exists roles (
  id uuid primary key default gen_random_uuid(),
  code varchar(255) not null unique,
  name varchar(255) not null,
  is_system boolean not null default false
);

create table if not exists role_permissions (
  id uuid primary key default gen_random_uuid(),
  role_id uuid not null,
  permission_id uuid not null
);

create table if not exists user_roles (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null,
  role_id uuid not null,
  scope_type varchar(255) not null default 'GLOBAL',
  department_id uuid,
  project_id uuid,
  assigned_by uuid,
  assigned_at timestamptz not null default now(),
  revoked_at timestamptz
);

create table if not exists departments (
  id uuid primary key default gen_random_uuid(),
  name varchar(255) not null,
  code varchar(255) not null unique,
  parent_department_id uuid
);

create table if not exists designations (
  id uuid primary key default gen_random_uuid(),
  title varchar(255) not null,
  code varchar(255) not null unique
);

create table if not exists employees (
  id uuid primary key default gen_random_uuid(),
  user_id uuid,
  department_id uuid,
  designation_id uuid,
  employee_code varchar(255) not null unique,
  joining_date date,
  employment_status varchar(255) not null default 'ACTIVE'
);

create table if not exists reporting_relationships (
  id uuid primary key default gen_random_uuid(),
  employee_id uuid not null,
  manager_employee_id uuid not null,
  effective_from date not null,
  effective_to date
);

create table if not exists employment_records (
  id uuid primary key default gen_random_uuid(),
  employee_id uuid not null,
  department_id uuid,
  designation_id uuid,
  start_date date not null,
  end_date date
);

create table if not exists platform_settings (
  id uuid primary key default gen_random_uuid(),
  setting_key varchar(255) not null unique,
  category varchar(255),
  setting_value jsonb,
  description text,
  is_active boolean not null default true,
  version integer not null default 1,
  updated_by uuid,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists audit_logs (
  id uuid primary key default gen_random_uuid(),
  actor_user_id uuid,
  action varchar(255) not null,
  entity_type varchar(255),
  entity_id uuid,
  old_values jsonb,
  new_values jsonb,
  outcome varchar(255),
  request_id varchar(255),
  ip_address inet,
  user_agent varchar(255),
  created_at timestamptz not null default now()
);

create table if not exists job_openings (
  id uuid primary key default gen_random_uuid(),
  department_id uuid,
  created_by uuid not null,
  hiring_manager_id uuid,
  title varchar(255) not null,
  description text,
  employment_type varchar(255),
  status varchar(255) not null default 'OPEN',
  closing_date date,
  created_at timestamptz not null default now()
);

create table if not exists candidates (
  id uuid primary key default gen_random_uuid(),
  full_name varchar(255) not null,
  email varchar(255) not null,
  phone varchar(255),
  current_location varchar(255),
  created_at timestamptz not null default now()
);

create table if not exists candidate_documents (
  id uuid primary key default gen_random_uuid(),
  candidate_id uuid not null,
  document_type varchar(255) not null,
  file_name varchar(255) not null,
  storage_key varchar(255) not null,
  uploaded_at timestamptz not null default now()
);

create table if not exists job_applications (
  id uuid primary key default gen_random_uuid(),
  candidate_id uuid not null,
  job_opening_id uuid not null,
  status varchar(255) not null default 'APPLIED',
  applied_at timestamptz not null default now()
);

create table if not exists interviews (
  id uuid primary key default gen_random_uuid(),
  job_application_id uuid not null,
  interview_type varchar(255),
  scheduled_at timestamptz,
  status varchar(255) not null default 'SCHEDULED'
);

create table if not exists job_offers (
  id uuid primary key default gen_random_uuid(),
  job_application_id uuid not null,
  offer_status varchar(255) not null default 'DRAFT',
  offered_at date,
  response_deadline date
);

create table if not exists interview_feedback (
  id uuid primary key default gen_random_uuid(),
  interview_id uuid not null,
  interviewer_user_id uuid not null,
  feedback text,
  recommendation varchar(255),
  created_at timestamptz not null default now()
);

create table if not exists products (
  id uuid primary key default gen_random_uuid(),
  code varchar(255) not null unique,
  name varchar(255) not null,
  description text,
  status varchar(255) not null default 'ACTIVE',
  owner_employee_id uuid,
  created_by uuid not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists product_teams (
  id uuid primary key default gen_random_uuid(),
  product_id uuid not null,
  employee_id uuid not null,
  team_role varchar(255),
  joined_at date,
  left_at date
);

create table if not exists projects (
  id uuid primary key default gen_random_uuid(),
  product_id uuid,
  code varchar(255) not null unique,
  name varchar(255) not null,
  description text,
  status varchar(255) not null default 'PLANNED',
  owner_employee_id uuid,
  created_by uuid not null,
  start_date date,
  end_date date,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists project_members (
  id uuid primary key default gen_random_uuid(),
  project_id uuid not null,
  employee_id uuid not null,
  member_role varchar(255),
  joined_at timestamptz not null default now(),
  left_at timestamptz
);

create table if not exists milestones (
  id uuid primary key default gen_random_uuid(),
  project_id uuid not null,
  name varchar(255) not null,
  description text,
  due_date date,
  status varchar(255) not null default 'PENDING',
  created_at timestamptz not null default now()
);

create table if not exists tasks (
  id uuid primary key default gen_random_uuid(),
  project_id uuid not null,
  milestone_id uuid,
  title varchar(255) not null,
  description text,
  status varchar(255) not null default 'TODO',
  priority varchar(255) not null default 'MEDIUM',
  start_date date,
  due_date date,
  created_by uuid not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists task_assignments (
  id uuid primary key default gen_random_uuid(),
  task_id uuid not null,
  employee_id uuid not null,
  is_primary boolean not null default false,
  assigned_by uuid,
  assigned_at timestamptz not null default now(),
  revoked_at timestamptz
);

create table if not exists leave_policies (
  id uuid primary key default gen_random_uuid(),
  code varchar(255) not null unique,
  name varchar(255) not null,
  annual_entitlement numeric(10,2) not null default 0,
  is_paid boolean not null default true,
  is_active boolean not null default true
);

create table if not exists leave_requests (
  id uuid primary key default gen_random_uuid(),
  employee_id uuid not null,
  leave_policy_id uuid not null,
  start_date date not null,
  end_date date not null,
  requested_days numeric(10,2) not null,
  reason varchar(255),
  status varchar(255) not null default 'PENDING',
  submitted_at timestamptz not null default now()
);

create table if not exists leave_balances (
  id uuid primary key default gen_random_uuid(),
  employee_id uuid not null,
  leave_policy_id uuid not null,
  leave_year integer not null,
  opening_balance numeric(10,2) not null default 0,
  accrued_days numeric(10,2) not null default 0,
  used_days numeric(10,2) not null default 0,
  available_days numeric(10,2) not null default 0
);

create table if not exists leave_approvals (
  id uuid primary key default gen_random_uuid(),
  leave_request_id uuid not null,
  approver_user_id uuid not null,
  approval_level integer not null default 1,
  decision varchar(255) not null,
  comments text,
  acted_at timestamptz
);

create table if not exists leave_balance_transactions (
  id uuid primary key default gen_random_uuid(),
  leave_balance_id uuid not null,
  leave_request_id uuid,
  transaction_type varchar(255) not null,
  days numeric(10,2) not null,
  created_at timestamptz not null default now()
);

create table if not exists attendance_records (
  id uuid primary key default gen_random_uuid(),
  employee_id uuid not null,
  attendance_date date not null,
  work_mode varchar(255) default 'OFFICE',
  check_in_at timestamptz,
  check_out_at timestamptz,
  total_work_minutes integer,
  status varchar(255) not null default 'PRESENT',
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists attendance_adjustments (
  id uuid primary key default gen_random_uuid(),
  attendance_record_id uuid not null,
  requested_by uuid not null,
  reviewed_by uuid,
  reason text,
  old_check_in_at timestamptz,
  new_check_in_at timestamptz,
  old_check_out_at timestamptz,
  new_check_out_at timestamptz,
  status varchar(255) not null default 'PENDING',
  created_at timestamptz not null default now()
);

create table if not exists support_categories (
  id uuid primary key default gen_random_uuid(),
  code varchar(255) not null unique,
  name varchar(255) not null,
  is_active boolean not null default true
);

create table if not exists support_tickets (
  id uuid primary key default gen_random_uuid(),
  ticket_number varchar(255) not null unique,
  requester_employee_id uuid not null,
  category_id uuid,
  assigned_to_user_id uuid,
  subject varchar(255) not null,
  description text,
  priority varchar(255) not null default 'MEDIUM',
  status varchar(255) not null default 'OPEN',
  created_at timestamptz not null default now(),
  resolved_at timestamptz
);

create table if not exists support_ticket_comments (
  id uuid primary key default gen_random_uuid(),
  ticket_id uuid not null,
  author_user_id uuid not null,
  comment text not null,
  is_internal boolean not null default false,
  created_at timestamptz not null default now()
);

create table if not exists announcements (
  id uuid primary key default gen_random_uuid(),
  created_by uuid not null,
  title varchar(255) not null,
  content text not null,
  audience_type varchar(255) not null default 'ALL',
  status varchar(255) not null default 'DRAFT',
  publish_at timestamptz,
  expires_at timestamptz,
  created_at timestamptz not null default now()
);

create table if not exists announcement_targets (
  id uuid primary key default gen_random_uuid(),
  announcement_id uuid not null,
  department_id uuid not null
);

create table if not exists notifications (
  id uuid primary key default gen_random_uuid(),
  recipient_user_id uuid not null,
  notification_type varchar(255),
  title varchar(255) not null,
  message text,
  reference_type varchar(255),
  reference_id uuid,
  read_at timestamptz,
  created_at timestamptz not null default now()
);

create table if not exists documents (
  id uuid primary key default gen_random_uuid(),
  owner_employee_id uuid,
  uploaded_by uuid not null,
  title varchar(255) not null,
  file_name varchar(255) not null,
  storage_key varchar(255) not null unique,
  mime_type varchar(255),
  file_size bigint,
  visibility varchar(255) not null default 'PRIVATE',
  status varchar(255) not null default 'ACTIVE',
  created_at timestamptz not null default now()
);

create table if not exists document_permissions (
  id uuid primary key default gen_random_uuid(),
  document_id uuid not null,
  user_id uuid,
  role_id uuid,
  permission_level varchar(255) not null default 'VIEW',
  granted_by uuid,
  granted_at timestamptz not null default now(),
  revoked_at timestamptz
);

-- Foreign keys
do $$ begin alter table password_reset_tokens add constraint fk_password_reset_tokens_user_id foreign key (user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table mfa_credentials add constraint fk_mfa_credentials_user_id foreign key (user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table sessions add constraint fk_sessions_user_id foreign key (user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table role_permissions add constraint fk_role_permissions_role_id foreign key (role_id) references roles(id); exception when duplicate_object then null; end $$;
do $$ begin alter table role_permissions add constraint fk_role_permissions_permission_id foreign key (permission_id) references permissions(id); exception when duplicate_object then null; end $$;
do $$ begin alter table user_roles add constraint fk_user_roles_user_id foreign key (user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table user_roles add constraint fk_user_roles_role_id foreign key (role_id) references roles(id); exception when duplicate_object then null; end $$;
do $$ begin alter table user_roles add constraint fk_user_roles_department_id foreign key (department_id) references departments(id); exception when duplicate_object then null; end $$;
do $$ begin alter table user_roles add constraint fk_user_roles_project_id foreign key (project_id) references projects(id); exception when duplicate_object then null; end $$;
do $$ begin alter table user_roles add constraint fk_user_roles_assigned_by foreign key (assigned_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table departments add constraint fk_departments_parent_department_id foreign key (parent_department_id) references departments(id); exception when duplicate_object then null; end $$;
do $$ begin alter table employees add constraint fk_employees_user_id foreign key (user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table employees add constraint fk_employees_department_id foreign key (department_id) references departments(id); exception when duplicate_object then null; end $$;
do $$ begin alter table employees add constraint fk_employees_designation_id foreign key (designation_id) references designations(id); exception when duplicate_object then null; end $$;
do $$ begin alter table reporting_relationships add constraint fk_reporting_relationships_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table reporting_relationships add constraint fk_reporting_relationships_manager_employee_id foreign key (manager_employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table employment_records add constraint fk_employment_records_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table employment_records add constraint fk_employment_records_department_id foreign key (department_id) references departments(id); exception when duplicate_object then null; end $$;
do $$ begin alter table employment_records add constraint fk_employment_records_designation_id foreign key (designation_id) references designations(id); exception when duplicate_object then null; end $$;
do $$ begin alter table platform_settings add constraint fk_platform_settings_updated_by foreign key (updated_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table audit_logs add constraint fk_audit_logs_actor_user_id foreign key (actor_user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table job_openings add constraint fk_job_openings_department_id foreign key (department_id) references departments(id); exception when duplicate_object then null; end $$;
do $$ begin alter table job_openings add constraint fk_job_openings_created_by foreign key (created_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table job_openings add constraint fk_job_openings_hiring_manager_id foreign key (hiring_manager_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table candidate_documents add constraint fk_candidate_documents_candidate_id foreign key (candidate_id) references candidates(id); exception when duplicate_object then null; end $$;
do $$ begin alter table job_applications add constraint fk_job_applications_candidate_id foreign key (candidate_id) references candidates(id); exception when duplicate_object then null; end $$;
do $$ begin alter table job_applications add constraint fk_job_applications_job_opening_id foreign key (job_opening_id) references job_openings(id); exception when duplicate_object then null; end $$;
do $$ begin alter table interviews add constraint fk_interviews_job_application_id foreign key (job_application_id) references job_applications(id); exception when duplicate_object then null; end $$;
do $$ begin alter table job_offers add constraint fk_job_offers_job_application_id foreign key (job_application_id) references job_applications(id); exception when duplicate_object then null; end $$;
do $$ begin alter table interview_feedback add constraint fk_interview_feedback_interview_id foreign key (interview_id) references interviews(id); exception when duplicate_object then null; end $$;
do $$ begin alter table interview_feedback add constraint fk_interview_feedback_interviewer_user_id foreign key (interviewer_user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table products add constraint fk_products_owner_employee_id foreign key (owner_employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table products add constraint fk_products_created_by foreign key (created_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table product_teams add constraint fk_product_teams_product_id foreign key (product_id) references products(id); exception when duplicate_object then null; end $$;
do $$ begin alter table product_teams add constraint fk_product_teams_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table projects add constraint fk_projects_product_id foreign key (product_id) references products(id); exception when duplicate_object then null; end $$;
do $$ begin alter table projects add constraint fk_projects_owner_employee_id foreign key (owner_employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table projects add constraint fk_projects_created_by foreign key (created_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table project_members add constraint fk_project_members_project_id foreign key (project_id) references projects(id); exception when duplicate_object then null; end $$;
do $$ begin alter table project_members add constraint fk_project_members_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table milestones add constraint fk_milestones_project_id foreign key (project_id) references projects(id); exception when duplicate_object then null; end $$;
do $$ begin alter table tasks add constraint fk_tasks_project_id foreign key (project_id) references projects(id); exception when duplicate_object then null; end $$;
do $$ begin alter table tasks add constraint fk_tasks_milestone_id foreign key (milestone_id) references milestones(id); exception when duplicate_object then null; end $$;
do $$ begin alter table tasks add constraint fk_tasks_created_by foreign key (created_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table task_assignments add constraint fk_task_assignments_task_id foreign key (task_id) references tasks(id); exception when duplicate_object then null; end $$;
do $$ begin alter table task_assignments add constraint fk_task_assignments_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table task_assignments add constraint fk_task_assignments_assigned_by foreign key (assigned_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_requests add constraint fk_leave_requests_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_requests add constraint fk_leave_requests_leave_policy_id foreign key (leave_policy_id) references leave_policies(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_balances add constraint fk_leave_balances_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_balances add constraint fk_leave_balances_leave_policy_id foreign key (leave_policy_id) references leave_policies(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_approvals add constraint fk_leave_approvals_leave_request_id foreign key (leave_request_id) references leave_requests(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_approvals add constraint fk_leave_approvals_approver_user_id foreign key (approver_user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_balance_transactions add constraint fk_leave_balance_transactions_leave_balance_id foreign key (leave_balance_id) references leave_balances(id); exception when duplicate_object then null; end $$;
do $$ begin alter table leave_balance_transactions add constraint fk_leave_balance_transactions_leave_request_id foreign key (leave_request_id) references leave_requests(id); exception when duplicate_object then null; end $$;
do $$ begin alter table attendance_records add constraint fk_attendance_records_employee_id foreign key (employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table attendance_adjustments add constraint fk_attendance_adjustments_attendance_record_id foreign key (attendance_record_id) references attendance_records(id); exception when duplicate_object then null; end $$;
do $$ begin alter table attendance_adjustments add constraint fk_attendance_adjustments_requested_by foreign key (requested_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table attendance_adjustments add constraint fk_attendance_adjustments_reviewed_by foreign key (reviewed_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table support_tickets add constraint fk_support_tickets_requester_employee_id foreign key (requester_employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table support_tickets add constraint fk_support_tickets_category_id foreign key (category_id) references support_categories(id); exception when duplicate_object then null; end $$;
do $$ begin alter table support_tickets add constraint fk_support_tickets_assigned_to_user_id foreign key (assigned_to_user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table support_ticket_comments add constraint fk_support_ticket_comments_ticket_id foreign key (ticket_id) references support_tickets(id); exception when duplicate_object then null; end $$;
do $$ begin alter table support_ticket_comments add constraint fk_support_ticket_comments_author_user_id foreign key (author_user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table announcements add constraint fk_announcements_created_by foreign key (created_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table announcement_targets add constraint fk_announcement_targets_announcement_id foreign key (announcement_id) references announcements(id); exception when duplicate_object then null; end $$;
do $$ begin alter table announcement_targets add constraint fk_announcement_targets_department_id foreign key (department_id) references departments(id); exception when duplicate_object then null; end $$;
do $$ begin alter table notifications add constraint fk_notifications_recipient_user_id foreign key (recipient_user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table documents add constraint fk_documents_owner_employee_id foreign key (owner_employee_id) references employees(id); exception when duplicate_object then null; end $$;
do $$ begin alter table documents add constraint fk_documents_uploaded_by foreign key (uploaded_by) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table document_permissions add constraint fk_document_permissions_document_id foreign key (document_id) references documents(id); exception when duplicate_object then null; end $$;
do $$ begin alter table document_permissions add constraint fk_document_permissions_user_id foreign key (user_id) references users(id); exception when duplicate_object then null; end $$;
do $$ begin alter table document_permissions add constraint fk_document_permissions_role_id foreign key (role_id) references roles(id); exception when duplicate_object then null; end $$;
do $$ begin alter table document_permissions add constraint fk_document_permissions_granted_by foreign key (granted_by) references users(id); exception when duplicate_object then null; end $$;

-- Indexes on foreign keys
create index if not exists idx_password_reset_tokens_user_id on password_reset_tokens(user_id);
create index if not exists idx_mfa_credentials_user_id on mfa_credentials(user_id);
create index if not exists idx_sessions_user_id on sessions(user_id);
create index if not exists idx_role_permissions_role_id on role_permissions(role_id);
create index if not exists idx_role_permissions_permission_id on role_permissions(permission_id);
create index if not exists idx_user_roles_user_id on user_roles(user_id);
create index if not exists idx_user_roles_role_id on user_roles(role_id);
create index if not exists idx_user_roles_department_id on user_roles(department_id);
create index if not exists idx_user_roles_project_id on user_roles(project_id);
create index if not exists idx_user_roles_assigned_by on user_roles(assigned_by);
create index if not exists idx_departments_parent_department_id on departments(parent_department_id);
create index if not exists idx_employees_user_id on employees(user_id);
create index if not exists idx_employees_department_id on employees(department_id);
create index if not exists idx_employees_designation_id on employees(designation_id);
create index if not exists idx_reporting_relationships_employee_id on reporting_relationships(employee_id);
create index if not exists idx_reporting_relationships_manager_employee_id on reporting_relationships(manager_employee_id);
create index if not exists idx_employment_records_employee_id on employment_records(employee_id);
create index if not exists idx_employment_records_department_id on employment_records(department_id);
create index if not exists idx_employment_records_designation_id on employment_records(designation_id);
create index if not exists idx_platform_settings_updated_by on platform_settings(updated_by);
create index if not exists idx_audit_logs_actor_user_id on audit_logs(actor_user_id);
create index if not exists idx_job_openings_department_id on job_openings(department_id);
create index if not exists idx_job_openings_created_by on job_openings(created_by);
create index if not exists idx_job_openings_hiring_manager_id on job_openings(hiring_manager_id);
create index if not exists idx_candidate_documents_candidate_id on candidate_documents(candidate_id);
create index if not exists idx_job_applications_candidate_id on job_applications(candidate_id);
create index if not exists idx_job_applications_job_opening_id on job_applications(job_opening_id);
create index if not exists idx_interviews_job_application_id on interviews(job_application_id);
create index if not exists idx_job_offers_job_application_id on job_offers(job_application_id);
create index if not exists idx_interview_feedback_interview_id on interview_feedback(interview_id);
create index if not exists idx_interview_feedback_interviewer_user_id on interview_feedback(interviewer_user_id);
create index if not exists idx_products_owner_employee_id on products(owner_employee_id);
create index if not exists idx_products_created_by on products(created_by);
create index if not exists idx_product_teams_product_id on product_teams(product_id);
create index if not exists idx_product_teams_employee_id on product_teams(employee_id);
create index if not exists idx_projects_product_id on projects(product_id);
create index if not exists idx_projects_owner_employee_id on projects(owner_employee_id);
create index if not exists idx_projects_created_by on projects(created_by);
create index if not exists idx_project_members_project_id on project_members(project_id);
create index if not exists idx_project_members_employee_id on project_members(employee_id);
create index if not exists idx_milestones_project_id on milestones(project_id);
create index if not exists idx_tasks_project_id on tasks(project_id);
create index if not exists idx_tasks_milestone_id on tasks(milestone_id);
create index if not exists idx_tasks_created_by on tasks(created_by);
create index if not exists idx_task_assignments_task_id on task_assignments(task_id);
create index if not exists idx_task_assignments_employee_id on task_assignments(employee_id);
create index if not exists idx_task_assignments_assigned_by on task_assignments(assigned_by);
create index if not exists idx_leave_requests_employee_id on leave_requests(employee_id);
create index if not exists idx_leave_requests_leave_policy_id on leave_requests(leave_policy_id);
create index if not exists idx_leave_balances_employee_id on leave_balances(employee_id);
create index if not exists idx_leave_balances_leave_policy_id on leave_balances(leave_policy_id);
create index if not exists idx_leave_approvals_leave_request_id on leave_approvals(leave_request_id);
create index if not exists idx_leave_approvals_approver_user_id on leave_approvals(approver_user_id);
create index if not exists idx_leave_balance_transactions_leave_balance_id on leave_balance_transactions(leave_balance_id);
create index if not exists idx_leave_balance_transactions_leave_request_id on leave_balance_transactions(leave_request_id);
create index if not exists idx_attendance_records_employee_id on attendance_records(employee_id);
create index if not exists idx_attendance_adjustments_attendance_record_id on attendance_adjustments(attendance_record_id);
create index if not exists idx_attendance_adjustments_requested_by on attendance_adjustments(requested_by);
create index if not exists idx_attendance_adjustments_reviewed_by on attendance_adjustments(reviewed_by);
create index if not exists idx_support_tickets_requester_employee_id on support_tickets(requester_employee_id);
create index if not exists idx_support_tickets_category_id on support_tickets(category_id);
create index if not exists idx_support_tickets_assigned_to_user_id on support_tickets(assigned_to_user_id);
create index if not exists idx_support_ticket_comments_ticket_id on support_ticket_comments(ticket_id);
create index if not exists idx_support_ticket_comments_author_user_id on support_ticket_comments(author_user_id);
create index if not exists idx_announcements_created_by on announcements(created_by);
create index if not exists idx_announcement_targets_announcement_id on announcement_targets(announcement_id);
create index if not exists idx_announcement_targets_department_id on announcement_targets(department_id);
create index if not exists idx_notifications_recipient_user_id on notifications(recipient_user_id);
create index if not exists idx_documents_owner_employee_id on documents(owner_employee_id);
create index if not exists idx_documents_uploaded_by on documents(uploaded_by);
create index if not exists idx_document_permissions_document_id on document_permissions(document_id);
create index if not exists idx_document_permissions_user_id on document_permissions(user_id);
create index if not exists idx_document_permissions_role_id on document_permissions(role_id);
create index if not exists idx_document_permissions_granted_by on document_permissions(granted_by);

-- Business constraints
create unique index if not exists uq_attendance_employee_day on attendance_records(employee_id, attendance_date);
create unique index if not exists uq_leave_balance on leave_balances(employee_id, leave_policy_id, leave_year);
create unique index if not exists uq_role_permission on role_permissions(role_id, permission_id);
create unique index if not exists uq_employee_user on employees(user_id) where user_id is not null;
create unique index if not exists uq_user_email_lower on users(lower(email));
create index if not exists idx_notifications_unread on notifications(recipient_user_id) where read_at is null;
create index if not exists idx_audit_logs_created on audit_logs(created_at desc);
