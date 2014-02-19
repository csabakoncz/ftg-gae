package com.ck.rt1.model;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = String.class, versionType = Long.class)
public class Exercise {

    private String title;

    @Size(max = 64000)
    private String content;

    @NotNull
    @ManyToOne
    private Stylesheet stylesheet;

    @NotNull
    @ManyToOne
    private Template template;
}
