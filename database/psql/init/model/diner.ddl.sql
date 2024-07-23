/**
 * A patron or person dining at a restaurant.
 */
create table if not exists diner (
    id serial primary key,
    -- longest name is german, 666 chars
    -- https://en.wikipedia.org/wiki/Hubert_Blaine_Wolfeschlegelsteinhausenbergerdorff_Sr.
    -- might not be as much but adding a bit of buffer, could likely be compacted over time.
    name varchar(200) not null,
    phone_number_e164 varchar(16) not null
);

/**
 * Dietary restrictions.
 */
create table if not exists dietary_restriction (
    id serial primary key,
    name varchar(50) unique not null,
    description varchar(500)
);

/**
 * Dietary restrictions recorded for diners, any entry is an ingredient that
 * should not be served to a diner.
 *
 * This is  "lookup" table and simply joins diners and dietary restrictions.
 */
create table if not exists diner_dietary_restriction (
    diner_id integer not null,
    dietary_restriction_id integer not null,
    constraint fk_diner_id foreign key (diner_id)
        references diner (id)
            on delete cascade
            on update cascade,
    constraint fk_dietary_restriction_id foreign key (dietary_restriction_id)
        references dietary_restriction (id)
            on delete cascade
            on update cascade
);
