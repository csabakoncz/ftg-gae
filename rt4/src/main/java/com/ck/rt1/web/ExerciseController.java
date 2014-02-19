package com.ck.rt1.web;

import com.ck.rt1.model.Exercise;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/exercises")
@Controller
@RooWebScaffold(path = "exercises", formBackingObject = Exercise.class)
public class ExerciseController {
}
