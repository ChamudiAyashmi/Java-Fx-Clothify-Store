package org.example.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.db.DBConnection;
import org.example.model.Employer;
import org.example.tm.EmployerTm;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class EmployerFormController implements Initializable {
    public DatePicker datePicker;
    public TreeTableColumn colId;
    public TreeTableColumn colTitle;
    public TreeTableColumn colName;
    public TreeTableColumn colNic;
    public TreeTableColumn colDob;
    public TreeTableColumn colAddress;
    public TreeTableColumn colContact;
    public TreeTableColumn colBankAccNo;
    public TreeTableColumn colBankBranch;
    public TreeTableColumn colOption;
    private Stage stage;
    public JFXTextField txtNic;
    public JFXTextField txtAddress;
    public JFXTextField txtEmployerId;
    public JFXTextField txtBankBranch;
    @FXML
    private JFXTreeTableView<EmployerTm> tblEmployer;
    public JFXComboBox cmbTitle;
    public JFXTextField txtSearchbar;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtBankAccNo;

    public void btnPrintOnAction(ActionEvent actionEvent) {

    }

    public void clearItems(){
        txtEmployerId.setText("");
        cmbTitle.setValue(null);
        txtName.setText("");
        txtNic.setText("");
        datePicker.getEditor().clear();
        txtAddress.setText("");
        txtContact.setText("");
        txtBankAccNo.setText("");
        txtBankBranch.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearItems();
        generateId();
    }

    public static String getLastSupplierId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT empId FROM Employer ORDER BY empId DESC LIMIT 1");
        return rst.next() ? rst.getString("empId") : null;
    }
    private void generateId() {
        try {
            String lastSupplierId = getLastSupplierId();
            if (lastSupplierId != null && lastSupplierId.matches("EMP-\\d{4}")) {
                int numericPart = Integer.parseInt(lastSupplierId.substring(4)) + 1;
                String newSupplierId = String.format("EMP-%04d", numericPart);
                txtEmployerId.setText(newSupplierId);
            } else {
                txtEmployerId.setText("EMP-0001");
            }
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{

            Employer employer = new Employer(
                    txtEmployerId.getText(),
                    cmbTitle.getSelectionModel().getSelectedItem().toString(),
                    txtName.getText(),
                    txtNic.getText(),
                    datePicker.getValue(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtBankAccNo.getText(),
                    txtBankBranch.getText()
            );

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO employer VALUES (?,?,?,?,?,?,?,?,?)");
            pstm.setObject(1, employer.getEmpId());
            pstm.setObject(2, employer.getTitle());
            pstm.setObject(3, employer.getName());
            pstm.setObject(4, employer.getNic());
            pstm.setObject(5, employer.getDateOfBirth());
            pstm.setObject(6, employer.getAddress());
            pstm.setObject(7, employer.getContactNo());
            pstm.setObject(8, employer.getBankAccNo());
            pstm.setObject(9, employer.getBankBranch());

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Employer Added Successfully !").show();
                clearItems();
                generateId();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }

        }catch (SQLException | ClassNotFoundException | NullPointerException exception){
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please Add an Employer !").show();

        }

    }

    private void loadTable(){
        ObservableList<EmployerTm> empTmList = FXCollections.observableArrayList();
        try {
            List<Employer> list = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM employer");
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()){
                list.add(new Employer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5).toLocalDate(),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                ));
            }

            for (Employer employer:list) {
                JFXButton btn = new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(250,15,15)));

                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst = connection.prepareStatement("DELETE FROM employer WHERE empId=?");
                        pst.setString(1,employer.getEmpId());

                        if (pst.executeUpdate()>0){
                            new Alert(Alert.AlertType.INFORMATION,"Employer Deleted...!").show();
                            loadTable();
                        }else {
                            new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                empTmList.add(new EmployerTm(
                        employer.getEmpId(),
                        employer.getTitle(),
                        employer.getName(),
                        employer.getNic(),
                        employer.getDateOfBirth(),
                        employer.getAddress(),
                        employer.getContactNo(),
                        employer.getBankAccNo(),
                        employer.getBankBranch(),
                        btn
                ));


            }

            TreeItem<EmployerTm> employerTmTreeItem = new RecursiveTreeItem<>(empTmList, RecursiveTreeObject::getChildren);
            tblEmployer.setRoot(employerTmTreeItem);
            tblEmployer.setShowRoot(false);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("empId"));
        colTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new TreeItemPropertyValueFactory<>("nic"));
        colDob.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateOfBirth"));
        colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new TreeItemPropertyValueFactory<>("contactNo"));
        colBankAccNo.setCellValueFactory(new TreeItemPropertyValueFactory<>("bankAccNo"));
        colBankBranch.setCellValueFactory(new TreeItemPropertyValueFactory<>("bankBranch"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnDelete"));
        generateId();
        loadTable();

        tblEmployer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                setData(newValue);
            }
        } );
        txtSearchbar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblEmployer.setPredicate(new Predicate<TreeItem<EmployerTm>>() {
                    @Override
                    public boolean test(TreeItem<EmployerTm> employerTmTreeItem) {
                        boolean flag = employerTmTreeItem.getValue().getEmpId().contains(newValue) ||
                                employerTmTreeItem.getValue().getName().contains(newValue);
                        return flag;
                    }
                });
            }
        });

        cmbTitle.getItems().addAll("Mr", "Mrs","Miss");

    }
    private void setData(TreeItem<EmployerTm> value){
        txtEmployerId.setText(value.getValue().getEmpId());
        cmbTitle.setValue(value.getValue().getTitle());
        txtName.setText(value.getValue().getName());
        txtNic.setText(value.getValue().getNic());
        datePicker.setValue(value.getValue().getDateOfBirth());
        txtAddress.setText(value.getValue().getAddress());
        txtContact.setText(value.getValue().getContactNo());
        txtBankAccNo.setText(value.getValue().getBankAccNo());
        txtBankBranch.setText(value.getValue().getBankBranch());
    }

    public void btnOnKeyPressed(KeyEvent keyEvent) {

    }
}
