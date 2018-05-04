package com.study.demo.base.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.study.demo.config.ExportTemplateProperties;
import com.study.demo.constant.ApplicationEnum;
import com.study.demo.entity.ExportTemplate;
import com.study.demo.entity.User;
import com.study.demo.service.IExportTemplateService;
import com.study.demo.utils.bean.ApplicationUtil;
import com.study.demo.utils.bean.BeanUtils;
import com.study.demo.utils.file.ExportUtil;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.ResultVO;

@Controller
@RequestMapping("/api/export")
public class ExportTemplateController {

	@Autowired
	private ExportTemplateProperties exportTemplateProperties;
	@Autowired
	private IExportTemplateService exportTemplateService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 列表查询导出模板信息
	 * 
	 * @param pagesize
	 * @param pagenum
	 * @param template
	 * @return
	 */
	@PostMapping("/page/{pagesize}/{pagenum}")
	@ResponseBody
	@RequiresPermissions("export:manager")
	@SuppressWarnings("rawtypes")
	public ResultVO page(@PathVariable("pagesize") int pagesize, @PathVariable("pagenum") int pagenum,
			@RequestBody ExportTemplate template) {
		PageVO page = new PageVO(pagenum, pagesize);
		Map whereMap = BeanUtils.beanToMap(template);
		exportTemplateService.page(page, whereMap);
		return new ResultVO(page);
	}

	/**
	 * 模板文件上传
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	@ResponseBody
	public ResultVO upload(@RequestParam(value = "file", required = true) MultipartFile file) {
		if (file.isEmpty()) {
			return new ResultVO(ApplicationEnum.FILE_NOT_EMPTY);
		}
		try {
			String newFileName = saveFile(file);
			String fileName = file.getOriginalFilename();
			Map<String, String> data = new HashMap<String, String>();
			data.put("fileName", fileName);
			data.put("newFileName", newFileName);
			return new ResultVO(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultVO(ApplicationEnum.FILE_UPLOAD_ERROR);
	}

	@GetMapping("/download")
	public void download(HttpServletResponse response, @RequestParam("name") String name) {
		if (StringUtils.isEmpty(name)) {
			responseOutWithJson(response, new ResultVO(ApplicationEnum.PARAM_ERROR));
			return;
		}
		// 查询数据信息
		Map<String, String> whereMap = new HashMap<String, String>();
		whereMap.put("name", name);
		List<ExportTemplate> templates = exportTemplateService.list(whereMap);
		if (templates.size() > 0) {
			ExportTemplate template = templates.get(0);
			File file = new File(exportTemplateProperties.getDir() + template.getNewFileName());
			if (file.exists()) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/force-download");
				response.addHeader("Content-Disposition", "attachment;fileName=" + template.getFileName());
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return;
			}
		}
		responseOutWithJson(response, new ResultVO(ApplicationEnum.FILE_NOT_EXISTS));
	}

	/**
	 * 以JSON格式输出
	 * 
	 * @param response
	 */
	protected void responseOutWithJson(HttpServletResponse response, Object responseObject) {
		// 将实体对象转换为JSON Object转换
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String json = JSON.toJSONString(responseObject);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private String saveFile(MultipartFile source) throws IllegalStateException, IOException {
		String fileName = source.getOriginalFilename();
		logger.info("上传的模板名：" + fileName);
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		logger.info("上传的文件后缀名：" + suffixName);
		// 生成新的名字
		String newFileName = UUID.randomUUID() + suffixName;
		File dest = new File(exportTemplateProperties.getDir() + newFileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		// 保存文件
		source.transferTo(dest);
		return newFileName;
	}

	/**
	 * 保存模板信息
	 * 
	 * @param template
	 * @return
	 */
	@PostMapping("/save")
	@ResponseBody
	public ResultVO save(@RequestBody ExportTemplate template) {
		User currentUser = ApplicationUtil.getCurrentUser();
		if (template.getId() == null) {
			template.setCreater(currentUser.getId());
		} else {
			template.setUpdater(currentUser.getId());
		}
		exportTemplateService.save(template);
		return new ResultVO();
	}

	@GetMapping("/delete/{id}")
	@ResponseBody
	public ResultVO delete(@PathVariable("id") Long id) {
		ExportTemplate template = exportTemplateService.getOne(id);
		File file = new File(exportTemplateProperties.getDir() + template.getNewFileName());
		file.delete();
		exportTemplateService.delete(id);
		return new ResultVO();
	}

	@GetMapping("/get/{id}")
	@ResponseBody
	public ResultVO getExportTemplate(@PathVariable("id") Long id) {
		ExportTemplate template = exportTemplateService.getOne(id);
		return new ResultVO(template);
	}

	@GetMapping("/test")
	public void exportTest(HttpServletResponse response) {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("name", "hi");
		dataMap.put("score1", "10");
		dataMap.put("score2", "8");
		dataMap.put("score3", "9");
		File file = ExportUtil.createDocument(dataMap, "test");
		if (file.exists()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return;
		}
	}

}
