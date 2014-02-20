package com.ck.rt1.web;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ck.rt1.model.Exercise;

import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

@RequestMapping("/exercises")
@Controller
@RooWebScaffold(path = "exercises", formBackingObject = Exercise.class)
@RooWebJson(jsonObject = Exercise.class)
public class ExerciseController {

	@Autowired
	FreeMarkerConfig freeMarkerConfig;

	@RequestMapping(value = "/plain0/{id}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> showPlain(@PathVariable("id") String id,
			Model uiModel) throws IOException, Exception {
		Exercise exercise = Exercise.findExercise(id);
		Template template = freeMarkerConfig.getConfiguration().getTemplate(
				"exercises/show.ftl");
		// Template template =
		// freeMarkerConfig.getConfiguration().getTemplate("Puzzle.html");
		/* Create a data-model */
		Map root = new HashMap();
		root.put("exercise", exercise);
		/* Merge data-model with template */
		StringWriter out = new StringWriter();
		template.process(root, out);
		out.flush();
		uiModel.addAttribute("exercise", exercise);
		uiModel.addAttribute("itemId", id);
		HttpHeaders headers = new HttpHeaders();
		String body = out.toString();
		return new ResponseEntity<String>(body, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/plain1/{id}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> showPlain1(@PathVariable("id") String id,
			Model uiModel, HttpServletRequest request) throws IOException, Exception {
		
		String baseUrl = getBaseUrl(request);
		
		Exercise exercise = Exercise.findExercise(id);
		
		Template template = freeMarkerConfig.getConfiguration().getTemplate(
				"Puzzle.html");
		
		/* Create a data-model */
		Map root = new HashMap();
		root.put("exercise", exercise);
		root.put("baseUrl", baseUrl);
		/* Merge data-model with template */
		StringWriter out = new StringWriter();
		template.process(root, out);
		out.flush();
		uiModel.addAttribute("exercise", exercise);
		uiModel.addAttribute("itemId", id);
		HttpHeaders headers = new HttpHeaders();
		String body = out.toString();
		return new ResponseEntity<String>(body, headers, HttpStatus.OK);
	}

	private String getBaseUrl(HttpServletRequest request) {
		String url=request.getRequestURL().toString();
		String baseUrl=url.substring(0,url.indexOf("/exercises/"))+"/resources/gwt";
		return baseUrl;
	}

	@RequestMapping(value = "/plain/{id}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> showPlain2(@PathVariable("id") String id,
			Model uiModel, HttpServletRequest request) throws IOException, Exception {
		Exercise exercise = Exercise.findExercise(id);

		StringWriter out = new StringWriter();

		Configuration configuration = freeMarkerConfig.getConfiguration();
		TemplateLoader templateLoader = configuration.getTemplateLoader();

		StringTemplateLoader stl = new StringTemplateLoader();
		stl.putTemplate("t", exercise.getTemplate().getContent());
		try {
			configuration.setTemplateLoader(stl);
			Template template = configuration.getTemplate("t");

			/* Create a data-model */
			Map<String, Object> root = new HashMap();
			root.put("baseUrl", getBaseUrl(request));
			root.put("exercise", exercise);

			/* Merge data-model with template */
			template.process(root, out);
			out.flush();
		} finally {
			configuration.setTemplateLoader(templateLoader);
		}

		HttpHeaders headers = new HttpHeaders();
		String body = out.toString();
		return new ResponseEntity<String>(body, headers, HttpStatus.OK);
	}
}
