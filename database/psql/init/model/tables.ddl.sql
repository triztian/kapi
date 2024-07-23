/**
 * Tables available to host patrons.
 */
create table if not exists tables (
    restaurant_id integer not null,
    table_id integer not null,
    capacity smallint not null default 2
)