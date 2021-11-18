package com.example.avaz.models;

import java.util.List;

public class ApiData{
	private int total;
	private String generatedAt;
	private Collection collection;
	private List<IconsItem> icons;

	public int getTotal(){
		return total;
	}

	public String getGeneratedAt(){
		return generatedAt;
	}

	public Collection getCollection(){
		return collection;
	}

	public List<IconsItem> getIcons(){
		return icons;
	}
}