package org.example.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Util.CrudUtil;
import org.example.db.DBConnection;
import org.example.entity.Item;
import org.example.tm.ItemTm;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    public JFXTextField txtType;
    public JFXTextField txtSize;
    public JFXButton btnAdd;
    public TreeTableColumn colCode;
    public JFXTreeTableView<ItemTm> tblItem;
    public TreeTableColumn colDescription;
    public TreeTableColumn colQty;
    public TreeTableColumn colSellingPrice;
    public TreeTableColumn colBuyingPrice;
    public TreeTableColumn colSize;
    public TreeTableColumn colType;
    public TreeTableColumn colProfit;
    public TreeTableColumn colSupplierId;
    public TreeTableColumn colOption;
    private Stage stage;
    public JFXTextField txtItemDescription;
    public JFXTextField txtQty;
    public JFXTextField txtItemCode;
    public JFXComboBox cmbType;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtProfit;
    public JFXButton btnPrintOnAction;
    public JFXComboBox cmbSupplierId;
    public JFXComboBox cmbSupplierName;
    public JFXTextField txtBuyingPrice;
    public JFXTextField txtSearchbar;
    public JFXTextField txtAddQty;
    public JFXComboBox cmbSize;

    public void btnPrintOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearItems();
    }

    public void btnAddStockOnAction(ActionEvent actionEvent) {

    }

    private void loadTable(){
        ObservableList<ItemTm> itmTmList = FXCollections.observableArrayList();
        try {
            List<Item> list = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()){
                list.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getDouble(9)
                ));
            }

            for (Item item:list) {
                JFXButton btn = new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(250,15,15)));

                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst = connection.prepareStatement("DELETE FROM item WHERE itemCode=?");
                        pst.setString(1,item.getSupplierId());

                        if (pst.executeUpdate()>0){
                            new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted...!").show();
                            loadTable();
                        }else {
                            new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                itmTmList.add(new ItemTm(
                        item.getItemCode(),
                        item.getDescription(),
                        item.getQty(),
                        item.getSellingPrice(),
                        item.getBuyingPrice(),
                        item.getType(),
                        item.getSize(),
                        item.getSupplierId(),
                        item.getProfit(),
                        btn
                ));
            }

            TreeItem<ItemTm> supplierTmTreeItem = new RecursiveTreeItem<>(itmTmList, RecursiveTreeObject::getChildren);
            tblItem.setRoot(supplierTmTreeItem);
            tblItem.setShowRoot(false);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setData(TreeItem<ItemTm> value){
        txtItemCode.setText(value.getValue().getItemCode());
        txtItemDescription.setText(value.getValue().getDescription());
        txtQty.setText(String.valueOf(value.getValue().getQty()));
        txtSellingPrice.setText(String.valueOf(value.getValue().getSellingPrice()));
        txtBuyingPrice.setText(String.valueOf(value.getValue().getBuyingPrice()));
        cmbType.setValue(value.getValue().getType());
        cmbSize.setValue(value.getValue().getSize());
        cmbSupplierId.setValue(value.getValue().getSupplierId());
        txtProfit.setText(String.valueOf(value.getValue().getProfit()));
    }

    public void clearItems(){
        txtItemDescription.clear();
        txtQty.clear();
        txtSellingPrice.clear();
        txtBuyingPrice.clear();
        txtType.clear();
        txtSize.clear();
//        cmbSupplierId.setValue(null);
//        cmbSupplierName.setValue(null);
        txtProfit.clear();
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
                clearItems();
                generateId();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }

        }catch (SQLException | ClassNotFoundException | NullPointerException exception){
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please Add an Item !").show();

        }

    }

    public static List<Item> getItemsBySupplierId(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM item WHERE supplierId=?", id);
        List<Item> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9)
            ));
        }
        return list;
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
        loadTable();

        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colSellingPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("sellingPrice"));
        colBuyingPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("buyingPrice"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        colSupplierId.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierId"));
        colProfit.setCellValueFactory(new TreeItemPropertyValueFactory<>("profit"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnDelete"));

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                setData(newValue);
            }
        } );


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
