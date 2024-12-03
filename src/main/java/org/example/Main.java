package org.example;
import window.mainWindow;


import java.io.IOException;


public class Main{

    public static void main(String[] args) throws IOException {
        mainWindow window = new mainWindow();
        for(String arg : args){
            System.out.println(arg);
        }
        window.setVisible(true);
    }
}