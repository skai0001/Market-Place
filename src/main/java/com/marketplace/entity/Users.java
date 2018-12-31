package com.marketplace.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
	@NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
	@NamedQuery(name = "Users.findByRequestKey", query = "SELECT u FROM Users u WHERE u.requestKey = :key")
})
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
	private String name;
	

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "email")
	private String email;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "password_hash")
	private String passwordHash;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "register_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
	private Date registerTimestamp;
	
	@Column(name = "last_login_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTimestamp;
	
	@Size(max = 18)
    @Column(name = "ip_address")
	private String ipAddress;
	
	@Size(max = 128)
    @Column(name = "address")
	private String address;
	
	@Size(max = 64)
    @Column(name = "province")
	private String province;
	
	@Size(max = 64)
    @Column(name = "city")
	private String city;
	
	@Size(max = 6)
    @Column(name = "postal_code")
	private String postalCode;
	
	@Size(max = 64)
    @Column(name = "country")
	private String country;
	
	@Column(name = "phone_number")
	private Integer phoneNumber;
	
	@Size(max = 2048)
    @Column(name = "profile_picture_url")
	private String profilePictureUrl;
	
	@Size(max = 64)
    @Column(name = "request_key")
	private String requestKey;
	
	@Column(name = "request_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
	private Date requestTimestamp;
	
    @Column(name = "request_type")
	private Integer requestType;

	public Users() {
	}
	
	public Users(Integer id) {
		this.id = id;
	}

	public Users(String name, String email, String passwordHash, Date registerTimestamp) {
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.registerTimestamp = registerTimestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Date getRegisterTimestamp() {
		return registerTimestamp;
	}

	public void setRegisterTimestamp(Date registerTimestamp) {
		this.registerTimestamp = registerTimestamp;
	}

	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public Date getRequestTimestamp() {
		return requestTimestamp;
	}

	public void setRequestTimestamp(Date requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Users)) {
			return false;
		}
		Users other = (Users) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
