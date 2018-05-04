package com.study.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.demo.entity.FileInfo;

@Mapper
@SuppressWarnings("rawtypes")
public interface FileInfoDao {

	FileInfo getOne(Long id);

	List<FileInfo> list(Map map);

	int total(Map map);

	int insert(FileInfo entity);

	int update(FileInfo entity);

	int delete(Long id);
}
