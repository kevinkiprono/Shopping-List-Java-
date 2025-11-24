package com.shoppinglist.model;

/**
 * Represents a single item in the shopping list
 */
public class ShoppingItem {
    private String id;
    private String name;
    private int quantity;
    private String category;
    private boolean completed;
    private double price;

    public ShoppingItem(String id, String name, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.completed = false;
        this.price = 0.0;
    }

    public ShoppingItem(String id, String name, int quantity, String category, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.completed = false;
        this.price = price;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", completed=" + completed +
                ", price=" + price +
                '}';
    }
}
