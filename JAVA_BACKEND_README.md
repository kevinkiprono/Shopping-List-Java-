# Shopping List Application - Java Backend Edition

## Overview
This is a complete Shopping List application with a **Java Spring Boot REST API backend** and an interactive HTML/CSS frontend. All event handlers and business logic are strictly implemented in Java, following enterprise-grade patterns.

## Architecture

### Backend (Java Spring Boot)
- **REST API Server** running on `http://localhost:8080`
- **Event Handlers** for all user interactions
- **Service Layer** for business logic
- **Controller Layer** for HTTP endpoints
- **Model Layer** for data structures

### Frontend (HTML/CSS/Fetch API)
- HTML/CSS interactive dashboard
- Communicates with Java backend via REST API
- All events trigger Java methods through HTTP calls
- No client-side business logic

## Project Structure

```
Shopping List/
├── src/main/java/com/shoppinglist/
│   ├── ShoppingListApplication.java          (Spring Boot Main)
│   ├── ShoppingListApp.java                   (Console App)
│   ├── model/
│   │   └── ShoppingItem.java                  (Data Model)
│   ├── service/
│   │   └── ShoppingListService.java           (Business Logic)
│   ├── controller/
│   │   └── ShoppingListController.java        (REST API Endpoints)
│   └── event/
│       └── ShoppingListEventHandler.java      (Event Handlers)
├── src/main/resources/
│   └── application.properties                 (Spring Configuration)
├── shopping_api_dashboard.html                (Frontend Dashboard)
├── shopping_dashboard.html                    (Legacy Dashboard)
├── pom.xml                                    (Maven Dependencies)
└── README.md
```

## Key Components

### 1. ShoppingListController.java
REST API endpoints for all operations:

```
POST   /api/items              - Create item (Java event handler)
GET    /api/items              - Retrieve all items
GET    /api/items/{id}         - Get single item
PUT    /api/items/{id}         - Update item (Java event handler)
DELETE /api/items/{id}         - Delete item (Java event handler)
PATCH  /api/items/{id}/toggle  - Toggle completion (Java event handler)
GET    /api/items/filter/completed     - Get completed items
GET    /api/items/filter/incomplete    - Get incomplete items
GET    /api/items/category/{category}  - Filter by category
GET    /api/items/stats/summary        - Get statistics
DELETE /api/items/clear         - Clear all items (Java event handler)
```

### 2. ShoppingListEventHandler.java
Handles all user events with Java methods:

```java
handleAddItem(name, quantity, category, price)
handleRemoveItem(id)
handleToggleCompletion(id)
handleUpdateItem(id, name, quantity, category, price)
handleFilterByCategory(category)
handleViewCompleted()
handleViewIncomplete()
handleClearAll()
handleViewStatistics()
handleSearchItems(searchTerm)
```

### 3. ShoppingListService.java
Core business logic:

```java
addItem(item)                   - Add new item
removeItem(id)                  - Remove item
updateItem(item)                - Update item
getItemById(id)                 - Get single item
getAllItems()                   - Get all items
getCompletedItems()             - Filter completed
getIncompleteItems()            - Filter incomplete
getItemsByCategory(category)    - Filter by category
markAsCompleted(id)             - Mark item complete
markAsIncompleted(id)           - Mark item incomplete
calculateTotalPrice()           - Calculate total
clearAll()                      - Clear all items
```

## How It Works

### Event Flow
1. **User clicks button in HTML** → 
2. **JavaScript calls fetch() to REST API** → 
3. **Java Controller receives HTTP request** → 
4. **EventHandler processes the request** → 
5. **Service updates data** → 
6. **Controller returns JSON response** → 
7. **JavaScript updates UI with response data**

### Example: Adding an Item
```
Frontend: User fills form and clicks "Add Item"
   ↓
JavaScript: POST to /api/items with item data
   ↓
Java Controller: @PostMapping handler validates input
   ↓
EventHandler: handleAddItem() processes event
   ↓
Service: addItem() stores in memory
   ↓
Controller: Returns JSON success response
   ↓
Frontend: Reloads items and shows notification
```

## Running the Application

### Method 1: Spring Boot (Recommended)

Prerequisites:
- Java 11+
- Maven 3.6+
- Port 8080 available

Steps:
```bash
# Build the project
mvn clean install

# Run Spring Boot server
mvn spring-boot:run

# In another terminal, open dashboard
start shopping_api_dashboard.html
```

### Method 2: Console Application
```bash
# Compile
javac -d bin src/main/java/com/shoppinglist/**/*.java

# Run
java -cp bin com.shoppinglist.ShoppingListApp
```

## API Response Format

### Success Response
```json
{
    "success": true,
    "data": { /* response data */ },
    "message": "Operation successful"
}
```

### Error Response
```json
{
    "success": false,
    "message": "Error description",
    "error": "Detailed error"
}
```

## Event Logging
All events are logged to console when triggered:
```
✓ Event: Item added - Milk
✓ Event: Item marked completed - Bread
✓ Event: Filtered items by category - Grocery (5 items)
✓ Event: Item removed - Eggs
```

## Features

### Frontend Features
- ✅ Interactive dashboard with statistics
- ✅ Real-time item list updates
- ✅ Filter by status (All, Pending, Completed)
- ✅ Responsive design (Desktop, Tablet, Mobile)
- ✅ Smooth animations and transitions
- ✅ Notification system

### Backend Features
- ✅ Complete CRUD operations
- ✅ Event-driven architecture
- ✅ Error handling and validation
- ✅ Statistics calculation
- ✅ Filter and search capabilities
- ✅ RESTful API design
- ✅ CORS enabled for cross-origin requests

## Dependencies

### Maven Dependencies
- Spring Boot Starter Web (REST API)
- Spring Boot Starter Data JPA (Database)
- H2 Database (Embedded)
- Lombok (Boilerplate reduction)
- JUnit (Testing)

## Configuration

Edit `src/main/resources/application.properties`:

```properties
server.port=8080                          # Server port
spring.datasource.url=jdbc:h2:mem:...     # Database URL
spring.jpa.hibernate.ddl-auto=create-drop # Auto create tables
logging.level.com.shoppinglist=DEBUG      # Debug logging
```

## Java Event Handler Example

```java
@Component
public class ShoppingListEventHandler {
    
    @Autowired
    private ShoppingListService service;
    
    public ShoppingItem handleAddItem(String name, int qty, String cat, double price) {
        // Validation
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name required");
        }
        
        // Create item
        ShoppingItem item = new ShoppingItem(
            String.valueOf(System.currentTimeMillis()),
            name.trim(),
            Math.max(1, qty),
            cat,
            Math.max(0, price)
        );
        
        // Add via service
        service.addItem(item);
        
        // Log event
        System.out.println("✓ Event: Item added - " + name);
        
        return item;
    }
}
```

## Testing Event Handlers

### Via REST API
```bash
# Add item
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{"name":"Milk","quantity":2,"category":"Grocery","price":3.50}'

# Toggle item
curl -X PATCH http://localhost:8080/api/items/{id}/toggle

# Delete item
curl -X DELETE http://localhost:8080/api/items/{id}

# Get statistics
curl http://localhost:8080/api/items/stats/summary
```

### Via Frontend
1. Open `shopping_api_dashboard.html`
2. Fill form and click "Add Item"
3. Check console logs for event output
4. Verify item appears in list
5. Click checkbox to toggle completion
6. Click trash icon to delete

## Advantages of This Approach

1. **Separation of Concerns** - Frontend handles UI, Backend handles logic
2. **Reusability** - API can be used by multiple clients
3. **Scalability** - Easy to add new features/endpoints
4. **Security** - Sensitive logic stays server-side
5. **Maintainability** - Changes in one place affect all clients
6. **Testing** - Each layer can be tested independently
7. **Enterprise Pattern** - Follows industry standards

## Development Notes

### Adding New Event Handler
1. Create method in `ShoppingListEventHandler.java`
2. Add corresponding endpoint in `ShoppingListController.java`
3. Call from frontend using `fetch()` API
4. Handle response and update UI

### Error Handling
All errors are caught and returned as JSON with proper HTTP status codes:
- 400: Bad Request (validation error)
- 404: Not Found (item doesn't exist)
- 500: Internal Server Error (unexpected issue)

## Future Enhancements

- [ ] Add database persistence (PostgreSQL)
- [ ] Implement authentication/authorization
- [ ] Add search functionality
- [ ] Implement sorting options
- [ ] Add export to CSV/PDF
- [ ] Create mobile app using same API
- [ ] Add WebSocket for real-time updates
- [ ] Implement caching strategies

## License

This project is open source and available under the MIT License.

## Author

Kevin Kiprono - Shopping List Application Developer

---

**API Server URL**: http://localhost:8080/api/items
**Frontend Dashboard**: shopping_api_dashboard.html
**Console Application**: java -cp bin com.shoppinglist.ShoppingListApp
