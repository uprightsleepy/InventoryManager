package controller;
/**
 * This program allows the user to manage an inventory of both parts and products, which are a group of parts.
 *
 * Error Correction:
 * For the longest time, at the start of this assignment, I could not figure out why my modPart screen was overwriting a different part in the menu.
 * The culprit was my logic imposed on the id and modifyPartIndex. I add 1 to my ID, as it is based on the ObservableList size, so to counter the ID = 0, I add one to the index.
 * Because of this, I was changing the part next in line in the list, and saving the original part to its original index, thus duplicating the part.
 * I corrected this by ensuring that if any changes were being made, they were made on the base level of the part's index, and saving the modified (...index+1) as the ID text.
 * This made sure that the correctly indexed part was being modified, and the user saw the "prettied" ID listings.
 *
 *  Future Enhancement:
 *  The application can be greatly enhanced, I think, if the "Company Name" listed in Outsourced products, could be used in another screen to find products also produced by that Company.
 *  This would allow the user to search based on Company Name to return only parts made by that company, for example Intel or AMD.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The type Main.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 1050, 500));
        primaryStage.show();
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}