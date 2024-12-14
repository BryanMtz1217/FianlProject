import java.io.*;
import java.util.*;

public class InventoryManager {

    private ArrayList<Item> itemsInStock;
    private Item[] archivedItems;
    private final String stockFile = "inventory_stock.txt";
    private final String archivedFile = "archived_items.txt";
    
    public InventoryManager() {
        itemsInStock = new ArrayList<>();
        archivedItems = new Item[10]; // Array to store archived sold-out items.
        loadInventory();
    }

    public void addItem(Item item) {
        itemsInStock.add(item);
    }

    public void sellItem(String name, int quantity) {
        boolean found = false;
        for (Item item : itemsInStock) {
            if (item.getName().equals(name) && item.getQuantity() >= quantity) {
                item.decreaseQuantity(quantity);
                if (item.getQuantity() == 0) {
                    archiveItem(item);
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Item not found or insufficient stock.");
        }
    }

    private void archiveItem(Item item) {
        for (int i = 0; i < archivedItems.length; i++) {
            if (archivedItems[i] == null) {
                archivedItems[i] = item;
                break;
            }
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Item item : itemsInStock) {
            System.out.println(item);
        }
    }

    public void saveInventory() {
        try (ObjectOutputStream stockOut = new ObjectOutputStream(new FileOutputStream(stockFile));
             ObjectOutputStream archivedOut = new ObjectOutputStream(new FileOutputStream(archivedFile))) {
            stockOut.writeObject(itemsInStock);
            archivedOut.writeObject(archivedItems);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public void loadInventory() {
        try (ObjectInputStream stockIn = new ObjectInputStream(new FileInputStream(stockFile));
             ObjectInputStream archivedIn = new ObjectInputStream(new FileInputStream(archivedFile))) {
            itemsInStock = (ArrayList<Item>) stockIn.readObject();
            archivedItems = (Item[]) archivedIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    // Recursive input validation
    public int getValidIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        try {
            int input = Integer.parseInt(scanner.nextLine());
            return input;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return getValidIntegerInput(prompt);  // Recursive call for valid input
        }
    }
}
