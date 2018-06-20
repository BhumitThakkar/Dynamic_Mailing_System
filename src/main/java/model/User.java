package model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

	@Entity
	@Table(name= "User")
	public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "password")
	private String password;
	
	// fetch = FetchType.EAGER --> to solve lazyinitializationexception
	@OneToMany(mappedBy="user", fetch = FetchType.EAGER)					// user is variable in the class with whom User class shares 1 to "many" relation.
	private Set<EmailIdLists> emailIdLists;
	
	public User() {
	}
	public User(String firstName, String lastName, String emailId, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
	}
	
	public long getId() {  
	    return id;  
	}  
	public void setId(long id) {  
	    this.id = id;  
	}  
	public String getFirstName() {  
	    return firstName;  
	}  
	public void setFirstName(String firstName) {  
	    this.firstName = firstName;  
	}  
	public String getLastName() {  
	    return lastName;  
	}  
	public void setLastName(String lastName) {  
	    this.lastName = lastName;  
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<EmailIdLists> getEmailIdLists() {
		return emailIdLists;
	}
	public void setEmailIdLists(Set<EmailIdLists> emailIdLists) {
		this.emailIdLists = emailIdLists;
	}
	
}
