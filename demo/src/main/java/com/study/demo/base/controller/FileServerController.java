package com.study.demo.base.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.study.demo.config.FileServerProperties;
import com.study.demo.constant.ApplicationEnum;
import com.study.demo.constant.ValidEnum;
import com.study.demo.entity.FileInfo;
import com.study.demo.service.IFileInfoService;
import com.study.demo.utils.MD5Util;
import com.study.demo.vo.ResultVO;

@Controller
@RequestMapping("/api/file")
public class FileServerController {

	@Autowired
	private FileServerProperties fileServerProperties;
	@Autowired
	private IFileInfoService fileInfoService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/upload")
	@ResponseBody
	public ResultVO upload(@RequestParam(value = "file", required = true) MultipartFile file) {
		if (file.isEmpty()) {
			return new ResultVO(ApplicationEnum.FILE_NOT_EMPTY);
		}
		try {
			String md5 = saveFile(file);
			return new ResultVO(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultVO(ApplicationEnum.FILE_UPLOAD_ERROR);
	}

	@PostMapping("/batch/upload")
	@ResponseBody
	public ResultVO uploadMore(HttpServletRequest request) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		List<String> data = new ArrayList<String>();
		for (MultipartFile f : files) {
			if (f.isEmpty()) {
				return new ResultVO(ApplicationEnum.FILE_NOT_EMPTY);
			}
			try {
				String md5 = saveFile(f);
				data.add(md5);
			} catch (Exception e) {
				logger.error("发生异常：", e);
			}
		}
		return new ResultVO(data);

	}

	@GetMapping("/download/{fileId}")
	public void download(HttpServletResponse response, @PathVariable("fileId") String fileId) {
		if (StringUtils.isEmpty(fileId)) {
			responseOutWithJson(response, new ResultVO(ApplicationEnum.PARAM_ERROR));
			return;
		}
		// 查询数据信息
		Map<String, String> whereMap = new HashMap<String, String>();
		whereMap.put("md5", fileId);
		whereMap.put("valid", ValidEnum.Y.name());
		List<FileInfo> infos = fileInfoService.list(whereMap);
		if (infos.size() > 0) {
			FileInfo info = infos.get(0);
			File file = new File(fileServerProperties.getDir() + info.getNewname());
			if (file.exists()) {
				response.setContentType("application/force-download");
				response.addHeader("Content-Disposition", "attachment;fileName=" + info.getName());
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

	private String saveFile(MultipartFile source) throws Exception {
		// 计算文件md5 MultipartFile计算md5;
		String md5 = MD5Util.getMD5(source);
		// 查询是否存在
		Map<String, String> whereMap = new HashMap<String, String>();
		whereMap.put("md5", md5);
		whereMap.put("valid", ValidEnum.Y.name());
		List<FileInfo> infos = fileInfoService.list(whereMap);
		// 否
		if (infos.size() < 1) {
			String fileName = source.getOriginalFilename();
			logger.info("上传的文件名：" + fileName);
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			logger.info("上传的文件后缀名：" + suffixName);
			// 生成新的名字
			String newFileName = UUID.randomUUID() + suffixName;
			File dest = new File(fileServerProperties.getDir() + newFileName);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			// 保存文件
			source.transferTo(dest);
			// 保存数据记录
			Date dateTime = new Date();
			FileInfo fileInfo = new FileInfo();
			fileInfo.setName(fileName);
			fileInfo.setNewname(newFileName);
			fileInfo.setSize(source.getSize());
			fileInfo.setSuffixname(suffixName);
			fileInfo.setMd5(md5);
			fileInfo.setUploadtime(dateTime);
			fileInfo.setValid(ValidEnum.Y.name());
			fileInfo.setCreatetime(dateTime);
			fileInfoService.save(fileInfo);
		} else {
			// 判断文件是否存在
			FileInfo info = infos.get(0);
			File file = new File(fileServerProperties.getDir() + info.getMd5() + info.getSuffixname());
			if (!file.exists()) {
				// 保存文件
				File dest = new File(fileServerProperties.getDir() + info.getNewname());
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				// 保存文件
				source.transferTo(dest);
			}
		}
		return md5;
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
}
