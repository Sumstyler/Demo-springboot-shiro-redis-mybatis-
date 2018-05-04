package com.study.demo.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class MenuVO {
	@JSONField(serialize = false)
	private Long id;
	@JSONField(ordinal = 1)
	private String title;
	@JSONField(ordinal = 2)
	private String path;
	@JSONField(ordinal = 3)
	private String icon;
	@JSONField(ordinal = 4)
	private List<MenuVO> child;

	public MenuVO() {

	}

	public MenuVO(Long id, String title, String path, String icon) {
		this.id = id;
		this.title = title;
		this.path = path;
		this.icon = icon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path == null ? "" : path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<MenuVO> getChild() {
		return child;
	}

	public void setChild(List<MenuVO> child) {
		this.child = child;
	}
}
