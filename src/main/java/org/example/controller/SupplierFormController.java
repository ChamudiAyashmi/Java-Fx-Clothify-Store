package org.example.controller;

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

public class SupplierFormController {
    private Stage stage;

    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierContact;
    public JFXTextField txtSupplierId;
    public TableView tblSupplier;
    public JFXComboBox cmbTitle;
    public JFXTextField txtCompany;
    public JFXTextField txtSearchbar;
    public TableView tblItem;

    public void btnClearOnAction(ActionEvent actionEvent) {

    }

    public void btnPrintOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

    }

    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }
}
