/**
 * Possible statuses in which a reservation can be found in.
 *
 * NOTES: It may seem like we could make this a table on it's own, however
 */
create type reservation_status as enum (
    'pending', -- Reservation was made but is pending confirmation by the originator, this is the default status
    'confirmed', -- Reservation made and confirmed
    'finalized', -- The patrons arrived, were seated and are eating or have already left
    'rescheduled', -- Reservation was re-scheduled, this will create a new entry
    'cancelled', -- Reservation was cancelled
    'abandoned' -- Reservation lost due to patrons not arriving in the time window
);

/**
 * Reservations.
 */
create table if not exists reservation (
    id bigserial primary key,
    restaurant_id bigint not null,
    datetime timestamp not null,
    status reservation_status not null default 'pending',
    reschedule_reservation_id bigint,
    constraint fk_restaurant_id foreign key (restaurant_id)
        references restaurant (id)
            on update cascade
            on delete restrict
);