package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part){
        allParts.add(part);
    }
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    public static boolean deletePart(Part part) {
        allParts.remove(part);
        return true;
    }

    public static boolean deleteProduct(Product product) {
        allProducts.remove(product);
        return true;
    }

    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for(Part p: allParts){
            if(p.getName().contains(partName) || p.getName().toLowerCase(Locale.ROOT).contains(partName)){
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    public static ObservableList<Product> lookupProduct(int prodID){
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        for(Product p: allProducts){
            if((p.getId()) == prodID){
                namedProduct.add(p);
            }
        }
        return namedProduct;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for(Product p: allProducts){
            if(p.getName().contains(productName) || p.getName().toLowerCase(Locale.ROOT).contains(productName)){
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    public static ObservableList<Part> lookupPart(int partID){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for(Part p: allParts){
            if((p.getId()) == partID){
                namedParts.add(p);
            }
        }
        return namedParts;
    }
}
