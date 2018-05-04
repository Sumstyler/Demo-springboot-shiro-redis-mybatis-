package com.study.demo.service;

import java.util.List;
import java.util.Map;

import com.study.demo.vo.PageVO;
import com.study.demo.entity.ExportTemplate;

public interface IExportTemplateService {

	ExportTemplate getOne(Long id);

	@SuppressWarnings("rawtypes")
	List<ExportTemplate> list(Map whereMap);

	@SuppressWarnings("rawtypes")
	PageVO page(PageVO page, Map whereMap);

	int save(ExportTemplate entity);

	int delete(Long id);
}
