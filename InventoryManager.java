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
      System.out.println("2. Exit");

      int choice = Integer.parseInt(scanner.nextLine());

      if (choice == 1) {
        addProduct();
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
}
