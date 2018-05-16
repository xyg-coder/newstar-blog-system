package com.xinyuan.blog.domain;

import com.github.rjeschke.txtmark.Processor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Blog Entity
 * @since 1.0.0 2018.2.26
 * @author Xinyuan Gui
 */
@Entity
@Document(indexName = "blog", type = "blog")
public class Blog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Titles cannot be empty")
    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "Summary cannot be empty")
    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String summary;

    @Lob // long content, will map to the long text of MySQL
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "Content cannot be null")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "content cannot be empty")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent; // transfer from md to html content

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp // create the timestamp automatically by the database
    private Timestamp createTime;

    // removing the blog will lead to the removal of comments
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "blog_vote", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
    private List<Vote> votes;

    @Column(name = "reading")
    private Integer reading = 0;

    @Column(name = "comments")
    private Integer commentSize = 0;

    @Column(name = "likes")
    private Integer likes = 0;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @Column(name = "tags", length = 100)
    private String tags;// tags split with ","

    protected Blog() {
    }

    public Blog(String title, String summary, String content) {
        this.id = null;
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.htmlContent = Processor.process(content);
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
        this.setLikes(votes.size());
    }

    /**
     * Add comment
     * @param comment
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    /**
     * Delete comments
     * @param commentId
     */
    public void removeComment(Long commentId) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getId().equals(commentId)) {
                this.comments.remove(i);
                break;
            }
        }
        this.commentSize = this.comments.size();
    }

    /**
     * return true if there exists the vote already
     * */
    public boolean addVote(Vote vote) {
        for(Vote alreadyVote : this.votes) {
            if (alreadyVote.getUser().getId().equals(vote.getUser().getId())) {
                return true;
            }
        }
        this.votes.add(vote);
        this.setLikes(this.votes.size());
        return false;
    }

    public void removeVote(Long voteId) {
        for (int i = 0; i < this.votes.size(); i++) {
            if (this.votes.get(i).getId().equals(voteId)) {
                this.votes.remove(i);
                this.setLikes(this.votes.size());
                return;
            }
        }
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
