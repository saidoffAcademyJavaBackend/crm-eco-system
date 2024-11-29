CREATE TABLE attachment
(
    id                 UUID    NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    created_by         UUID,
    updated_by         UUID,
    deleted            BOOLEAN NOT NULL,
    name               VARCHAR(255),
    file_original_name VARCHAR(255),
    size               BIGINT  NOT NULL,
    content_type       VARCHAR(255),
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

CREATE TABLE attachment_content
(
    id            UUID    NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    created_by    UUID,
    updated_by    UUID,
    deleted       BOOLEAN NOT NULL,
    main_content  OID,
    attachment_id UUID,
    CONSTRAINT pk_attachmentcontent PRIMARY KEY (id)
);

CREATE TABLE balance
(
    id         UUID    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    deleted    BOOLEAN NOT NULL,
    amount     DOUBLE PRECISION,
    currency   VARCHAR(255),
    CONSTRAINT pk_balance PRIMARY KEY (id)
);

CREATE TABLE category
(
    id          UUID    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    deleted     BOOLEAN NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    is_income   BOOLEAN NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE comment_task
(
    id               UUID    NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       UUID,
    updated_by       UUID,
    deleted          BOOLEAN NOT NULL,
    comment          VARCHAR(255),
    task_id          UUID    NOT NULL,
    user_id          UUID    NOT NULL,
    reply_comment_id UUID,
    CONSTRAINT pk_commenttask PRIMARY KEY (id)
);

CREATE TABLE file_entity
(
    id         UUID    NOT NULL,
    file_name  VARCHAR(255),
    file_type  VARCHAR(255),
    file_size  BIGINT,
    imagesdata OID,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by UUID,
    deleted    BOOLEAN NOT NULL,
    CONSTRAINT pk_fileentity PRIMARY KEY (id)
);

CREATE TABLE group_student
(
    id            UUID    NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    created_by    UUID,
    updated_by    UUID,
    deleted       BOOLEAN NOT NULL,
    group_id_id   UUID,
    student_id_id UUID,
    CONSTRAINT pk_groupstudent PRIMARY KEY (id)
);

CREATE TABLE group_type
(
    id          UUID         NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    deleted     BOOLEAN      NOT NULL,
    specialty   VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_grouptype PRIMARY KEY (id)
);

CREATE TABLE groups
(
    id                UUID    NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        UUID,
    updated_by        UUID,
    deleted           BOOLEAN NOT NULL,
    name              VARCHAR(255),
    teacher_id        UUID,
    start_time        time WITHOUT TIME ZONE,
    end_time          time WITHOUT TIME ZONE,
    active            BOOLEAN NOT NULL,
    link_for_telegram VARCHAR(255),
    group_type_id     UUID,
    started_date      date,
    payment_amount    DOUBLE PRECISION,
    student           BOOLEAN NOT NULL,
    room              VARCHAR(255),
    CONSTRAINT pk_groups PRIMARY KEY (id)
);

CREATE TABLE groups_week_days
(
    groups_id UUID NOT NULL,
    week_days VARCHAR(255)
);

CREATE TABLE news
(
    id            UUID    NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    created_by    UUID,
    updated_by    UUID,
    deleted       BOOLEAN NOT NULL,
    title         VARCHAR(255),
    content       VARCHAR(255),
    attachment_id UUID,
    CONSTRAINT pk_news PRIMARY KEY (id)
);

CREATE TABLE news_roles
(
    news_id  UUID NOT NULL,
    roles_id UUID NOT NULL
);

CREATE TABLE notification
(
    id          UUID    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    deleted     BOOLEAN NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(255),
    object_id   UUID,
    user_id     UUID,
    read        BOOLEAN,
    type        VARCHAR(255),
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

CREATE TABLE payment_for_month
(
    id               UUID    NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       UUID,
    updated_by       UUID,
    deleted          BOOLEAN NOT NULL,
    group_student_id UUID,
    month            date,
    payment_amount   DOUBLE PRECISION,
    status           SMALLINT,
    active           BOOLEAN NOT NULL,
    current_month    BOOLEAN NOT NULL,
    CONSTRAINT pk_paymentformonth PRIMARY KEY (id)
);

CREATE TABLE project
(
    id         UUID    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    deleted    BOOLEAN NOT NULL,
    name       VARCHAR(255),
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_project PRIMARY KEY (id)
);

CREATE TABLE project_project_users
(
    project_id       UUID NOT NULL,
    project_users_id UUID NOT NULL
);

CREATE TABLE project_user
(
    id         UUID    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    deleted    BOOLEAN NOT NULL,
    user_id    UUID    NOT NULL,
    project_id UUID    NOT NULL,
    CONSTRAINT pk_projectuser PRIMARY KEY (id)
);

CREATE TABLE role
(
    id         UUID    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    deleted    BOOLEAN NOT NULL,
    name       VARCHAR(255),
    role_type  VARCHAR(255),
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE room_count_equipment
(
    id                UUID    NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        UUID,
    updated_by        UUID,
    deleted           BOOLEAN NOT NULL,
    count             INTEGER,
    room_equipment_id UUID,
    CONSTRAINT pk_roomcountequipment PRIMARY KEY (id)
);

CREATE TABLE room_equipment
(
    id           UUID    NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    created_by   UUID,
    updated_by   UUID,
    deleted      BOOLEAN NOT NULL,
    name         VARCHAR(255),
    total_number INTEGER,
    CONSTRAINT pk_roomequipment PRIMARY KEY (id)
);

CREATE TABLE rooms
(
    id                    UUID    NOT NULL,
    created_at            TIMESTAMP WITHOUT TIME ZONE,
    updated_at            TIMESTAMP WITHOUT TIME ZONE,
    created_by            UUID,
    updated_by            UUID,
    deleted               BOOLEAN NOT NULL,
    room_name             VARCHAR(255),
    capacity              INTEGER NOT NULL,
    comment               VARCHAR(255),
    room_type             VARCHAR(255),
    group_id              UUID,
    responsible_person_id UUID,
    CONSTRAINT pk_rooms PRIMARY KEY (id)
);

CREATE TABLE speciality
(
    id          UUID    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    deleted     BOOLEAN NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_speciality PRIMARY KEY (id)
);

CREATE TABLE stage
(
    id          UUID    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    deleted     BOOLEAN NOT NULL,
    name        VARCHAR(255),
    stage_order INTEGER,
    done        BOOLEAN NOT NULL,
    project_id  UUID,
    CONSTRAINT pk_stage PRIMARY KEY (id)
);

CREATE TABLE task
(
    id          UUID    NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    deleted     BOOLEAN NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(255),
    deadline    TIMESTAMP WITHOUT TIME ZONE,
    stage_id    UUID,
    task_order  INTEGER,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE task_comment
(
    id         UUID    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    deleted    BOOLEAN NOT NULL,
    text       VARCHAR(255),
    user_id    UUID,
    task_id    UUID,
    replay_id  UUID,
    CONSTRAINT pk_taskcomment PRIMARY KEY (id)
);

CREATE TABLE task_user
(
    id         UUID    NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    deleted    BOOLEAN NOT NULL,
    user_id    UUID    NOT NULL,
    task_id    UUID    NOT NULL,
    CONSTRAINT pk_taskuser PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id            UUID    NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    created_by    UUID,
    updated_by    UUID,
    deleted       BOOLEAN NOT NULL,
    amount        DOUBLE PRECISION,
    description   VARCHAR(255),
    category_id   UUID,
    currency      SMALLINT,
    is_income     BOOLEAN,
    attachment_id UUID,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                                             UUID    NOT NULL,
    created_at                                     TIMESTAMP WITHOUT TIME ZONE,
    updated_at                                     TIMESTAMP WITHOUT TIME ZONE,
    created_by                                     UUID,
    updated_by                                     UUID,
    deleted                                        BOOLEAN NOT NULL,
    attachment_id                                  UUID,
    password                                       VARCHAR(255),
    first_name                                     VARCHAR(255),
    last_name                                      VARCHAR(255),
    father_name                                    VARCHAR(255),
    phone_number                                   VARCHAR(255),
    second_phone_number                            VARCHAR(255),
    birth_date                                     date,
    birth_place                                    VARCHAR(255),
    current_residence                              VARCHAR(255),
    number_of_children                             VARCHAR(255),
    gender                                         BOOLEAN,
    speciality_id                                  UUID,
    passport_series                                VARCHAR(255),
    salary                                         DOUBLE PRECISION,
    start_work                                     date,
    start_studying                                 date,
    warning                                        INTEGER,
    role_id                                        UUID    NOT NULL,
    enabled                                        BOOLEAN NOT NULL,
    account_non_expired                            BOOLEAN NOT NULL,
    account_non_locked                             BOOLEAN NOT NULL,
    credentials_non_expired                        BOOLEAN NOT NULL,
    account_non_expired_or_credentials_non_expired BOOLEAN NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_notifications
(
    notifications_id UUID NOT NULL,
    users_id         UUID NOT NULL
);

CREATE TABLE users_permissions
(
    users_id    UUID NOT NULL,
    permissions VARCHAR(255)
);

CREATE TABLE users_project_users
(
    project_users_id UUID NOT NULL,
    users_id         UUID NOT NULL
);

CREATE TABLE warnings
(
    id            UUID    NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    created_by    UUID,
    updated_by    UUID,
    deleted       BOOLEAN NOT NULL,
    reason        VARCHAR(255),
    warning_count INTEGER NOT NULL,
    punishment    BOOLEAN NOT NULL,
    user_id       UUID,
    CONSTRAINT pk_warnings PRIMARY KEY (id)
);

ALTER TABLE group_type
    ADD CONSTRAINT uc_grouptype_specialty UNIQUE (specialty);

ALTER TABLE project_project_users
    ADD CONSTRAINT uc_project_project_users_projectusers UNIQUE (project_users_id);

ALTER TABLE rooms
    ADD CONSTRAINT uc_rooms_roomname UNIQUE (room_name);

ALTER TABLE users_notifications
    ADD CONSTRAINT uc_users_notifications_notifications UNIQUE (notifications_id);

ALTER TABLE users_project_users
    ADD CONSTRAINT uc_users_project_users_projectusers UNIQUE (project_users_id);

ALTER TABLE attachment_content
    ADD CONSTRAINT FK_ATTACHMENTCONTENT_ON_ATTACHMENT FOREIGN KEY (attachment_id) REFERENCES attachment (id) ON DELETE CASCADE;

ALTER TABLE comment_task
    ADD CONSTRAINT FK_COMMENTTASK_ON_REPLYCOMMENT FOREIGN KEY (reply_comment_id) REFERENCES comment_task (id);

ALTER TABLE comment_task
    ADD CONSTRAINT FK_COMMENTTASK_ON_TASK FOREIGN KEY (task_id) REFERENCES task (id);

ALTER TABLE comment_task
    ADD CONSTRAINT FK_COMMENTTASK_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE group_student
    ADD CONSTRAINT FK_GROUPSTUDENT_ON_GROUPID FOREIGN KEY (group_id_id) REFERENCES groups (id);

ALTER TABLE group_student
    ADD CONSTRAINT FK_GROUPSTUDENT_ON_STUDENTID FOREIGN KEY (student_id_id) REFERENCES users (id);

ALTER TABLE groups
    ADD CONSTRAINT FK_GROUPS_ON_GROUPTYPE FOREIGN KEY (group_type_id) REFERENCES group_type (id);

ALTER TABLE groups
    ADD CONSTRAINT FK_GROUPS_ON_TEACHER FOREIGN KEY (teacher_id) REFERENCES users (id);

ALTER TABLE news
    ADD CONSTRAINT FK_NEWS_ON_ATTACHMENT FOREIGN KEY (attachment_id) REFERENCES attachment (id);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE project_user
    ADD CONSTRAINT FK_PROJECTUSER_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE project_user
    ADD CONSTRAINT FK_PROJECTUSER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE room_count_equipment
    ADD CONSTRAINT FK_ROOMCOUNTEQUIPMENT_ON_ROOMEQUIPMENT FOREIGN KEY (room_equipment_id) REFERENCES room_equipment (id);

ALTER TABLE rooms
    ADD CONSTRAINT FK_ROOMS_ON_GROUP FOREIGN KEY (group_id) REFERENCES groups (id);

ALTER TABLE rooms
    ADD CONSTRAINT FK_ROOMS_ON_RESPONSIBLE_PERSON FOREIGN KEY (responsible_person_id) REFERENCES users (id);

ALTER TABLE stage
    ADD CONSTRAINT FK_STAGE_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE task_user
    ADD CONSTRAINT FK_TASKUSER_ON_TASK FOREIGN KEY (task_id) REFERENCES task (id);

ALTER TABLE task_user
    ADD CONSTRAINT FK_TASKUSER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_STAGE FOREIGN KEY (stage_id) REFERENCES stage (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_ATTACHMENT FOREIGN KEY (attachment_id) REFERENCES attachment (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ATTACHMENT FOREIGN KEY (attachment_id) REFERENCES attachment (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_SPECIALITY FOREIGN KEY (speciality_id) REFERENCES speciality (id);

ALTER TABLE warnings
    ADD CONSTRAINT FK_WARNINGS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE groups_week_days
    ADD CONSTRAINT fk_groups_weekdays_on_group FOREIGN KEY (groups_id) REFERENCES groups (id);

ALTER TABLE news_roles
    ADD CONSTRAINT fk_newrol_on_news FOREIGN KEY (news_id) REFERENCES news (id);

ALTER TABLE news_roles
    ADD CONSTRAINT fk_newrol_on_role FOREIGN KEY (roles_id) REFERENCES role (id);

ALTER TABLE project_project_users
    ADD CONSTRAINT fk_proprouse_on_project FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE project_project_users
    ADD CONSTRAINT fk_proprouse_on_project_user FOREIGN KEY (project_users_id) REFERENCES project_user (id);

ALTER TABLE users_notifications
    ADD CONSTRAINT fk_usenot_on_notification FOREIGN KEY (notifications_id) REFERENCES notification (id);

ALTER TABLE users_notifications
    ADD CONSTRAINT fk_usenot_on_user FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE users_project_users
    ADD CONSTRAINT fk_useprouse_on_project_user FOREIGN KEY (project_users_id) REFERENCES project_user (id);

ALTER TABLE users_project_users
    ADD CONSTRAINT fk_useprouse_on_user FOREIGN KEY (users_id) REFERENCES users (id);

ALTER TABLE users_permissions
    ADD CONSTRAINT fk_users_permissions_on_user FOREIGN KEY (users_id) REFERENCES users (id);