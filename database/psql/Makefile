.PHONY: clean-data init model data

init:
	psql postgres -f init/db.ddl.sql

model:
	psql -U kapi reserva -q -f init/model/diner.ddl.sql
	psql -U kapi reserva -q -f init/model/tables.ddl.sql
	psql -U kapi reserva -q -f init/model/restaurant.ddl.sql
	psql -U kapi reserva -q -f init/model/reservation.ddl.sql

data:
	find init/data -iname "*.sql" \
        | xargs printf -- ' -f %s' \
        | xargs -t psql -U kapi reserva -q

clean-data:
	dropdb reserva