package com.loujin.model;

import java.util.Date;

public class News {

	private int id;

	private String title;
	
	private String content;

	private Date publishDate;

	private String commentCount;
	
	private String imgrul;
	
	private String category;
	
	private String ContentUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getImgrul() {
		return imgrul;
	}

	public void setImgrul(String imgrul) {
		this.imgrul = imgrul;
	}

	public String getContentUrl() {
		return ContentUrl;
	}

	public void setContentUrl(String contentUrl) {
		ContentUrl = contentUrl;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
