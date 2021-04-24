package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class mainController implements Initializable{


    private static boolean firstTime = true;

    public TableView<Part> partsTable;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn invColumn;
    public TableColumn cpiColumn;

    public TableView<Product> productsTable;
    public TableColumn prodID;
    public TableColumn prodName;
    public TableColumn prodInv;
    public TableColumn prodPrice;

    public Button closeButton;
    public Button deleteButton;
    public Button searchTF;
    public TextField partTF;
    public static boolean deleted = false;

    private static int partToModifyIndex;
    private static Part partToModify;

    public static int getPartToModifyIndex() {
        return partToModifyIndex;
    }

    public static Part getPartToModify() {
        return partToModify;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTestData();

        partsTable.setItems(Inventory.getAllParts());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cpiColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,550,550);
        stage.setTitle("Add Part");
        stage.setScene(scene);

        stage.show();
    }

    public void toModPart(ActionEvent actionEvent) throws IOException {
        partToModify = partsTable.getSelectionModel().getSelectedItem();
        partToModifyIndex = Inventory.getAllParts().indexOf(partToModify);

        Parent root = FXMLLoader.load(getClass().getResource("/view/modPart.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,550,550);
        stage.setTitle("Modify Part");
        stage.setScene(scene);

        stage.show();
    }

    public void delete(ActionEvent actionEvent) throws RuntimeException{
        Part part = partsTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
    }

    private void addTestData() {

        if(!firstTime){
            return;
        }

        firstTime = false;

        InHouse i = new InHouse(1,"Wheel",9.99,1,1,5,93);
        Inventory.addPart(i);

        Outsourced o = new Outsourced(2,"Door Handle",13.76,2,2,8,"AutoZone");
        Inventory.addPart(o);

        InHouse l = new InHouse(3,"Engine",595.99,1,1,2,97);
        Inventory.addPart(l);

        InHouse k = new InHouse(4,"Oil Drain Plug",2.85,35,15,50,83);
        Inventory.addPart(k);

        InHouse j = new InHouse(5,"ATF Filter",17.87,3,1,5,72);
        Inventory.addPart(j);
    }

    public void toExit(ActionEvent actionEvent) {
        ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void getResultsHandler(ActionEvent actionEvent){
        try{
            int q = Integer.parseInt(partTF.getText());
            ObservableList<Part> parts = Inventory.lookupPart(q);
            partsTable.setItems(parts);
        } catch (NumberFormatException exception){
            String q = partTF.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            partsTable.setItems(parts);
        }
    }
}
