echo "Running /node_exporter on PD"
for ($i=1; $i -lt 4; $i+=1) {
  docker-compose exec -T pd$i ash -c "/node_exporter &" 2>&1 | Out-Null
}
echo "OK"

echo "Running /node_exporter on TiKV"
for ($i=1; $i -lt 4; $i+=1) {
  docker-compose exec -T tikv$i ash -c "/node_exporter &"  2>&1| Out-Null
}
echo "OK"

echo "Running /node_exporter on PD"
docker-compose exec -T tidb ash -c "/node_exporter &"  2>&1 | Out-Null
echo "OK"
