package dp.pa.controller;

import dp.pa.model.Inventory;
import dp.pa.model.Part;
import dp.pa.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The controller for the Add Product Menu of the GUI. */
public class AddProductMenuController extends BaseController {

    /**List that holds associated parts of a product*/
    private static ObservableList<Part> addedParts = FXCollections.observableArrayList();

    /**Field for user to input product name*/
    @FXML
    private TextField nameTextField;

    /**Field for user to input product inv*/
    @FXML
    private TextField invTextField;

    /**Field for user to input product price*/
    @FXML
    private TextField priceTextField;

    /**Field for user to input product min inv*/
    @FXML
    private TextField minTextField;

    /**Field for user to input product max inv*/
    @FXML
    private TextField maxTextField;

    /**Field for user to input search for product (ID or Name)*/
    @FXML
    private TextField searchPart;

    /**Table that holds all parts in Inventory*/
    @FXML
    private TableView partsTableView;

    /**Parts table col that holds part ID*/
    @FXML
    private TableColumn partIdCol;

    /**Parts table col that holds part Name*/
    @FXML
    private TableColumn partNameCol;

    /**Parts table col that holds part Inv*/
    @FXML
    private TableColumn partInvCol;

    /**Parts table col that holds part Price*/
    @FXML
    private TableColumn partPriceCol;

    /**Table that holds all parts added to Product*/
    @FXML
    private TableView addedPartsTableView;

    /**AddedParts table col that holds part ID*/
    @FXML
    private TableColumn addedPartIdCol;

    /**AddedParts table col that holds part Name*/
    @FXML
    private TableColumn addedPartNameCol;

    /**AddedParts table col that holds part Inv*/
    @FXML
    private TableColumn addedPartInvCol;

    /**AddedParts table col that holds part Price*/
    @FXML
    private TableColumn addedPartPriceCol;

    /**
     Initializes the parts and associated parts tableviews.
     @param url the URL
     @param resourceBundle the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the data source of the parts table
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // set the data source of the added parts table
        addedPartsTableView.setItems(addedParts);
        addedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     Adds a part from the parts tableview to the associated parts tableview.
     If no part is selected, nothing occurs.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnActionAddPartToProduct(ActionEvent actionEvent) {

        // retrieve selected part
        Part part = (Part)partsTableView.getSelectionModel().getSelectedItem();

        // check for null
        if (part == null) {
            return;
        }

        // add part to list of added parts
        addedParts.add(part);
    }

    /**
     Removes a part from the associated parts tableview.
     Asks for confirmation of removal via dialog box.
     If no part is selected, nothing occurs.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnActionRemovePartFromProduct(ActionEvent actionEvent) {

        // retrieve selected part
        Part part = (Part)addedPartsTableView.getSelectionModel().getSelectedItem();

        // check for null
        if (part == null) {
            return;
        }

        // pop up box to confirm part removal
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this associated part?");
        confirm.setTitle("Confirm Removal");
        Optional<ButtonType> result = confirm.showAndWait();
        // act upon user selection
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // remove part from product - popup box if failure
            if (!(addedParts.remove(part))) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setContentText("Could not remove.");
                error.showAndWait();
            }
        }
    }

    /**
     On click of Save button, saves a new product to the Inventory and redirects user to main menu.
     Retrieves user input from the form to create a new product object.
     Prevents invalid/empty data input and displays errors via dialog boxes.
     @param actionEvent the event that triggers the method
     @throws IOException exception
     */
    @FXML
    public void OnActionSaveProductMainMenu(ActionEvent actionEvent) throws IOException {

        // set up alert box for multipurpose checking
        Alert error = new Alert(Alert.AlertType.ERROR, "Please provide input for all fields.");
        error.setTitle("Empty Fields");

        // retrieve current product data
        int id = Inventory.getProductCount();
        String name = nameTextField.getText();
        String invStr = invTextField.getText();
        String priceStr = priceTextField.getText();
        String minStr = minTextField.getText();
        String maxStr = maxTextField.getText();

        // initialize variable for type conversion
        int inv;
        double price;
        int min;
        int max;

        // empty field check
        if (name.isBlank() || invStr.isBlank() || priceStr.isBlank() || maxStr.isBlank() || minStr.isBlank()) {
            error.showAndWait();
            return;
        }

        // type conversion
        error.setTitle("Invalid Entry");
        try {
            inv = Integer.parseInt(invStr);
            price = Double.parseDouble(priceStr);
            min = Integer.parseInt(minStr);
            max = Integer.parseInt(maxStr);
        } catch (NumberFormatException e) {
            error.setContentText("Inventory must be an integer\nPrice must be a double\nMin must be an integer\nMax must be an integer");
            error.showAndWait();
            return;
        }

        // logical check
        if (max < min | inv < min | inv > max) {
            error.setContentText("Max must be >= min\nInv must be >= min\nInv must be <= max");
            error.showAndWait();
            return;
        }

        // create product object
        Product newProduct = new Product(id, name, price, inv, min, max);

        // add parts to product
        for (Part p : addedParts) {
            newProduct.addAssociatedPart(p);
        }

        // add product to inventory
        Inventory.addProduct(newProduct);

        // return to main menu
        this.goToMainMenu(actionEvent);
    }

    /**
     On click of button, redirects to the Main Menu.
     @param actionEvent the event that triggers the method
     @throws IOException exception
     */
    @FXML
    public void OnActionMainMenu(ActionEvent actionEvent) throws IOException {
        this.goToMainMenu(actionEvent);
    }

    /**
     Searches the products tableView for products matching the user input.
     User may search via id (integer) or name (string) - if one search fails, the other is attempted.
     If one result is found, it is highlighted in the table.
     If multiple results are found, the table is filtered.
     If no results found, an error message will display.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnSearchPart(ActionEvent actionEvent) {

        // reset the filter on every search
        partsTableView.setItems(Inventory.getAllParts());

        // retrieve search input
        String search = searchPart.getText();

        // if empty, no further action
        if (search.isBlank()) {
            return;
        }

        try {       // if integer, search by id

            // parse to int and search
            int searchId = Integer.parseInt(search);
            Part part = Inventory.lookupPart(searchId);

            if (part != null) {
                // highlight the match in the tableview (can only be one match when searching by id)
                partsTableView.getSelectionModel().select(part);
                return;
            }

        } catch (NumberFormatException e) {     // if not integer, search by name


        }

        // look for part matches
        ObservableList<Part> matches = Inventory.lookupPart(search);

        // if results
        if (!(matches.isEmpty())) {

            // if single match, highlight the match
            if (matches.size() == 1) {
                partsTableView.getSelectionModel().select(matches.get(0));
            }

            // if multiple matches, filter the table
            else {
                partsTableView.setItems(matches);
            }

            return;
        }

        // search not found by either id or name
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Not found");
        error.setContentText("No results found.");
        error.showAndWait();
    }
}
