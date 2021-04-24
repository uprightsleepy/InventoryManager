package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modPartController implements Initializable {
    public TextField companyName;
    public TextField nameTF;
    public TextField priceTF;
    public TextField minTF;
    public TextField maxTF;
    public TextField invTF;
    public TextField idTextField;
    public TextField machineIDTF;
    public RadioButton inhouseRadio;
    public RadioButton outsourcedRadio;
    private static Part modifiedPart = mainController.getPartToModify();

    boolean inhouseBool = true;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextFields(mainController.getPartToModify());
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1050,500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

        stage.show();
    }

    public void setTextFields(Part part) {
        idTextField.setText(String.valueOf(mainController.getPartToModifyIndex()+1));
        nameTF.setText(String.valueOf(mainController.getPartToModify().getName()));
        priceTF.setText(String.valueOf(mainController.getPartToModify().getPrice()));
        minTF.setText(String.valueOf(mainController.getPartToModify().getMin()));
        maxTF.setText(String.valueOf(mainController.getPartToModify().getMax()));
        invTF.setText(String.valueOf(mainController.getPartToModify().getStock()));
    }

    public void modifyPart(ActionEvent actionEvent){
        if(inhouseBool){
            InHouse inHouseModify = new InHouse(modifiedPart.getId(),modifiedPart.getName(), modifiedPart.getPrice(), modifiedPart.getStock(), modifiedPart.getMin(), modifiedPart.getMax(), 0);

            inHouseModify.setName(nameTF.getText());
            inHouseModify.setPrice(Double.parseDouble(priceTF.getText()));
            inHouseModify.setMin(Integer.parseInt(minTF.getText()));
            inHouseModify.setMax(Integer.parseInt(maxTF.getText()));
            inHouseModify.setStock(Integer.parseInt(invTF.getText()));
            inHouseModify.setMachineID(Integer.parseInt(machineIDTF.getText()));

            Inventory.updatePart(mainController.getPartToModifyIndex(),inHouseModify);
        } else{
            Outsourced outsourcedModify = new Outsourced(modifiedPart.getId(),modifiedPart.getName(), modifiedPart.getPrice(), modifiedPart.getStock(), modifiedPart.getMin(), modifiedPart.getMax(), "0");

            outsourcedModify.setName(nameTF.getText());
            outsourcedModify.setPrice(Double.parseDouble(priceTF.getText()));
            outsourcedModify.setMin(Integer.parseInt(minTF.getText()));
            outsourcedModify.setMax(Integer.parseInt(maxTF.getText()));
            outsourcedModify.setStock(Integer.parseInt(invTF.getText()));
            outsourcedModify.setCompanyName(companyName.getText());

            Inventory.updatePart(mainController.getPartToModifyIndex(),outsourcedModify);
        }

    }

    public void setInhouse(ActionEvent actionEvent) {
        companyName.setPromptText("Part is In-House");
        companyName.setEditable(false);
        companyName.setDisable(true);
        inhouseBool = true;

        machineIDTF.setPromptText("Machine ID");
        machineIDTF.setEditable(true);
        machineIDTF.setDisable(false);
    }

    public void setOutsourced(ActionEvent actionEvent) {
        companyName.setPromptText("Company Name");
        companyName.setEditable(true);
        companyName.setDisable(false);

        inhouseBool = false;

        machineIDTF.setPromptText("Not Applicable");
        machineIDTF.setEditable(false);
        machineIDTF.setDisable(true);
    }
}
