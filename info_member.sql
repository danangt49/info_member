-- -------------------------------------------------------------
-- TablePlus 5.9.2(542)
--
-- https://tableplus.com/
--
-- Database: info_member
-- Generation Time: 2024-03-24 22:23:19.8660
-- -------------------------------------------------------------


DROP TABLE IF EXISTS "public"."databasechangelog";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."databasechangelog" (
    "id" varchar(255) NOT NULL,
    "author" varchar(255) NOT NULL,
    "filename" varchar(255) NOT NULL,
    "dateexecuted" timestamp NOT NULL,
    "orderexecuted" int4 NOT NULL,
    "exectype" varchar(10) NOT NULL,
    "md5sum" varchar(35),
    "description" varchar(255),
    "comments" varchar(255),
    "tag" varchar(255),
    "liquibase" varchar(20),
    "contexts" varchar(255),
    "labels" varchar(255),
    "deployment_id" varchar(10)
);

DROP TABLE IF EXISTS "public"."databasechangeloglock";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."databasechangeloglock" (
    "id" int4 NOT NULL,
    "locked" bool NOT NULL,
    "lockgranted" timestamp,
    "lockedby" varchar(255),
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."member_roles";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."member_roles" (
    "member_id" bpchar(36) NOT NULL,
    "role_id" bpchar(36) NOT NULL
);

DROP TABLE IF EXISTS "public"."members";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."members" (
    "id" bpchar(36) NOT NULL,
    "name" varchar(255) NOT NULL,
    "username" varchar(255),
    "email" varchar(255) NOT NULL,
    "password" varchar(255) NOT NULL,
    "phone_number" varchar(255),
    "date_of_birth" timestamp,
    "gender" varchar(255),
    "identity_number" varchar(255),
    "photo_url" varchar(255),
    "address" varchar(255),
    "created_at" timestamp NOT NULL,
    "updated_at" timestamp NOT NULL,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."roles";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."roles" (
    "id" bpchar(36) NOT NULL,
    "name" varchar(255),
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "public"."user_roles";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."user_roles" (
    "role_id" bpchar(36) NOT NULL,
    "user_id" bpchar(36) NOT NULL
);

DROP TABLE IF EXISTS "public"."users";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Table Definition
CREATE TABLE "public"."users" (
    "id" bpchar(36) NOT NULL,
    "name" varchar(255) NOT NULL,
    "username" varchar(255),
    "password" varchar(255) NOT NULL,
    "created_at" timestamp NOT NULL,
    "updated_at" timestamp NOT NULL,
    PRIMARY KEY ("id")
);

INSERT INTO "public"."databasechangelog" ("id", "author", "filename", "dateexecuted", "orderexecuted", "exectype", "md5sum", "description", "comments", "tag", "liquibase", "contexts", "labels", "deployment_id") VALUES
('1', 'danangt49@gmail.com', 'db/changelog/module/table/table.scheme.yaml', '2024-03-24 17:40:44.300893', 1, 'EXECUTED', '9:d41d8cd98f00b204e9800998ecf8427e', 'empty', '', NULL, '4.26.0', NULL, NULL, '1276844276'),
('raw', 'includeAll', 'db/changelog/module/table/ddl/23032024_init_table.sql', '2024-03-24 17:40:44.569519', 2, 'EXECUTED', '9:4d67373b8f111b3db4ec71fdba9f5f8e', 'sql', '', NULL, '4.26.0', NULL, NULL, '1276844276'),
('raw', 'includeAll', 'db/changelog/module/table/dml/23032024_insert_table.sql', '2024-03-24 17:40:44.642532', 3, 'EXECUTED', '9:d068d86fca9bc09be75395b8f7c99b0d', 'sql', '', NULL, '4.26.0', NULL, NULL, '1276844276');

INSERT INTO "public"."databasechangeloglock" ("id", "locked", "lockgranted", "lockedby") VALUES
(1, 'f', NULL, NULL);

INSERT INTO "public"."member_roles" ("member_id", "role_id") VALUES
('6b856a38-2047-4222-a0c7-f9bf508999a2', '1c33707f-d50a-4bd0-be4c-b16e261e13e0'),
('8a7fa428-88e5-4737-8c68-83da23fa953e', '1c33707f-d50a-4bd0-be4c-b16e261e13e0');

INSERT INTO "public"."members" ("id", "name", "username", "email", "password", "phone_number", "date_of_birth", "gender", "identity_number", "photo_url", "address", "created_at", "updated_at") VALUES
('6b856a38-2047-4222-a0c7-f9bf508999a2', 'Member1', 'member1', 'member1@example.com', '$2a$12$qpqTAp/2LON/QPIDvQveqOVzn/PlEjky3XaBBofujQJRHLcviPat.', '123456789', '2000-01-01 00:00:00', 'Male', '1234567890', 'http://example.com/photo1.jpg', '123 Street, City', '2024-03-24 17:40:44.573695', '2024-03-24 17:40:44.573695'),
('8a7fa428-88e5-4737-8c68-83da23fa953e', 'Member2', 'member2', 'member2@example.com', '$2a$12$h124oEVhi67517iQiVLkL.cVzpQUpIJM7lCVqn9ObcRLX8y2k.JQC', '987654321', '2001-02-02 00:00:00', 'Female', '0987654321', 'http://example.com/photo2.jpg', '456 Road, Town', '2024-03-24 17:40:44.573695', '2024-03-24 17:40:44.573695');

INSERT INTO "public"."roles" ("id", "name") VALUES
('1c33707f-d50a-4bd0-be4c-b16e261e13e0', 'MEMBER'),
('537c3ac7-df1e-4d76-bd76-010585792c16', 'USER');

INSERT INTO "public"."user_roles" ("role_id", "user_id") VALUES
('537c3ac7-df1e-4d76-bd76-010585792c16', '4c4d1a3f-c81d-4cc8-b372-91660399f019'),
('537c3ac7-df1e-4d76-bd76-010585792c16', '706c9e76-4ace-45c9-b3c3-064d218ec6bc');

INSERT INTO "public"."users" ("id", "name", "username", "password", "created_at", "updated_at") VALUES
('4c4d1a3f-c81d-4cc8-b372-91660399f019', 'Admin', 'admin', '$2a$12$taIHxzbtFCIRzKtgpB83Je2S45MrQ5XvI65DZEnDrZVnlLZvyV1O.', '2024-03-24 17:40:44.573695', '2024-03-24 17:40:44.573695'),
('706c9e76-4ace-45c9-b3c3-064d218ec6bc', 'User1', 'user1', '$2a$12$O2mq/PmduyhbZhFR2m65R.7rHn1I8LeBoMsoyzcMGS.Wz1Btvroh.', '2024-03-24 17:40:44.573695', '2024-03-24 17:40:44.573695');

ALTER TABLE "public"."member_roles" ADD FOREIGN KEY ("member_id") REFERENCES "public"."members"("id");
ALTER TABLE "public"."member_roles" ADD FOREIGN KEY ("role_id") REFERENCES "public"."roles"("id");
ALTER TABLE "public"."user_roles" ADD FOREIGN KEY ("user_id") REFERENCES "public"."users"("id");
ALTER TABLE "public"."user_roles" ADD FOREIGN KEY ("role_id") REFERENCES "public"."roles"("id");
