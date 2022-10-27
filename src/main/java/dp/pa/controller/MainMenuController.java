package dp.pa.controller;

import dp.pa.model.Inventory;
import dp.pa.model.Part;
import dp.pa.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The controller for the Main Menu of the GUI. */
public class MainMenuController extends BaseController {

    /**Field for user to input search for part (ID or Name)*/
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

    /**Field for user to input search for product (ID or Name)*/
    @FXML
    private TextField searchProducts;

    /**Table that holds all products in Inventory*/
    @FXML
    private TableView<Product> productTableView;

    /**Product table col that holds part ID*/
    @FXML
    private TableColumn<Product, Integer> productIdCol;

    /**Product table col that holds part Name*/
    @FXML
    private TableColumn<Product, String> productNameCol;

    /**Product table col that holds part Inv*/
    @FXML
    private TableColumn<Product, Integer> productInvCol;

    /**Product table col that holds part Price*/
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     Populates the parts and products tableviews.
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

        // set the data source of the products table
        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     Navigates to Add Part Menu.
     @param actionEvent the event that triggers the method
     @throws IOException exception
     */
    @FXML
    public void OnActionAddPartMenu(ActionEvent actionEvent) throws IOException {
        Parent newScene = this.loadScene("AddPartMenu");
        Stage stage = this.getStageWithSetScene(actionEvent, newScene);
        stage.setTitle("Add Part");
        stage.show();
    }

    /**
     Navigates to Modify Part Menu if a part is selected.
     Sets the part chosen as a static data member in the Modify Part Menu controller.
     If a part is not selected, displays an error box suggesting to choose a part.
     @param actionEvent the event that triggers the method
     @throws IOException exception
     */
    @FXML
    public void OnActionModifyPartMenu(ActionEvent actionEvent) throws IOException {

        // retrieve selected part
        Part part = (Part)partsTableView.getSelectionModel().getSelectedItem();

        // ensure part is selected
        if (part == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Please select a part to modify");
            error.setTitle("No part selected");
            error.showAndWait();
            return;
        }

        // set part in modify controller
        ModifyPartMenuController.part = part;

        // navigate to modify part menu
        Parent newScene = this.loadScene("ModifyPartMenu");
        Stage stage = this.getStageWithSetScene(actionEvent, newScene);
        stage.setTitle("Modify Part");
        stage.show();
    }

    /**
     Navigates to Modify Product Menu if a part is selected.
     Sets the product chosen as a static data member in the Modify Product Menu controller.
     If a product is not selected, displays an error box suggesting to choose a product.
     @param actionEvent the event that triggers the method
     @throws IOException exception
     */
    @FXML
    public void OnActionModifyProductMenu(ActionEvent actionEvent) throws IOException {

        // retrieve selected product
        Product product = (Product)productTableView.getSelectionModel().getSelectedItem();

        // ensure product is selected
        if (product == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Please select a product to modify");
            error.setTitle("No product selected");
            error.showAndWait();
            return;
        }

        // set the product in modify controller
        ModifyProductMenuController.product = product;

        // navigate to modify product menu
        Parent newScene = this.loadScene("ModifyProductMenu");
        Stage stage = this.getStageWithSetScene(actionEvent, newScene);
        stage.setTitle("Modify Product");
        stage.show();
    }

    /**
     Deletes a selected part from the Inventory.
     Confirms deletion via a pop-up box asking for confirmation.
     If the part deletion fails, an error box is shown.
     If no part is selected, nothing occurs.
     @param actionEvent the event that triggers the method
     */
    @FXML
    public void OnActionDeletePart(ActionEvent actionEvent) {

        // retrieve selected part
        Part part = (Part)partsTableView.getSelectionModel().getSelectedItem();

        // check for null
        if (part == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Please select a part to delete");
            error.setTitle("No part selected");
            error.showAndWait();
            return;
        }

        // pop up box to confirm deletion
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        confirm.setTitle("Confirm Deletion");
        Optional<ButtonType> result = confirm.showAndWait();
        // act upon user selection
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // remove from part list - pop up box if failure
            if (!(Inventory.deletePart(part))) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Could not delete.");
                error.setTitle("Error");
                error.showAndWait();
            }
            // reset table filter (accounts for deletion from a filtered data set
            productTableView.setItems(Inventory.getAllProducts());
        }

    }

    /**
     Navigates to the Add Product Menu.
     @param actionEvent the event that triggers the method
     @throws IOException exception
     */
    @FXML
    public void OnActionAddProductMenu(ActionEvent actionEvent) throws IOException {
        Parent newScene = this.loadScene("AddProductMenu");
        Stage stage = this.getStageWithSetScene(actionEvent, newScene);
        stage.setTitle("Add Product");
        stage.show();
    }

    /**
     Deletes a selected product from the Inventory.
     Confirms deletion via a pop-up box asking for confirmation.
     A Product with associated parts cannot be deleted - an error message will be shown.
     If the deletion fails, an error box is shown.
     If no product is selected, nothing occurs.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnActionDeleteProduct(ActionEvent actionEvent) {

        // retrieve selected product
        Product product = (Product)productTableView.getSelectionModel().getSelectedItem();

        // check for null
        if (product == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Please select a product to delete");
            error.setTitle("No product selected");
            error.showAndWait();
            return;
        }

        // product cannot be deleted if it has associated parts
        if (!(product.getAllAssociatedParts().isEmpty())) {
            Alert hasParts = new Alert(Alert.AlertType.ERROR);
            hasParts.setTitle("Error");
            hasParts.setContentText("This product cannot be deleted because it has associated parts");
            hasParts.showAndWait();
            return;
        }


        // pop up box to confirm deletion
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
        confirm.setTitle("Confirm Deletion");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // remove from product list - pop up box if failure
            if (!(Inventory.deleteProduct(product))) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setContentText("Could not delete.");
                error.showAndWait();
            }
            // reset table filter (accounts for deletion from a filtered data set
            productTableView.setItems(Inventory.getAllProducts());
        }


    }

    /**
     Searches the parts tableView for parts matching the user input.
     User may search via id (integer) or name (string) - if one search fails, the other is attempted.
     If one result is found, it is highlighted in the table.
     If multiple results are found, the table is filtered.
     If no results found, an error message will display.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnActionSearchParts(ActionEvent actionEvent) {

        // reset the filter on every search
        partsTableView.setItems(Inventory.getAllParts());

        // retrieve search input
        String search = searchPart.getText();

        // if empty, no further action
        if (search.isBlank()) {
            return;
        }

        // if integer, search by id
        try {
            int searchId = Integer.parseInt(search);
            Part part = Inventory.lookupPart(searchId);

            if (part != null) {
                // highlight the match in the tableview (can only be one match when searching by id)
                partsTableView.getSelectionModel().select(part);
                return;
            }

        // if not integer, search by name
        } catch (NumberFormatException e) {
            // continue on to search by string
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

    /**
     Searches the products tableView for products matching the user input.
     User may search via id (integer) or name (string) - if one search fails, the other is attempted.
     If one result is found, it is highlighted in the table.
     If multiple results are found, the table is filtered.
     If no results found, an error message will display.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnActionSearchProducts(ActionEvent actionEvent) {

        // reset the filter on every search
        productTableView.setItems(Inventory.getAllProducts());

        // retrieve search input
        String search = searchProducts.getText();

        // if empty, no further action
        if (search.isBlank()) {
            return;
        }

        // if integer, search by id
        try {
            int searchId = Integer.parseInt(search);
            Product product = Inventory.lookupProduct(searchId);

            if (product != null) {
                // highlight the match in the tableview (can only be one match when searching by id)
                productTableView.getSelectionModel().select(product);
                return;
            }

            // if not integer, search by name
        } catch (NumberFormatException e) {

            // continue on to search by string
        }

        // look for part matches
        ObservableList<Product> matches = Inventory.lookupProduct(search);

        // if results
        if (!(matches.isEmpty())) {

            // if single match, highlight the match
            if (matches.size() == 1) {
                productTableView.getSelectionModel().select(matches.get(0));
            }

            // if multiple matches, filter the table
            else {
                productTableView.setItems(matches);
            }

            return;
        }

        // search not found by either id or name
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Not found");
        error.setContentText("No results found.");
        error.showAndWait();
    }

    /**
     Exits the program.
     A dialog box asks the user to confirm prior to program shutdown.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnActionExit(ActionEvent actionEvent) {
        // pop up box to confirm deletion
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        confirm.setTitle("Don't go!");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}