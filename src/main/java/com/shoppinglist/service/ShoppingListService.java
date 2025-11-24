package com.shoppinglist.service;

import com.shoppinglist.model.ShoppingItem;
import java.util.*;

/**
 * Service class for managing shopping list operations
 */
public class ShoppingListService {
    private List<ShoppingItem> items;
    private static final ShoppingListService instance = new ShoppingListService();

    private ShoppingListService() {
        this.items = new ArrayList<>();
    }

    public static ShoppingListService getInstance() {
        return instance;
    }

    /**
     * Add a new item to the shopping list
     */
    public void addItem(ShoppingItem item) {
        if (item != null && !itemExists(item.getId())) {
            items.add(item);
            System.out.println("Item added: " + item.getName());
        } else {
            System.out.println("Item already exists or is null!");
        }
    }

    /**
     * Remove an item from the shopping list by ID
     */
    public void removeItem(String id) {
        items.removeIf(item -> item.getId().equals(id));
        System.out.println("Item removed with ID: " + id);
    }

    /**
     * Get all items in the shopping list
     */
    public List<ShoppingItem> getAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * Get item by ID
     */
    public ShoppingItem getItemById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Update an existing item
     */
    public void updateItem(ShoppingItem updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updatedItem.getId())) {
                items.set(i, updatedItem);
                System.out.println("Item updated: " + updatedItem.getName());
                return;
            }
        }
        System.out.println("Item not found!");
    }

    /**
     * Mark item as completed
     */
    public void markAsCompleted(String id) {
        ShoppingItem item = getItemById(id);
        if (item != null) {
            item.setCompleted(true);
            System.out.println("Item marked as completed: " + item.getName());
        }
    }

    /**
     * Mark item as not completed
     */
    public void markAsIncompleted(String id) {
        ShoppingItem item = getItemById(id);
        if (item != null) {
            item.setCompleted(false);
            System.out.println("Item marked as incomplete: " + item.getName());
        }
    }

    /**
     * Get all completed items
     */
    public List<ShoppingItem> getCompletedItems() {
        List<ShoppingItem> completed = new ArrayList<>();
        for (ShoppingItem item : items) {
            if (item.isCompleted()) {
                completed.add(item);
            }
        }
        return completed;
    }

    /**
     * Get all incomplete items
     */
    public List<ShoppingItem> getIncompleteItems() {
        List<ShoppingItem> incomplete = new ArrayList<>();
        for (ShoppingItem item : items) {
            if (!item.isCompleted()) {
                incomplete.add(item);
            }
        }
        return incomplete;
    }

    /**
     * Get items by category
     */
    public List<ShoppingItem> getItemsByCategory(String category) {
        List<ShoppingItem> categoryItems = new ArrayList<>();
        for (ShoppingItem item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                categoryItems.add(item);
            }
        }
        return categoryItems;
    }

    /**
     * Calculate total price of all items
     */
    public double calculateTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Check if item exists
     */
    private boolean itemExists(String id) {
        return items.stream().anyMatch(item -> item.getId().equals(id));
    }

    /**
     * Clear all items
     */
    public void clearAll() {
        items.clear();
        System.out.println("Shopping list cleared!");
    }

    /**
     * Get total number of items
     */
    public int getTotalItems() {
        return items.size();
    }
}
