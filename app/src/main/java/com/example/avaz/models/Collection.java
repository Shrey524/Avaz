package com.example.avaz.models;

import java.util.List;

public class Collection{
	private String template;
	private Sponsor sponsor;
	private String dateUpdated;
	private Author author;
	private String dateCreated;
	private String isCollaborative;
	private String isPublished;
	private String description;
	private String sponsorCampaignLink;
	private String sponsorId;
	private List<Object> tags;
	private String isStoreItem;
	private String name;
	private String id;
	private String authorId;
	private String permalink;
	private String isFeatured;
	private String slug;

	public String getTemplate(){
		return template;
	}

	public Sponsor getSponsor(){
		return sponsor;
	}

	public String getDateUpdated(){
		return dateUpdated;
	}

	public Author getAuthor(){
		return author;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getIsCollaborative(){
		return isCollaborative;
	}

	public String getIsPublished(){
		return isPublished;
	}

	public String getDescription(){
		return description;
	}

	public String getSponsorCampaignLink(){
		return sponsorCampaignLink;
	}

	public String getSponsorId(){
		return sponsorId;
	}

	public List<Object> getTags(){
		return tags;
	}

	public String getIsStoreItem(){
		return isStoreItem;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getAuthorId(){
		return authorId;
	}

	public String getPermalink(){
		return permalink;
	}

	public String getIsFeatured(){
		return isFeatured;
	}

	public String getSlug(){
		return slug;
	}
}