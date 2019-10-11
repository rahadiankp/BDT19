# BDT 2019 - ETS  
## Rancang Bangun Infrastruktur dan Spesifikasi  
![Infrastructure Detail](img/architecture.png)  
Change log:  
- 00:44 09/10/2019:- Resize ProxySQL and MySQL memory limit to **256MB**  
- Change ProxySQL IP and **Apache** to **Apache Tomcat**  
## Implementasi  
Untuk menjalankan dan menguji tugas ETS basis data ini, digunakan **Docker** untuk menjalankan kontainer yang dibutuhkan. Apabila menggunakan [Chocolatey](https://chocolatey.org/), eksekusi perintah di bawah ini untuk menginstall Docker:
```
choco install docker-desktop
```
### Konfigurasi MySQL dan ProxySQL  

#### Docker Image
Untuk pull image yang digunakan untuk tugas ini, eksekusi perintah berikut:
```
docker pull mysql:5.7.27
docker pull proxysql/proxysql
docker pull adminer:4.7.3-standalone (OPTIONAL)
```
Setelah proses unduh selesai, cek dengan cara mengeksekusi perintah:
```
docker images
```
![Docker Images](img/docker_images.png)

**Adminer** sepadan dengan phpMyAdmin. Tidak wajib digunakan, namun sangat mempermudah di tugas ini.

#### Docker Network
Membuat subnet khusus kontainer yang akan di-*deploy*. Di ilustrasi arsitektur di atas, digunakan subnet **172.69.16.0/28**. Sedangkan *driver* yang akan digunakan oleh Docker adalah `bridge`. Untuk menyiapkan jaringan tersebut, eksekusi perintah berikut:
```
docker network create -d brigde --subnet 172.69.16.0/28 ets_mysqlnet
```
Cek apakah subnet sudah *up*, dengan:
```
docker network ls
```
![Docker Network is Up](img/docker_network.png)  
Selain menggunakan perintah `docker network create`, bisa juga menggunakan `docker-compose.yml`  untuk menyiapkan jaringan yang dibutuhkan.  
docker-compose.yml:
```
networks:
  mysqlnet:
    driver: bridge
    ipam:
      config:
        - subnet: 172.69.16.0/28
```
#### Dockerfile
##### Dockerfile untuk MySQL Server

##### Dockerfile untuk ProxySQL

### Aplikasi yang Digunakan
Aplikasi yang saya gunakan untuk menguji basis data terdistribusi ini adalah aplikasi berbasis Spring Framework yang kelompok kami kerjakan, dapat diakses [di sini](https://github.com/shunpeicloser/FP-PBKK-Payment). Aplikasi REST API ini digunakan untuk melayani pembayaran di dalam suatu sistem yang mirip dengan GrabFood atau GoFood.  
Aplikasi ini awalnya menggunakan basis data non-relasional, **MongoDB**. Beberapa adjustment harus dilakukan.
#### Dependensi yang Dibutuhkan
```
mysql:mysql-connector-java
org.springframework.boot:spring-boot-starter-data-jpa
org.springframework.boot:spring-boot-starter-web
```
#### Konfigurasi Aplikasi
application.properties:
```
server.port=8090  
  
spring.datasource.url=jdbc:mysql://localhost:3306/ojack_wallet?useSSL=false  
spring.datasource.username=ojack  
spring.datasource.password=w4ll3t  
  
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect  
  
spring.jpa.hibernate.ddl-auto = update
```

### Run
