package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderFormController {
    private Stage stage;
    public Label lblDiscount;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtCustomerEmail;
    public JFXTextField txtCustomerName;
    public JFXComboBox cmbEmployerId;
    public JFXTextField txtEmployerName;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtType;
    public JFXTextField txtSize;
    public JFXTextField txtDiscount;
    public JFXTextField txtProfit;
    public Label lblTotal;
    public Label lblCash;
    public Label lblBalance;

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

    }

    public void btnRemoveOrderOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

    }

    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }
}
