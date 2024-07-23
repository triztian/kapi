.PHONY: clean-data init model data

init:
	psql postgres -f init/db.ddl.sql

model:
	find init/model -iname "*.sql" \
        | xargs printf -- ' -f %s' \
        | xargs -t psql -U kapi reserva -q
data:
	find init/data -iname "*.sql" \
        | xargs printf -- ' -f %s' \
        | xargs -t psql -U kapi reserva -q

clean-data:
	dropdb reserva