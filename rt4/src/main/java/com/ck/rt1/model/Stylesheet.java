package com.ck.rt1.model;

import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = String.class, versionType = Long.class)
public class Stylesheet {

    private String name;

    @Size(max = 64000)
    @Lob
    private String content;
}
