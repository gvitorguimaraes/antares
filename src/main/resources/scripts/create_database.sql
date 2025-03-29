
do $$
begin


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
        role                        varchar(2)      not null,
        last_login_data             timestamp
    );

    create table if not exists universe
    (
        id                          UUID            primary key default gen_random_uuid(),
        inclusion                   timestamp		not null,
        exclusion                   timestamp,
        id_user                     UUID,

        constraint fk_universe_user foreign key (id_user) references app_user (id)
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

    create table if not exists task
    (
        id                          UUID            primary key default gen_random_uuid(),
        inclusion                   timestamp		not null,
        exclusion                   timestamp,
        title                       varchar(500)    not null,
        description                 varchar(5000)   not null,
        status                      varchar(2)      not null,
        end_date                    timestamp,
        id_galaxy                   UUID,

        constraint fk_task_galaxy foreign key (id_galaxy) references Galaxy (id)
    );

    create table if not exists note
    (
        id                          UUID            primary key default gen_random_uuid(),
        inclusion                   timestamp		not null,
        exclusion                   timestamp,
        title                       varchar(500)    not null,
        description                 varchar(10000),
        id_galaxy                   UUID,

        constraint fk_note_galaxy foreign key (id_galaxy) references Galaxy (id)
    );

    create table if not exists userActivityLog
    (
        id                          UUID            primary key default gen_random_uuid(),
        inclusion                   timestamp		not null,
        exclusion                   timestamp,
        description                 varchar(2000),
        time_reg                    numeric(10,2),
        date_activity               timestamp       not null,
        showInTimeline              boolean         not null,
        id_user                     UUID,
        id_task                     UUID,
        id_note                     UUID,

        constraint fk_userActivityLog_user          foreign key (id_user)           references app_user (id),
        constraint fk_userActivityLog_task          foreign key (id_task)           references task (id),
        constraint fk_userActivityLog_note          foreign key (id_note)           references note (id)
    );

end $$;