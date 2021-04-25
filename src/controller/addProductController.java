package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class addProductController implements Initializable {
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
    public static int nextID = 0;
    public static double currentPrice = 0.00;
    public static double newPrice = 0.00;

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
        productPartList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1050,500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

        stage.show();
        currentPrice = 0.00;
    }

    public void addToProductList(ActionEvent actionEvent) throws RuntimeException{
        ObservableList<Part> selectedRows, productParts;

        selectedRows = pickList.getSelectionModel().getSelectedItems();
        productParts = productPartList.getItems();

        for(Part part : selectedRows){
            productParts.add(part);
            currentPrice += part.getPrice();
        }

        prodPriceTF.setText(String.valueOf(currentPrice));

        productPartList.setItems(productParts);
        System.out.println(productPartList.getItems());
        prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public void delete(ActionEvent actionEvent) throws RuntimeException{
        Part selectedParts = productPartList.getSelectionModel().getSelectedItem();
        ObservableList<Part> productParts = productPartList.getItems();
        productParts.remove(selectedParts);
        productPartList.setItems(productParts);
        currentPrice-=selectedParts.getPrice();
        prodPriceTF.setText(String.valueOf(currentPrice));
    }

    public void saveProduct(ActionEvent actionEvent) throws RuntimeException{
        Product product = new Product(null,0,"0",0,0,0,0);
        product.setAssociatedPart(productPartList.getItems());
        product.setId(nextID+=1);
        product.setName(prodNameTF.getText());
        product.setPrice(Double.parseDouble(prodPriceTF.getText()));
        product.setStock(Integer.parseInt(prodInvTF.getText()));
        product.setMin(Integer.parseInt(prodMinTF.getText()));
        product.setMax(Integer.parseInt(prodMaxTF.getText()));

        System.out.println(product);
        Inventory.addProduct(product);
        clearTextFields();
    }

    public void search(ActionEvent actionEvent){
        try{
            int q = Integer.parseInt(partTF.getText());
            ObservableList<Part> parts = Inventory.lookupPart(q);
            pickList.setItems(parts);
        } catch (NumberFormatException exception){
            String q = partTF.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            pickList.setItems(parts);
        }
    }

    public void clearTextFields() {
        prodNameTF.clear();
        prodPriceTF.clear();
        prodInvTF.clear();
        prodMaxTF.clear();
        prodMinTF.clear();
    }
}


