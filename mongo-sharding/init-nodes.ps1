echo "Running init script for configsvr..."
docker-compose exec config1 mongo --port 27019 scripts/init-configserver.js
sleep 5
echo "Running init script for 5 shard clusters..."
docker-compose exec shard1 mongo --port 27018 scripts/init-shard.js
docker-compose exec shard2 mongo --port 27018 scripts/init-shard.js
docker-compose exec shard3 mongo --port 27018 scripts/init-shard.js
docker-compose exec shard4 mongo --port 27018 scripts/init-shard.js
docker-compose exec shard5 mongo --port 27018 scripts/init-shard.js
sleep 1
echo "Waiting for 30 secs to execute init router script"
sleep 30
docker-compose exec router mongo scripts/init-router.js
echo "OK"
