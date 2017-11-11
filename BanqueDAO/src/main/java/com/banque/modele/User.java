package com.banque.modele;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
// JPA Annotation trop peu parametrable.. @Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@Table(name = "utilisateur", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))

public class User implements Serializable,Comparable<User> {

	/**
     * 
     */
	private static final long serialVersionUID = 1399405569633L;

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "login", nullable = false, unique = true)
	private String login;
	
	@Column(name = "password", nullable = false)
	private String password;


	@ManyToOne(cascade = CascadeType.MERGE)
	
	@JoinColumn(name="idrole")
	private Role role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int compareTo(User otherUser) {
		if (otherUser == null){
			return 1;
		}
		
		return this.login.compareTo(otherUser.login);
	}

}
