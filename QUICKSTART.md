# Quick Start Guide - Shopping List Java Backend

## ✅ What's Now in Place

✓ **Java Spring Boot REST API Server**
✓ **Event Handler System** (all events use Java)
✓ **REST Endpoints** for all operations
✓ **HTML/CSS Dashboard** (communicates via API)
✓ **Complete Documentation**

## 🚀 Running the Application

### Option 1: Spring Boot Server (Recommended for Java Backend)

```bash
# Navigate to project folder
cd "c:\Users\user\Desktop\Shopping List"

# Install dependencies (if Maven is installed)
mvn clean install

# Start the server
mvn spring-boot:run
```

The server will start on: **http://localhost:8080**

### Option 2: Without Maven (Using Compiled Classes)

```bash
# The classes are already compiled in bin/ folder
java -cp bin com.shoppinglist.ShoppingListApplication
```

### Option 3: Console Application (No Server)

```bash
java -cp bin com.shoppinglist.ShoppingListApp
```

## 📊 Using the Dashboard

### With Server Running:
1. Start the Java backend server (see above)
2. Open file: `shopping_api_dashboard.html` in browser
3. Dashboard will communicate with Java API

### Dashboard Features:
- Add items via form (triggers Java event handler)
- Click checkboxes to mark complete (Java event: handleToggleCompletion)
- Delete items (Java event: handleRemoveItem)
- Filter items (Java events: handleViewCompleted, handleViewIncomplete)
- Real-time statistics from Java backend

## 🔌 API Endpoints

### Create Item
```bash
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{"name":"Milk","quantity":2,"category":"Grocery","price":3.50}'
```
**Triggers Java Event**: `handleAddItem()`

### Get All Items
```bash
curl http://localhost:8080/api/items
```

### Toggle Item Completion
```bash
curl -X PATCH http://localhost:8080/api/items/{item-id}/toggle
```
**Triggers Java Event**: `handleToggleCompletion()`

### Delete Item
```bash
curl -X DELETE http://localhost:8080/api/items/{item-id}
```
**Triggers Java Event**: `handleRemoveItem()`

### Get Statistics
```bash
curl http://localhost:8080/api/items/stats/summary
```
**Triggers Java Event**: `handleViewStatistics()`

### Get Completed Items
```bash
curl http://localhost:8080/api/items/filter/completed
```
**Triggers Java Event**: `handleViewCompleted()`

### Get Incomplete Items
```bash
curl http://localhost:8080/api/items/filter/incomplete
```
**Triggers Java Event**: `handleViewIncomplete()`

## 📝 Event Handler Methods (All in Java)

Located in: `src/main/java/com/shoppinglist/event/ShoppingListEventHandler.java`

```java
handleAddItem()              // Add new item
handleRemoveItem()           // Remove item
handleToggleCompletion()     // Mark complete/incomplete
handleUpdateItem()           // Update item details
handleFilterByCategory()     // Filter by category
handleViewCompleted()        // View completed items
handleViewIncomplete()        // View incomplete items
handleClearAll()             // Clear all items
handleViewStatistics()       // Get statistics
handleSearchItems()          // Search items
handleSortItems()            // Sort items
handleExportList()           // Export list
handleImportList()           // Import list
```

## 🎯 Event Flow Example

### User Action: Add Item
```
User fills form and clicks "Add Item"
         ↓
JavaScript: fetch POST to /api/items
         ↓
Java Controller: @PostMapping endpoint receives request
         ↓
Java EventHandler: handleAddItem() processes
         ↓
Java Service: addItem() stores data
         ↓
Controller: Returns JSON response
         ↓
JavaScript: Updates UI with response
         ↓
User sees new item in list
```

**NO JavaScript business logic involved!** ✓

## 📂 File Structure

```
Project Root/
├── src/main/java/com/shoppinglist/
│   ├── ShoppingListApplication.java          ← Spring Boot Main
│   ├── controller/
│   │   └── ShoppingListController.java       ← REST Endpoints
│   ├── event/
│   │   └── ShoppingListEventHandler.java     ← Java Event Handlers
│   ├── service/
│   │   └── ShoppingListService.java          ← Business Logic
│   └── model/
│       └── ShoppingItem.java                 ← Data Model
├── src/main/resources/
│   └── application.properties                ← Spring Config
├── shopping_api_dashboard.html               ← Frontend Dashboard
├── pom.xml                                   ← Maven Config
└── JAVA_BACKEND_README.md                   ← Full Documentation
```

## 🔍 Monitoring Events

When running the server, you'll see events logged in console:

```
✓ Event: Item added - Milk
✓ Event: Item marked completed - Bread
✓ Event: Filtered items by category - Grocery (5 items)
✓ Event: Item removed - Eggs
```

## ⚙️ System Requirements

- **Java**: 11 or higher
- **Maven**: 3.6+ (optional, classes already compiled)
- **Port 8080**: Must be available
- **Browser**: Modern browser (Chrome, Firefox, Edge, Safari)

## 🐛 Troubleshooting

### Port 8080 Already in Use
```bash
# Find what's using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID)
taskkill /PID <PID> /F

# Or change port in application.properties:
server.port=8081
```

### Maven Not Installed
- Download and install Maven from: https://maven.apache.org/
- Or use pre-compiled classes with: `java -cp bin com.shoppinglist.ShoppingListApplication`

### Dashboard Not Connecting
1. Ensure server is running on port 8080
2. Check browser console (F12) for errors
3. Verify CORS is enabled in controller (@CrossOrigin)
4. Check server logs for request details

## 📚 Additional Resources

- **API Documentation**: See endpoint descriptions above
- **Event Handler Details**: Check `ShoppingListEventHandler.java`
- **Full README**: Read `JAVA_BACKEND_README.md`
- **Service Logic**: See `ShoppingListService.java`

## ✨ Key Advantages

✓ **100% Java Event Handling** - No JavaScript business logic
✓ **REST API** - Can be consumed by any client
✓ **Event-Driven** - Clean architecture with event handlers
✓ **Type-Safe** - Java strong typing prevents errors
✓ **Scalable** - Enterprise-grade patterns
✓ **Maintainable** - Clear separation of concerns
✓ **Testable** - Each component can be tested independently

---

**Need Help?**
Check the console output for event logs and error messages. All events are logged showing exactly what Java methods are being called.

**Happy Shopping!** 🛒
