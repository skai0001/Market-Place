package com.marketplace.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "images")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Images.findAll", query = "SELECT i FROM Images i"),
	@NamedQuery(name = "Images.findById", query = "SELECT i FROM Items i WHERE i.id = :id")
})
public class Images implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
	private Integer id;
	
	@Size(max = 2048)
    @Column(name = "url")
	private String url;
	
	@Column(name = "for_post_id")
	private Integer forPostId;

	public Images() {
	}

	public Images(String url, Integer forPostId) {
		this.url = url;
		this.forPostId = forPostId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getForPostId() {
		return forPostId;
	}

	public void setForPostId(Integer forPostId) {
		this.forPostId = forPostId;
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
		if (!(object instanceof Images)) {
			return false;
		}
		Images other = (Images) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return url;
	}
	
}
