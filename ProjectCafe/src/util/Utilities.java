/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Support class
 * @author David Fuchs
 */
public class Utilities {
    /**
     * Message box for displaying error and informational messages to the user.
     * @param infoMessage
     * @param titleBar
     * @param headerMessage
     * @param alertType 
     */
    public static void messageBox(String infoMessage, String titleBar,
            String headerMessage, AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
