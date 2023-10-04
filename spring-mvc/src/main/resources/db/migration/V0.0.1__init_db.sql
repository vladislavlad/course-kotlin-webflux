create table users (
    id bigint generated by default as identity primary key,
    first_name varchar(255),
    last_name varchar(255),
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone
);

create table posts (
    id bigint generated by default as identity primary key,
    title varchar(255),
    summary varchar(255),
    content varchar(255),
    published_at timestamp with time zone,
    created_at timestamp with time zone,
    created_by_user_id bigint,
    updated_at timestamp with time zone,
    updated_by_user_id bigint,
    deleted_at timestamp with time zone,
    deleted_by_user_id bigint
);

create table comments (
    id bigint generated by default as identity primary key,
    post_id bigint,
    text varchar(255),
    created_at timestamp with time zone,
    created_by_user_id bigint,
    updated_at timestamp with time zone,
    updated_by_user_id bigint,
    deleted_at timestamp with time zone,
    deleted_by_user_id bigint
);
