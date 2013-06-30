import java.util.Date;


public class Msg {
	private String subject;
	private String content;
	private Date sentDate;
	private String ddmmyyyyhhmmss;
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
}
