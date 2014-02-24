package com.ck.rt1.web;

import com.ck.rt1.model.Stylesheet;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stylesheets")
@Controller
@RooWebScaffold(path = "stylesheets", formBackingObject = Stylesheet.class)
@RooWebFinder
public class StylesheetController {
}
