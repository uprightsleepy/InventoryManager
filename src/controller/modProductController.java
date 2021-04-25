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
import java.util.ResourceBundle;

public class modProductController implements Initializable {

    private static Product modifiedProduct = mainController.getProdToModify();

    public TableView<Part> pickList;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn invColumn;
    public TableColumn cpiColumn;

    public TableView<Part> productPartList;
    public TableColumn prodID;
    public TableColumn prodName;
    public TableColumn prodInv;
    public TableColumn prodPrice;

    public TextField prodIdTF;
    public TextField prodNameTF;
    public TextField prodInvTF;
    public TextField prodPriceTF;
    public TextField prodMinTF;
    public TextField prodMaxTF;

    public TextField partTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTableViews();
    }

    public void fillTableViews() {
        setTextFields(mainController.getProdToModify());
        pickList.setItems(Inventory.getAllParts());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cpiColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        pickList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        System.out.println("PickList -> populated");

        productPartList.setItems(mainController.getProdToModify().getAssociatedParts());
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        System.out.println("productPartList -> populated");
    }

    public void setTextFields(Product product) {
        prodIdTF.setText(String.valueOf(mainController.getProdToModifyIndex()+1));
        prodNameTF.setText(String.valueOf(mainController.getProdToModify().getName()));
        prodPriceTF.setText(String.valueOf(mainController.getProdToModify().getPrice()));
        prodMinTF.setText(String.valueOf(mainController.getProdToModify().getMin()));
        prodMaxTF.setText(String.valueOf(mainController.getProdToModify().getMax()));
        prodInvTF.setText(String.valueOf(mainController.getProdToModify().getStock()));
    }

    public void delete(ActionEvent actionEvent) throws RuntimeException{
        Part selectedParts = productPartList.getSelectionModel().getSelectedItem();
        ObservableList<Part> productParts = productPartList.getItems();

        productParts.remove(selectedParts);
        productPartList.setItems(productParts);
    }

    public void saveProduct(ActionEvent actionEvent) throws RuntimeException, IOException {
        Product product = new Product(null,mainController.getProdToModifyIndex()+1,"0",0,0,0,0);
        product.setAssociatedPart(productPartList.getItems());
        product.setName(prodNameTF.getText());
        product.setPrice(Double.parseDouble(prodPriceTF.getText()));
        product.setStock(Integer.parseInt(prodInvTF.getText()));
        product.setMin(Integer.parseInt(prodMinTF.getText()));
        product.setMax(Integer.parseInt(prodMaxTF.getText()));

        System.out.println(product);
        Inventory.updateProduct(mainController.getPartToModifyIndex(),product);
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1050,500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

        stage.show();
    }

    public void addToProductList(ActionEvent actionEvent) {
        ObservableList<Part> selectedRows, productParts;

        selectedRows = pickList.getSelectionModel().getSelectedItems();
        productParts = productPartList.getItems();

        for(Part part : selectedRows){
            productParts.add(part);
        }

        productPartList.setItems(productParts);
        System.out.println(productPartList.getItems());
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

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
