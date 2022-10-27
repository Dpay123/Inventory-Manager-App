package dp.pa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** The base controller class defines methods that all controllers will use. */
public abstract class BaseController implements Initializable {

    /**This method retrieves the stage of the event handed to it,
     and sets the scene of that stage to the scene handed to it to load.
     Loads a new scene on action for the sake of navigation through menus.
     @param actionEvent The event that triggers the method, used to retrieve the stage
     @param sceneToLoad The scene that will be set to the stage
     @return Returns a stage with a new scene set  */
    public Stage getStageWithSetScene(ActionEvent actionEvent, Parent sceneToLoad) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(sceneToLoad));
        return stage;
    }

    /**This method retrieves a scene to be loaded for navigation.
     Using this method ensures no typos regarding the project structure,
     and if the project has to be restructured, only this method has
     to be updated to the new location of the views.
     @param viewName The name of view to load, without file type suffix
     @return Returns the scene to be loaded
     @throws IOException exception*/
    public Parent loadScene(String viewName) throws IOException {
        String viewPath = "/dp/pa/view/" + viewName + ".fxml";
        return FXMLLoader.load(getClass().getResource(viewPath));
    }

    /**This method navigates to the main menu.
     There are many controllers and buttons that navigate to the
     main menu so this functions to reduce code clutter, typos, and increase readability.
     @param actionEvent The event that triggers the method, used to retrieve the stage
     @throws IOException exception*/
    public void goToMainMenu(ActionEvent actionEvent) throws IOException  {
        Parent newScene = this.loadScene("MainMenu");
        Stage stage = this.getStageWithSetScene(actionEvent, newScene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    /**This method allows implementation of code/logic when controller is first called.
     For example, can be used to pre-populate data into a list. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
