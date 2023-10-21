package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public Label lblOrderId;
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

    public static String getLastOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1");
        return rst.next() ? rst.getString("orderId") : null;
    }
    private void generateId() {
        try {
            String lastOrderId = getLastOrderId();
            if (lastOrderId != null && lastOrderId.matches("ORD-\\d{4}")) {
                int numericPart = Integer.parseInt(lastOrderId.substring(4)) + 1;
                String newOrderId = String.format("ORD-%04d", numericPart);
                lblOrderId.setText(newOrderId);
            } else {
                lblOrderId.setText("ORD-0001");
            }
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }


    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateId();
    }
}
