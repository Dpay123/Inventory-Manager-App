
package dp.pa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 This is the main application file.

 FUTURE ENHANCEMENT - It would be beneficial to introduce a way to load data into the program without the user
 having to enter parts/products one by one using the menu. For example, this could be done by implementing a
 .csv file reader. A program user could provide the program an Excel or .csv file (common business format) containing
 product and part data to be entered. A method could be created within the Inventory class (ex. called "loadCSV()")
 that would read and parse each row of the file, format the data if needed, create a Part or Product object, and add to the
 Inventory. This would be done at runtime upon program startup so that the inventory is pre-populated with data at startup.
 
 */
public class InventoryApp extends Application {
    /**
     This initializes the Stage and the first scene, starting with the Main Menu.
     Note that all scenes (views) in the program are located in the "src/main/resources/dp/pa/view" directory.
     @param stage the stage to set
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApp.class.getResource("/dp/pa/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    /**
     * This is the main method to configure/run the application.
     * JAVADOCS LOCATION - in the root directory (PA/JavaDocs)
     *
     @param args unused
     */
    public static void main(String[] args) {

        // start the program
        launch();
    }
}