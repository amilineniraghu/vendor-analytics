package com.vendoranalytics.xyz;

import com.vendoranalytics.xyz.business.Process;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField directoryPath;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(directoryPath.getText());
        Process process = new Process();
        process.readAllFiles(directoryPath.getText());
    }

}