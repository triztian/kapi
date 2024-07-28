/**
 * Tables available to host patrons.
 */
create table if not exists tables (
    id serial primary key,
    restaurant_id integer not null,
    table_id integer not null, -- name of the table within the restaurant
    capacity smallint not null default 2
)