package com.hivacation.webapp.dto;

import java.io.Serializable;

public class CommentImg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2583514221653348477L;

	private Integer id;
	private Integer commentId;
	private String url;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
