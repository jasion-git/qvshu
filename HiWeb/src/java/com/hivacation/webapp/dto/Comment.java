package com.hivacation.webapp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1818928490707317435L;

	private Integer id;
	private Integer userId;
	private String userName;
	private String userHeadImg;
	private Integer houseId;
	private String content;
	private Date createTime;

	private List<CommentImg>commentImgList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadImg() {
		return userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<CommentImg> getCommentImgList() {
		return commentImgList;
	}

	public void setCommentImgList(List<CommentImg> commentImgList) {
		this.commentImgList = commentImgList;
	}
	
}
