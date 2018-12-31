package com.marketplace.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "items")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Items.findAll", query = "SELECT i FROM Items i"),
	@NamedQuery(name = "Items.findByCategory", query = "SELECT i FROM Items i WHERE i.category = :category"),
	@NamedQuery(name = "Items.findByPrice", query = "SELECT i FROM Items i WHERE i.price >= :min AND i.price <= :max")
})
public class Items implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	
	@Size(max = 128)
    @Column(name = "title")
	private String title;
	
	@Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "info")
	private String info;
	
	@Column(name = "price")
	private Integer price;
	
	@Size(max = 128)
    @Column(name = "address")
	private String address;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "city")
	private String city;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "province")
	private String province;
	
	@Size(max = 6)
    @Column(name = "postal_code")
	private String postalCode;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "country")
	private String country;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "post_owner_id")
	private int postOwnerId;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "post_init_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
	private Date postInitTimestamp;
	
	@Column(name = "post_exit_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
	private Date postExitTimestamp;
	
	@Column(name = "main_image_id")
	private Integer mainImageId;
	
	@Size(max = 32)
	@Column(name = "category")
	private String category;

	public Items() {
	}

	public Items(String info, String city, String province, String country, int postOwnerId, Date postInitTimestamp) {
		this.info = info;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postOwnerId = postOwnerId;
		this.postInitTimestamp = postInitTimestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public int getPostOwnerId() {
		return postOwnerId;
	}

	public void setPostOwnerId(int postOwnerId) {
		this.postOwnerId = postOwnerId;
	}

	public Date getPostInitTimestamp() {
		return postInitTimestamp;
	}

	public void setPostInitTimestamp(Date postInitTimestamp) {
		this.postInitTimestamp = postInitTimestamp;
	}

	public Date getPostExitTimestamp() {
		return postExitTimestamp;
	}

	public void setPostExitTimestamp(Date postExitTimestamp) {
		this.postExitTimestamp = postExitTimestamp;
	}

	public Integer getMainImageId() {
		return mainImageId;
	}

	public void setMainImageId(Integer mainImageId) {
		this.mainImageId = mainImageId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
		if (!(object instanceof Items)) {
			return false;
		}
		Items other = (Items) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Item:" + id + " - " + title;
	}
}
