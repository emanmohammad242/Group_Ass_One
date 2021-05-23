package com.example.groupassigment1.Models;

import java.io.Serializable;

public class Book implements Serializable {
    int id;
    String bookName;
    String bookPhoto;
    String bookCategory;
    String author;
    String publisher;
    String originalLanguage;
    String releaseDate;

    public Book() {}

    public Book(int id, String bookName, String bookPhoto, String bookCategory, String author, String publisher, String originalLanguage, String releaseDate) {
        this.id = id;
        this.bookName = bookName;
        this.bookPhoto = bookPhoto;
        this.bookCategory = bookCategory;
        this.author = author;
        this.publisher = publisher;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPhoto() {
        return bookPhoto;
    }

    public void setBookPhoto(String bookPhoto) {
        this.bookPhoto = bookPhoto;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", bookPhoto='" + bookPhoto + '\'' +
                ", bookCategory='" + bookCategory + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
