package dp.pa.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Represents a product to be handled by the Inventory app.
 * Has 0 or more associated Parts.
 * Has a unique id but all other fields can be non-unique.
 */
public class Product {

    /**The list of parts of a product*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**The product id*/
    private int id;

    /**The product name*/
    private String name;

    /**The product price*/
    private double price;

    /**The product stock/inv*/
    private int stock;

    /**The product min inv*/
    private int min;

    /**The product max inv*/
    private int max;

    /**The product constructor*/
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock (inventory)
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock/inventory to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the minimum stock/inventory that can be held
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the minimum stock/inventory to be set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the maximum stock/inventory that can be held
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the maximum stock/inventory to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     Adds a part to the product's list of associated parts.
     @param part the part to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     Deletes a part from the product's list of associated parts.
     @param selectedAssociatedPart the part to delete
     * */
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
    }

    /**
     @return the list parts for a product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

    /**
     Removes all parts from a product.
     */
    public void clearParts() {
        associatedParts.clear();
    }
}
