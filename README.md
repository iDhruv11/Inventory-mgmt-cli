
# Inventory System

Basic inventory management in Java. Tracks products and stock.

## Features

- Add/remove products
- Track stock levels
- Search products
- Low stock alerts
- File storage
## Run it

```bash
javac InventoryManager.java
java InventoryManager
```

Uses text file to store data.

Products are stored in `inventory.txt` with format:
```
ID|Name|Category|Price|Quantity|Threshold|LastUpdated
```

Example:
```
ABC123|Laptop Computer|Electronics|999.99|15|5|2026-01-15 14:30:22
DEF456|Office Chair|Furniture|199.50|8|3|2026-01-15 15:45:10
```
## What it does

- Add/update/remove products with ID, name, category, price
- Track stock quantities with add/remove operations
- Set custom low stock thresholds per product
- Search by ID, name (partial match), or category
- View low stock alerts automatically
- Generate inventory reports with totals and categories
- File-based storage (inventory.txt)
- Text-based console interface only
- Single file storage 
- No user authentication
- No transaction history
