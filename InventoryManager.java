import java.util.*;

class Product {
  String id;
  String name;
  double price;
  int quantity;

  Product(String id, String name, double price, int quantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
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

    System.out.print("Name: ");
    String name = scanner.nextLine();

    System.out.print("Price: ");
    double price = Double.parseDouble(scanner.nextLine());

    System.out.print("Quantity: ");
    int quantity = Integer.parseInt(scanner.nextLine());

    inventory.put(id, new Product(id, name, price, quantity));

    System.out.println("Added");
  }

  static void viewProducts() {

    if (inventory.isEmpty()) {
      System.out.println("No products");
      return;
    }
    for (Product p : inventory.values()) {
      System.out.println(
          p.id + " " +
              p.name + " " +
              p.price + " " +
              p.quantity);
    }
  }

  static void searchProduct() {
    System.out.print("Enter product id: ");
    String id = scanner.nextLine();
    Product p = inventory.get(id);

    if (p == null) {
      System.out.println("Not found");
      return;
    }
    System.out.println(
        p.id + " " +
            p.name + " " +
            p.price + " " +
            p.quantity);
  }
}
