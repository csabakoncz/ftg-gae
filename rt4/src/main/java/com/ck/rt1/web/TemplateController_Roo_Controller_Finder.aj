// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ck.rt1.web;

import com.ck.rt1.model.Template;
import com.ck.rt1.web.TemplateController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect TemplateController_Roo_Controller_Finder {
    
    @RequestMapping(params = { "find=ByContentLike", "form" }, method = RequestMethod.GET)
    public String TemplateController.findTemplatesByContentLikeForm(Model uiModel) {
        return "templates/findTemplatesByContentLike";
    }
    
    @RequestMapping(params = "find=ByContentLike", method = RequestMethod.GET)
    public String TemplateController.findTemplatesByContentLike(@RequestParam("content") String content, Model uiModel) {
        uiModel.addAttribute("templates", Template.findTemplatesByContentLike(content).getResultList());
        return "templates/list";
    }
    
    @RequestMapping(params = { "find=ByNameLike", "form" }, method = RequestMethod.GET)
    public String TemplateController.findTemplatesByNameLikeForm(Model uiModel) {
        return "templates/findTemplatesByNameLike";
    }
    
    @RequestMapping(params = "find=ByNameLike", method = RequestMethod.GET)
    public String TemplateController.findTemplatesByNameLike(@RequestParam("name") String name, Model uiModel) {
        uiModel.addAttribute("templates", Template.findTemplatesByNameLike(name).getResultList());
        return "templates/list";
    }
    
}
