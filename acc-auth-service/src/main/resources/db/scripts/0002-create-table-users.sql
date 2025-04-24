create table users
(
    id bigserial NOT NULL,
    login varchar(255) NOT NULL,
    CONSTRAINT pk_users_id PRIMARY KEY (id),
    CONSTRAINT uc_users_login UNIQUE (login)
);
