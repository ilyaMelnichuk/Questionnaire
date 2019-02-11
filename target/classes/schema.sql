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
    CONSTRAINT role_name_foreign_key FOREIGN KEY (role_id)
        REFERENCES security.role (role_name) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT user_id_foreign_key FOREIGN KEY (user_id)
        REFERENCES security."user" (email) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
 
ALTER TABLE security.user_role
    OWNER to postgres;
 
CREATE INDEX IF NOT EXISTS fki_user_id_foreign_key
    ON security.user_role USING btree
    (user_id COLLATE pg_catalog."default")
    TABLESPACE pg_default;
CREATE TABLE IF NOT EXISTS security.token
(
    token_id bigint NOT NULL,
    expiration_date timestamp without time zone,
    token character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT token_pkey PRIMARY KEY (token_id),
    CONSTRAINT email_foreign_key FOREIGN KEY (email)
        REFERENCES security."user" (email) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
 
ALTER TABLE security.token
    OWNER to postgres;
 
-- Index: fki_email_foreign_key
 
-- DROP INDEX security.fki_email_foreign_key;
 
CREATE INDEX IF NOT EXISTS fki_email_foreign_key
    ON security.token USING btree
    (email COLLATE pg_catalog."default")
    TABLESPACE pg_default;
CREATE SCHEMA IF NOT EXISTS data AUTHORIZATION postgres;

CREATE SEQUENCE IF NOT EXISTS data.field_id_seq;
CREATE TABLE IF NOT EXISTS data.field
(
    id  bigint NOT NULL default nextval('"data".field_id_seq'),
    label character varying(255) COLLATE pg_catalog."default" NOT NULL,
    isactive boolean,
    required boolean,
    type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT field_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
 
ALTER TABLE data.field
    OWNER to postgres;
ALTER SEQUENCE data.field_id_seq
    OWNED BY data.field.id;	
   
CREATE TABLE IF NOT EXISTS data.option
(
    id bigint NOT NULL,
    value character varying(255) COLLATE pg_catalog."default",
    field_id serial,
    CONSTRAINT option_pkey PRIMARY KEY (field_id, id),
    CONSTRAINT field_foreign_key FOREIGN KEY (field_id)
        REFERENCES data.field (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
 
ALTER TABLE data.option
    OWNER to postgres;
 
-- Index: fki_field_foreign_key
 
-- DROP INDEX data.fki_field_foreign_key;
 
CREATE INDEX IF NOT EXISTS fki_field_foreign_key
    ON data.option USING btree
    (field_id)
    TABLESPACE pg_default;
CREATE SEQUENCE IF NOT EXISTS data.poll_id_seq; 
CREATE TABLE IF NOT EXISTS data.poll
(
  id  bigint NOT NULL default nextval('"data".poll_id_seq'),
  email character varying(255),
  CONSTRAINT poll_pkey PRIMARY KEY (id),
  CONSTRAINT user_foreign_key FOREIGN KEY (email)
      REFERENCES security."user" (email) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE data.poll
  OWNER TO postgres;
ALTER SEQUENCE data.poll_id_seq
  OWNED BY data.poll.id;	
-- Index: data.fki_email_foreign_key

-- DROP INDEX data.fki_email_foreign_key;

CREATE INDEX IF NOT EXISTS fki_user_foreign_key
  ON data.poll
  USING btree
  (email COLLATE pg_catalog."default");    
CREATE SEQUENCE IF NOT EXISTS data.response_id_seq;
CREATE TABLE IF NOT EXISTS data.response
(
  id  bigint NOT NULL default nextval('"data".response_id_seq'),
  value character varying(255),
  field_id serial NOT NULL,
  poll_id bigint,
  CONSTRAINT response_pkey PRIMARY KEY (field_id, id),
  CONSTRAINT field_id_foreign_key FOREIGN KEY (field_id)
      REFERENCES data.field (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT poll_id_foreign_key FOREIGN KEY (poll_id)
      REFERENCES data.poll (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE data.response
  OWNER TO postgres;
ALTER SEQUENCE data.response_id_seq
OWNED BY data.response.id;	  

-- Index: data.fki_field_id_foreign_key

-- DROP INDEX data.fki_field_id_foreign_key;

CREATE INDEX IF NOT EXISTS fki_field_id_foreign_key
  ON data.response
  USING btree
  (field_id);

-- Index: data.fki_poll_id_foreign_key

-- DROP INDEX data.fki_poll_id_foreign_key;

CREATE INDEX IF NOT EXISTS fki_poll_id_foreign_key
  ON data.response
  USING btree
  (poll_id);


