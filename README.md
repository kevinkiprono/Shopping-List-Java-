# Shopping List Application

A comprehensive Shopping List management application built with Java. This application provides both a console-based UI and a web-based interface for managing your shopping items efficiently.

## Features

- ✅ Add items to your shopping list
- ✅ Remove items from the list
- ✅ Mark items as completed/incomplete
- ✅ View all items, completed items, or incomplete items
- ✅ Filter items by category
- ✅ Calculate total price of items
- ✅ Persistent storage (local storage for web version)
- ✅ User-friendly interface

## Project Structure

```
Shopping List/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── shoppinglist/
│                   ├── model/
│                   │   └── ShoppingItem.java
│                   ├── service/
│                   │   └── ShoppingListService.java
│                   ├── ui/
│                   │   └── ShoppingListUI.java
│                   └── ShoppingListApp.java
├── shopping_list.html      (Web version)
├── pom.xml                 (Maven configuration)
└── README.md              (This file)
```

## Java Classes

### ShoppingItem (Model)
Represents a single item in the shopping list with:
- ID
- Name
- Quantity
- Category
- Completion Status
- Price

### ShoppingListService (Service)
Business logic layer handling:
- Add/Remove items
- Mark items as completed
- Filter by category
- Calculate total price
- Singleton pattern for single instance

### ShoppingListUI (UI)
Console-based user interface with menu-driven navigation

### ShoppingListApp (Main)
Entry point of the application

## How to Use

### Console Application

1. **Build the project:**
   ```bash
   mvn clean compile
   ```

2. **Run the application:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.shoppinglist.ShoppingListApp"
   ```

3. **Menu Options:**
   - Option 1: Add new item to the list
   - Option 2: View all items
   - Option 3: Mark item as completed
   - Option 4: Remove item from list
   - Option 5: View completed items
   - Option 6: View incomplete items
   - Option 7: Filter items by category
   - Option 8: Calculate total price
   - Option 9: Clear entire list
   - Option 10: Exit application

### Web Version

Open `shopping_list.html` in your web browser to use the interactive web-based shopping list.

## Requirements

- Java 11 or higher
- Maven 3.6 or higher
- Modern web browser (for web version)

## Technologies Used

- **Backend**: Java
- **Build Tool**: Maven
- **Frontend (Web)**: HTML5, CSS3, JavaScript
- **Design Pattern**: Singleton Pattern (Service Layer)

## License

This project is part of the Shopping-List-Java- repository

## Author

Kevin Kiprono
