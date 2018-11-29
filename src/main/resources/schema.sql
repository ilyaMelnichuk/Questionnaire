CREATE SCHEMA IF NOT EXISTS security AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS security."user"
(
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (email)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE security."user"
    OWNER to postgres;
    
CREATE TABLE IF NOT EXISTS security.role
(
    role_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (role_name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE security.role  OWNER to postgres;
    
CREATE TABLE IF NOT EXISTS security.user_role
(
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id)
        REFERENCES security."user" (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id)
        REFERENCES security.role (role_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE security.user_role
    OWNER to postgres;
    
CREATE TABLE IF NOT EXISTS security.token
(
    token_id bigint NOT NULL,
    expiration_date timestamp without time zone,
    token character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT token_pkey PRIMARY KEY (token_id),
    CONSTRAINT fktq6tin4uayw3fwhspdukad20p FOREIGN KEY (email)
        REFERENCES security."user" (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE security.token
    OWNER to postgres;    
    
CREATE SCHEMA IF NOT EXISTS data AUTHORIZATION postgres; 

CREATE TABLE IF NOT EXISTS data.field
(
    label character varying(255) COLLATE pg_catalog."default" NOT NULL,
    isactive boolean,
    required boolean,
    type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT field_pkey PRIMARY KEY (label)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE data.field
    OWNER to postgres;
    
CREATE TABLE IF NOT EXISTS data.option
(
    id bigint NOT NULL,
    value character varying(255) COLLATE pg_catalog."default",
    field_label character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT option_pkey PRIMARY KEY (field_label, id),
    CONSTRAINT fkn58rhyyw89yq23bq0afetyijd FOREIGN KEY (field_label)
        REFERENCES data.field (label) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE data.option
    OWNER to postgres;
    
CREATE TABLE IF NOT EXISTS data.response
(
    id bigint NOT NULL,
    value character varying(255) COLLATE pg_catalog."default",
    field_label character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT response_pkey PRIMARY KEY (field_label, id),
    CONSTRAINT fksxw863lrt6a17lul39dt40tom FOREIGN KEY (field_label)
        REFERENCES data.field (label) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE data.response
    OWNER to postgres;    