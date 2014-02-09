package com.ck.rt1.web;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.ck.rt1.model.Exercise;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
	
    @RequestMapping(value = "/plain/{id}", produces = "text/html")
    @ResponseBody
    public ResponseEntity<String> showPlain(@PathVariable("id") Long id, Model uiModel) throws IOException, Exception {
        Exercise exercise = Exercise.findExercise(id);
        Template template = freeMarkerConfig.getConfiguration().getTemplate("exercises/show.ftl");
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
        
        String  body= out.toString();
        return new ResponseEntity<String>(body, headers, HttpStatus.OK);
    }

}
