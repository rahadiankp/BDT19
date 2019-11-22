echo "Creating DB..."
docker-compose exec db mysql -u root -proot -e "CREATE DATABASE wordpress_nocache;"
echo "OK"

echo "Granting privileges..."
docker-compose exec db mysql -u root -proot -e "GRANT ALL PRIVILEGES ON wordpress_nocache.* to 'wordpress'@'%'; FLUSH PRIVILEGES;"
echo "OK"
