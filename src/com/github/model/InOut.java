package com.github.model;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InOut {

    public static void writeToFile(String fileName){
        try {
            File file = new File(fileName);
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //out.writeObject(items);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public static void readFromFile(String fileName){ //ArrayList<Item> items
        try {
            Path path = Paths.get(fileName);
            FileInputStream fileIn = new FileInputStream(String.valueOf(path));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //items = (ArrayList<Item>) in.readObject();
            in.close();
            fileIn.close();
            File file = new File(String.valueOf(path.toString()));
            file.delete();
        } catch (IOException i) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No data stored!");
            alert.showAndWait();
        }
    }

}
