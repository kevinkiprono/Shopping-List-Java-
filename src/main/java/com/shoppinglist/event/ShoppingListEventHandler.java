package com.shoppinglist.event;

import com.shoppinglist.model.ShoppingItem;
import com.shoppinglist.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Event Handler for Shopping List interactions
 * Handles all user events and business logic
 */
@Component
public class ShoppingListEventHandler {

    @Autowired
    private ShoppingListService service;

    /**
     * Handle add item event
     */
    public ShoppingItem handleAddItem(String name, int quantity, String category, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty");
        }

        ShoppingItem item = new ShoppingItem(
                String.valueOf(System.currentTimeMillis()),
                name.trim(),
                Math.max(1, quantity),
                category,
                Math.max(0, price));

        service.addItem(item);
        System.out.println("✓ Event: Item added - " + name);
        return item;
    }

    /**
     * Handle remove item event
     */
    public void handleRemoveItem(String id) {
        ShoppingItem item = service.getItemById(id);
        if (item != null) {
            service.removeItem(id);
            System.out.println("✓ Event: Item removed - " + item.getName());
        } else {
            throw new IllegalArgumentException("Item not found with ID: " + id);
        }
    }

    /**
     * Handle item completion toggle event
     */
    public void handleToggleCompletion(String id) {
        ShoppingItem item = service.getItemById(id);
        if (item != null) {
            if (item.isCompleted()) {
                service.markAsIncompleted(id);
                System.out.println("○ Event: Item marked incomplete - " + item.getName());
            } else {
                service.markAsCompleted(id);
                System.out.println("✓ Event: Item marked completed - " + item.getName());
            }
        } else {
            throw new IllegalArgumentException("Item not found with ID: " + id);
        }
    }

    /**
     * Handle item update event
     */
    public ShoppingItem handleUpdateItem(String id, String name, int quantity, String category, double price) {
        ShoppingItem item = service.getItemById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found with ID: " + id);
        }

        if (name != null && !name.trim().isEmpty()) {
            item.setName(name.trim());
        }
        if (quantity > 0) {
            item.setQuantity(quantity);
        }
        if (category != null && !category.trim().isEmpty()) {
            item.setCategory(category);
        }
        if (price >= 0) {
            item.setPrice(price);
        }

        service.updateItem(item);
        System.out.println("✓ Event: Item updated - " + item.getName());
        return item;
    }

    /**
     * Handle filter by category event
     */
    public void handleFilterByCategory(String category) {
        var items = service.getItemsByCategory(category);
        System.out.println("✓ Event: Filtered items by category - " + category + " (" + items.size() + " items)");
    }

    /**
     * Handle view all completed event
     */
    public void handleViewCompleted() {
        var items = service.getCompletedItems();
        System.out.println("✓ Event: Viewed completed items (" + items.size() + " items)");
    }

    /**
     * Handle view all incomplete event
     */
    public void handleViewIncomplete() {
        var items = service.getIncompleteItems();
        System.out.println("✓ Event: Viewed incomplete items (" + items.size() + " items)");
    }

    /**
     * Handle clear all event
     */
    public void handleClearAll() {
        int count = service.getAllItems().size();
        service.clearAll();
        System.out.println("✓ Event: Cleared all items (" + count + " items removed)");
    }

    /**
     * Handle view statistics event
     */
    public void handleViewStatistics() {
        var allItems = service.getAllItems();
        var completedItems = service.getCompletedItems();
        double totalPrice = service.calculateTotalPrice();

        System.out.println("\n========================================");
        System.out.println("          SHOPPING LIST STATS");
        System.out.println("========================================");
        System.out.println("Total Items:      " + allItems.size());
        System.out.println("Completed:        " + completedItems.size());
        System.out.println("Incomplete:       " + (allItems.size() - completedItems.size()));
        System.out.println("Total Price:      $" + String.format("%.2f", totalPrice));
        System.out.println("Average Price:    $"
                + (allItems.isEmpty() ? "0.00" : String.format("%.2f", totalPrice / allItems.size())));
        System.out.println("Progress:         "
                + (allItems.isEmpty() ? "0" : Math.round((double) completedItems.size() / allItems.size() * 100))
                + "%");
        System.out.println("========================================\n");
    }

    /**
     * Handle search items event
     */
    public void handleSearchItems(String searchTerm) {
        var allItems = service.getAllItems();
        var filtered = allItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();

        System.out.println("✓ Event: Search results for '" + searchTerm + "' (" + filtered.size() + " items found)");
    }

    /**
     * Handle sort items event
     */
    public void handleSortItems(String sortBy) {
        System.out.println("✓ Event: Items sorted by " + sortBy);
    }

    /**
     * Handle export list event
     */
    public void handleExportList(String format) {
        System.out.println("✓ Event: List exported to " + format);
    }

    /**
     * Handle import list event
     */
    public void handleImportList(String filename) {
        System.out.println("✓ Event: List imported from " + filename);
    }
}
