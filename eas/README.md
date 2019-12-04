## Architecture
![Architecture](assets/architecture.png)

## How-To Start Containers
1. Build necessary images
```powershell
docker build .\img\ -f .\img\Dockerfile_pd -t bdt19/pd
docker build .\img\ -f .\img\Dockerfile_tidb -t bdt19/tidb
docker build .\img\ -f .\img\Dockerfile_tikv -t bdt19/tikv
docker build .\img\ -f .\img\Dockerfile_prometheus -t bdt19/prometheus
```
2. Deploy Placement Driver (`pd`) containers
```powershell
docker-compose up -d pd1 pd2 pd3
```
3. Deploy TiKV containers
```powershell
docker-compose up -d tikv1 tikv2 tikv3
```
4. Deploy TiDB
```powershell
docker-compose up -d tidb
```
5. Deploy Prometheus
```powershell
docker-compose up -d prometheus
```
6. Deploy Grafana
```powershell
docker-compose up -d grafana
```
7. Test environment is ready
## `docker-compose.yml` Brief Explanation
### Networking
```yaml
networks:
  tidbnet:
    driver: bridge
    ipam:
      config:
        - subnet: 172.80.16.0/28
```
IP Assignments:
- TiDB : `172.80.16.6`
- PD : `172.80.16.{2..4}`
- TiKV : `172.80.16.{8..10}`
- Prometheus : `172.80.16.12`
- Grafana : `172.80.16.14`
### TiDB
```yaml
services:
  ...
  tidb:
    image: bdt19/tidb:latest
    container_name: tidb
    hostname: tidb
    entrypoint: >
      ash -c 
      "/node_exporter & /tidb-server
        --store=tikv
        --path=172.80.16.2:2379,172.80.16.3:2379,172.80.16.4:2379
      "
    ports:
      - 3306:4000
      - 10080:10080
    networks:
      tidbnet:
        ipv4_address: 172.80.16.6
  ...
```
### TiKV
```yaml
services:
  ...
  tikv1:
    image: bdt19/tikv:latest
    container_name: tikv1
    hostname: tikv1
    entrypoint: >
      ash -c
      "/node_exporter & /tikv-server
        --addr=0.0.0.0:20160
        --advertise-addr=172.80.16.8:20160
        --pd=172.80.16.2:2379,172.80.16.3:2379,172.80.16.4:2379
      "
    ports:
      - 20160:20160
    networks:
      tidbnet:
        ipv4_address: 172.80.16.8
  ...
```
### Placement Driver
```yaml
services:
  pd1:
    image: bdt19/pd:latest
    container_name: pd1
    hostname: pd1
    entrypoint: >
      ash -c 
      "/node_exporter & /pd-server
        --name=pd1
        --client-urls=http://0.0.0.0:2379
        --peer-urls=http://0.0.0.0:2380
        --advertise-client-urls=http://172.80.16.2:2379
        --advertise-peer-urls=http://172.80.16.2:2380 
        --initial-cluster=pd1=http://172.80.16.2:2380,pd2=http://172.80.16.3:2380,pd3=http://172.80.16.4:2380
      "
    ports:
      - 2379:2379
    networks:
      tidbnet:
        ipv4_address: 172.80.16.2
  ...
```
### Prometheus
```yaml
services:
  ...
  prometheus:
    image: bdt19/prometheus:latest
    container_name: prometheus
    hostname: prometheus
    networks:
      tidbnet:
        ipv4_address: 172.80.16.12
  ...
```
### Grafana
```yaml
services:
  ...
  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    hostname: grafana
    environment:
      - GF_SERVER_HTTP_PORT=3000
      - GF_SERVER_DOMAIN=172.80.16.14
      - GF_ANALYTICS_CHECK_FOR_UPDATES=true
      - GF_SECURITY_ADMIN_USER=receh
      - GF_SECURITY_ADMIN_PASSWORD=receh
      - GF_LOG_MODE=file
      - GF_LOG_FILE_LEVEL=info
      - GF_LOG_FILE_FORMAT=text
      - GF_DASHBOARDS_JSON_ENABLED=false
      - GF_DASHBOARDS_JSON_PATH=./data/dashboards
      - GF_GRAFANA_NET=https://grafana.net
    ports:
      - 3000:3000
    networks:
      tidbnet:
        ipv4_address: 172.80.16.14
  ...
```
