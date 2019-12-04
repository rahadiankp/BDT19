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
