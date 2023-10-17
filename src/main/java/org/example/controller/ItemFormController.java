package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.db.DBConnection;
import org.example.model.Item;
import org.example.model.Supplier;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    public JFXTextField txtType;
    public JFXTextField txtSize;
    public JFXButton btnAdd;
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
        try{
            Item item = new Item(
                    txtItemCode.getText(),
                    txtItemDescription.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtBuyingPrice.getText()),
                    cmbType.getValue().toString(),
                    cmbSize.getValue().toString(),
                    cmbSupplierId.getValue().toString(),
                    Double.parseDouble(txtProfit.getText())

            );

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?,?,?,?,?)");
            pstm.setObject(1, item.getItemCode());
            pstm.setObject(2, item.getDescription());
            pstm.setObject(3, item.getQty());
            pstm.setObject(4, item.getSellingPrice());
            pstm.setObject(5, item.getBuyingPrice());
            pstm.setObject(6, item.getType());
            pstm.setObject(7, item.getSize());
            pstm.setObject(8, item.getSupplierId());
            pstm.setObject(9, item.getProfit());

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added Successfully !").show();
//                clearItems();
                generateId();
//                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }

        }catch (SQLException | ClassNotFoundException | NullPointerException exception){
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please Add an Item !").show();

        }

    }

    public void btnAddOnAction(ActionEvent actionEvent) {

    }

    public static String getLastItemId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1");
        return rst.next() ? rst.getString("itemCode") : null;
    }
    private void generateId() {
        try {
            String lastItemId = getLastItemId();
            if (lastItemId != null && lastItemId.matches("P-\\d{4}")) {
                int numericPart = Integer.parseInt(lastItemId.substring(4)) + 1;
                String newItemId = String.format("P-%04d", numericPart);
                txtItemCode.setText(newItemId);
            } else {
                txtItemCode.setText("P-0001");
            }
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    private void loadProfit() {
        txtSellingPrice.setOnKeyReleased(ke -> {
            if (txtSellingPrice.getText()!=null && txtSellingPrice.getText().matches("^-?\\d+(\\.\\d{2}+)?$")) {
                txtSellingPrice.setOnKeyTyped(actionEvent -> {
                    if (!txtSellingPrice.getText().isEmpty() && !txtBuyingPrice.getText().isEmpty()) {
                        String profit = String.format("%8.2f", Double.parseDouble(txtSellingPrice.getText()) - Double.parseDouble(txtBuyingPrice.getText()));
                        txtProfit.setText(profit);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Enter only 0-9 values..!").show();
            }
        });
        txtBuyingPrice.setOnKeyReleased(ke -> {
            if (txtBuyingPrice.getText()!=null && txtBuyingPrice.getText().matches("^-?\\d+(\\.\\d{2}+)?$")) {
                txtBuyingPrice.setOnKeyTyped(actionEvent -> {
                    if (!txtSellingPrice.getText().isEmpty() && !txtBuyingPrice.getText().isEmpty()) {
                        String profit = String.format("%8.2f", Double.parseDouble(txtSellingPrice.getText()) - Double.parseDouble(txtBuyingPrice.getText()));
                        txtProfit.setText(profit);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Enter only 0-9 values..!").show();
            }
        });
    }

    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }

    private void loadAllSupplierId() throws SQLException, ClassNotFoundException {
        ArrayList<String> supplierId= new SupplierFormController().getAllSupplierId();
        cmbSupplierId.getItems().addAll(supplierId);
    }

    private void loadAllSupplierName() throws SQLException, ClassNotFoundException {
        ArrayList<String> supplierName= new SupplierFormController().getAllSupplierName();
        cmbSupplierName.getItems().addAll(supplierName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbType.getItems().addAll("Gents", "Ladies","Kids","Others");
        cmbSize.getItems().addAll("M", "S","Custom");
        generateId();
        loadProfit();
        try {
            loadAllSupplierId();
            loadAllSupplierName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        cmbType.setOnAction(event -> {
            if (cmbType.getValue()!=null){
                if (!cmbType.getValue().equals("Others") && !cmbType.getValue().toString().isEmpty()){
                    txtType.setDisable(true);
                    txtSize.clear();
                    txtType.setDisable(true);
                }else if (cmbType.getValue().equals("Others")){
                    txtType.setDisable(false);
                }
            }
        });
        cmbSize.setOnAction(event1 -> {
            if (cmbSize.getValue()!=null) {
                if (cmbSize.getValue().equals("Custom")) {
                    txtSize.setDisable(false);
                } else {
                    txtSize.clear();
                    txtSize.setDisable(true);
                }
            }else{
                txtSize.clear();
                txtSize.setDisable(true);
            }
        });

        txtType.setOnKeyReleased(ke -> {
                if (!txtType.getText().isEmpty()) {
                    btnAdd.setDisable(false);
                } else {
                    btnAdd.setDisable(true);
                }
        });

        txtSize.setOnKeyReleased(ke -> {
            if (!txtSize.getText().isEmpty()) {
                btnAdd.setDisable(false);
            } else {
                btnAdd.setDisable(true);
            }
        });





    }

    public void cmbSupplierIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String supId = cmbSupplierId.getSelectionModel().getSelectedItem().toString();
        cmbSupplierName.setValue(SupplierFormController.searchSupplierById(supId).getSupplierName());
    }

    public void cmbNameOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String supName = cmbSupplierName.getSelectionModel().getSelectedItem().toString();
        cmbSupplierId.setValue(SupplierFormController.searchSupplierByName(supName).getSupplierId());
    }

}
