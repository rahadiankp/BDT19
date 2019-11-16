package me.pyradian.reddit.model;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Post {
//    @BsonId
//    @BsonProperty("_id")
    private ObjectId id;
    private String postId;
    private String author;
    private String subreddit;
    private String title;
    private int score;
    private int upvotes;
    private int downvotes;
    private int awards;
    private boolean oc;
    private long created;

    public Post() {
    }

    public Post(Document that) {
        this.id = that.getObjectId("_id");
        this.postId = that.getString("postId");
        this.author = that.getString("author");
        this.subreddit = that.getString("subreddit");
        this.title = that.getString("title");
        this.score = that.getInteger("score", 0);
        this.upvotes = that.getInteger("upvotes", 0);
        this.downvotes = that.getInteger("downvotes", 0);
        this.awards = that.getInteger("awards", 0);
        this.oc = that.getBoolean("oc", true);
        this.created = that.getLong("created");
    }

    public Post(ObjectId id, String postId, String author, String subreddit, String title, int score, int upvotes, int downvotes, int awards, boolean oc, long created) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.subreddit = subreddit;
        this.title = title;
        this.score = score;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.awards = awards;
        this.oc = oc;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post{" +
                "_id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                ", author='" + author + '\'' +
                ", subreddit='" + subreddit + '\'' +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                ", awards=" + awards +
                ", oc=" + oc +
                ", created=" + created +
                '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public int getAwards() {
        return awards;
    }

    public void setAwards(int awards) {
        this.awards = awards;
    }

    public boolean isOc() {
        return oc;
    }

    public void setOc(boolean oc) {
        this.oc = oc;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
