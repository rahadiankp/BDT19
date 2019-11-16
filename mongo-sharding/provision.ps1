echo "Executing config server script..."
Write-Host "Waiting for mongod in config1 to ready" -NoNewLine
while(-Not (docker-compose exec config1 mongo --port 27019 --eval "PONG" | Select-String -Pattern PONG)) {
  Write-Host "." -NoNewLine
  sleep 1
}
echo ""
echo "mongod config1 is up. Executing now..."
docker-compose exec config1 mongo --port 27019 scripts/init-configserver.js | Out-Null
echo "OK - Config servers configured"
echo ""

echo "Running init script for 5 shard clusters..."
docker-compose exec shard1 mongo --port 27018 scripts/init-shard.js | Out-Null
echo "shard1 OK"
docker-compose exec shard2 mongo --port 27018 scripts/init-shard.js | Out-Null
echo "shard2 OK"
docker-compose exec shard3 mongo --port 27018 scripts/init-shard.js | Out-Null
echo "shard3 OK"
docker-compose exec shard4 mongo --port 27018 scripts/init-shard.js | Out-Null
echo "shard4 OK"
docker-compose exec shard5 mongo --port 27018 scripts/init-shard.js | Out-Null
echo "shard5 OK"
echo "OK - Shard servers configured"
echo ""

echo "Executing init router script..."
Write-Host "Waiting for mongos in router to ready" -NoNewLine
while(-Not (docker-compose exec router mongo --port 27017 --eval "PONG" | Select-String -Pattern PONG)) {
  Write-Host "." -NoNewLine
  sleep 1
}
echo ""
echo "mongos router is up. Executing now..."
docker-compose exec router mongo scripts/init-router.js | Out-Null
echo "OK - Router configured"
echo ""

echo "Initializing database and collection for sharding-ready"
docker-compose exec router mongo localhost/reddit scripts/init-router-db.js | Out-Null
echo "OK - Database and collection is ready for sharding"
echo ""

echo "Importing documents to reddit.posts..."
mongoimport --db reddit --collection posts --file posts_new.json
echo "OK - Data imported"
