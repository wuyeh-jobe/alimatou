/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Study
 */
public class CustomerDetailsController implements Initializable {

    @FXML
    private Label header_label1;
    @FXML
    private VBox edit_space;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField txt_search;
    @FXML
    private VBox search_results;
    @FXML
    private Text staff_name;
    @FXML
    private Button customerD;
    @FXML
    private TableView<Customer> table;
    @FXML
    private TableColumn<Customer, String> cname;
    @FXML
    private TableColumn<Customer, String> phoneNum;
    @FXML
    private TableColumn<Customer, String> adress;
    java.sql.Connection conn = null;
    @FXML
    private Label staff;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
        staff.setVisible(false);
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://localhost/alimatou?user=root&password=");
            System.out.println("Connection established");

        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        loadData();
        
    }    

    @FXML
    private void Exit(ActionEvent event) throws IOException {
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        FXMLDocumentController al = fxmlloader.<FXMLDocumentController>getController();
        al.Exit(event);
    }

    @FXML
    private void display_Record_form(ActionEvent event) throws IOException {
        Stage stage = (Stage) phoneNumber.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("TransactionRecord.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        TransactionRecordController al = fxmlloader.<TransactionRecordController>getController();
        stage.show();
    }

    @FXML
    private void display_AvailableItemForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) phoneNumber.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AvailableMatrials.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        AvailableMatrialsController al = fxmlloader.<AvailableMatrialsController>getController();
        stage.show();
    }

    @FXML
    private void ShowTransactions(ActionEvent event) throws IOException {
        Stage stage = (Stage) phoneNumber.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Transations.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        TransationsController al = fxmlloader.<TransationsController>getController();
        stage.show();
    }

    @FXML
    private void customer_details(ActionEvent event) throws IOException {
        
    }

    @FXML
    private void btn_edit(ActionEvent event) {
        edit_space.setVisible(true);
        
    }

    @FXML
    private void btn_record(ActionEvent event) {
    }

    @FXML
    private void delete(ActionEvent event) {
    }

    @FXML
    private void btn_search(ActionEvent event) {
    }

    @FXML
    private void homepage(ActionEvent event) throws IOException {
         Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        FXMLDocumentController al = fxmlloader.<FXMLDocumentController>getController();
        al.setThread();
        stage.show();
    }
    public void loadData() {
        cname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneNum.setCellValueFactory(cellData -> cellData.getValue().PhoneProperty());
        adress.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        
        ObservableList<Customer> data = FXCollections.observableArrayList();
        try {
            java.sql.Statement s = conn.createStatement();
            java.sql.ResultSet r = s.executeQuery("SELECT * FROM Customers");
            while (r.next()) {
                ObservableList<PieChart.Data> details = observableArrayList();
                data.add(new Customer(r.getString("cFname"), r.getString("Phone"),
                        r.getString("address")));
               
            }

        } catch (SQLException ex) {
            Logger.getLogger(AvailableMatrialsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Customer> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customers -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (customers.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (customers.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }
    
}
