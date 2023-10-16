package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserRegisterController implements Initializable {
    private Stage stage;
    @FXML
    private JFXComboBox<String> cmbUserType;

    @FXML
    private JFXTextField txtAdminPassword;

    @FXML
    private JFXTextField txtAdminUserName;

    @FXML
    private JFXTextField txtConfirmPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtNewUsername;

    @FXML
    private JFXTextField txtOTP;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
        stage.show();

    }

    @FXML
    void btnCheckOnAction(ActionEvent event) {

    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {

    }

    @FXML
    void btnSendOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbUserType.getItems().addAll("Admin", "Default");
    }
}
