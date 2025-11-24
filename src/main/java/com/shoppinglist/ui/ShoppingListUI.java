package com.shoppinglist.ui;

import com.shoppinglist.model.ShoppingItem;
import com.shoppinglist.service.ShoppingListService;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Console UI for the Shopping List Application
 */
public class ShoppingListUI {
    private ShoppingListService service;
    private Scanner scanner;

    public ShoppingListUI() {
        this.service = ShoppingListService.getInstance();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display the main menu
     */
    public void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("       SHOPPING LIST APPLICATION");
        System.out.println("========================================");
        System.out.println("1. Add Item");
        System.out.println("2. View All Items");
        System.out.println("3. Mark Item as Completed");
        System.out.println("4. Remove Item");
        System.out.println("5. View Completed Items");
        System.out.println("6. View Incomplete Items");
        System.out.println("7. View Items by Category");
        System.out.println("8. Calculate Total Price");
        System.out.println("9. Clear All Items");
        System.out.println("10. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }

    /**
     * Add a new item to the shopping list
     */
    private void addItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter category (e.g., Grocery, Clothing, Electronics): ");
        String category = scanner.nextLine().trim();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        String id = UUID.randomUUID().toString();
        ShoppingItem item = new ShoppingItem(id, name, quantity, category, price);
        service.addItem(item);
    }

    /**
     * Display all items
     */
    private void viewAllItems() {
        List<ShoppingItem> items = service.getAllItems();
        if (items.isEmpty()) {
            System.out.println("\nNo items in the shopping list!");
            return;
        }

        System.out.println("\n--- All Items ---");
        for (int i = 0; i < items.size(); i++) {
            ShoppingItem item = items.get(i);
            String status = item.isCompleted() ? "[✓]" : "[ ]";
            System.out.printf("%d. %s %s - Qty: %d, Category: %s, Price: $%.2f%n",
                    i + 1, status, item.getName(), item.getQuantity(),
                    item.getCategory(), item.getPrice());
        }
    }

    /**
     * Mark item as completed
     */
    private void markAsCompleted() {
        viewAllItems();
        System.out.print("\nEnter item number to mark as completed: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            List<ShoppingItem> items = service.getAllItems();
            if (index >= 0 && index < items.size()) {
                service.markAsCompleted(items.get(index).getId());
            } else {
                System.out.println("Invalid item number!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    /**
     * Remove item
     */
    private void removeItem() {
        viewAllItems();
        System.out.print("\nEnter item number to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            List<ShoppingItem> items = service.getAllItems();
            if (index >= 0 && index < items.size()) {
                service.removeItem(items.get(index).getId());
            } else {
                System.out.println("Invalid item number!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    /**
     * View completed items
     */
    private void viewCompletedItems() {
        List<ShoppingItem> items = service.getCompletedItems();
        if (items.isEmpty()) {
            System.out.println("\nNo completed items!");
            return;
        }

        System.out.println("\n--- Completed Items ---");
        for (int i = 0; i < items.size(); i++) {
            ShoppingItem item = items.get(i);
            System.out.printf("%d. [✓] %s - Qty: %d, Category: %s, Price: $%.2f%n",
                    i + 1, item.getName(), item.getQuantity(),
                    item.getCategory(), item.getPrice());
        }
    }

    /**
     * View incomplete items
     */
    private void viewIncompleteItems() {
        List<ShoppingItem> items = service.getIncompleteItems();
        if (items.isEmpty()) {
            System.out.println("\nNo incomplete items!");
            return;
        }

        System.out.println("\n--- Incomplete Items ---");
        for (int i = 0; i < items.size(); i++) {
            ShoppingItem item = items.get(i);
            System.out.printf("%d. [ ] %s - Qty: %d, Category: %s, Price: $%.2f%n",
                    i + 1, item.getName(), item.getQuantity(),
                    item.getCategory(), item.getPrice());
        }
    }

    /**
     * View items by category
     */
    private void viewByCategory() {
        System.out.print("Enter category to search: ");
        String category = scanner.nextLine().trim();

        List<ShoppingItem> items = service.getItemsByCategory(category);
        if (items.isEmpty()) {
            System.out.println("\nNo items in category: " + category);
            return;
        }

        System.out.println("\n--- Items in " + category + " ---");
        for (int i = 0; i < items.size(); i++) {
            ShoppingItem item = items.get(i);
            String status = item.isCompleted() ? "[✓]" : "[ ]";
            System.out.printf("%d. %s %s - Qty: %d, Price: $%.2f%n",
                    i + 1, status, item.getName(), item.getQuantity(), item.getPrice());
        }
    }

    /**
     * Calculate and display total price
     */
    private void calculateTotalPrice() {
        double total = service.calculateTotalPrice();
        System.out.printf("\nTotal Price: $%.2f%n", total);
    }

    /**
     * Run the application
     */
    public void run() {
        boolean running = true;

        while (running) {
            displayMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addItem();
                        break;
                    case 2:
                        viewAllItems();
                        break;
                    case 3:
                        markAsCompleted();
                        break;
                    case 4:
                        removeItem();
                        break;
                    case 5:
                        viewCompletedItems();
                        break;
                    case 6:
                        viewIncompleteItems();
                        break;
                    case 7:
                        viewByCategory();
                        break;
                    case 8:
                        calculateTotalPrice();
                        break;
                    case 9:
                        service.clearAll();
                        break;
                    case 10:
                        System.out.println("\nThank you for using Shopping List Application!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        scanner.close();
    }
}
