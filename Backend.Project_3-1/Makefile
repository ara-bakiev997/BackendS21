copy_nginx: 
	docker cp distribution/nginx/nginx.conf nginx:/etc/nginx/nginx.conf

reload_nginx:
	docker exec nginx nginx -s reload

exec_nginx:
	docker exec -it nginx bash

build:
	docker compose build

up:
	docker compose up -d

down:
	docker compose down


.PHONY: all, copy_nginx, exec_nginx, build, up, down