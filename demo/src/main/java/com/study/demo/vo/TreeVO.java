package com.study.demo.vo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class TreeVO {
	@JSONField(ordinal = 1)
	private Long id;
	@JSONField(ordinal = 2)
	private String label;
	@JSONField(ordinal = 3)
	private List<TreeVO> children = new ArrayList<TreeVO>();

	public TreeVO() {

	}

	public TreeVO(Long id, String label) {
		this.id = id;
		this.label = label;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<TreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVO> children) {
		this.children = children;
	}
}
