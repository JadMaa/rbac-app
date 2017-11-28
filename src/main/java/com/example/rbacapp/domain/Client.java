package com.example.rbacapp.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "clients")
public class Client {

    private String name;
    private String description;
    private String type;
    private Date creationDate;

    public Client(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.creationDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
