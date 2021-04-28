package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The class allows modification of previously existing products.*/
public class modProductController implements Initializable {

    private static Product modifiedProduct = mainController.getProdToModify();
    /**
     * The constant currentPrice.
     */
    public static double currentPrice = 0.00;

    /**
     * The Pick list.
     */
    public TableView<Part> pickList;
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
     * The Product part list.
     */
    public TableView<Part> productPartList;
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
     * The Prod id TextField.
     */
    public TextField prodIdTF;
    /**
     * The Prod name TextField.
     */
    public TextField prodNameTF;
    /**
     * The Prod inv TextField.
     */
    public TextField prodInvTF;
    /**
     * The Prod price TextField.
     */
    public TextField prodPriceTF;
    /**
     * The Prod min TextField.
     */
    public TextField prodMinTF;
    /**
     * The Prod max TextField.
     */
    public TextField prodMaxTF;

    /**
     * The Part TextField.
     */
    public TextField partTF;
    /**
     * The constant modified.
     */
    public static boolean modified = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTableViews();
        setTextFields(mainController.getProdToModify());
        pickList.setItems(Inventory.getAllParts());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cpiColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        pickList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        productPartList.setItems(mainController.getProdToModify().getAssociatedParts());
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Fills table views with selected product's values.
     */
    public void fillTableViews() {
        setTextFields(mainController.getProdToModify());

        pickList.setItems(Inventory.getAllParts());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cpiColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        pickList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        productPartList.setItems(mainController.getProdToModify().getAssociatedParts());
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Sets initial text fields based on the previously selected product to modify.
     *
     * @param product the product
     */
    public void setTextFields(Product product) {
        prodIdTF.setText(String.valueOf(mainController.getProdToModifyIndex()+1));
        prodNameTF.setText(String.valueOf(mainController.getProdToModify().getName()));
        prodPriceTF.setText(String.format("%#.2f", modifiedProduct.getPrice()));

        currentPrice = mainController.getProdToModify().getPrice();

        prodMinTF.setText(String.valueOf(mainController.getProdToModify().getMin()));
        prodMaxTF.setText(String.valueOf(mainController.getProdToModify().getMax()));
        prodInvTF.setText(String.valueOf(mainController.getProdToModify().getStock()));
    }

    /**
     * deletes a part from the product listing in the lower tableview
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
    public void delete(ActionEvent actionEvent) throws RuntimeException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove part from product listing?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedParts = productPartList.getSelectionModel().getSelectedItem();
            ObservableList<Part> productParts = productPartList.getItems();
            productParts.remove(selectedParts);
            productPartList.setItems(productParts);
            currentPrice -= selectedParts.getPrice();
            prodPriceTF.setText(String.format("%#.2f", currentPrice));

            if (productPartList.getItems().isEmpty()) {
                currentPrice = 0.00;
                prodPriceTF.setText(String.format("%#.2f", currentPrice));
            }
        }
    }

    /**
     * Checks to see if all of the fields are filled out. If they are, it saves the product.
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
    public void saveProduct(ActionEvent actionEvent) throws RuntimeException {
        try {
            int id = 0;
            String name = prodNameTF.getText();
            double price = Double.parseDouble(prodPriceTF.getText());
            int stock = Integer.parseInt(prodInvTF.getText());
            int min = Integer.parseInt(prodMinTF.getText());
            int max = Integer.parseInt(prodMaxTF.getText());


            Product product = new Product(null, id, name, price, stock, min, max);
            product.setAssociatedPart(productPartList.getItems());
            product.setId(mainController.getProdToModify().getId());
            product.setPrice(price);
            product.setName(name);

            if (prodNameTF.getText().trim().isEmpty()) {
                Alert fail = new Alert(Alert.AlertType.INFORMATION);
                fail.setHeaderText("Please Fill Out All Fields");
                fail.setContentText("Your product is missing some values.");
                fail.showAndWait();
            } else {
                if (stock <= max && stock > min && min >= 0) {
                    product.setStock(stock);
                    product.setMin(min);
                    product.setMax(max);

                    Inventory.updateProduct(mainController.getProdToModifyIndex(),product);
                    currentPrice = 0.00;
                    backToMain(actionEvent);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Min must be less than max. Stock should be a value \nbetween the two.");
                    alert.showAndWait();
                }
            }
        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each TextField.");
            alert.showAndWait();
        }
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
            currentPrice = 0.00;
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
     * Adds a part from the upper list to the lower list for part association.
     *
     * @param actionEvent the action event
     */
    public void addToProductList(ActionEvent actionEvent) {
        ObservableList<Part> selectedRows, productParts;

        selectedRows = pickList.getSelectionModel().getSelectedItems();
        productParts = productPartList.getItems();

        for (Part part : selectedRows) {
            productParts.add(part);
            currentPrice += part.getPrice();
        }

        prodPriceTF.setText(String.format("%#.2f", currentPrice));

        productPartList.setItems(productParts);
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * Searches the upper list for a part.
     *
     * @param actionEvent the action event
     */
    public void search(ActionEvent actionEvent) {
        try {
            int q = Integer.parseInt(partTF.getText());
            ObservableList<Part> parts = Inventory.lookupPart(q);
            pickList.setItems(parts);
        } catch (NumberFormatException exception) {
            String q = partTF.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            pickList.setItems(parts);
        }
    }
}