-- Insert roles
INSERT INTO roles (id, name)
VALUES ('537c3ac7-df1e-4d76-bd76-010585792c16', 'USER'),
       ('1c33707f-d50a-4bd0-be4c-b16e261e13e0', 'MEMBER');

-- Insert users
INSERT INTO users (id, name, username, password, created_at, updated_at)
VALUES ('4c4d1a3f-c81d-4cc8-b372-91660399f019', 'Admin', 'admin', '$2a$12$taIHxzbtFCIRzKtgpB83Je2S45MrQ5XvI65DZEnDrZVnlLZvyV1O.', NOW(), NOW()),
       ('706c9e76-4ace-45c9-b3c3-064d218ec6bc', 'User1', 'user1', '$2a$12$O2mq/PmduyhbZhFR2m65R.7rHn1I8LeBoMsoyzcMGS.Wz1Btvroh.', NOW(), NOW());

-- Insert user_roles
INSERT INTO user_roles (role_id, user_id)
VALUES ('537c3ac7-df1e-4d76-bd76-010585792c16', '4c4d1a3f-c81d-4cc8-b372-91660399f019'),
       ('537c3ac7-df1e-4d76-bd76-010585792c16', '706c9e76-4ace-45c9-b3c3-064d218ec6bc');

-- Insert members
INSERT INTO members (id, name, username, email, password, phone_number, date_of_birth, gender, identity_number, photo_url, address, created_at, updated_at)
VALUES ('6b856a38-2047-4222-a0c7-f9bf508999a2', 'Member1', 'member1', 'member1@example.com', '$2a$12$qpqTAp/2LON/QPIDvQveqOVzn/PlEjky3XaBBofujQJRHLcviPat.', '123456789', '2000-01-01', 'Male', '1234567890', 'http://example.com/photo1.jpg', '123 Street, City', NOW(), NOW()),
       ('8a7fa428-88e5-4737-8c68-83da23fa953e', 'Member2', 'member2', 'member2@example.com', '$2a$12$h124oEVhi67517iQiVLkL.cVzpQUpIJM7lCVqn9ObcRLX8y2k.JQC', '987654321', '2001-02-02', 'Female', '0987654321', 'http://example.com/photo2.jpg', '456 Road, Town', NOW(), NOW());

-- Insert member_roles
INSERT INTO member_roles (member_id, role_id)
VALUES ('6b856a38-2047-4222-a0c7-f9bf508999a2', '1c33707f-d50a-4bd0-be4c-b16e261e13e0'),
       ('8a7fa428-88e5-4737-8c68-83da23fa953e', '1c33707f-d50a-4bd0-be4c-b16e261e13e0');
