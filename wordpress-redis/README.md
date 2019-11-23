## Architecture
![Architecture](assets/architecture.png)
### Network and IP Assignments
Subnet: `172.69.16.0/27`
- Wordpress (Redis cache enabled): `172.69.16.6`
- Wordpress (No cache): `172.69.16.7`
- MySQL Database: `172.69.16.8`
- Redis Master: `172.69.16.9`
- Redis Slaves: any unassigned IPs
- Sentinels: any unassigned IPs
## How-To Start Containers
1. Deploy `db` container first. Wordpress depends on MySQL instance 
```powershell
docker-compose up -d db
```
**NOTE**: Check if MySQL service is already up by executing `docker-compose logs db`

2. After MySQL is up, create new database for Wordpress without cache. Database for Wordpress with cache is automatically created
```powershell
.\create_db_web_nocache.ps1
```
3. Deploy 2 Wordpress instances, `web` and `web_nocache`
```powershell
docker-compose up -d web web_nocache
```
4. Deploy Redis Master
```powershell
docker-compose up -d master
```
5. Deploy 2 Redis Slaves and 3 Redis Sentinel by using `--scale` options
```powershell
docker-compose up -d --scale sentinel=3 --scale slave=2 sentinel slave
```
