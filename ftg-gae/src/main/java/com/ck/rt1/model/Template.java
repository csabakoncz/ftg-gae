package com.ck.rt1.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findTemplatesByContentLike", "findTemplatesByNameLike" })
public class Template {

    /**
     */
    private String name;

    /**
     */
    @Size(max = 64000)
    private String content;
}
