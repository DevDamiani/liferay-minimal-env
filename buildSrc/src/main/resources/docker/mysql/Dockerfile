FROM mysql:8.0.22

COPY --chown=mysql:mysql ./dump/. /docker-entrypoint-initdb.d/
COPY --chown=mysql:mysql ./scripts/. /docker-entrypoint-initdb.d/
COPY --chown=mysql:mysql ./fix-client-extension/. /docker-entrypoint-initdb.d/
