package com.study.demo.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class PermissionVO {
	@JSONField(ordinal = 1)
	private List<Long> checks;
	@JSONField(ordinal = 2)
	private TreeVO tree;

	public List<Long> getChecks() {
		return checks;
	}

	public void setChecks(List<Long> checks) {
		this.checks = checks;
	}

	public TreeVO getTree() {
		return tree;
	}

	public void setTree(TreeVO tree) {
		this.tree = tree;
	}
}
