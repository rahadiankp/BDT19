# Replication & ProxySQL Config
## Replication
1. Create user for replication
    ```
    mysql -p < config_replication.sql
    ```
2. Install `GROUP REPLICATION`  plugin
    ```
    mysql -p < install_replication_plugin.sql
    ```
3. Start replication. Run this after after every restart  
    a. On bootstrap node
    ```
    mysql -p < start_replication_bootstrap.sql
    ```
    b. On other nodes
    ```
    mysql -p < start_replication.sql
    ```
## ProxySQL
1. Run config for enabling MySQL server to be monitored by ProxySQL
    ```
    mysql -p < addition_to_sys.sql
    ```
2. Create `monitor` user
    ```
    mysql -p < create_proxysql_user.sql
    ```
