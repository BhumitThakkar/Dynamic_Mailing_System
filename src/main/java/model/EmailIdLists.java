package model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="EmailIdLists")
public class EmailIdLists {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "list_name", nullable=false)
	private String listName;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
	
	// fetch = FetchType.EAGER --> to solve lazyinitializationexception
	@OneToMany(mappedBy="emailIdLists",fetch = FetchType.EAGER)
    private Set<EmailIds> emailIds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<EmailIds> getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(Set<EmailIds> emailIds) {
		this.emailIds = emailIds;
	}
	
}
