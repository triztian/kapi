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
    (1, 1, 1, 2),
    (2, 1, 2, 2),
    (3, 1, 3, 2),
    (4, 1, 4, 2),
    (5, 1, 5, 4),
    (6, 1, 6, 4),
    (7, 1, 7, 6),

    -- Panaderia Rosetta
    (8, 2, 1, 2),
    (9, 2, 2, 2),
    (10, 2, 3, 2),
    (11, 2, 4, 4),
    (12, 2, 5, 4),

    -- Tetetlan
    (14, 3, 1, 2),
    (15, 3, 2, 2),
    (16, 3, 3, 2),
    (17, 3, 4, 2),
    (18, 3, 5, 4),
    (19, 3, 6, 4),
    (20, 3, 7, 6),

    -- Falling Piano
    (21, 4, 1, 2),
    (22, 4, 2, 2),
    (23, 4, 3, 2),
    (24, 4, 4, 2),
    (25, 4, 5, 2),
    (26, 4, 6, 4),
    (27, 4, 7, 4),
    (28, 4, 8, 4),
    (29, 4, 9, 4),
    (30, 4, 10, 6),
    (31, 4, 11, 6),
    (32, 4, 12, 6),
    (33, 4, 13, 6),
    (34, 4, 14, 6),
    (35, 4, 15, 6),

    -- Utopia
    (36, 5, 1, 2),
    (37, 5, 2, 2)
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