/**
 * Create the first well known restaurants
 */
 insert into restaurant values
    (1, 'Lardo'),
    (2, 'Panadería Rosetta'),
    (3, 'Tetetlán'),
    (4, 'Falling Piano Brewing Co'),
    (5, 'u.to.pi.a')
;

insert into tables values
    -- Lardo
    (1, 1, 2),
    (1, 2, 2),
    (1, 3, 2),
    (1, 4, 2),
    (1, 5, 4),
    (1, 6, 4),
    (1, 7, 6),

    -- Panaderia Rosetta
    (2, 1, 2),
    (2, 2, 2),
    (2, 3, 2),
    (2, 4, 4),
    (2, 5, 4),

    -- Tetetlan
    (3, 1, 2),
    (3, 2, 2),
    (3, 3, 2),
    (3, 4, 2),
    (3, 5, 4),
    (3, 6, 4),
    (3, 7, 6),

    -- Falling Piano
    (4, 1, 2),
    (4, 2, 2),
    (4, 3, 2),
    (4, 4, 2),
    (4, 5, 2),
    (4, 6, 4),
    (4, 7, 4),
    (4, 8, 4),
    (4, 9, 4),
    (4, 10, 6),
    (4, 11, 6),
    (4, 12, 6),
    (4, 13, 6),
    (4, 14, 6),
    (4, 15, 6),

    -- Utopia
    (5, 1, 2),
    (5, 2, 2)
;

insert into restaurant_endorsement values
    -- Lardo
    (1, 4), -- Gluten Free

    -- Panaderia Roseta
    (2, 4), -- Gluten Free
    (2, 1), -- Vegetarian
    -- Tetetlan
    (3, 3), -- Paleo
    (3, 4), -- Gluten Free

    -- Piano Falling
    -- No endorsements

    -- Utopia
    (5, 1), -- Veggie
    (5, 2)  -- Vegan
;