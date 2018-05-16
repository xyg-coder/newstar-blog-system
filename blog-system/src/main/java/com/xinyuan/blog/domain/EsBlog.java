package com.xinyuan.blog.domain;

import com.xinyuan.blog.domain.Blog;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The blog entity stored in elasticsearch
 * */

@Document(indexName = "blog", type = "blog")
@XmlRootElement //mediatype to xml
public class EsBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Field(index = FieldIndex.not_analyzed) // won't be included in the whole search
    private Long blogId; // the blogId stored in sql

    private String title;

    private String summary;

    private String content;

    @Field(index = FieldIndex.not_analyzed) // won't be included in the whole search
    private String username;
    @Field(index = FieldIndex.not_analyzed) // won't be included in the whole search
    private String avatar;
    @Field(index = FieldIndex.not_analyzed) // won't be included in the whole search
    private Timestamp createTime;
    @Field(index = FieldIndex.not_analyzed) // won't be included in the whole search
    private Integer readCount = 0;
    @Field(index = FieldIndex.not_analyzed) // won't be included in the whole search
    private Integer commentCount = 0;
    @Field(index = FieldIndex.not_analyzed) // won't be included in the whole search
    private Integer voteCount;

    private String tags;

    protected EsBlog() {
    }

    public EsBlog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public EsBlog(Long blogId, String title, String summary, String content,
                  String username, String avatar, Timestamp createTime,
                  Integer readCount, Integer commentCount, Integer voteCount, String tags) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
        this.readCount = readCount;
        this.commentCount = commentCount;
        this.voteCount = voteCount;
        this.tags = tags;
    }

    public EsBlog(Blog blog) {
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.username = blog.getUser().getUsername();
        this.avatar = blog.getUser().getAvatar();
        this.createTime = blog.getCreateTime();
        this.readCount = blog.getReading();
        this.commentCount = blog.getCommentSize();
        this.voteCount = blog.getLikes();
        this.tags = blog.getTags();
    }

    public void update(Blog blog){
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.username = blog.getUser().getUsername();
        this.avatar = blog.getUser().getAvatar();
        this.createTime = blog.getCreateTime();
        this.readCount = blog.getReading();
        this.commentCount = blog.getCommentSize();
        this.voteCount = blog.getLikes();
        this.tags = blog.getTags();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, title='%s', content='%s']",
                blogId, title, content);
    }
}
