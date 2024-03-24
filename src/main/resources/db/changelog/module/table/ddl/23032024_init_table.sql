CREATE TABLE member_roles
(
    member_id CHAR(36) NOT NULL,
    role_id   CHAR(36) NOT NULL
);

CREATE TABLE members
(
    id              CHAR(36)                    NOT NULL,
    name            VARCHAR(255)                NOT NULL,
    username        VARCHAR(255),
    email           VARCHAR(255)                NOT NULL,
    password        VARCHAR(255)                NOT NULL,
    phone_number    VARCHAR(255),
    date_of_birth   TIMESTAMP WITHOUT TIME ZONE,
    gender          VARCHAR(255),
    identity_number VARCHAR(255),
    photo_url       VARCHAR(255),
    address         VARCHAR(255),
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_members PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   CHAR(36) NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL
);

CREATE TABLE users
(
    id         CHAR(36)                    NOT NULL,
    name       VARCHAR(255)                NOT NULL,
    username   VARCHAR(255),
    password   VARCHAR(255)                NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE members
    ADD CONSTRAINT uc_members_email UNIQUE (email);

ALTER TABLE members
    ADD CONSTRAINT uc_members_username UNIQUE (username);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE member_roles
    ADD CONSTRAINT fk_memrol_on_members FOREIGN KEY (member_id) REFERENCES members (id);

ALTER TABLE member_roles
    ADD CONSTRAINT fk_memrol_on_roles FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_roles FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_users FOREIGN KEY (user_id) REFERENCES users (id);