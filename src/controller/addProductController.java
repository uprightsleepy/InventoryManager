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
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * The type Add product controller.
 */
public class addProductController implements Initializable {
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
     * The Prod id tf.
     */
    public TextField prodIdTF;
    /**
     * The Prod name tf.
     */
    public TextField prodNameTF;
    /**
     * The Prod inv tf.
     */
    public TextField prodInvTF;
    /**
     * The Prod price tf.
     */
    public TextField prodPriceTF;
    /**
     * The Prod min tf.
     */
    public TextField prodMinTF;
    /**
     * The Prod max tf.
     */
    public TextField prodMaxTF;
    /**
     * The constant nextID.
     */
    public static int nextID = 0;
    /**
     * The constant currentPrice.
     */
    public static double currentPrice = 0.00;

    /**
     * The Part tf.
     */
    public TextField partTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Inventory.getAllParts());
        pickList.setItems(Inventory.getAllParts());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cpiColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        pickList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root, 1050, 500);
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
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root, 1050, 500);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

            stage.show();
        }
    }

    /**
     * Add to product list.
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
    public void addToProductList(ActionEvent actionEvent) throws RuntimeException {
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
     * Delete.
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
    public void delete(ActionEvent actionEvent) throws RuntimeException {
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
     * Save product.
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
            product.setId(Inventory.getAllProducts().size() + 1);
            product.setPrice(price);
            product.setName(name);

            if (prodNameTF.getText().trim().isEmpty() || productPartList.getItems().isEmpty()) {
                Alert fail = new Alert(Alert.AlertType.INFORMATION);
                fail.setHeaderText("Please Fill Out All Fields");
                fail.setContentText("Your product is missing some values.");
                fail.showAndWait();
            } else {
                if ((stock <= max) && (stock > min)
                        && (min < max) && (min) >= 0 && (max > 0)) {
                    product.setStock(stock);
                    product.setMin(min);
                    product.setMax(max);

                    Inventory.addProduct(product);
                    currentPrice = 0.00;
                    backToMain(actionEvent);

                } else {

                    prodInvTF.clear();
                    prodInvTF.setPromptText("Enter a Valid #");

                    prodMinTF.clear();
                    prodMinTF.setPromptText("Enter a Valid #");

                    prodMaxTF.clear();
                    prodMaxTF.setPromptText("Enter a Valid #");
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
     * Search.
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