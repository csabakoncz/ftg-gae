// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ck.rt1.model;

import com.ck.rt1.model.Stylesheet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Stylesheet_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Stylesheet.entityManager;
    
    public static final List<String> Stylesheet.fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "content");
    
    public static final EntityManager Stylesheet.entityManager() {
        EntityManager em = new Stylesheet().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    @Transactional
    public static long Stylesheet.countStylesheets() {
        return findAllStylesheets().size();
    }
    
    @Transactional
    public static List<Stylesheet> Stylesheet.findAllStylesheets() {
        return entityManager().createQuery("SELECT o FROM Stylesheet o", Stylesheet.class).getResultList();
    }
    
    @Transactional
    public static List<Stylesheet> Stylesheet.findAllStylesheets(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Stylesheet o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Stylesheet.class).getResultList();
    }
    
    @Transactional
    public static Stylesheet Stylesheet.findStylesheet(Long id) {
        if (id == null) return null;
        return entityManager().find(Stylesheet.class, id);
    }
    
    @Transactional
    public static List<Stylesheet> Stylesheet.findStylesheetEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Stylesheet o", Stylesheet.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public static List<Stylesheet> Stylesheet.findStylesheetEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Stylesheet o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Stylesheet.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void Stylesheet.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Stylesheet.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Stylesheet attached = Stylesheet.findStylesheet(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Stylesheet.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Stylesheet.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Stylesheet Stylesheet.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Stylesheet merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
