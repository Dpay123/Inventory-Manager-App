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

/** The controller for the Modify Product Menu of the GUI. */
public class ModifyProductMenuController extends BaseController {

    /**The product to be modified*/
    public static Product product;

    /**Placeholder list that holds associated parts of a product
     This list represents the changes made to the associated parts list of the product.
     In the event of a saved modification, this list becomes the product's new parts list.
     In the event of a canceled modification, this list is discarded, the product is unchanged.
     */
    public static ObservableList<Part> currentParts = FXCollections.observableArrayList();

    /**Field for the auto-generated unique product ID*/
    @FXML
    private TextField idTextField;

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

        // set current product details
        idTextField.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        invTextField.setText(String.valueOf(product.getStock()));
        priceTextField.setText(String.valueOf(product.getPrice()));
        maxTextField.setText(String.valueOf(product.getMax()));
        minTextField.setText(String.valueOf(product.getMin()));

        // set current product parts
        currentParts.clear();
        currentParts.addAll(product.getAllAssociatedParts());
        addedPartsTableView.setItems(currentParts);
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

        // retrieve the selected part
        Part part = (Part)partsTableView.getSelectionModel().getSelectedItem();

        // check for null
        if (part == null) {
            return;
        }

        // add part to current parts list
        currentParts.add(part);
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
            if (!(currentParts.remove(part))) {
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

        // retrieve product data from form
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

        // set new product data
        product.setName(name);
        product.setPrice(price);
        product.setStock(inv);
        product.setMin(min);
        product.setMax(max);

        // clear current product parts
        product.clearParts();

        // add new selected parts
        for (Part p : currentParts) {
            product.addAssociatedPart(p);
        }

        // navigate to main menu
        this.goToMainMenu(actionEvent);
    }

    /**
     Cancels any changes and returns to Main Menu
     @param actionEvent the event that triggers the method
     @throws IOException exception
     */
    @FXML
    public void OnCancelMainMenu(ActionEvent actionEvent) throws IOException {
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
