package com.app.model;

import java.util.List;


public class SidebarItem {

	private String title;
	private String icon;
	private String link;
	private List<SidebarItem> subItems;
	
	
	
	public SidebarItem() {
		super();
	}
	public SidebarItem(String title, String icon, String link, List<SidebarItem> subItems) {
		super();
		this.title = title;
		this.icon = icon;
		this.link = link;
		this.subItems = subItems;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public List<SidebarItem> getSubItems() {
		return subItems;
	}
	public void setSubItems(List<SidebarItem> subItems) {
		this.subItems = subItems;
	}
	
	
}
