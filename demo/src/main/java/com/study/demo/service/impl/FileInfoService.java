package com.study.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.study.demo.constant.PageConstant;
import com.study.demo.dao.FileInfoDao;
import com.study.demo.entity.FileInfo;
import com.study.demo.service.IFileInfoService;
import com.study.demo.vo.PageVO;

@Service
public class FileInfoService implements IFileInfoService {

	@Autowired
	private FileInfoDao fileInfoDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public FileInfo getOne(Long id) {
		return fileInfoDao.getOne(id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<FileInfo> list(Map whereMap) {
		return fileInfoDao.list(whereMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageVO page(PageVO page, Map whereMap) {
		whereMap.put(PageConstant.PAGE_START, page.getStart());
		whereMap.put(PageConstant.PAGE_SIZE, page.getPageSize());
		int total = fileInfoDao.total(whereMap);
		List<FileInfo> rows = new ArrayList<FileInfo>();
		if (total > 0) {
			rows = list(whereMap);
		}
		page.setRows(rows);
		page.setTotal(total);
		return page;
	}

	@Override
	public int save(FileInfo entity) {
		Long id = entity.getId();
		if (id == null) {
			return fileInfoDao.insert(entity);
		} else {
			return fileInfoDao.update(entity);
		}
	}
}
