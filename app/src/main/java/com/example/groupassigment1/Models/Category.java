package com.example.groupassigment1.Models;

import java.io.Serializable;

public class Category implements Serializable {
    int id;
    String categoryName;
    String categoryPhoto;

    public Category() { }

    public Category(int id, String categoryName, String categoryPhoto) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryPhoto = categoryPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPhoto() {
        return categoryPhoto;
    }

    public void setCategoryPhoto(String categoryPhoto) {
        this.categoryPhoto = categoryPhoto;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryPhoto='" + categoryPhoto + '\'' +
                '}';
    }
}
