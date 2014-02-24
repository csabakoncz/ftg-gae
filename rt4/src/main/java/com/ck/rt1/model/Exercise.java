package com.ck.rt1.model;

import com.google.appengine.datanucleus.annotations.Unowned;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = String.class, versionType = Long.class, finders = { "findExercisesByTitleLike", "findExercisesByContentLike" })
public class Exercise {

    private String title;

    @Size(max = 64000)
    @Lob
    private String content;

    @Unowned
    @NotNull
    @ManyToOne
    private Stylesheet stylesheet;

    @Unowned
    @NotNull
    @ManyToOne
    private Template template;
}
