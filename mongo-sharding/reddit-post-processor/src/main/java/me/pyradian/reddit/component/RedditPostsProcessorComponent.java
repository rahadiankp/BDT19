package me.pyradian.reddit.component;

import me.pyradian.reddit.model.Author;
import me.pyradian.reddit.model.Post;
import me.pyradian.reddit.model.Subreddit;
import me.pyradian.reddit.repository.PostsRepository;

import java.util.List;
import java.util.Scanner;

public class RedditPostsProcessorComponent {
    final private PostsRepository repository;
    final private Scanner scanner;

    public RedditPostsProcessorComponent(String host, Integer port) {
        this.repository = new PostsRepository(host, port);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int selection;
        while (true) {
            System.out.println("Reddit Posts Processor");
            System.out.println(" > 1. Create Post");
            System.out.println(" > 2. Read Post");
            System.out.println(" > 3. Update Post");
            System.out.println(" > 4. Delete Post");
            System.out.println(" > 5. Top 5 Subreddits");
            System.out.println(" > 6. Top 5 Authors");
            System.out.println(" > 7. Exit");
            System.out.print("Select [> ");
            try {
                selection = this.scanner.nextInt();
            } catch (Exception e) {
                this.scanner.next(); // flush stdin
                System.out.println("Weird input bruh");
                continue;
            }
            switch (selection) {
                case 1:
                    this.createPost();
                    break;
                case 2:
                    this.readPost();
                    break;
                case 3:
                    this.updatePost();
                    break;
                case 4:
                    this.deletePost();
                    break;
                case 5:
                    this.getTop5Subreddit();
                    break;
                case 6:
                    this.getTop5Authors();
                    break;
                case 7:
                    this.repository.closeConnection();
                    return;
                default:
                    System.out.println("Wrong selection");
            }
        }
    }

    private void createPost() {
        System.out.print("ID: ");
        String id = this.scanner.next();
        System.out.print("Author: ");
        String author = this.scanner.next();
        System.out.print("Title: ");
        String title = this.scanner.next();
        System.out.print("Subreddit: ");
        String subreddit = this.scanner.next();

        Post post = new Post();
        post.setAuthor(author);
        post.setPostId(id);
        post.setTitle(title);
        post.setSubreddit(subreddit);

        this.repository.createPost(post);

        System.out.println(post);
        System.out.println("-------------");
    }

    private void readPost() {
        System.out.print("ID: ");
        String id = this.scanner.next();
        Post post = this.repository.readPost(id);
        if (post == null) {
            System.out.println("ID " + id + " does not exist");
        } else {
            System.out.println(post);
            System.out.println("-------------");
        }
    }

    private void updatePost() {
        System.out.print("ID: ");
        String id = this.scanner.next();
        System.out.print("Title: ");
        String title = this.scanner.next();
        Post post = this.repository.updatePostTitle(id, title);
        if (post == null) {
            System.out.println("ID " + id + " does not exist");
        } else {
            System.out.println(post);
            System.out.println("-------------");
        }
    }

    private void deletePost() {
        System.out.print("ID: ");
        String id = this.scanner.next();
        Post post = this.repository.deletePost(id);
        if (post == null) {
            System.out.println("ID " + id + " does not exist");
        } else {
            System.out.println(post);
            System.out.println("-------------");
        }
    }

    private void getTop5Subreddit() {
        List<Subreddit> subreddits = this.repository.findTop5Subreddit();
        for (Subreddit subreddit: subreddits) {
            System.out.println(subreddit);
            System.out.println("-------------");
        }
    }

    private void getTop5Authors() {
        List<Author> authors = this.repository.findTop5Author();
        for (Author author: authors) {
            System.out.println(author);
            System.out.println("-------------");
        }
    }
}
