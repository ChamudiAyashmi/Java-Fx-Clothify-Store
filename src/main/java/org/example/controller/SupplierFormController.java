package org.example.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.example.db.DBConnection;
import org.example.entity.Item;
import org.example.entity.Supplier;
import org.example.tm.SupplierTm;
import org.example.tm.SuppliesTm;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SupplierFormController implements Initializable {
    public JFXTextField txtSupplierEmail;
    public JFXTreeTableView<SupplierTm> tblSuppliers;
    public TreeTableColumn colId;
    public TreeTableColumn colTitle;
    public TreeTableColumn colName;
    public TreeTableColumn colCompany;
    public TreeTableColumn colContact;
    public TreeTableColumn colOption;
    public TreeTableColumn colEmail;
    public JFXTreeTableView<SuppliesTm> tblSupplies;
    public TreeTableColumn colItemCode;
    public TreeTableColumn colDescription;
    public TreeTableColumn colQty;
    private Stage stage;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierContact;
    public JFXTextField txtSupplierId;
    public JFXComboBox cmbTitle;
    public JFXTextField txtCompany;
    public JFXTextField txtSearchbar;
//    public TableView tblItem;

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearItems();
        generateId();
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {

    }

    private void loadTable(){
        ObservableList<SupplierTm> supTmList = FXCollections.observableArrayList();
        try {
            List<Supplier> list = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM supplier");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()){
                list.add(new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));
            }

            for (Supplier supplier:list) {
                JFXButton btn = new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(250,15,15)));

                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst = connection.prepareStatement("DELETE FROM supplier WHERE supplierId=?");
                        pst.setString(1,supplier.getSupplierId());

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

                supTmList.add(new SupplierTm(
                        supplier.getSupplierId(),
                        supplier.getTitle(),
                        supplier.getSupplierName(),
                        supplier.getContact(),
                        supplier.getCompany(),
                        supplier.getEmail(),
                        btn
                ));
            }

            TreeItem<SupplierTm> supplierTmTreeItem = new RecursiveTreeItem<>(supTmList, RecursiveTreeObject::getChildren);
            tblSuppliers.setRoot(supplierTmTreeItem);
            tblSuppliers.setShowRoot(false);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearItems(){
        txtSupplierId.setText("");
        cmbTitle.setValue(null);
        txtSupplierName.setText("");
        txtSupplierContact.setText("");
        txtCompany.setText("");
        txtSupplierEmail.setText("");
    }

    public static String getLastSupplierId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT supplierId FROM supplier ORDER BY supplierId DESC LIMIT 1");
        return rst.next() ? rst.getString("supplierId") : null;
    }
    private void generateId() {
        try {
            String lastSupplierId = getLastSupplierId();
            if (lastSupplierId != null && lastSupplierId.matches("SUP-\\d{4}")) {
                int numericPart = Integer.parseInt(lastSupplierId.substring(4)) + 1;
                String newSupplierId = String.format("SUP-%04d", numericPart);
                txtSupplierId.setText(newSupplierId);
            } else {
                txtSupplierId.setText("SUP-0001");
            }
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try{
            Supplier supplier = new Supplier(
                    txtSupplierId.getText(),
                    cmbTitle.getSelectionModel().getSelectedItem().toString(),
                    txtSupplierName.getText(),
                    txtSupplierContact.getText(),
                    txtCompany.getText(),
                    txtSupplierEmail.getText()
            );

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO supplier VALUES (?,?,?,?,?,?)");
            pstm.setObject(1, supplier.getSupplierId());
            pstm.setObject(2, supplier.getTitle());
            pstm.setObject(3, supplier.getSupplierName());
            pstm.setObject(4, supplier.getContact());
            pstm.setObject(5, supplier.getCompany());
            pstm.setObject(6, supplier.getEmail());

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Added Successfully !").show();
                clearItems();
                generateId();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }

        }catch (SQLException | ClassNotFoundException | NullPointerException exception){
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please Add an Supplier !").show();

        }

    }

    public static Supplier searchSupplierById(String supId) throws SQLException, ClassNotFoundException {
        ResultSet rst=DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * From Supplier where supplierId='"+supId+"'");
        return rst.next() ? new Supplier(supId,rst.getString("title"),rst.getString("supplierName"),rst.getString("contact"),rst.getString("company"),rst.getString("email")):null;
    }
    public static Supplier searchSupplierByName(String supName) throws SQLException, ClassNotFoundException {
        ResultSet rst=DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * From Supplier where supplierName='"+supName+"'");
        return rst.next() ? new Supplier(rst.getString("supplierId"),rst.getString("title"),supName,rst.getString("contact"),rst.getString("company"),rst.getString("email")):null;
    }


    public static ArrayList<String> getAllSupplierId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT supplierId from supplier");
        ResultSet rst = pstm.executeQuery();
        ArrayList<String> idSet = new ArrayList<>();
        while (rst.next()){
            idSet.add(rst.getString(1));
        }
        return idSet;
    }

    public static ArrayList<String> getAllSupplierName() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT supplierName from supplier");
        ResultSet rst = pstm.executeQuery();
        ArrayList<String> idSet = new ArrayList<>();
        while (rst.next()){
            idSet.add(rst.getString(1));
        }
        return idSet;
    }

    public void btnBackToDashboardOnClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierId"));
        colTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierName"));
        colContact.setCellValueFactory(new TreeItemPropertyValueFactory<>("contact"));
        colCompany.setCellValueFactory(new TreeItemPropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnDelete"));

        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));

        generateId();
        loadTable();

        tblSuppliers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                setData(newValue);
            }
        } );

        txtSearchbar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblSuppliers.setPredicate(new Predicate<TreeItem<SupplierTm>>() {
                    @Override
                    public boolean test(TreeItem<SupplierTm> supplierTmTreeItem) {
                        boolean flag = supplierTmTreeItem.getValue().getSupplierId().contains(newValue) ||
                                supplierTmTreeItem.getValue().getSupplierName().contains(newValue);
                        return flag;
                    }
                });
            }
        });


        cmbTitle.getItems().addAll("Mr", "Mrs","Miss");

    }

    private void setData(TreeItem<SupplierTm> value){
        txtSupplierId.setText(value.getValue().getSupplierId());
        cmbTitle.setValue(value.getValue().getTitle());
        txtSupplierName.setText(value.getValue().getSupplierName());
        txtSupplierContact.setText(value.getValue().getContact());
        txtCompany.setText(value.getValue().getCompany());
        txtSupplierEmail.setText(value.getValue().getEmail());

        setSupplies(value.getValue().getSupplierId());

    }

    private void setSupplies(String supplierId) {
        ObservableList<SuppliesTm> list=FXCollections.observableArrayList();
        try {
            for (Item item :ItemFormController.getItemsBySupplierId(supplierId) ) {
                list.add(new SuppliesTm(
                                item.getItemCode(), item.getDescription(),item.getQty()
                        )

                );
            }
            TreeItem<SuppliesTm> treeItem=new RecursiveTreeItem<>(list,RecursiveTreeObject::getChildren);
            tblSupplies.setRoot(treeItem);
            tblSupplies.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
