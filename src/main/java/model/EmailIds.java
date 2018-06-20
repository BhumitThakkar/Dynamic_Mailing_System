package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="EmailIds")
public class EmailIds {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "email_id")
	private String emailId;
	
	@ManyToOne
    @JoinColumn(name="emailid_lists_id", nullable=false)
    private EmailIdLists emailIdLists;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public EmailIdLists getEmailIdLists() {
		return emailIdLists;
	}

	public void setEmailIdLists(EmailIdLists emailIdLists) {
		this.emailIdLists = emailIdLists;
	}

}