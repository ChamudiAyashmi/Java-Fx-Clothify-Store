package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class DashboardController {
    private Stage stage;
    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/order_form.fxml"))));
        stage.show();
    }
    public void btnItemsOnAction(ActionEvent actionEvent) {

    }

    public void btnEmployersOnAction(ActionEvent actionEvent) {

    }

    public void btnSuppliersOnAction(ActionEvent actionEvent) {

    }

    public void btnOrderDetailsOnAction(ActionEvent actionEvent) {

    }

    public void btnSalesReturnsOnAction(ActionEvent actionEvent) {

    }

    public void btnSalesReportsOnAction(ActionEvent actionEvent) {

    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
        stage.show();
    }
}
