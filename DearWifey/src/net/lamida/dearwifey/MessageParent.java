package net.lamida.dearwifey;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class MessageParent {
	@Id
	Long id;
	
	public MessageParent() {
		super();
	}

	public MessageParent(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
