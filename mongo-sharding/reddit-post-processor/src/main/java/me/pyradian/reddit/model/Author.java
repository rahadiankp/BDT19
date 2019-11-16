package me.pyradian.reddit.model;

import org.bson.Document;

public class Author {
    private String author;
    private int totalUpvotes;
    private int totalAwards;

    public Author() {
    }

    public Author(Document that) {
        this.author = that.getString("_id");
        this.totalUpvotes = that.getInteger("totalUpvotes", 0);
        this.totalAwards = that.getInteger("totalAwards", 0);
    }

    @Override
    public String toString() {
        return "Author{" +
                "author='" + author + '\'' +
                ", totalUpvotes=" + totalUpvotes +
                ", totalAwards=" + totalAwards +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalUpvotes() {
        return totalUpvotes;
    }

    public int getTotalAwards() {
        return totalAwards;
    }
}
