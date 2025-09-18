import java.util.*;

class Product {
  String id;
  String name;
  double price;
  int quantity;
  String category;

  Product(String id, String name, double price, int quantity, String category) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.category = category;
  }
}

public class InventoryManager {

  static Map<String, Product> inventory = new HashMap<>();
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    while (true) {
      System.out.println("1. Add Product");
      System.out.println("2. View Products");
      System.out.println("3. Search Product");
      System.out.println("4. Exit");

      int choice = Integer.parseInt(scanner.nextLine());

      if (choice == 1) {
        addProduct();
      } else if (choice == 2) {
        viewProducts();
      } else {
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

    inventory.put(id, new Product(id, name, price, quantity, category));

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
}
