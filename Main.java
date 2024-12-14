import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Inventory Management System ---");
            System.out.println("1. Display Inventory");
            System.out.println("2. Add New Item");
            System.out.println("3. Sell Item");
            System.out.println("4. Exit");

            int choice = manager.getValidIntegerInput("Choose an option: ");
            switch (choice) {
                case 1:
                    manager.displayInventory();
                    break;
                case 2:
                    String name = scanner.nextLine();
                    double price = manager.getValidIntegerInput("Enter the price: ");
                    int quantity = manager.getValidIntegerInput("Enter the quantity: ");
                    manager.addItem(new Item(name, price, quantity));
                    break;
                case 3:
                    name = scanner.nextLine();
                    quantity = manager.getValidIntegerInput("Enter the quantity to sell: ");
                    manager.sellItem(name, quantity);
                    break;
                case 4:
                    manager.saveInventory();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
