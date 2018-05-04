package com.study.demo.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.study.demo.entity.ExportTemplate;

@Mapper
@SuppressWarnings("rawtypes")
public interface ExportTemplateDao {

	ExportTemplate getOne(Long id);

	List<ExportTemplate> list(Map map);

	int total(Map map);

	int insert(ExportTemplate entity);

	int update(ExportTemplate entity);

	int delete(Long id);
}
