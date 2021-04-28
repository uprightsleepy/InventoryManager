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

/** The class allows for modification of previously existing parts.*/
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
     * The Origin data label.
     */
    public Label originDataLabel;
    /**
     * The Origin data tf.
     */
    public TextField originDataTF;

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
     * Also to main, used as a means to demystify the code and make it more readable to myself.
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
     * Sets the initial data from the partToModify and checks to see if partToModify is an instance of InHouse or Outsourced.
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

        if(mainController.getPartToModify() instanceof InHouse){
            inhouseRadio.setSelected(true);
            originDataTF.setText(String.valueOf(((InHouse) mainController.getPartToModify()).getMachineID()));
            originDataLabel.setText("Machine ID");
        } else{
            outsourcedRadio.setSelected(true);
            originDataLabel.setText("Company Name");
            originDataTF.setText(String.valueOf(((Outsourced) mainController.getPartToModify()).getCompanyName()));
        }
    }

    /**
     * Checks to see if the partToModify is an instance of the InHouse class or the Outsourced class.
     * Uses that information to determine what data is saved where.
     * Sets the names of the part based initially on the original part that was selected.
     * Also has error checking for the inventory values.
     *
     * @param actionEvent the action event
     */
    public void modifyPart(ActionEvent actionEvent){
        try {
            String name = nameTF.getText();
            double price = Double.parseDouble(priceTF.getText());
            int stock = Integer.parseInt(invTF.getText());
            int min = Integer.parseInt(minTF.getText());
            int max = Integer.parseInt(maxTF.getText());
            String dataFlip = originDataTF.getText();


            if(mainController.getPartToModify() instanceof InHouse){
                InHouse inHouse = (InHouse) mainController.getPartToModify();
                inHouse.setId(mainController.getPartToModify().getId());
                inHouse.setName(name);

                if(stock <= max && stock > min && min >= 0){
                    inHouse.setStock(stock);
                    inHouse.setMin(min);
                    inHouse.setMax(min);
                    inHouse.setMachineID(Integer.parseInt(dataFlip));

                    Inventory.updatePart(mainController.getPartToModifyIndex(),inHouse);
                    backToMain(actionEvent);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Min must be less than max. Stock should be a value \nbetween the two.");
                    alert.showAndWait();
                }

            } else {
                Outsourced outsourced = (Outsourced) mainController.getPartToModify();
                outsourced.setId(mainController.getPartToModify().getId());
                outsourced.setName(name);

                if(stock <= max && stock > min && min >= 0){
                    outsourced.setCompanyName(dataFlip);
                    outsourced.setStock(stock);
                    outsourced.setMin(min);
                    outsourced.setMax(max);

                    Inventory.updatePart(mainController.getPartToModifyIndex(),outsourced);
                    backToMain(actionEvent);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Min must be less than max. Stock should be a value \nbetween the two.");
                    alert.showAndWait();
                }
            }
        } catch(NumberFormatException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each TextField.");
            alert.showAndWait();
        }

    }

    /**
     * Outsource radio button action. Changes the text of the originDataLabel to match the Outsourced class requirements
     *
     * @param actionEvent the action event
     */
    public void outsourceRadioButtonAction(ActionEvent actionEvent) {
        originDataLabel.setText("Company Name");
        originDataTF.setPromptText("Company Name");
    }

    /**
     * In house radio button action.Changes teh text of the originDataLabel to match the InHouse class requirements.
     *
     * @param actionEvent the action event
     */
    public void inHouseRadioButtonAction(ActionEvent actionEvent) {
        originDataLabel.setText("Machine ID");
        originDataTF.setPromptText("Machine ID");
    }
}