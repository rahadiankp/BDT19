package me.pyradian.reddit;

import me.pyradian.reddit.component.RedditPostsProcessorComponent;

public class MainApplication {
    final private static String HOST = "localhost";
    final private static Integer PORT = 27017;

    public static void main(String[] args) {
        RedditPostsProcessorComponent component = new RedditPostsProcessorComponent(HOST, PORT);
        component.start();
//        MongoCollection<Document> collAsDoc = mongoDatabase.getCollection("posts");
//        List<Document> aggregates = new ArrayList<>();
//        aggregates.add(
//                new Document("$group",
//                        new Document("_id", "$subreddit")
//                            .append("totalUpvotes", new Document("$sum", "$ups"))
//                            .append("postCount", new Document("$sum", 1))));
//        aggregates.add(new Document("$sort", new Document("totalUpvotes", -1)));
//        aggregates.add(new Document("$limit", 5));
//
//        AggregateIterable<Document> resultTop = collAsDoc.aggregate(aggregates);
//        for (Document document: resultTop) {
//            System.out.println(document);
//        }

    }
}
