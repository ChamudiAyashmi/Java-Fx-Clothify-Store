package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SalesReportsFormController {
    private Stage stage;
    public JFXComboBox cmbDateTime;

    public void btnDailyReportsOnAction(ActionEvent actionEvent) {

    }

    public void btnMonthlyReportsOnAction(ActionEvent actionEvent) {

    }

    public void btnAnnulaReportOnAction(ActionEvent actionEvent) {

    }

    public void btnDailyReturnsOnAction(ActionEvent actionEvent) {

    }

    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }
}
