package me.pyradian.reddit.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import me.pyradian.reddit.model.Author;
import me.pyradian.reddit.model.Post;
import me.pyradian.reddit.model.Subreddit;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class PostsRepository {
    final private static String DB = "reddit";
    final private static String COLLECTION = "posts";
    final private static Logger LOGGER = Logger.getLogger("org.mongodb.driver");

    final private MongoClient client;
    final private MongoDatabase db;

    public PostsRepository(String host, Integer port) {
        // disable logging
        LOGGER.setLevel(Level.SEVERE);

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        this.client = MongoClients.create("mongodb://"+host+":"+port.toString());
        this.db = this.client.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
    }

    public List<String> getCollections() {
        List<String> collections = new ArrayList<>();
        for (String collection: this.db.listCollectionNames()) {
            collections.add(collection);
        }
        return collections;
    }

    public void createPost(Post post) {
        MongoCollection<Post> coll = this.db.getCollection(COLLECTION, Post.class);
        coll.insertOne(post);
    }

    public Post readPost(String id) {
        MongoCollection<Post> coll = this.db.getCollection(COLLECTION, Post.class);
        FindIterable<Post> posts = coll.find(Filters.eq("postId", id));
        return posts.first();
    }

    public Post updatePostTitle(String id, String title) {
        MongoCollection<Post> coll = this.db.getCollection(COLLECTION, Post.class);
        FindIterable<Post> posts = coll.find(Filters.eq("postId", id));
        if (posts == null && posts.first() == null) {
            return null;
        }
        UpdateResult result = coll.updateOne(Filters.eq("_id", posts.first().getId()), Updates.set("title", title));
        if (result.getModifiedCount() < 1) {
            return null;
        } else {
            posts = coll.find(Filters.eq("postId", id));
            return posts.first();
        }
    }

    public Post deletePost(String id) {
        MongoCollection<Post> coll = this.db.getCollection(COLLECTION, Post.class);
        FindIterable<Post> posts = coll.find(Filters.eq("postId", id));
        if (posts == null && posts.first() == null) {
            return null;
        }
        Post post = posts.first();
        DeleteResult result = coll.deleteOne(Filters.eq("_id", post.getId()));
        return post;
    }

    public List<Subreddit> findTop5Subreddit() {
        MongoCollection<Document> coll = this.db.getCollection(COLLECTION);
        List<Subreddit> subreddits = new ArrayList<>();

        List<Bson> aggregatesPipeline = new ArrayList<>();
        aggregatesPipeline.add(
                Aggregates.group(
                        "$subreddit",
                        Accumulators.sum("totalUpvotes", "$upvotes"),
                        Accumulators.avg("avgUpvotes", "$upvotes"),
                        Accumulators.sum("postCount", 1)
                )
        );
        aggregatesPipeline.add(
                Aggregates.sort(
                        Sorts.orderBy(
                                Sorts.descending("totalUpvotes"),
                                Sorts.descending("avgUpvotes")
                        )
                )
        );
        aggregatesPipeline.add(Aggregates.limit(5));

        AggregateIterable<Document> result = coll.aggregate(aggregatesPipeline);
        for (Document document: result) {
            subreddits.add(new Subreddit(document));
        }

        return subreddits;
    }

    public List<Author> findTop5Author() {
        MongoCollection<Document> coll = this.db.getCollection(COLLECTION);
        List<Author> authors = new ArrayList<>();

        List<Bson> aggregatesPipeline = new ArrayList<>();
        aggregatesPipeline.add(
                Aggregates.group(
                        "$author",
                        Accumulators.sum("totalUpvotes", "$upvotes"),
                        Accumulators.sum("totalAwards", "$awards")
                )
        );
        aggregatesPipeline.add(
                Aggregates.sort(
                        Sorts.orderBy(
                                Sorts.descending("totalUpvotes"),
                                Sorts.descending("totalAwards")
                        )
                )
        );
        aggregatesPipeline.add(Aggregates.limit(5));

        AggregateIterable<Document> result = coll.aggregate(aggregatesPipeline);
        for (Document document: result) {
            authors.add(new Author(document));
        }

        return authors;
    }

    public void closeConnection() {
        this.client.close();
    }
}
