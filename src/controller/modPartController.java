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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Mod part controller.
 */
public class modPartController implements Initializable {
    /**
     * The Company name.
     */
    public TextField companyName;
    /**
     * The Name tf.
     */
    public TextField nameTF;
    /**
     * The Price tf.
     */
    public TextField priceTF;
    /**
     * The Min tf.
     */
    public TextField minTF;
    /**
     * The Max tf.
     */
    public TextField maxTF;
    /**
     * The Inv tf.
     */
    public TextField invTF;
    /**
     * The Id text field.
     */
    public TextField idTextField;
    /**
     * The Machine idtf.
     */
    public TextField machineIDTF;
    /**
     * The Inhouse radio.
     */
    public RadioButton inhouseRadio;
    /**
     * The Outsourced radio.
     */
    public RadioButton outsourcedRadio;
    private static Part modifiedPart = mainController.getPartToModify();

    /**
     * The Inhouse bool.
     */
    boolean inhouseBool = true;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextFields(mainController.getPartToModify());
    }

    /**
     * To main.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all changes made. Do you want to continue?");
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
     * To main.
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
     * Sets text fields.
     *
     * @param part the part
     */
    public void setTextFields(Part part) {
        idTextField.setText(String.valueOf(mainController.getPartToModifyIndex()+1));
        nameTF.setText(String.valueOf(mainController.getPartToModify().getName()));
        priceTF.setText(String.valueOf(mainController.getPartToModify().getPrice()));
        minTF.setText(String.valueOf(mainController.getPartToModify().getMin()));
        maxTF.setText(String.valueOf(mainController.getPartToModify().getMax()));
        invTF.setText(String.valueOf(mainController.getPartToModify().getStock()));
    }

    /**
     * Modify part.
     *
     * @param actionEvent the action event
     */
    public void modifyPart(ActionEvent actionEvent){
        if(inhouseBool){
            try {
                InHouse inHouseModify = new InHouse(modifiedPart.getId(),modifiedPart.getName(), modifiedPart.getPrice(), modifiedPart.getStock(), modifiedPart.getMin(), modifiedPart.getMax(), 0);

                inHouseModify.setName(nameTF.getText());
                inHouseModify.setId(Integer.parseInt(idTextField.getText()));
                inHouseModify.setPrice(Double.parseDouble(priceTF.getText()));

                if( (Integer.parseInt(invTF.getText()) <= Integer.parseInt(maxTF.getText())) && ((Integer.parseInt(invTF.getText())) > (Integer.parseInt(minTF.getText())))
                        && (Integer.parseInt(minTF.getText())) < (Integer.parseInt(maxTF.getText())) && (Integer.parseInt(minTF.getText())) >= 0 && (Integer.parseInt(maxTF.getText())) > 0){

                    inHouseModify.setMin(Integer.parseInt(minTF.getText()));
                    inHouseModify.setMax(Integer.parseInt(maxTF.getText()));
                    inHouseModify.setStock(Integer.parseInt(invTF.getText()));

                    Inventory.updatePart(mainController.getPartToModifyIndex(),inHouseModify);
                    backToMain(actionEvent);
                } else{
                    invTF.clear();
                    invTF.setPromptText("Enter a Valid #");

                    minTF.clear();
                    minTF.setPromptText("Enter a Valid #");

                    maxTF.clear();
                    maxTF.setPromptText("Enter a Valid #");
                }

            } catch (NumberFormatException | IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each TextField.");
                alert.showAndWait();
            }

        } else{
            try {
                Outsourced outsourcedModify = new Outsourced(modifiedPart.getId(),modifiedPart.getName(), modifiedPart.getPrice(), modifiedPart.getStock(), modifiedPart.getMin(), modifiedPart.getMax(), "0");

                outsourcedModify.setName(nameTF.getText());
                outsourcedModify.setId(Integer.parseInt(idTextField.getText()));
                outsourcedModify.setPrice(Double.parseDouble(priceTF.getText()));

                if( (Integer.parseInt(invTF.getText()) <= Integer.parseInt(maxTF.getText())) && ((Integer.parseInt(invTF.getText())) > (Integer.parseInt(minTF.getText())))
                        && (Integer.parseInt(minTF.getText())) < (Integer.parseInt(maxTF.getText())) && (Integer.parseInt(minTF.getText())) >= 0 && (Integer.parseInt(maxTF.getText())) > 0){
                    outsourcedModify.setMin(Integer.parseInt(minTF.getText()));
                    outsourcedModify.setMax(Integer.parseInt(maxTF.getText()));
                    outsourcedModify.setStock(Integer.parseInt(invTF.getText()));
                    outsourcedModify.setCompanyName(companyName.getText());

                    Inventory.updatePart(mainController.getPartToModifyIndex(),outsourcedModify);
                    backToMain(actionEvent);
                } else{
                    invTF.clear();
                    invTF.setPromptText("Enter a Valid #");

                    minTF.clear();
                    minTF.setPromptText("Enter a Valid #");

                    maxTF.clear();
                    maxTF.setPromptText("Enter a Valid #");
                }

            } catch (NumberFormatException | IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each TextField.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Sets inhouse.
     *
     * @param actionEvent the action event
     */
    public void setInhouse(ActionEvent actionEvent) {
        companyName.setPromptText("Part is In-House");
        companyName.setEditable(false);
        companyName.setDisable(true);
        inhouseBool = true;

        machineIDTF.setPromptText("Machine ID");
        machineIDTF.setEditable(true);
        machineIDTF.setDisable(false);
    }

    /**
     * Sets outsourced.
     *
     * @param actionEvent the action event
     */
    public void setOutsourced(ActionEvent actionEvent) {
        companyName.setPromptText("Company Name");
        companyName.setEditable(true);
        companyName.setDisable(false);

        inhouseBool = false;

        machineIDTF.setPromptText("Not Applicable");
        machineIDTF.setEditable(false);
        machineIDTF.setDisable(true);
    }


    /**
     * Reset text fields.
     */
    public void resetTextFields() {
        nameTF.clear();
        nameTF.setPromptText("Name Saved");

        invTF.clear();
        invTF.setPromptText("Inv Saved");

        maxTF.clear();
        maxTF.setPromptText("Max Saved");

        minTF.clear();
        minTF.setPromptText("Min Saved");

        priceTF.clear();
        priceTF.setPromptText("Price Saved");

        if(inhouseBool){
            machineIDTF.clear();
            machineIDTF.setPromptText("Machine ID Saved");
        }
        else{
            companyName.clear();
            companyName.setPromptText("Company Name Saved");
        }
    }
}