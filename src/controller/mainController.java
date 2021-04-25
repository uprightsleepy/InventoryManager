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
    public TextField prodTF;

    private static int partToModifyIndex;
    private static Part partToModify;

    private static int prodToModifyIndex;
    private static Product prodToModify;

    public static int getPartToModifyIndex() {
        System.out.println("getPartToModifyIndex() called");
        return partToModifyIndex;
    }

    public static Part getPartToModify() {
        System.out.println("getPartToModify() called");
        return partToModify;
    }

    public static int getProdToModifyIndex() {
        System.out.println("getProdToModifyIndex() called");
        return prodToModifyIndex;
    }

    public static Product getProdToModify() {
        System.out.println("getProdToModify() called");
        return prodToModify;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addTestData();

        partsTable.setItems(Inventory.getAllParts());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cpiColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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

    public void toModProduct(ActionEvent actionEvent) throws IOException {
        prodToModify = productsTable.getSelectionModel().getSelectedItem();
        prodToModifyIndex = Inventory.getAllProducts().indexOf(prodToModify);

        Parent root = FXMLLoader.load(getClass().getResource("/view/modProduct.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1050,500);
        stage.setTitle("Modify Product");
        stage.setScene(scene);

        stage.show();
    }

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1050,500);
        stage.setTitle("Add Product");
        stage.setScene(scene);

        stage.show();
    }


    public void delete(ActionEvent actionEvent) throws RuntimeException{
        Part part = partsTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
    }

    public void deleteProd(ActionEvent actionEvent) throws RuntimeException{
        Product product = productsTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(product);
    }

    private void addTestData() {

        if(!firstTime){
            return;
        }

        firstTime = false;

        InHouse a = new InHouse(1,"Brake Caliper",29.99,4,1,5,93);
        Inventory.addPart(a);

        InHouse b = new InHouse(2,"Brake Pads",48.79,2,2,5,93);
        Inventory.addPart(b);

        InHouse c = new InHouse(3,"Brake Rotor",99.85,4,2,6,93);
        Inventory.addPart(c);

        InHouse d = new InHouse(4,"Oil Filter",29.99,9,1,10,93);
        Inventory.addPart(d);

        InHouse e = new InHouse(5,"5Q 5W20 Oil",10.15,24,3,30,93);
        Inventory.addPart(e);

        InHouse f = new InHouse(6,"Oil Drain Plug",2.99,10,1,25,93);
        Inventory.addPart(f);

        InHouse g = new InHouse(7,"Transmission Fluid",29.99,36,12,60,93);
        Inventory.addPart(g);

        InHouse h = new InHouse(8,"ATF Filter",59.99,1,0,2,93);
        Inventory.addPart(h);

        InHouse i = new InHouse(9,"Stop Leak",19.99,6,6,12,93);
        Inventory.addPart(i);

    }

    public void toExit(ActionEvent actionEvent) {
        ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void getResultsHandler(ActionEvent actionEvent){
        try{
            int q = Integer.parseInt(partTF.getText());
            ObservableList<Part> parts = Inventory.lookupPart(q);
            partsTable.setItems(parts);

            if(partTF.getText().isEmpty()){
                partsTable.setItems(Inventory.getAllParts());
            }
        } catch (NumberFormatException exception){
            String q = partTF.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            partsTable.setItems(parts);

            if(partTF.getText().isEmpty()){
                partsTable.setItems(Inventory.getAllParts());
            }
        }
    }

    public void searchProducts(ActionEvent actionEvent) throws RuntimeException{
        try{
            int q = Integer.parseInt(prodTF.getText());
            ObservableList<Product> productsFound = Inventory.lookupProduct(q);
            productsTable.setItems(productsFound);

            if(prodTF.getText().isEmpty()){
                productsTable.setItems(Inventory.getAllProducts());
            }

        } catch (NumberFormatException exception){
            String q = prodTF.getText();
            ObservableList<Product> productsFound = Inventory.lookupProduct(q);
            productsTable.setItems(productsFound);
            if(prodTF.getText().isEmpty()){
                productsTable.setItems(Inventory.getAllProducts());
            }
        }
    }
}
