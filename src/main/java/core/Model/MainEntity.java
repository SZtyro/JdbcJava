package core.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
@JsonIgnoreProperties(value={"entityType","createdAt","updatedAt", "contextable", "entityDescription"}, allowGetters=true)
public abstract class MainEntity {
	private static final String HB4_JAVASSIST  = "_$$_jvst" ;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@JsonProperty(access= JsonProperty.Access.READ_ONLY)
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@JsonProperty(access= JsonProperty.Access.READ_ONLY)
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(nullable=false)
	private boolean draft = true;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public final int getVersion() {
		return version;
	}

	public final void setVersion(int version) {
		this.version = version;
	}

	public final Date getCreatedAt() {
		return createdAt;
	}

	public final Date getUpdatedAt() {
		return updatedAt;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	@JsonProperty(access= JsonProperty.Access.READ_ONLY)
	public final String getEntityType() {
		String name = this.getClass().getCanonicalName();
		int idx = name.indexOf(HB4_JAVASSIST);
		if (idx > 0)
			return name.substring(0, idx);
		return name;
	}

	/**
	 * Sets createdAt before insert
	 */
	@PrePersist
	public final void setCreationDate() {
		this.createdAt = new Date();
	}

	/**
	 * Sets updatedAt before update
	 */
	@PreUpdate
	public final void setChangeDate() {
		this.updatedAt = new Date();
	}

	public static boolean isLazyInitialized(Object o)
	{
		String name = o.getClass().getCanonicalName();
		return name.indexOf(HB4_JAVASSIST) == -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!obj.getClass().equals(this.getClass())) {
			return false;
		} 
		
		if(!(obj instanceof MainEntity)) {
			return false;
		}
		
		Long otherId = ((MainEntity)obj).getId();
		if(this.id == null || otherId == null) {
			return false;
		}
		
		return this.id.equals(otherId);
	}
	
	@Override
	public int hashCode() {
		if(this.id == null) {
			return 0;
		} else {
			return this.id.hashCode();
		}
	}
}
