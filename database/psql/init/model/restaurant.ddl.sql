/**
 * Available restaurants.
 */
create table if not exists restaurant (
    id bigserial primary key,
    -- Names do not have to be unique
    name varchar(300) not null
);

/**
 * Endorsements that a restaurant has, these are mapped to restrictions meaning
 * that they'd be able to host a diner with the given restriction.
 */
create table if not exists restaurant_endorsement (
    restaurant_id bigint not null,
    dietary_restriction_id integer not null,
    constraint fk_restaurant_id foreign key (restaurant_id)
        references restaurant (id)
            on delete cascade
            on update cascade,
    constraint fk_dietary_restriction_id foreign key (dietary_restriction_id)
        references dietary_restriction (id)
            on delete cascade
            on update cascade
);