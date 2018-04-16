/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.javafxapp1.entity;

/**
 *
 * @author student
 */
public class Achievement {

    public static Long getLastId() {
        return lastId;
    }

    public static void setLastId(Long aLastId) {
        lastId = aLastId;
    }
    
    private Long id;
    private String title;
    private String content;
    private String picture;
    
    private static Long lastId = 1L;
    
    public Achievement() {}

    public Achievement(String title, String content, String picture) {
        
        this.id = lastId;
        this.title = title;
        this.content = content;
        this.picture = picture;
        lastId++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    
}
