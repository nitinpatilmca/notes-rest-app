package org.gotprint.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the USER_ROLES database table.
 * 
 */
@Entity
@Table(name = "USER_ROLES", schema = "gotprint")
public class UserRoles implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRolePK id;

	// uni-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private Users user;

	public UserRoles() {
	}

	public UserRolePK getId() {
		return this.id;
	}

	public void setId(UserRolePK id) {
		this.id = id;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
