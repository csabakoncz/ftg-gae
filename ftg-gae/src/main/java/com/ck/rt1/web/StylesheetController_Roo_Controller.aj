// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ck.rt1.web;

import com.ck.rt1.model.Stylesheet;
import com.ck.rt1.web.StylesheetController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect StylesheetController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String StylesheetController.create(@Valid Stylesheet stylesheet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, stylesheet);
            return "stylesheets/create";
        }
        uiModel.asMap().clear();
        stylesheet.persist();
        return "redirect:/stylesheets/" + encodeUrlPathSegment(stylesheet.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String StylesheetController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Stylesheet());
        return "stylesheets/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String StylesheetController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("stylesheet", Stylesheet.findStylesheet(id));
        uiModel.addAttribute("itemId", id);
        return "stylesheets/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String StylesheetController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("stylesheets", Stylesheet.findStylesheetEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) Stylesheet.countStylesheets() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("stylesheets", Stylesheet.findAllStylesheets(sortFieldName, sortOrder));
        }
        return "stylesheets/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String StylesheetController.update(@Valid Stylesheet stylesheet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, stylesheet);
            return "stylesheets/update";
        }
        uiModel.asMap().clear();
        stylesheet.merge();
        return "redirect:/stylesheets/" + encodeUrlPathSegment(stylesheet.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String StylesheetController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Stylesheet.findStylesheet(id));
        return "stylesheets/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String StylesheetController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Stylesheet stylesheet = Stylesheet.findStylesheet(id);
        stylesheet.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/stylesheets";
    }
    
    void StylesheetController.populateEditForm(Model uiModel, Stylesheet stylesheet) {
        uiModel.addAttribute("stylesheet", stylesheet);
    }
    
    String StylesheetController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
