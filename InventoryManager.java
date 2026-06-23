import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

class Product {
  private String id;
  private String name;
  private double price;
  private int quantity;
  private LocalDateTime lastUpdated;
  private String category;
  private int lowStockThreshold;

  Product(String id, String name, double price, int quantity, String category, int lowStock) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.category = category;
    this.lowStockThreshold = lowStock;
    this.lastUpdated = LocalDateTime.now();
  }

  void addStock(int amount) {
    this.quantity += amount;
    this.lastUpdated = LocalDateTime.now();
  }

  boolean removeStock(int amount) {
    if (amount > this.quantity) {
      return false;
    }
    this.quantity -= amount;
    this.lastUpdated = LocalDateTime.now();
    return true;
  }

  boolean isLowStock() {
    return quantity <= lowStockThreshold;
  }

  void setLowStockThreshold(int threshold) {
    this.lowStockThreshold = threshold;
    this.lastUpdated = LocalDateTime.now();
  }

  String getId() {
    return id;
  }

  String getName() {
    return name;
  }

  String getCategory() {
    return category;
  }

  double getPrice() {
    return price;
  }

  int getQuantity() {
    return quantity;
  }

  int getLowStockThreshold() {
    return lowStockThreshold;
  }

  double getTotalValue() {
    return price * quantity;
  }

  LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  void setName(String name) {
    this.name = name;
    this.lastUpdated = LocalDateTime.now();
  }

  void setCategory(String category) {
    this.category = category;
    this.lastUpdated = LocalDateTime.now();
  }

  void setPrice(double price) {
    this.price = price;
    this.lastUpdated = LocalDateTime.now();

  }

  @Override
  public String toString() {
    return id + " | " +
        name + " | " +
        category + " | " +
        price + " | " +
        quantity + " | " +
        getTotalValue();
  }
}

public class InventoryManager {

  static Map<String, Product> inventory = new HashMap<>();
  static Scanner scanner = new Scanner(System.in);
  static final String FILE_NAME = "inventory.txt";

  public static void main(String[] args) {
    loadInventory();
    while (true) {
      System.out.println("1. Add Product");
      System.out.println("2. View Products");
      System.out.println("3. Search Product");
      System.out.println("4. Update Stock");
      System.out.println("5. Low Stock Threshold");
      System.out.println("6. Delete Product");
      System.out.println("7. Inventory Report");
      System.out.println("8. Update Product");
      System.out.println("9. Low Stock Items");
      System.out.println("10. Exit");

      int choice = getIntInput();

      if (choice == 1) {
        addProduct();
      } else if (choice == 2) {
        viewProducts();
      } else if (choice == 3) {
        searchProduct();
      } else if (choice == 4) {
        updateStock();
      } else if (choice == 5) {
        updateThreshold();
      } else if (choice == 6) {
        deleteProduct();
      } else if (choice == 7) {
        showReport();
      } else if (choice == 8) {
        updateProduct();
      } else if (choice == 9) {
        showLowStockItems();
      } else if (choice == 10) {
        saveInventory();
        break;
      } else {
        System.out.println("Invalid choice");
      }
    }
  }

  static void addProduct() {

    System.out.print("ID: ");
    String id = scanner.nextLine().trim().toUpperCase();

    System.out.print("Category: ");
    String category = scanner.nextLine();

    System.out.print("Name: ");
    String name = scanner.nextLine();

    System.out.print("Price: ");
    double price = getDoubleInput();

    System.out.print("Quantity: ");
    int quantity = getIntInput();

    System.out.print("Low stock threshold: ");
    int threshold = Integer.parseInt(scanner.nextLine());

    if (inventory.containsKey(id)) {
      System.out.println("Product already exists");
      return;
    }
    if (quantity <= threshold) {
      System.out.println("Product is already below threshold");
    }
    inventory.put(id, new Product(id, name, price, quantity, category, threshold));
    System.out.println("Added");
  }

  static void viewProducts() {

    if (inventory.isEmpty()) {
      System.out.println("No products");
      return;
    }
    List<Product> products = new ArrayList<>(inventory.values());

    products.sort((a, b) -> a.getId().compareTo(b.getId()));
    for (Product p : products) {
      printProduct(p);
    }
  }

  static void searchProduct() {

    System.out.println("1. Search by ID");
    System.out.println("2. Search by Name");
    System.out.println("3. Search by Category");

    int choice = getIntInput();

    if (choice == 1) {
      searchById();
    } else if (choice == 2) {
      searchByName();
    } else if (choice == 3) {
      searchByCategory();
    }
  }

  static void searchById() {

    System.out.print("ID: ");
    String id = scanner.nextLine().trim().toUpperCase();
    Product p = inventory.get(id);
    if (p == null) {
      System.out.println("Not found");
      return;
    }
    printProduct(p);
  }

  static void searchByName() {
    System.out.print("Name: ");
    String search = scanner.nextLine();
    boolean found = false;

    for (Product p : inventory.values()) {
      if (p.getName().toLowerCase()
          .contains(search.toLowerCase())) {
        found = true;
        printProduct(p);
      }
    }

    if (!found) {
      System.out.println("No results");
    }
  }

  static void searchByCategory() {
    System.out.print("Category: ");
    String search = scanner.nextLine();
    boolean found = false;
    for (Product p : inventory.values()) {
      if (p.getCategory().toLowerCase()
          .contains(search.toLowerCase())) {
        found = true;
        printProduct(p);
      }
    }

    if (!found) {
      System.out.println("No results");
    }
  }

  static void updateStock() {

    System.out.print("Product ID: ");
    String id = scanner.nextLine().trim().toUpperCase();
    Product p = inventory.get(id);

    if (p == null) {
      System.out.println("Not found");
      return;
    }

    System.out.println("1. Add");
    System.out.println("2. Remove");

    int choice = getIntInput();
    System.out.print("Amount: ");
    int amount = getIntInput();

    if (choice == 1) {
      p.addStock(amount);
      System.out.println("New quantity = " + p.getQuantity());
    } else if (choice == 2) {

      if (!p.removeStock(amount)) {
        System.out.println("Not enough stock");
        return;
      }

      if (p.isLowStock()) {
        System.out.println("LOW STOCK WARNING");
      }
      System.out.println("New quantity = " + p.getQuantity());
    }
  }

  static void deleteProduct() {

    System.out.print("Enter product id: ");
    String id = scanner.nextLine().trim().toUpperCase();
    Product removed = inventory.remove(id);

    if (removed == null) {
      System.out.println("Product not found");
    } else {
      System.out.println("Deleted");
    }
  }

  static void showReport() {

    int totalProducts = inventory.size();
    int totalQuantity = 0;
    double totalValue = 0;
    int lowStock = 0;
    Map<String, Integer> categories = new HashMap<>();

    System.out.println("\nProducts By Category");
    for (Product p : inventory.values()) {
      totalQuantity += p.getQuantity();
      totalValue += p.getTotalValue();
      if (p.isLowStock()) {
        lowStock++;
      }
      String category = p.getCategory();
      categories.put(category, categories.getOrDefault(category, 0) + 1);
    }
    for (Map.Entry<String, Integer> entry : categories.entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }

    System.out.println("Products: " + totalProducts);
    System.out.println("Quantity: " + totalQuantity);
    System.out.println("Value: " + totalValue);
    System.out.println("Low stock products: " + lowStock);
  }

  static void saveInventory() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    try {
      PrintWriter writer = new PrintWriter(FILE_NAME);
      for (Product p : inventory.values()) {
        writer.println(
            p.getId() + "|" +
                p.getName() + "|" +
                p.getCategory() + "|" +
                p.getPrice() + "|" +
                p.getQuantity() + "|" +
                p.getLowStockThreshold() + "|" +
                p.getLastUpdated()
                    .format(formatter));
      }
      writer.close();
      System.out.println("Inventory saved");
    } catch (Exception e) {
      System.out.println("Save failed");
    }
  }

  static void loadInventory() {

    try {
      File file = new File(FILE_NAME);
      if (!file.exists()) {
        return;
      }
      Scanner fileScanner = new Scanner(file);
      while (fileScanner.hasNextLine()) {
        try {
          String line = fileScanner.nextLine();
          String[] parts = line.split("\\|");
          if (parts.length < 6) {
            continue;
          }

          Product p = new Product(
              parts[0],
              parts[1],
              Double.parseDouble(parts[3]),
              Integer.parseInt(parts[4]),
              parts[2],
              Integer.parseInt(parts[5]));
          inventory.put(p.getId(), p);

        } catch (NumberFormatException e) {
          System.out.println("Skipping Bad Record");
        }
      }

      fileScanner.close();
      System.out.println("Loaded " + inventory.size() + " products");

    } catch (Exception e) {
      System.out.println("Load failed");
    }
  }

  static void updateProduct() {

    System.out.print("Product ID: ");
    String id = scanner.nextLine().trim().toUpperCase();
    Product p = inventory.get(id);
    if (p == null) {
      System.out.println("Not found");
      return;
    }
    System.out.println("Current Product:");
    printProduct(p);
    System.out.println("1. Name");
    System.out.println("2. Category");
    System.out.println("3. Price");

    int choice = getIntInput();
    if (choice == 1) {
      System.out.print("New name: ");
      p.setName(scanner.nextLine());
    } else if (choice == 2) {
      System.out.print("New category: ");
      p.setCategory(scanner.nextLine());
    } else if (choice == 3) {
      System.out.print("New price: ");
      p.setPrice(getDoubleInput());
    }
  }

  static void printProduct(Product p) {
    System.out.println(p);
  }

  static int getIntInput() {
    while (true) {
      try {
        return Integer.parseInt(scanner.nextLine());
      } catch (Exception e) {
        System.out.println("Enter a valid number");
      }
    }
  }

  static double getDoubleInput() {
    while (true) {
      try {
        return Double.parseDouble(scanner.nextLine());
      } catch (Exception e) {
        System.out.println("Enter a valid number");
      }
    }
  }

  static void showLowStockItems() {
    List<Product> lowStock = new ArrayList<>();
    for (Product p : inventory.values()) {
      if (p.isLowStock()) {
        lowStock.add(p);
      }
    }
    if (lowStock.isEmpty()) {
      System.out.println("No low stock items");
      return;
    }
    System.out.println("LOW STOCK ALERT");
    System.out.println("Items below threshold: " + lowStock.size());
    for (Product p : lowStock) {
      printProduct(p);
    }
  }

  static void updateThreshold() {
    System.out.print("Product ID: ");
    String id = scanner.nextLine()
        .trim()
        .toUpperCase();

    Product p = inventory.get(id);
    if (p == null) {
      System.out.println("Product not found");
      return;
    }

    System.out.println("Current threshold: " + p.getLowStockThreshold());
    System.out.print("New threshold: ");
    int threshold = getIntInput();
    p.setLowStockThreshold(threshold);
    System.out.println("Threshold updated");
  }

}
