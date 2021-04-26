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

/**
 * The type Add part controller.
 */
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
     * The Company name.
     */
    public TextField companyName;

    /**
     * The Part name.
     */
    public TextField partName;
    /**
     * The Part inv.
     */
    public TextField partInv;
    /**
     * The Part price.
     */
    public TextField partPrice;
    /**
     * The Part max.
     */
    public TextField partMax;
    /**
     * The Part min.
     */
    public TextField partMin;
    /**
     * The Machine idtf.
     */
    public TextField machineIDTF;

    /**
     * The constant inhouseBool.
     */
    public static boolean inhouseBool;
    /**
     * The constant addedPart.
     */
    public static boolean addedPart = false;
    /**
     * The constant nextId.
     */
    public static int nextId = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inhouseBool = true;
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
     * Add new part.
     *
     * @param actionEvent the action event
     */
    public void addNewPart(ActionEvent actionEvent) {
        if(inhouseBool) {
            try{
                InHouse newInhouse = new InHouse(1,"0",0,0,0,0,0);

                newInhouse.setId(Inventory.getAllParts().size()+1);

                newInhouse.setName(partName.getText());
                newInhouse.setPrice(Double.parseDouble(partPrice.getText()));
                if(partName.getText().trim().isEmpty()){
                    Alert fail= new Alert(Alert.AlertType.INFORMATION);
                    fail.setHeaderText("Enter a Part Name");
                    fail.setContentText("You haven't named your part.");
                    fail.showAndWait();
                } else {

                    if ((Integer.parseInt(partInv.getText()) <= Integer.parseInt(partMax.getText())) && ((Integer.parseInt(partInv.getText())) > (Integer.parseInt(partMin.getText())))
                            && (Integer.parseInt(partMin.getText())) < (Integer.parseInt(partMax.getText())) && (Integer.parseInt(partMin.getText())) >= 0 && (Integer.parseInt(partMax.getText())) > 0) {
                        newInhouse.setStock(Integer.parseInt(partInv.getText()));
                        newInhouse.setMin(Integer.parseInt(partMin.getText()));
                        newInhouse.setMax(Integer.parseInt(partMax.getText()));
                        newInhouse.setMachineID(Integer.parseInt(machineIDTF.getText()));

                        Inventory.addPart(newInhouse);

                        backToMain(actionEvent);

                    } else {
                        partInv.clear();
                        partInv.setPromptText("Enter a Valid #");

                        partMin.clear();
                        partMin.setPromptText("Enter a Valid #");

                        partMax.clear();
                        partMax.setPromptText("Enter a Valid #");
                    }
                }
            } catch (NumberFormatException | IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each TextField.");
                alert.showAndWait();
            }
        } else {
            try {
                Outsourced newOutsource = new Outsourced(1,"0",0,0,0,0,"0");

                newOutsource.setId(Inventory.getAllParts().size()+1);

                newOutsource.setName(partName.getText());
                newOutsource.setPrice(Double.parseDouble(partPrice.getText()));
                if(partName.getText().trim().isEmpty()){
                    Alert fail= new Alert(Alert.AlertType.INFORMATION);
                    fail.setHeaderText("Enter a Part Name");
                    fail.setContentText("You haven't named your part.");
                    fail.showAndWait();
                } else {
                    if ((Integer.parseInt(partInv.getText()) <= Integer.parseInt(partMax.getText())) && ((Integer.parseInt(partInv.getText())) > (Integer.parseInt(partMin.getText())))
                            && (Integer.parseInt(partMin.getText())) < (Integer.parseInt(partMax.getText())) && (Integer.parseInt(partMin.getText())) >= 0 && (Integer.parseInt(partMax.getText())) > 0) {
                        newOutsource.setStock(Integer.parseInt(partInv.getText()));
                        newOutsource.setMin(Integer.parseInt(partMin.getText()));
                        newOutsource.setMax(Integer.parseInt(partMax.getText()));
                        newOutsource.setCompanyName(companyName.getText());

                        Inventory.addPart(newOutsource);

                        backToMain(actionEvent);

                    } else {
                        partInv.clear();
                        partInv.setPromptText("Enter a Valid #");

                        partMin.clear();
                        partMin.setPromptText("Enter a Valid #");

                        partMax.clear();
                        partMax.setPromptText("Enter a Valid #");
                    }
                }
            } catch(NumberFormatException | IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each TextField.");
                alert.showAndWait();
            }
            resetTextFields();
        }
    }

    /**
     * Reset text fields.
     */
    public void resetTextFields() {
        partName.clear();
        partInv.clear();
        partInv.setPromptText("Inv");
        partMax.clear();
        partMin.clear();
        partPrice.clear();
        companyName.clear();
        machineIDTF.clear();
    }
}