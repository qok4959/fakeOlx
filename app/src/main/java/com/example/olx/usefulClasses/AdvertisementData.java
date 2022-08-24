package com.example.olx.usefulClasses;

import com.example.olx.model.AdvertisementModel;

import java.util.ArrayList;
import java.util.Map;

public class AdvertisementData {

    public String category, description, email, location, name, phoneNumber, price, title,id,date;
    public ArrayList<String> links;
    AdvertisementModel model;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AdvertisementModel getModel() {
        return model;
    }

    public void setModel(AdvertisementModel model) {
        this.model = model;
    }

    public AdvertisementData(){
        model = null;
    }

    public void fetchAllData(){
        model = new AdvertisementModel();
        model.retrieveAllData();
    }

    public AdvertisementData(String category, String description, String email, String location, String name, String phoneNumber, String price, String title, ArrayList<String> links, String id) {
        this.category = category;
        this.description = description;
        this.email = email;
        this.location = location;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.title = title;
        this.links = links;
        this.id = id;
        this.date = java.time.LocalDate.now().toString();
    }

    public AdvertisementData asignData(Map<String,Object> data){
        this.category = data.getOrDefault("category", "default").toString();
        this.description = data.getOrDefault("description", "default").toString();
        this.email = data.getOrDefault("email", "default").toString();
        this.location = data.getOrDefault("location", "default").toString();
        this.name = data.getOrDefault("name", "default").toString();
        this.phoneNumber = data.getOrDefault("phoneNumber", "default").toString();
        this.price = data.getOrDefault("price", "default").toString();
        this.title = data.getOrDefault("title", "default").toString();
        this.links = (ArrayList<String>) data.getOrDefault("links", "default");
        this.id = data.getOrDefault("id", "default").toString();
        this.date = java.time.LocalDate.now().toString();
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }
}
