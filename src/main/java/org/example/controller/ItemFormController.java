package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemFormController {
    private Stage stage;
    public JFXTextField txtItemDescription;
    public JFXTextField txtQty;
    public JFXTextField txtItemCode;
    public JFXComboBox cmbType;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtProfit;
    public JFXButton btnPrintOnAction;
    public TableView tblItem;
    public JFXComboBox cmbSupplierId;
    public JFXComboBox cmbSupplierName;
    public JFXTextField txtBuyingPrice;
    public JFXTextField txtSearchbar;
    public JFXTextField txtAddQty;
    public JFXComboBox cmbSize;

    public void btnPrintOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {

    }

    public void btnAddStockOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

    }

    public void btnAddOnAction(ActionEvent actionEvent) {

    }

    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }
}
