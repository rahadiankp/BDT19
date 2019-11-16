package me.pyradian.reddit.model;

import org.bson.Document;

public class Subreddit {
    private String name;
    private int totalUpvotes;
    private double avgUpvotes;
    private int postCount;

    public Subreddit() {
    }

    public Subreddit(Document that) {
        this.name = that.getString("_id");
        this.totalUpvotes = that.getInteger("totalUpvotes" ,0);
        this.avgUpvotes = that.getDouble("avgUpvotes");
        this.postCount = that.getInteger("postCount", 0);
    }

    @Override
    public String toString() {
        return "Subreddit{" +
                "name='" + name + '\'' +
                ", totalUpvotes=" + totalUpvotes +
                ", avgUpvotes=" + avgUpvotes +
                ", postCount=" + postCount +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getTotalUpvotes() {
        return totalUpvotes;
    }

    public double getAvgUpvotes() {
        return avgUpvotes;
    }
}
