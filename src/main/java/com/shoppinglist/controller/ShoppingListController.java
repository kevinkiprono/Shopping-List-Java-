package com.shoppinglist.controller;

import com.shoppinglist.model.ShoppingItem;
import com.shoppinglist.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Shopping List API
 * Handles all HTTP requests for CRUD operations
 */
@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ShoppingListController {

    @Autowired
    private ShoppingListService service;

    /**
     * GET /api/items - Retrieve all items
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllItems() {
        try {
            List<ShoppingItem> items = service.getAllItems();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", items);
            response.put("count", items.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleException(e, "Failed to retrieve items");
        }
    }

    /**
     * GET /api/items/{id} - Retrieve item by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getItemById(@PathVariable String id) {
        try {
            ShoppingItem item = service.getItemById(id);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Item not found"));
            }
            return ResponseEntity.ok(createSuccessResponse(item, "Item retrieved successfully"));
        } catch (Exception e) {
            return handleException(e, "Failed to retrieve item");
        }
    }

    /**
     * POST /api/items - Create new item
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createItem(@RequestBody ShoppingItem item) {
        try {
            if (item.getName() == null || item.getName().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Item name is required"));
            }

            service.addItem(item);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createSuccessResponse(item, "Item created successfully"));
        } catch (Exception e) {
            return handleException(e, "Failed to create item");
        }
    }

    /**
     * PUT /api/items/{id} - Update item
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateItem(@PathVariable String id,
            @RequestBody ShoppingItem updatedItem) {
        try {
            ShoppingItem existingItem = service.getItemById(id);
            if (existingItem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Item not found"));
            }

            updatedItem.setId(id);
            service.updateItem(updatedItem);
            return ResponseEntity.ok(createSuccessResponse(updatedItem, "Item updated successfully"));
        } catch (Exception e) {
            return handleException(e, "Failed to update item");
        }
    }

    /**
     * DELETE /api/items/{id} - Delete item
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteItem(@PathVariable String id) {
        try {
            ShoppingItem item = service.getItemById(id);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Item not found"));
            }

            service.removeItem(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item deleted successfully");
            response.put("itemName", item.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleException(e, "Failed to delete item");
        }
    }

    /**
     * PATCH /api/items/{id}/toggle - Toggle item completion status
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Map<String, Object>> toggleCompletion(@PathVariable String id) {
        try {
            ShoppingItem item = service.getItemById(id);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Item not found"));
            }

            if (item.isCompleted()) {
                service.markAsIncompleted(id);
            } else {
                service.markAsCompleted(id);
            }

            item.setCompleted(!item.isCompleted());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item completion status updated");
            response.put("itemName", item.getName());
            response.put("completed", item.isCompleted());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleException(e, "Failed to toggle item");
        }
    }

    /**
     * GET /api/items/filter/completed - Get completed items
     */
    @GetMapping("/filter/completed")
    public ResponseEntity<Map<String, Object>> getCompletedItems() {
        try {
            List<ShoppingItem> items = service.getCompletedItems();
            return ResponseEntity.ok(createSuccessResponse(items, "Completed items retrieved"));
        } catch (Exception e) {
            return handleException(e, "Failed to retrieve completed items");
        }
    }

    /**
     * GET /api/items/filter/incomplete - Get incomplete items
     */
    @GetMapping("/filter/incomplete")
    public ResponseEntity<Map<String, Object>> getIncompleteItems() {
        try {
            List<ShoppingItem> items = service.getIncompleteItems();
            return ResponseEntity.ok(createSuccessResponse(items, "Incomplete items retrieved"));
        } catch (Exception e) {
            return handleException(e, "Failed to retrieve incomplete items");
        }
    }

    /**
     * GET /api/items/category/{category} - Get items by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getItemsByCategory(@PathVariable String category) {
        try {
            List<ShoppingItem> items = service.getItemsByCategory(category);
            return ResponseEntity.ok(createSuccessResponse(items, "Items in category retrieved"));
        } catch (Exception e) {
            return handleException(e, "Failed to retrieve items by category");
        }
    }

    /**
     * GET /api/items/stats/summary - Get shopping list statistics
     */
    @GetMapping("/stats/summary")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        try {
            List<ShoppingItem> items = service.getAllItems();
            List<ShoppingItem> completed = service.getCompletedItems();
            double totalPrice = service.calculateTotalPrice();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalItems", items.size());
            stats.put("completedItems", completed.size());
            stats.put("incompleteItems", items.size() - completed.size());
            stats.put("totalPrice", String.format("%.2f", totalPrice));
            stats.put("averagePrice", items.isEmpty() ? 0 : String.format("%.2f", totalPrice / items.size()));
            stats.put("progressPercentage",
                    items.isEmpty() ? 0 : Math.round((double) completed.size() / items.size() * 100));

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", stats);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleException(e, "Failed to retrieve statistics");
        }
    }

    /**
     * DELETE /api/items/clear - Clear all items
     */
    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, Object>> clearAll() {
        try {
            service.clearAll();
            return ResponseEntity.ok(createSuccessResponse(null, "All items cleared successfully"));
        } catch (Exception e) {
            return handleException(e, "Failed to clear items");
        }
    }

    // Helper methods

    /**
     * Create success response
     */
    private Map<String, Object> createSuccessResponse(Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        response.put("message", message);
        return response;
    }

    /**
     * Create error response
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }

    /**
     * Handle exceptions
     */
    private ResponseEntity<Map<String, Object>> handleException(Exception e, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
