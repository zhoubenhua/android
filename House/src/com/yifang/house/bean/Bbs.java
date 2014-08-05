package com.yifang.house.bean;
/**
 * 论坛
 * @author Administrator
 *
 */
public class Bbs {
	private String author;//作者
	private String dateline;//发贴时间
	private String replies;//回贴数
	private String subject;//帖子主题
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	public String getReplies() {
		return replies;
	}
	public void setReplies(String replies) {
		this.replies = replies;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
