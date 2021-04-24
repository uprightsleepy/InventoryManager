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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addPartController implements Initializable {

    public RadioButton inhouseRadio;
    public RadioButton outsourcedRadio;
    public TextField idTextField;
    public TextField companyName;

    public TextField partName;
    public TextField partInv;
    public TextField partPrice;
    public TextField partMax;
    public TextField partMin;
    public TextField machineIDTF;

    public static boolean inhouseBool;
    public static int nextId = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inhouseBool = true;
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1050,500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

        stage.show();
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

    public void addNewPart(ActionEvent actionEvent) {
        if(inhouseBool) {
            InHouse newInhouse = new InHouse(1,"0",0,0,0,0,0);

            newInhouse.setId(nextId+=1);

            newInhouse.setName(partName.getText());
            newInhouse.setPrice(Double.parseDouble(partPrice.getText()));
            newInhouse.setStock(Integer.parseInt(partInv.getText()));
            newInhouse.setMin(Integer.parseInt(partMin.getText()));
            newInhouse.setMax(Integer.parseInt(partMax.getText()));
            newInhouse.setMachineID(Integer.parseInt(machineIDTF.getText()));

            Inventory.addPart(newInhouse);

            partName.clear();
            partInv.clear();
            partMax.clear();
            partMin.clear();
            partPrice.clear();
            machineIDTF.clear();
        } else {
            Outsourced newOutsource = new Outsourced(1,"0",0,0,0,0,"0");

            newOutsource.setId(nextId+=1);

            newOutsource.setName(partName.getText());
            newOutsource.setPrice(Double.parseDouble(partPrice.getText()));
            newOutsource.setStock(Integer.parseInt(partInv.getText()));
            newOutsource.setMin(Integer.parseInt(partMin.getText()));
            newOutsource.setMax(Integer.parseInt(partMax.getText()));
            newOutsource.setCompanyName(companyName.getText());

            Inventory.addPart(newOutsource);

            partName.clear();
            partInv.clear();
            partMax.clear();
            partMin.clear();
            partPrice.clear();
            companyName.clear();
        }
    }
}
