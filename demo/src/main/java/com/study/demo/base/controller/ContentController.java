package com.study.demo.base.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.demo.entity.FileInfo;
import com.study.demo.service.IFileInfoService;
import com.study.demo.utils.bean.BeanUtils;
import com.study.demo.vo.ContentVO;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.ResultVO;

@RestController
@RequestMapping("/api/content")
public class ContentController {

	@Autowired
	private IFileInfoService fileInfoService;

	/**
	 * 分页查找内容信息
	 * 
	 * @param pagesize
	 * @param pagenum
	 * @param user
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequiresPermissions("content:manager")
	@PostMapping("/page/{pagesize}/{pagenum}")
	public ResultVO page(@PathVariable("pagesize") int pagesize, @PathVariable("pagenum") int pagenum,
			@RequestBody FileInfo info) {
		PageVO page = new PageVO(pagenum, pagesize);
		Map whereMap = BeanUtils.beanToMap(info);
		fileInfoService.page(page, whereMap);
		List<FileInfo> infos = (List<FileInfo>) page.getRows();
		List<ContentVO> data = new ArrayList<ContentVO>();
		double size;
		for (FileInfo f : infos) {
			ContentVO vo = new ContentVO();
			vo.setName(f.getMd5());
			size = f.getSize() / 1024.f;
			vo.setSize(String.format("%.2f", size) + " KB");
			vo.setType(f.getSuffixname());
			vo.setUpdateTime(f.getUploadtime());
			data.add(vo);
		}
		page.setRows(data);
		return new ResultVO(page);
	}
}
