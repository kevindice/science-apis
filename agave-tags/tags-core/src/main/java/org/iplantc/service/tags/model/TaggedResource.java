package org.iplantc.service.tags.model;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.iplantc.service.common.Settings;
import org.iplantc.service.common.exceptions.UUIDException;
import org.iplantc.service.common.persistence.TenancyHelper;
import org.iplantc.service.common.uuid.AgaveUUID;
import org.iplantc.service.tags.model.constraints.ValidAgaveUuid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
// import org.hibernate.validator.constraints.NotEmpty;

/**
 * Resource domain class
 *
 * @author Generated by minnal-generator
 */
@Entity
@Table(name = "taggedresources")
@Access(AccessType.FIELD)
public class TaggedResource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", unique = true, nullable = false)
	@JsonIgnore
    private Long id;
    
    @Column(name = "associated_uuid", nullable = false, length = 64)
    @ValidAgaveUuid
    @NotEmpty
    @NotNull
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false)
	@JsonIgnore
	private Date created = new Date();

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @JsonIgnore
    private Tag tag;

    public TaggedResource() {}
    
    public TaggedResource(String uuid) {
    	setUuid(uuid);
    }
    
    public TaggedResource(String uuid, Tag tag) {
    	setUuid(uuid);
    	setTag(tag);
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the tag
     */
    public Tag getTag() {
        return this.tag;
    }

    /**
     * @param tag
     *            the tag to set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param uuid
     *            the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }
    
    @Transient
    @JsonProperty("_links")
    public JsonNode getLinks() { 
    	ObjectMapper mapper = new ObjectMapper();
    	ObjectNode linksObject = mapper.createObjectNode();
        try {
        	AgaveUUID uuid = new AgaveUUID(getUuid());
	        linksObject.put("self", (ObjectNode)mapper.createObjectNode()
	            .put("href", TenancyHelper.resolveURLToCurrentTenant(Settings.IPLANT_TAGS_SERVICE) 
	            		+ getTag().getUuid()  + "/associationIds/" + uuid.getObjectReference()));
        } catch (UUIDException e) {
        	linksObject.put("self", (ObjectNode)mapper.createObjectNode()
		            .putNull("href"));
    	}
        
        linksObject.put("tag", (ObjectNode)mapper.createObjectNode()
                .put("href", TenancyHelper.resolveURLToCurrentTenant(Settings.IPLANT_TAGS_SERVICE) + getTag().getUuid()));
        
        return linksObject;
    }
    
    @Override
    public String toString() {
    	return getUuid();
    }

    public boolean equals(String uuid) {
    	return StringUtils.equals(getUuid(), uuid);
    }
}
