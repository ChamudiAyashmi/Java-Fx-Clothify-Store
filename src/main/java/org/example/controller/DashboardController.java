package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label lblOderCount;
    public Label lblItemCount;
    public Label lblSuppliersCount;
    public Label lblEmployersCount;
    public Label lblTime;
    public Label lblDate;
    public PieChart pieChart;
    private Stage stage;
    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/order_form.fxml"))));
        stage.show();
    }
    public void btnItemsOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/item_form.fxml"))));
        stage.show();
    }
    public void btnEmployersOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employer_form.fxml"))));
        stage.show();
    }
    public void btnSuppliersOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"))));
        stage.show();
    }
    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/oderDetails_form.fxml"))));
        stage.show();
    }
    public void btnSalesReturnsOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/salesReturns_form.fxml"))));
        stage.show();
    }
    public void btnSalesReportsOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/salesReports_form.fxml"))));
        stage.show();
    }
    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
        stage.show();
    }

//    public void setCounts(){
//        String SQL1="Select Count(*) AS employerCount From Employer";
//        String SQL2="Select Count(*) AS supplierCount From Supplier";
////        String SQL3="Select Count(*) AS orderCount From Orders";
//
//        Connection connection = null;
//        try {
//            connection = DBConnection.getInstance().getConnection();
//            PreparedStatement pstm1 = connection.prepareStatement(SQL1);
//            ResultSet rst1 = pstm1.executeQuery();
//            int employerCount=0;
//            while(rst1.next()){
//                employerCount = rst1.getInt("employerCount");
//            }
//            lblEmployersCount.setText(String.valueOf(employerCount));
//            rst1.beforeFirst();
//
//            PreparedStatement pstm2 = connection.prepareStatement(SQL2);
//            ResultSet rst2 = pstm2.executeQuery();
//            int supplierCount = 0;
//            if (rst2.next()) {
//                supplierCount = rst2.getInt("supplierCount");
//            }
//            lblSuppliersCount.setText(String.valueOf(supplierCount));
//
////            PreparedStatement pstm3 = connection.prepareStatement(SQL3);
////            ResultSet rst3 = pstm3.executeQuery();
////            int orderCount = 0;
////            if (rst3.next()) {
////                orderCount = rst3.getInt("orderCount");
////            }
////            lblOderCount.setText(String.valueOf(orderCount));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manageDateAndTime();

        ObservableList<PieChart.Data> pieObservableList= FXCollections.observableArrayList(
                new PieChart.Data("Gents",10),
                new PieChart.Data("Ladies",40),
                new PieChart.Data("Kids",30),
                new PieChart.Data("Others",20)
        );
        pieChart.setData(pieObservableList);



//        setCounts();
    }

    private void manageDateAndTime() {
        Timeline date=new Timeline(new KeyFrame(Duration.ZERO,
                e->lblDate.setText(LocalDate.now().
                        format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))),
                new KeyFrame((Duration.seconds(1))));
        date.setCycleCount(Animation.INDEFINITE);
        date.play();
        Timeline time=new Timeline(new KeyFrame(Duration.ZERO,
                e-> lblTime.setText(LocalDateTime.now().
                        format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1)));

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
