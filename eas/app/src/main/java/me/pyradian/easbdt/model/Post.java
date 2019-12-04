package me.pyradian.easbdt.model;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.Assigned;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "id", length = 6)
    private String id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "subreddit")
    private String subreddit;

    @Column(name = "upvotes")
    private int upvotes;

    @Column(name = "downvotes")
    private int downvotes;

    @Column(name = "score")
    private int score;

    @Column(name = "awards")
    private int awards;

    @Column(name = "oc")
    private boolean oc;

    @Column(name = "created")
    private long created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", subreddit='" + subreddit + '\'' +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                ", score=" + score +
                ", awards=" + awards +
                ", oc=" + oc +
                ", created=" + created +
                '}';
    }
}