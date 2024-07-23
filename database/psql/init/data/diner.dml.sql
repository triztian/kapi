insert into diner values
    (1, 'Michael', '+161935482341'),
    (2, 'George Michael', '+14086377372'),
    (3, 'Lucile', '+14786327373'),
    (4, 'Gob', '+14086327574'),
    (5, 'Tobias', '+14086087375'),
    (6, 'Maeby', '+14886327376')
;

insert into dietary_restriction values
    (1, 'Vegetarian', null),
    (2, 'Vegan', null),
    (3, 'Paleo', null),
    (4, 'Gluten-Free', null)
;

insert into diner_dietary_restriction values
    (1, 1), -- Michael, Vegetarian
    (2, 1), -- G. Michael, Vegetarian
    (2, 4), -- G. Michael, Gluten-Free
    (3, 4), -- Lucile, Gluten-Free
    (4, 3), -- Gob, Paleo
    (6, 2)  -- Maeby, Vegan
;