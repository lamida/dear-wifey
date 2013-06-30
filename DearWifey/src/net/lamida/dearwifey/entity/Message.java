package net.lamida.dearwifey.entity;

import java.util.Date;

import net.lamida.dearwifey.MessageParent;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;

@Entity 
public class Message {
	@Parent Key<MessageParent> owner;
	@Id 
	private Long id;
	private String subject;
	@Ignore
	private String content;
	private Text contentText;
	private Date sentDate;
	private String ddmmyyyyhhmmss;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Text getContentText() {
		return contentText;
	}

	public void setContentText(Text contentText) {
		this.contentText = contentText;
	}

	public String getDdmmyyyyhhmmss() {
		return ddmmyyyyhhmmss;
	}

	public void setDdmmyyyyhhmmss(String ddmmyyyyhhmmss) {
		this.ddmmyyyyhhmmss = ddmmyyyyhhmmss;
	}

	public Key<MessageParent> getOwner() {
		return owner;
	}

	public void setOwner(Key<MessageParent> owner) {
		this.owner = owner;
	}
	
}
