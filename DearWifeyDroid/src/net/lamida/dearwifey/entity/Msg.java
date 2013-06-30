package net.lamida.dearwifey.entity;

import java.util.Date;
import java.util.Map;

import com.google.gson.annotations.Expose;

public class Msg {
	@Expose
	private String subject;
	private String content;
	@Expose
	private Map<String, String> contentText;
	private Date sentDate;
	@Expose
	private String ddmmyyyyhhmmss;
	private String param;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getDdmmyyyyhhmmss() {
		return ddmmyyyyhhmmss;
	}

	public void setDdmmyyyyhhmmss(String ddmmyyyyhhmmss) {
		this.ddmmyyyyhhmmss = ddmmyyyyhhmmss;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Map<String, String> getContentText() {
		return contentText;
	}

	public void setContentText(Map<String, String> contentText) {
		this.contentText = contentText;
	}
}
