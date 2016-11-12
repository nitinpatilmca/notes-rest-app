package org.gotprint.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USERS", schema = "gotprint" )
public class Users implements Serializable , UserDetails{

	protected static final Logger LOGGER = Logger.getLogger(Users.class);
	private static final long serialVersionUID = 299817514520320650L;

	@Id
	@Column(name = "USER_ID")
	private String username;

	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_ON")
	private Date createOn;

	@Column(name = "EMAIL")
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_ON")
	private Date lastUpdateOn;

	// uni-directional many-to-many association to Note
	@OneToMany(mappedBy = "users")
	private List<Notes> notes;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLES", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Roles> roles;

	public Users() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastUpdateOn() {
		return this.lastUpdateOn;
	}

	public void setLastUpdateOn(Date lastUpdateOn) {
		this.lastUpdateOn = lastUpdateOn;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Notes> getNotes() {
		return this.notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public Set<Roles> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	@Override
	public boolean isAccountNonExpired() {

		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();

		for (Roles role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleId()));
		}

		return grantedAuthorities;
	}
	
}
