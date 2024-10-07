FROM postgres:16.4-bullseye

COPY init-scripts/* /docker-entrypoint-initdb.d/