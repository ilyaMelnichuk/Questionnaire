INSERT INTO security.role (role_name) VALUES('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO security.role (role_name) VALUES('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO security.role (role_name) VALUES('ROLE_CHANGE_PASSWORD') ON CONFLICT DO NOTHING;
INSERT INTO security."user" (email, first_name, last_name, password, phone_number) VALUES('admin', '', '', '$2a$10$H.eAAMLvgIcscvJghcCn8ukzT0EpeFC6d0SuULXuq7VDJH8c5QNEu', '') ON CONFLICT DO NOTHING; 	
INSERT INTO security.user_role (role_id, user_id) VALUES('ROLE_ADMIN', 'admin') ON CONFLICT DO NOTHING;