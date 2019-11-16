db.createCollection("posts")
sh.enableSharding("reddit")
db.posts.ensureIndex({subreddit: "hashed"})
sh.shardCollection( "reddit.posts", { "subreddit" : "hashed" } )
