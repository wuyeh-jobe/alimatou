/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Study
 */
public class TransationsController implements Initializable {

    @FXML
    private TableView<Transaction> table;
    @FXML
    private Label header_label1;
    @FXML
    private Text staff_name;
    @FXML
    private Button transaction;
    @FXML
    private TableColumn<Transaction, String> cname;
    @FXML
    private TableColumn<Transaction, String> Items;
    @FXML
    private TableColumn<Transaction, String> quants;
    @FXML
    private TableColumn<Transaction, String> prices;
    @FXML
    private TableColumn<Transaction, String> atbp;
    @FXML
    private TableColumn<Transaction, String> ap;
    @FXML
    private TableColumn<Transaction, Date> dt;
    @FXML
    private TableColumn<Transaction, Date> dd;
    java.sql.Connection conn = null;
    @FXML
    private TableColumn<Transaction, String> returned_id;
    @FXML
    private TextField search_record;
    @FXML
    private Button btn_edit_record;
    @FXML
    private Button btn_print_record;
    @FXML
    private VBox Item_hired_vbox;
    @FXML
    private VBox Quantity_Vbox;
    @FXML
    private VBox Amount_paid;
    @FXML
    private VBox Date_taken;
    @FXML
    private VBox date_due;
    @FXML
    private VBox Returned;
    @FXML
    private HBox edit_vbox;
    @FXML
    private Button Delete_R;
    @FXML
    private Text error_display;
    Button btn_done = new Button("Done");
    String tmaount="";
    @FXML
    private Label staff;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void loadData() {
        cname.setCellValueFactory(cellData -> cellData.getValue().cnameProperty());
        Items.setCellValueFactory(cellData -> cellData.getValue().itemsProperty());
        quants.setCellValueFactory(cellData -> cellData.getValue().quantitiesProperty());
        prices.setCellValueFactory(cellData -> cellData.getValue().pricesProperty());
        atbp.setCellValueFactory(cellData -> cellData.getValue().tAmountProperty());
        ap.setCellValueFactory(cellData -> cellData.getValue().amountPProperty());
        dt.setCellValueFactory(cellData -> cellData.getValue().dtakenProperty());
        dd.setCellValueFactory(cellData -> cellData.getValue().dtakenProperty());
        returned_id.setCellValueFactory(cellData -> cellData.getValue().returnedProperty());
        ObservableList<Transaction> data = FXCollections.observableArrayList();
        try {
            java.sql.Statement s = conn.createStatement();
            java.sql.ResultSet r = s.executeQuery("SELECT * FROM Transactions");
            while (r.next()) {
                data.add(new Transaction(r.getString("cName"), r.getString("Item_hired"),
                        r.getString("Quantity"), r.getString("Price"), r.getString("TotalAmount"),
                        r.getString("AmountPaid"), r.getString("AmountDue"), r.getDate("DateTaken"),
                        r.getDate("DateDue"), r.getString("Returned")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AvailableMatrialsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Transaction> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        search_record.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transactions -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (transactions.getCname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (transactions.getReturned().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Transaction> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    @FXML
    private void Exit(ActionEvent event) throws IOException {
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        FXMLDocumentController al = fxmlloader.<FXMLDocumentController>getController();
        al.setThread();
        al.Exit(event);
    }

    @FXML
    private void display_Record_form(ActionEvent event) throws IOException {
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("TransactionRecord.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        TransactionRecordController al = fxmlloader.<TransactionRecordController>getController();
        stage.show();
    }

    @FXML
    private void display_AvailableItemForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AvailableMatrials.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        AvailableMatrialsController al = fxmlloader.<AvailableMatrialsController>getController();
        stage.show();
    }

    @FXML
    private void ShowTransactions(ActionEvent event) throws IOException {

    }

    @FXML
    private void customer_details(ActionEvent event) throws IOException {
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("CustomerDetails.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        CustomerDetailsController al = fxmlloader.<CustomerDetailsController>getController();
        stage.show();
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

    @FXML
    private void edit_record(ActionEvent event) {
        ArrayList<Text> tfs = new ArrayList<>();
        ArrayList<TextField> tf1s = new ArrayList<>();
        ArrayList<TextField> tf2s = new ArrayList<>();
        ArrayList<DatePicker> dp1s = new ArrayList<>();
        ArrayList<DatePicker> dp2s = new ArrayList<>();
        ArrayList<TextField> tf3s = new ArrayList<>();

        try {
            TablePosition pos1 = table.getSelectionModel().getSelectedCells().get(0);
            int row = pos1.getRow();
            Transaction t = table.getItems().get(row);
            edit_vbox.setVisible(true);
            String[] items = t.getItems().split("\n");
            String[] quants1 = t.getQuantities().split("\n");
            for (int i = 0; i < items.length; i++) {
                Text txt = new Text(items[i]);
                txt.setFill(Color.DARKORANGE);
                txt.setTextAlignment(TextAlignment.CENTER);
                txt.setStrokeWidth(25);
                tfs.add(txt);
                Item_hired_vbox.getChildren().add(txt);
                TextField tf = new TextField(quants1[i]);
                tf1s.add(tf);
                Quantity_Vbox.getChildren().add(tf);
                TextField tf2 = new TextField();
                tf2.setPromptText("To be paid");
                tf2s.add(tf2);
                Amount_paid.getChildren().add(tf2);
                DatePicker dp = new DatePicker();
                dp1s.add(dp);
                Date_taken.getChildren().add(dp);
                DatePicker dp2 = new DatePicker();
                dp2s.add(dp2);
                date_due.getChildren().add(dp2);
                TextField tf3 = new TextField();
                tf3.setPromptText("Returned?");
                tf3s.add(tf3);
                Returned.getChildren().add(tf3);

            }

            Amount_paid.getChildren().add(btn_done);

            btn_done.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent event) {
                    String adue = "";
                    String re = "";
                    String am = "";
                    String na = "";
                    java.sql.Date dt = null;
                    java.sql.Date dd = null;
                    try {
                        for (int i = 0; i < tf1s.size(); i++) {
                            na = na + tfs.get(i).getText() + "\n";
                            am = am + tf1s.get(i).getText() + "\n";
                            adue = adue + tf2s.get(i).getText() + "\n";
                            re = re + tf3s.get(i).getText() + "\n";
                            dt = java.sql.Date.valueOf(dp1s.get(i).getValue());
                            dd = java.sql.Date.valueOf(dp2s.get(i).getValue());
                        }
                        tmaount = am;
                        PreparedStatement p = conn.prepareStatement("Update transactions set "
                                + "Quantity=?,AmountPaid=?, DateTaken=?, DateDue=?, Returned=?"
                                + " where Item_hired= " + "\'" + na + "\'");
                        p.setString(1, am);
                        p.setString(2, adue);
                        p.setDate(3, dt);
                        p.setDate(4, dd);
                        p.setString(5, re);
                        p.execute(); //use execute if no results expected back
                        error_display.setText("Transaction Successfully updated");
                        edit_vbox.setVisible(true);
                        loadData();

                    } catch (SQLException ex) {
                        System.out.println(ex);
                        //Logger.getLogger(TransationsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // }

                }
            });

        } catch (Exception e) {
            error_display.setText("Select transaction to edit");
        }
    }

    @FXML
    private void Print(ActionEvent event) {
            TablePosition pos1 = table.getSelectionModel().getSelectedCells().get(0);
            int row = pos1.getRow();
            Transaction t = table.getItems().get(row);
                try{
            //BufferedReader bw = new BufferedReader(new InputStreamReader(
                    //new FileInputStream(t.getCname())));
            FileWriter fw = new FileWriter(t.getCname()+".txt");
            PrintWriter pw = new PrintWriter(fw);
            String line;
            
            pw.println("Customer Name : "+t.getCname()+"\n");
            pw.println("Items Hired :\n"+t.getItems()+"\n");
            pw.println("Quantities: \n"+t.getQuantities()+"\n");
            pw.println("Amount to be paid: \n"+t.gettAmount()+"\n");
            pw.println("Amount paid: \n"+t.getAmountP()+"\n");
            pw.println("Daate Taken:\n"+t.getDtaken()+"\n");
            pw.println("Date Due:\n"+t.getDdue().toString()+"\n");
            pw.println("Returned:\n"+t.getReturned().toString()+"\n");
            error_display.setText("Report created for "+t.getCname());
            pw.close();
        }
        catch(IOException | NumberFormatException e){
            error_display.setText("You have not selected a transaction");
            System.out.println(e);
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        try {
            TablePosition pos1 = table.getSelectionModel().getSelectedCells().get(0);
            int row = pos1.getRow();
            Transaction tr = table.getItems().get(row);
            java.sql.Statement s = conn.createStatement();
            s.executeUpdate("delete from Transactions where cName = " + "'" + tr.getCname() + "'");
            error_display.setText(tr.getCname() + "'s transaction was deleted");
            //charts_vbox.getChildren().clear();
            loadData();

        } catch (Exception ex) {
            //System.out.println(ex);
            error_display.setText("No transaction was selected");
            //btn_ok.setVisible(true);
        }
    }

}
