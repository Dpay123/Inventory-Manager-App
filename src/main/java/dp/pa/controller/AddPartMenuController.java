package dp.pa.controller;

import dp.pa.model.InHouse;
import dp.pa.model.Inventory;
import dp.pa.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

/** The controller for the Add Part Menu of the GUI. */
public class AddPartMenuController extends BaseController {

    /**The button that indicates an InHouse part (see method OnInHouseSetPartTypeLabel)*/
    @FXML
    private RadioButton inHouseRBtn;

    /**The button that indicates an Outsourced part (see method OnOutsourcedSetPartTypeLabel)*/
    @FXML
    private RadioButton outsourceRBtn;

    /**Field for user to input part name*/
    @FXML
    private TextField nameTextField;

    /**Field for user to input part inv*/
    @FXML
    private TextField invTextField;

    /**Field for user to input part price*/
    @FXML
    private TextField priceTextField;

    /**Field for user to input part max inv*/
    @FXML
    private TextField maxTextField;

    /**Field for user to input part Machine ID (InHouse) or Company Name (Outsourced)*/
    @FXML
    private TextField typeTextField;

    /**Field for user to input part min inv*/
    @FXML
    private TextField minTextField;

    /**Label that displays either "Machine ID" or "Company Name" depending on part type selection*/
    @FXML
    private Label partTypeLabel;

    /**
     On click of Save button, saves a new part to the Inventory and redirects user to main menu.
     Retrieves user input from the form to create a new part object.
     Prevents invalid/empty data input and displays errors via dialog boxes.

     RUNTIME ERROR - Initially, the creation of an InHouse part resulted in a NumberFormatException error
     at runtime - this is because the constructor for the InHouse part requires an integer for the machineID field,
     but the method was providing it a String, because the source of the data (typeTextField) returns a String -
     even if the user inputs an integer. This was corrected by casting the typeTextField to an integer,
     which required being wrapped in a try/catch block, as it was possible that the user entered a non-integer
     which would cause an error once the program attempted to cast to an integer.

     @param actionEvent the event that triggers the method.
     @throws IOException exception
     */
    @FXML
    public void OnActionSavePartMainMenu(ActionEvent actionEvent) throws IOException {

        // set up alert box for multipurpose checking
        Alert error = new Alert(Alert.AlertType.ERROR, "Please provide input for all fields.");
        error.setTitle("Empty Fields");


        // retrieve text field data
        int id = Inventory.getPartCount();
        String name = nameTextField.getText();
        String invStr = invTextField.getText();
        String priceStr = priceTextField.getText();
        String maxStr = maxTextField.getText();
        String minStr = minTextField.getText();
        String typeField = typeTextField.getText();

        // initialize variable for type conversion
        int inv;
        double price;
        int max;
        int min;

        // empty field check
        if (name.isBlank() || invStr.isBlank() || priceStr.isBlank() || maxStr.isBlank() || minStr.isBlank() || typeField.isBlank()) {
            error.showAndWait();
            return;
        }

        // type conversion
        error.setTitle("Invalid Entry");
        try {
            inv = Integer.parseInt(invStr);
            price = Double.parseDouble(priceStr);
            max = Integer.parseInt(maxStr);
            min = Integer.parseInt(minStr);
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

        if (inHouseRBtn.isSelected()) {

            // cast typeField to int
            int machineID;
            try {
                machineID = Integer.parseInt(typeField);
            } catch (NumberFormatException e) {
                error.setContentText("Machine ID must be an integer");
                error.showAndWait();
                return;
            }

            InHouse newPart = new InHouse(id, name, price, inv, min, max, machineID);
            Inventory.addPart(newPart);

        } else if (outsourceRBtn.isSelected()){

            Outsourced newPart = new Outsourced(id, name, price, inv, min, max, typeField);
            Inventory.addPart(newPart);
        }

        // return to main menu
        this.goToMainMenu(actionEvent);
    }

    /**
     On click of button, redirects to the Main Menu.
     @param actionEvent the event that triggers the method.
     @throws IOException exception
     */
    @FXML
    public void OnActionMainMenu(ActionEvent actionEvent) throws IOException {
        this.goToMainMenu(actionEvent);
    }

    /**
     On click of InHouse radio button, add Machine ID field to form.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnInHouseSetPartTypeLabel(ActionEvent actionEvent) {
        partTypeLabel.setText("Machine ID");
    }

    /**
     On click of Outsourced radio button, add Company Name field to form.
     @param actionEvent the event that triggers the method.
     */
    @FXML
    public void OnOutsourcedSetPartTypeLabel(ActionEvent actionEvent) {
        partTypeLabel.setText("Company Name");
    }
}
