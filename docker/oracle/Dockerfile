FROM  gvenzl/oracle-xe:21.3.0-faststart

# RUN echo "DISABLE_OOB=ON" >> /opt/oracle/product/19c/dbhome_1/network/admin/sqlnet.ora

COPY --chown=oracle:oracle ./scripts /container-entrypoint-initdb.d/setup

COPY --chown=oracle:oracle ./dump /dump/