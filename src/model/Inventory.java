package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part){
        allParts.add(part);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Part> getAllProducts(){
        return allProducts;
    }

    public static boolean deletePart(Part part) {
        allParts.remove(part);
        return true;
    }

    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
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
