import java.util.*;

class Product {
  String id;
  String name;
  double price;
  int quantity;
  String category;
  int lowStockThreshold;

  Product(String id, String name, double price, int quantity, String category, int lowStock) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.category = category;
    this.lowStockThreshold = lowStock;
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
      System.out.println("5. Delete Product");
      System.out.println("6. Inventory Report");
      System.out.println("7. Exit");

      int choice = Integer.parseInt(scanner.nextLine());

      if (choice == 1) {
        addProduct();
      } else if (choice == 2) {
        viewProducts();
        else if(choice == 3) {
        searchProduct();
      } else if(choice == 4) {
        updateStock();
      } else if(choice == 5) {
        deleteProduct();
      } else if(choice == 6) {
        showReport();
      } else{
        saveInventory();
        break;
      }
    }
  }

  static void addProduct() {

    System.out.print("ID: ");
    String id = scanner.nextLine();

    System.out.print("Category: ");
    String category = scanner.nextLine();

    System.out.print("Name: ");
    String name = scanner.nextLine();

    System.out.print("Price: ");
    double price = Double.parseDouble(scanner.nextLine());

    System.out.print("Quantity: ");
    int quantity = Integer.parseInt(scanner.nextLine());

    System.out.print("Low stock threshold: ");
    int threshold = Integer.parseInt(scanner.nextLine());

    inventory.put(id, new Product(id, name, price, quantity, category, threshold));

    System.out.println("Added");
  }

  static void viewProducts() {

    if (inventory.isEmpty()) {
      System.out.println("No products");
      return;
    }
    for (Product p : inventory.values()) {
      System.out.println(
        p.id + " | " +
        p.name + " | " +
        p.category + " | " +
        p.price + " | " +
        p.quantity
      )
    }
  }

  static void searchProduct() {

    System.out.println("1. Search by ID");
    System.out.println("2. Search by Name");
    System.out.println("3. Search by Category");

    int choice = Integer.parseInt(scanner.nextLine());

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
    String id = scanner.nextLine();
    Product p = inventory.get(id);
    if (p == null) {
      System.out.println("Not found");
      return;
    }

    System.out.println(
        p.id + " | " +
            p.name + " | " +
            p.category + " | " +
            p.price + " | " +
            p.quantity);
  }

  static void searchByName() {
    System.out.print("Name: ");
    String search = scanner.nextLine();
    boolean found = false;

    for (Product p : inventory.values()) {
      if (p.name.toLowerCase()
          .contains(search.toLowerCase())) {
        found = true;
        System.out.println(
            p.id + " | " +
                p.name + " | " +
                p.category + " | " +
                p.price + " | " +
                p.quantity);
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
      if (p.category.toLowerCase()
          .contains(search.toLowerCase())) {
        found = true;

        System.out.println(
            p.id + " | " +
                p.name + " | " +
                p.category + " | " +
                p.price + " | " +
                p.quantity);
      }
    }

    if (!found) {
      System.out.println("No results");
    }
  }

  static void updateStock() {

    System.out.print("Product ID: ");
    String id = scanner.nextLine();
    Product p = inventory.get(id);

    if (p == null) {
      System.out.println("Not found");
      return;
    }

    System.out.println("1. Add");
    System.out.println("2. Remove");

    int choice = Integer.parseInt(scanner.nextLine());
    System.out.print("Amount: ");
    int amount = Integer.parseInt(scanner.nextLine());

    if (choice == 1) {
      p.quantity += amount;
      System.out.println("New quantity = " + p.quantity);
    } else if (choice == 2) {

      if (amount > p.quantity) {
        System.out.println("Not enough stock");
        return;
      }
      p.quantity -= amount;

      if (p.quantity <= p.lowStockThreshold) {
        System.out.println("LOW STOCK WARNING");
      }
      System.out.println("New quantity = " + p.quantity);
    }
  }

  static void deleteProduct() {

    System.out.print("Enter product id: ");
    String id = scanner.nextLine();
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

    for (Product p : inventory.values()) {
      totalQuantity += p.quantity;
      totalValue += p.price * p.quantity;
      if (p.quantity <= p.lowStockThreshold) {
        lowStock++;
      }
    }

    System.out.println("Products: " + totalProducts);
    System.out.println("Quantity: " + totalQuantity);
    System.out.println("Value: " + totalValue);
    System.out.println("Low stock products: " + lowStock);
  }

  static void saveInventory() {

    try {
      PrintWriter writer = new PrintWriter(FILE_NAME);
      for (Product p : inventory.values()) {
        writer.println(
          p.id + "," +
          p.name + "," +
          p.category + "," +
          p.price + "," +
          p.quantity + "," +
          p.lowStockThreshold)
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
        String line = fileScanner.nextLine();
        String[] parts = line.split(",");
        if (parts.length != 6) {
          continue;
        }

        Product p = new Product(
          parts[0],
          parts[1],
          parts[2],
          Double.parseDouble(parts[3]),
          Integer.parseInt(parts[4]),
          Integer.parseInt(parts[5]))
        inventory.put(p.id, p);
      }

      fileScanner.close();
      System.out.println("Loaded inventory");

    } catch (Exception e) {
      System.out.println("Load failed");
    }
  }
}
