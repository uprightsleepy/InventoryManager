package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 * The type Inventory.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add part.
     *
     * @param part the part
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     * Add product.
     *
     * @param product the product
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     * Get all parts observable list.
     *
     * @return the observable list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Get all products observable list.
     *
     * @return the observable list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * Delete part boolean.
     *
     * @param part the part
     * @return the boolean
     */
    public static boolean deletePart(Part part) {
        allParts.remove(part);
        return true;
    }

    /**
     * Delete product boolean.
     *
     * @param product the product
     * @return the boolean
     */
    public static boolean deleteProduct(Product product) {
        allProducts.remove(product);
        return true;
    }

    /**
     * Update part.
     *
     * @param index        the index
     * @param selectedPart the selected part
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * Update product.
     *
     * @param index           the index
     * @param selectedProduct the selected product
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     * Lookup part observable list.
     *
     * @param partName the part name
     * @return the observable list
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for(Part p: allParts){
            if(p.getName().contains(partName) || p.getName().toLowerCase(Locale.ROOT).contains(partName)){
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /**
     * Lookup product observable list.
     *
     * @param prodID the prod id
     * @return the observable list
     */
    public static ObservableList<Product> lookupProduct(int prodID){
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        for(Product p: allProducts){
            if((p.getId()) == prodID){
                namedProduct.add(p);
            }
        }
        return namedProduct;
    }

    /**
     * Lookup product observable list.
     *
     * @param productName the product name
     * @return the observable list
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for(Product p: allProducts){
            if(p.getName().contains(productName) || p.getName().toLowerCase(Locale.ROOT).contains(productName)){
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    /**
     * Lookup part observable list.
     *
     * @param partID the part id
     * @return the observable list
     */
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