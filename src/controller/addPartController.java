package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class adds parts to the main screen to be modified, deleted, or added into product groups. */
public class addPartController implements Initializable {

    /**
     * The Inhouse radio.
     */
    public RadioButton inhouseRadio;
    /**
     * The Outsourced radio.
     */
    public RadioButton outsourcedRadio;
    /**
     * The Id text field.
     */
    public TextField idTextField;

    /**
     * The constant inhouseBool.
     */
    public static boolean inhouseBool;

    /**
     * The constant nextId.
     */
    public static int nextId = 0;
    /**
     * The Origin data tf.
     */
    public TextField originDataTF;
    /**
     * The Origin data label.
     */
    public Label originDataLabel;
    /**
     * The Name tf.
     */
    public TextField nameTF;
    /**
     * The Inv tf.
     */
    public TextField invTF;
    /**
     * The Price tf.
     */
    public TextField priceTF;
    /**
     * The Max tf.
     */
    public TextField maxTF;
    /**
     * The Min tf.
     */
    public TextField minTF;
    /**
     * The Save part.
     */
    public Button savePart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inhouseRadio.setSelected(true);
    }

    /**
     * To main.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all data. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1050,500);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

            stage.show();
        }
    }

    /**
     * Back to main. Created for readability.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void backToMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will submit all fields. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1050,500);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

            stage.show();
        }
    }

    /**
     * Add new part. Checks to see which radio toggle is selected and casts the part accordingly.
     *
     * @param actionEvent the action event
     */
    public void addNewPart(ActionEvent actionEvent) {
        int id = 0;
        String name = nameTF.getText();
        int stock = Integer.parseInt(invTF.getText());
        double price = Double.parseDouble(priceTF.getText());
        int min = Integer.parseInt(minTF.getText());
        int max = Integer.parseInt(maxTF.getText());
        String dataFlip = originDataTF.getText();

        try {
            if (stock <= max && stock > min && min >= 0) {
                if (inhouseRadio.isSelected()) {
                    InHouse inhouse = new InHouse(0,"0",0,0,0,0,0);
                    inhouse.setId(Inventory.getAllParts().size()+1);
                    inhouse.setName(name);
                    inhouse.setStock(stock);
                    inhouse.setPrice(price);
                    inhouse.setMin(min);
                    inhouse.setMax(max);
                    inhouse.setMachineID(Integer.parseInt(dataFlip));

                    Inventory.addPart(inhouse);
                } else {
                    Outsourced outsourced = new Outsourced(0,"0",0,0,0,0,"0");
                    outsourced.setId(id);
                    outsourced.setName(name);
                    outsourced.setStock(stock);
                    outsourced.setPrice(price);
                    outsourced.setMin(min);
                    outsourced.setMax(max);
                    outsourced.setCompanyName(dataFlip);

                    Inventory.addPart(outsourced);
                }
                backToMain(actionEvent);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Min must be less than max. Stock should be a value \nbetween the two.");
                alert.showAndWait();
            }
        } catch (NumberFormatException | IOException var11) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each TextField.");
            alert.showAndWait();
        }

    }

    /**
     * Outsource radio button action.
     *
     * @param actionEvent the action event
     */
    public void outsourceRadioButtonAction(ActionEvent actionEvent) {
        originDataLabel.setText("Company Name");
        originDataTF.setPromptText("Company Name");
    }

    /**
     * In house radio button action.
     *
     * @param actionEvent the action event
     */
    public void inHouseRadioButtonAction(ActionEvent actionEvent) {
        originDataLabel.setText("Machine ID");
        originDataTF.setPromptText("Machine ID");
    }
}