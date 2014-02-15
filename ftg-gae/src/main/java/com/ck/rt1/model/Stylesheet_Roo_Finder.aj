// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ck.rt1.model;

import com.ck.rt1.model.Stylesheet;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Stylesheet_Roo_Finder {
    
    public static Long Stylesheet.countFindStylesheetsByContentLike(String content) {
        if (content == null || content.length() == 0) throw new IllegalArgumentException("The content argument is required");
        content = content.replace('*', '%');
        if (content.charAt(0) != '%') {
            content = "%" + content;
        }
        if (content.charAt(content.length() - 1) != '%') {
            content = content + "%";
        }
        EntityManager em = Stylesheet.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Stylesheet AS o WHERE LOWER(o.content) LIKE LOWER(:content)", Long.class);
        q.setParameter("content", content);
        return ((Long) q.getSingleResult());
    }
    
    public static Long Stylesheet.countFindStylesheetsByNameLike(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        name = name.replace('*', '%');
        if (name.charAt(0) != '%') {
            name = "%" + name;
        }
        if (name.charAt(name.length() - 1) != '%') {
            name = name + "%";
        }
        EntityManager em = Stylesheet.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Stylesheet AS o WHERE LOWER(o.name) LIKE LOWER(:name)", Long.class);
        q.setParameter("name", name);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<Stylesheet> Stylesheet.findStylesheetsByContentLike(String content) {
        if (content == null || content.length() == 0) throw new IllegalArgumentException("The content argument is required");
        content = content.replace('*', '%');
        if (content.charAt(0) != '%') {
            content = "%" + content;
        }
        if (content.charAt(content.length() - 1) != '%') {
            content = content + "%";
        }
        EntityManager em = Stylesheet.entityManager();
        TypedQuery<Stylesheet> q = em.createQuery("SELECT o FROM Stylesheet AS o WHERE LOWER(o.content) LIKE LOWER(:content)", Stylesheet.class);
        q.setParameter("content", content);
        return q;
    }
    
    public static TypedQuery<Stylesheet> Stylesheet.findStylesheetsByContentLike(String content, String sortFieldName, String sortOrder) {
        if (content == null || content.length() == 0) throw new IllegalArgumentException("The content argument is required");
        content = content.replace('*', '%');
        if (content.charAt(0) != '%') {
            content = "%" + content;
        }
        if (content.charAt(content.length() - 1) != '%') {
            content = content + "%";
        }
        EntityManager em = Stylesheet.entityManager();
        String jpaQuery = "SELECT o FROM Stylesheet AS o WHERE LOWER(o.content) LIKE LOWER(:content)";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<Stylesheet> q = em.createQuery(jpaQuery, Stylesheet.class);
        q.setParameter("content", content);
        return q;
    }
    
    public static TypedQuery<Stylesheet> Stylesheet.findStylesheetsByNameLike(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        name = name.replace('*', '%');
        if (name.charAt(0) != '%') {
            name = "%" + name;
        }
        if (name.charAt(name.length() - 1) != '%') {
            name = name + "%";
        }
        EntityManager em = Stylesheet.entityManager();
        TypedQuery<Stylesheet> q = em.createQuery("SELECT o FROM Stylesheet AS o WHERE LOWER(o.name) LIKE LOWER(:name)", Stylesheet.class);
        q.setParameter("name", name);
        return q;
    }
    
    public static TypedQuery<Stylesheet> Stylesheet.findStylesheetsByNameLike(String name, String sortFieldName, String sortOrder) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        name = name.replace('*', '%');
        if (name.charAt(0) != '%') {
            name = "%" + name;
        }
        if (name.charAt(name.length() - 1) != '%') {
            name = name + "%";
        }
        EntityManager em = Stylesheet.entityManager();
        String jpaQuery = "SELECT o FROM Stylesheet AS o WHERE LOWER(o.name) LIKE LOWER(:name)";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<Stylesheet> q = em.createQuery(jpaQuery, Stylesheet.class);
        q.setParameter("name", name);
        return q;
    }
    
}
