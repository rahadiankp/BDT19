echo "Fixing Redis Object Cache plugin..."
docker-compose exec web sed -i "s/return 'INFO';/return 'info';/g" /var/www/html/wp-content/plugins/redis-cache/includes/predis/src/Command/ServerInfo.php
echo "OK"
