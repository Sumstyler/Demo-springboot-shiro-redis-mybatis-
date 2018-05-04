package com.study.demo.service;

import java.util.List;
import java.util.Map;

import com.study.demo.entity.FileInfo;
import com.study.demo.vo.PageVO;

public interface IFileInfoService {

	FileInfo getOne(Long id);

	@SuppressWarnings("rawtypes")
	List<FileInfo> list(Map whereMap);

	@SuppressWarnings("rawtypes")
	PageVO page(PageVO page, Map whereMap);

	int save(FileInfo entity);
}
