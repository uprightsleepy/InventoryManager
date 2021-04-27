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
 * The type Mod product controller.
 */
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
     * The Part tf.
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
     * Fill table views.
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
     * Sets text fields.
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
     * Delete.
     *
     * @param actionEvent the action event
     * @throws RuntimeException the runtime exception
     */
    public void delete(ActionEvent actionEvent) throws RuntimeException{
        Part selectedParts = productPartList.getSelectionModel().getSelectedItem();
        ObservableList<Part> productParts = productPartList.getItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to remove this part from the list?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            productParts.remove(selectedParts);
            productPartList.setItems(productParts);

            currentPrice-=selectedParts.getPrice();
            prodPriceTF.setText(String.format("%#.2f", currentPrice));

            if(productPartList.getItems().isEmpty()){
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
     * @throws IOException      the io exception
     */
    public void saveProduct(ActionEvent actionEvent) throws RuntimeException, IOException {
        try {
            if(prodNameTF.getText().trim().isEmpty()){
                Alert fail= new Alert(Alert.AlertType.INFORMATION);
                fail.setHeaderText("Enter a Product Name");
                fail.setContentText("You haven't named your product.");
                fail.showAndWait();
            } else {
                if ((Integer.parseInt(prodInvTF.getText()) <= Integer.parseInt(prodMaxTF.getText())) && ((Integer.parseInt(prodInvTF.getText())) > (Integer.parseInt(prodMinTF.getText())))
                        && (Integer.parseInt(prodMinTF.getText())) < (Integer.parseInt(prodMaxTF.getText())) && (Integer.parseInt(prodMinTF.getText())) >= 0 && (Integer.parseInt(prodMaxTF.getText())) > 0) {

                    Product product = new Product(null, mainController.getProdToModify().getId(), "0", 0, 0, 0, 0);
                    product.setAssociatedPart(productPartList.getItems());
                    product.setName(prodNameTF.getText());
                    product.setPrice(Double.parseDouble(prodPriceTF.getText()));
                    product.setStock(Integer.parseInt(prodInvTF.getText()));
                    product.setMin(Integer.parseInt(prodMinTF.getText()));
                    product.setMax(Integer.parseInt(prodMaxTF.getText()));


                    Inventory.updateProduct(mainController.getPartToModifyIndex(), product);
                    backToMain(actionEvent);
                } else {
                    prodInvTF.clear();
                    prodInvTF.setPromptText("Enter a Valid #");

                    prodMinTF.clear();
                    prodMinTF.setPromptText("Enter a Valid #");

                    prodMaxTF.clear();
                    prodMaxTF.setPromptText("Enter a Valid #");
                    modified = false;
                }
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each TextField.");
            alert.showAndWait();
            modified = false;
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
     * Add to product list.
     *
     * @param actionEvent the action event
     */
    public void addToProductList(ActionEvent actionEvent) {
        ObservableList<Part> selectedRows, productParts;

        selectedRows = pickList.getSelectionModel().getSelectedItems();
        productParts = productPartList.getItems();

        for(Part part : selectedRows){
            productParts.add(part);
            currentPrice+=part.getPrice();
            prodPriceTF.setText(String.format("%#.2f", currentPrice));
        }

        productPartList.setItems(productParts);
        System.out.println(productPartList.getItems());
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Search.
     *
     * @param actionEvent the action event
     */
    public void search(ActionEvent actionEvent){
        try{
            int q = Integer.parseInt(partTF.getText());
            ObservableList<Part> parts = Inventory.lookupPart(q);
            pickList.setItems(parts);

            if(partTF.getText().isEmpty()){
                pickList.setItems(Inventory.getAllParts());
            }
        } catch (NumberFormatException exception){
            String q = partTF.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            pickList.setItems(parts);

            if(partTF.getText().isEmpty()){
                pickList.setItems(Inventory.getAllParts());
            }
        }
    }
}