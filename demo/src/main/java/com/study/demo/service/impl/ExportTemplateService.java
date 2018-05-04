package com.study.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.demo.vo.PageVO;
import com.study.demo.constant.PageConstant;
import com.study.demo.dao.ExportTemplateDao;
import com.study.demo.service.IExportTemplateService;
import com.study.demo.entity.ExportTemplate;

@Service
public class ExportTemplateService implements IExportTemplateService {

	@Autowired
	private ExportTemplateDao exportTemplateDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ExportTemplate getOne(Long id) {
		return exportTemplateDao.getOne(id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ExportTemplate> list(Map whereMap) {
		return exportTemplateDao.list(whereMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageVO page(PageVO page, Map whereMap) {
		whereMap.put(PageConstant.PAGE_START, page.getStart());
		whereMap.put(PageConstant.PAGE_SIZE, page.getPageSize());
		int total = exportTemplateDao.total(whereMap);
		List<ExportTemplate> rows = new ArrayList<ExportTemplate>();
		if (total > 0) {
			rows = list(whereMap);
		}
		page.setRows(rows);
		page.setTotal(total);
		return page;
	}

	@Override
	public int save(ExportTemplate entity) {
		Long id = entity.getId();
		if (id == null) {
			return exportTemplateDao.insert(entity);
		} else {
			return exportTemplateDao.update(entity);
		}
	}

	@Override
	public int delete(Long id) {
		return exportTemplateDao.delete(id);
	}
}
