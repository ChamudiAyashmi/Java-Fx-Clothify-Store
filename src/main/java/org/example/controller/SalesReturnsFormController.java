package org.example.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SalesReturnsFormController {
    private Stage stage;
    public JFXTextField txtQty;
    public TableView tblSalesReturns;
    public JFXTextField txtOrderIdSearchbar;
    public Label lblTotal;

    public void btnClearOnAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnPlaceReturnOnAction(ActionEvent actionEvent) {

    }

    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }
}
