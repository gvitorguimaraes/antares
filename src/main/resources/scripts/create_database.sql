create table if not exists app_user
(
    id                          UUID            primary key default gen_random_uuid(),
    inclusion                   timestamp		not null,
    exclusion                   timestamp,
    name                        varchar(200)    not null,
    email                       varchar(200)    not null unique,
    password                    varchar(100)    not null,
    active                      boolean         default true,
    last_edit_data              timestamp,
    role                        varchar(2)      not null
);

create table if not exists universe
(
    id                          UUID            primary key default gen_random_uuid(),
    inclusion                   timestamp		not null,
    exclusion                   timestamp,
    name                        varchar(100)    not null,
    description                 varchar(500)
);

create table if not exists galaxy
(
    id                          UUID            primary key default gen_random_uuid(),
    inclusion                   timestamp		not null,
    exclusion                   timestamp,
    name                        varchar(100)    not null,
    description                 varchar(500),
    id_universe                 UUID,
    constraint fk_galaxy_universe foreign key (id_universe) references Universe (id)
);