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
