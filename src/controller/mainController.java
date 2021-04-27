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
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Main controller.
 */
public class mainController implements Initializable{


    private static boolean firstTime = true;

    /**
     * The Parts table.
     */
    public TableView<Part> partsTable;
    /**
     * The Id column.
     */
    public TableColumn idColumn;
    /**
     * The Name column.
     */
    public TableColumn nameColumn;
    /**
     * The Inv column.
     */
    public TableColumn invColumn;
    /**
     * The Cpi column.
     */
    public TableColumn cpiColumn;

    /**
     * The Products table.
     */
    public TableView<Product> productsTable;
    /**
     * The Prod id.
     */
    public TableColumn prodID;
    /**
     * The Prod name.
     */
    public TableColumn prodName;
    /**
     * The Prod inv.
     */
    public TableColumn prodInv;
    /**
     * The Prod price.
     */
    public TableColumn prodPrice;

    /**
     * The Close button.
     */
    public Button closeButton;
    /**
     * The Delete button.
     */
    public Button deleteButton;
    /**
     * The Search tf.
     */
    public Button searchTF;
    /**
     * The Part tf.
     */
    public TextField partTF;
    /**
     * The constant deleted.
     */
    public static boolean deleted = false;
    /**
     * The Prod tf.
     */
    public TextField prodTF;
    /**
     * The index of the partToModify.
     */
    private static int partToModifyIndex;
    /**
     * The partToModify.
     */
    private static Part partToModify;
    /**
     * The index of the prodToModify.
     */
    private static int prodToModifyIndex;
    /**
     * The prodToModify.
     */
    private static Product prodToModify;

    /**
     * Gets part to modify index, for update purposes.
     *
     * @return the part to modify index
     */
    public static int getPartToModifyIndex() {
        System.out.println("getPartToModifyIndex() called");
        return partToModifyIndex;
    }

    /**
     * Gets part to modify.
     *
     * @return the part to modify
     */
    public static Part getPartToModify() {
        System.out.println("getPartToModify() called");
        return partToModify;
    }

    /**
     * Gets the index of the product to modify, for update purposes
     *
     * @return the prod to modify index
     */
    public static int getProdToModifyIndex() {
        System.out.println("getProdToModifyIndex() called");
        return prodToModifyIndex;
    }

    /**
     * Gets the product to modify.
     *
     * @return the prod to modify
     */
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


    /**
     * Switches scenes to the addPart scene
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,550,550);
        stage.setTitle("Add Part");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * Switches scenes to the modPart scene
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void toModPart(ActionEvent actionEvent) throws IOException {
        partToModify = partsTable.getSelectionModel().getSelectedItem();
        if(partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no parts selected.");
            alert.showAndWait();
        } else{
            partToModifyIndex = Inventory.getAllParts().indexOf(partToModify);

            Parent root = FXMLLoader.load(getClass().getResource("/view/modPart.fxml"));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,550,550);
            stage.setTitle("Modify Part");
            stage.setScene(scene);

            stage.show();
        }
    }

    /**
     * Switches scenes to the mod product screen.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void toModProduct(ActionEvent actionEvent) throws IOException {
        prodToModify = productsTable.getSelectionModel().getSelectedItem();
        if(prodToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no products selected.");
            alert.showAndWait();
        } else{
            prodToModify = productsTable.getSelectionModel().getSelectedItem();
            prodToModifyIndex = Inventory.getAllProducts().indexOf(prodToModify);
            Parent root = FXMLLoader.load(getClass().getResource("/view/modProduct.fxml"));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1050,500);
            stage.setTitle("Modify Product");
            stage.setScene(scene);

            stage.show();
        }
    }

    /**
     * Switches to the addProduct screen.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1050,500);
        stage.setTitle("Add Product");
        stage.setScene(scene);

        stage.show();
    }


    /**
     * Asks the user if they'd like to delete the selection, and then gets the selection from the PartsTable and removes it from Inventory.
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
    public void delete(ActionEvent actionEvent) throws RuntimeException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to permanently delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Part part = partsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(part);
        }
    }

    /**
     * Asks the user if they'd like to delete the selection, and then gets the selection from the ProductsTable and removes it from Inventory.
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
    public void deleteProd(ActionEvent actionEvent) throws RuntimeException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to permanently delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Product product = productsTable.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(product);
        }
    }

    /**
     * Generates data to fill the parts table at initialization
     */
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

        Outsourced e = new Outsourced(5,"5Q 5W20 Oil",10.15,24,3,30,"O'Reilly's");
        Inventory.addPart(e);

        Outsourced f = new Outsourced(6,"Oil Drain Plug",2.99,10,1,25,"Napa");
        Inventory.addPart(f);

        Outsourced g = new Outsourced(7,"Transmission Fluid",29.99,36,12,60,"AutoZone");
        Inventory.addPart(g);

        Outsourced h = new Outsourced(8,"ATF Filter",59.99,1,0,2,"UnderCar");
        Inventory.addPart(h);

        Outsourced i = new Outsourced(9,"Stop Leak",19.99,6,6,12,"Napa");
        Inventory.addPart(i);

        Product product = new Product(Inventory.getAllParts(),1,"Everything",1000.99,2,1,2);
        Inventory.addProduct(product);
    }

    /**
     * Exits the program.
     *
     * @param actionEvent the action event
     */
    public void toExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the program. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).close();
        }
    }

    /**
     * Creates a temporary list of parts from the Inventory.lookupPart() method and sets the PartsTable to display the temporary list.
     * This allows for user search.
     *
     * @param actionEvent the action event
     */
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

    /**
     * Search products.
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
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