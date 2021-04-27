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
import java.text.DecimalFormat;
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
     * The constant inhouseBool.
     */
    public static boolean inhouseBool;

    /**
     * The constant nextId.
     */
    public static int nextId = 0;
    public TextField originDataTF;
    public Label originDataLabel;
    public TextField nameTF;
    public TextField invTF;
    public TextField priceTF;
    public TextField maxTF;
    public TextField minTF;
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
     * Add new part.
     *
     * @param actionEvent the action event
     */
    public void addNewPart(ActionEvent actionEvent) {
        try {
            int id = 0;
            String name = nameTF.getText();
            double price = Double.parseDouble(priceTF.getText());
            int stock = Integer.parseInt(invTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());
            int machineID = 0;
            String companyName = "";

            if(inhouseBool) {
                try{
                    InHouse newInhouse = new InHouse(id,name,price,stock,min,max,machineID);
                    newInhouse.setId(Inventory.getAllParts().size()+1);
                    newInhouse.setName(nameTF.getText());
                    newInhouse.setPrice(Double.parseDouble(priceTF.getText()));

                    if(nameTF.getText().trim().isEmpty()){
                        Alert fail= new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Enter a Part Name");
                        fail.setContentText("You haven't named your part.");
                        fail.showAndWait();
                    } else {
                        if (stock <= max && stock > min && min >= 0) {
                            newInhouse.setStock(stock);
                            newInhouse.setMin(min);
                            newInhouse.setMax(max);
                            newInhouse.setMachineID(Integer.parseInt(originDataTF.getText()));

                            Inventory.addPart(newInhouse);
                            backToMain(actionEvent);

                        } else {
                            invTF.clear();
                            invTF.setPromptText("Invalid Value");

                            minTF.clear();
                            minTF.setPromptText("Invalid Value");

                            maxTF.clear();
                            maxTF.setPromptText("Invalid Value");

                            Alert fail= new Alert(Alert.AlertType.INFORMATION);
                            fail.setHeaderText("Enter Valid Inventory Values");
                            fail.setContentText("Please re-enter your inventory values.");
                            fail.showAndWait();
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
                    Outsourced newOutsource = new Outsourced(id,name,price,stock,min,max,companyName);

                    newOutsource.setId(Inventory.getAllParts().size()+1);

                    newOutsource.setName(name);
                    newOutsource.setPrice(price);
                    if(nameTF.getText().trim().isEmpty()){
                        Alert fail= new Alert(Alert.AlertType.INFORMATION);
                        fail.setHeaderText("Enter a Part Name");
                        fail.setContentText("You haven't named your part.");
                        fail.showAndWait();
                    } else {
                        if (stock <= max && stock > min && min >= 0) {
                            newOutsource.setStock(stock);
                            newOutsource.setMin(min);
                            newOutsource.setMax(max);
                            newOutsource.setCompanyName(originDataTF.getText());

                            Inventory.addPart(newOutsource);

                            backToMain(actionEvent);

                        }
                        else if(stock > max){
                            Alert fail= new Alert(Alert.AlertType.INFORMATION);
                            fail.setHeaderText("Check Your Inventory Value");
                            fail.setContentText("Your Inventory Value is larger than Max");
                            fail.showAndWait();
                        } else {
                            invTF.clear();
                            invTF.setPromptText("Enter a Valid #");

                            minTF.clear();
                            minTF.setPromptText("Enter a Valid #");

                            maxTF.clear();
                            maxTF.setPromptText("Enter a Valid #");
                        }
                    }
                } catch(NumberFormatException | IOException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Please enter a valid value for each TextField.");
                    alert.showAndWait();
                }
            }
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each TextField.");
            alert.showAndWait();
        }
    }

    public void outsourceRadioButtonAction(ActionEvent actionEvent) {
        inhouseBool = false;
        originDataLabel.setText("Company Name");
        originDataTF.setPromptText("Company Name");
    }

    public void inHouseRadioButtonAction(ActionEvent actionEvent) {
        inhouseBool = true;
        originDataLabel.setText("Machine ID");
        originDataTF.setPromptText("Machine ID");
    }
}