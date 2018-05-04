package com.study.demo.utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.demo.config.ExportTemplateProperties;
import com.study.demo.constant.ApplicationEnum;
import com.study.demo.entity.ExportTemplate;
import com.study.demo.exceptions.ApplicationException;
import com.study.demo.service.IExportTemplateService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ExportUtil {

	@Autowired
	private ExportTemplateProperties exportTemplatePropertiesAutowired;
	@Autowired
	private IExportTemplateService exportTemplateServiceAutowired;

	private static IExportTemplateService exportTemplateService;

	private static ExportTemplateProperties exportTemplateProperties;

	private static Configuration configuration = null;

	static {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	@PostConstruct
	public void init() {
		exportTemplateProperties = this.exportTemplatePropertiesAutowired;
		exportTemplateService = exportTemplateServiceAutowired;
		try {
			configuration.setDirectoryForTemplateLoading(new File(exportTemplateProperties.getDir()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createDocument(Map<?, ?> dataMap, String templateFileName, String exportFileName) {
		File outFile = new File(exportTemplateProperties.getTemp() + exportFileName);
		try {
			Template template = configuration.getTemplate(templateFileName);
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}

			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
			template.process(dataMap, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outFile;
	}

	public static File createDocument(Map<?, ?> dataMap, String templateName) {
		Map<String, String> whereMap = new HashMap<String, String>();
		whereMap.put("name", templateName);
		List<ExportTemplate> templates = exportTemplateService.list(whereMap);
		if (templates.size() == 0) {
			throw new ApplicationException(ApplicationEnum.TEMPLATE_NOT_FOUND);
		}
		ExportTemplate template = templates.get(0);
		File outFile = createDocument(dataMap, template.getNewFileName(), template.getExportName());
		return outFile;
	}

}
