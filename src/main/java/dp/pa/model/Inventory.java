package dp.pa.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The main handler for parts and products of the app.
 * An inventory can contain many parts and products, and
 * can be initialized the app with test data.
 */
public class Inventory {

    /**The list of all parts in the inventory*/
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**The list of all products in the inventory*/
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**The part count used to assign unique int ID to parts*/
    private static int partCount;

    /**The part count used to assign unique int ID to products*/
    private static int productCount;

    static {
        partCount = 0;
        productCount = 0;
        addTestData();
    }

    /**
     @return a unique integer to assign to a part id
     */
    public static int getPartCount() {
        return partCount;
    }

    /**
     @return a unique integer to assign to a product id
     */
    public static int getProductCount() {
        return productCount;
    }

    /**
     * Populate the inventory with test data.
     * Call this function in the static initializer.
     */
    public static void addTestData() {
        InHouse part1 = new InHouse(0, "Part 1", 9.99, 5, 1, 9, 999);
        Outsourced part2 = new Outsourced(1, "Part 2", 19.99, 3, 1, 9, "Company 1");
        InHouse part3 = new InHouse(2, "Part 3", 69.99, 9, 1, 9, 909);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Product prod1 = new Product(0, "Product 1", 59.99, 3,1, 9);
        prod1.addAssociatedPart(part1);
        Product prod2 = new Product(1, "Product 2", 199.99, 2,1, 9);
        prod2.addAssociatedPart(part1);
        prod2.addAssociatedPart(part2);
        Product prod3 = new Product(2, "Product 3", 430.99, 1,1, 9);
        prod3.addAssociatedPart(part1);
        prod3.addAssociatedPart(part2);
        prod3.addAssociatedPart(part2);
        prod3.addAssociatedPart(part3);
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
    }

    /**
     Adds a part to the inventory and increments the current part counter.
     @param newPart the part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
        partCount++;
    }

    /**
     Adds a product to the inventory and increments the current product counter.
     @param newProduct the product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        productCount++;
    }

    /**
     Looks up a part in the inventory by id.
     @param partId the id to search for
     @return part if found, else null
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     Looks up a product in the inventory by id.
     @param productId the id to search for
     @return product if found, else null
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     Looks up parts in the inventory by name.
     A part name that contains the parameter string is a match.
     @param name the name to search for
     @return a list of parts that match the param
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(name)) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**
     Looks up products in the inventory by name.
     A product name that contains the parameter string is a match.
     @param name the name to search for
     @return a list of products that match the param
     */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(name)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     Replaces a part in the inventory with a modified part.
     @param index the index of the part in the list to replace
     @param selectedPart the replacement part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     Replaces a product in the inventory with a modified product.
     @param index the index of the part in the list to replace
     @param newProduct the replacement product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     Deletes a part from the inventory.
     @param selectedPart the part to delete
     @return true if successful, else false
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     Deletes a product from the inventory.
     @param selectedProduct the part to delete
     @return true if successful, else false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     @return a list of all parts in the inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     @return a list of all products in the inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
