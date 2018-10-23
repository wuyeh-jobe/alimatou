/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import java.awt.Checkbox;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Study
 */
public class TransactionRecordController implements Initializable {

    @FXML
    private Rectangle rect1;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Label header_label1;
    @FXML
    private DatePicker tdate;
    @FXML
    private TextField address;
    @FXML
    private Text staff_name;
    @FXML
    private Button recordT;
    private Thread th;
    private boolean exit = false;
    @FXML
    private Button btn_select_item;
    @FXML
    private VBox items_to_hire_display;
    java.sql.Connection conn = null;
    @FXML
    private ScrollPane item_sp;
    HashMap<CheckBox, TextField> hm = new HashMap();
    @FXML
    private DatePicker ddate;
    @FXML
    private TextArea Quantity_not;
    @FXML
    private ScrollPane nAvailPane;
    @FXML
    private Button btn_ok;
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
    }

    @FXML
    private void record(ActionEvent event) {
        String items = "";
        String quantities = "";
        String amounts = "";
        String prices = "";
        String returned = "";
        boolean avail = true;
        String nAvail = "";
        String[] a = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        boolean contains = false;
        for (int i = 0; i < a.length; i++) {
            if (phoneNumber.getText().contains(a[i].toLowerCase())) {
                contains = true;
            }
        }
        if (contains) {
            Quantity_not.setText("Phone number should not contain letters");
            nAvailPane.setVisible(true);
        } else if (tdate.getValue() == null || ddate.getValue() == null) {
            Quantity_not.setText("The date field can't be empty");
            nAvailPane.setVisible(true);
        } else {
            try {
                PreparedStatement p = conn.prepareStatement("Insert into Customers set cFname=?,"
                        + " cLname=? ,Phone=?,Address=?");
                PreparedStatement p2 = conn.prepareStatement("Insert into Transactions set cName=?,"
                        + " Item_hired=? ,Quantity=?,Price=?, TotalAmount=?"
                        + ",DateTaken=?,DateDue=?, Returned=?");

                for (CheckBox c : hm.keySet()) {
                    if (!hm.get(c).getText().equalsIgnoreCase("")) {
                        items = items + c.getText() + "\n";
                        quantities = quantities + hm.get(c).getText() + "\n";
                        returned = returned + "No\n";
                    }
                }
                java.sql.Statement s = conn.createStatement();
                java.sql.ResultSet r = s.executeQuery("SELECT * FROM Items");
                String[] quants = quantities.split("\n");
                ArrayList<Double> pr = new ArrayList<>();
                boolean cvalues = true;
                while (r.next()) {
                    for (CheckBox c : hm.keySet()) {
                        if (r.getString("Name").equalsIgnoreCase(c.getText()) && !hm.get(c).getText().equalsIgnoreCase("")
                                && !hm.get(c).getText().equalsIgnoreCase("0") && hm.get(c).getText().compareTo("0") > 0) {
                            System.out.println("names" + r.getString("Name"));
                            pr.add(r.getDouble("Price"));
                            System.out.println(r.getDouble("Price"));
                            PreparedStatement p3 = conn.prepareStatement("Update Items set Quantity_Available=?, Quantity_Rented=?"
                                    + " where Name= " + "\'" + r.getString("Name") + "\'");
                            /*boolean confirm = false;
                            for (int i = 0; i < a.length; i++) {
                                if (hm.get(c).getText().contains(a[i].toLowerCase())) {
                                    confirm = true;
                                }
                            }
                            if(confirm==false){*/
                            try{
                                if (r.getInt("Quantity_Available") - Integer.parseInt(hm.get(c).getText()) >= 0) {
                                    p3.setInt(1, r.getInt("Quantity_Available") - Integer.parseInt(hm.get(c).getText()));
                                    p3.setInt(2, Integer.parseInt(hm.get(c).getText()) + r.getInt("Quantity_Rented"));
                                    p3.execute(); //use execute if no results expected back*/

                                } else {
                                    nAvail = nAvail + r.getString("Name") + "     " + r.getString("Quantity_Available") + "(Avail)\n";
                                    avail = false;
                                }
                            }catch(Exception e){
                                avail = false;
                                Quantity_not.setText("Ensure that the quantities do not contain letters");
                            }
                           // }
                            /*else{
                                Quantity_not.setText("Ensure that the quantities does not contain letters");
                    nAvailPane.setVisible(true);
                            }*/
                        } else {
                            //cvalues=false;
                        }
                    }
                }

                if (avail == true) {
                    for (int i = 0; i < pr.size(); i++) {
                        prices = prices + pr.get(i) + "\n";
                        amounts = amounts + (pr.get(i) * Integer.parseInt(quants[i])) + "\n";
                    }
                    System.out.println("Prices" + prices);
                    p.setString(1, fname.getText());
                    p.setString(2, lname.getText());
                    p.setString(3, phoneNumber.getText());
                    p.setString(4, address.getText());
                    p.execute();
                    p2.setString(1, fname.getText() + " " + lname.getText());
                    p2.setString(2, items);
                    p2.setString(3, quantities);
                    p2.setString(4, prices);
                    p2.setString(5, amounts);
                    p2.setDate(6, Date.valueOf(tdate.getValue()));
                    p2.setDate(7, Date.valueOf(ddate.getValue()));
                    p2.setString(8, returned);
                    p2.execute();
                    Quantity_not.setText("Items successfully entered");
                    nAvailPane.setVisible(true);
                    btn_ok.setVisible(true);
                } else if (cvalues == false) {
                    Quantity_not.setText("Check if you have entered the correct Values");
                    nAvailPane.setVisible(true);
                } else {
                    Quantity_not.setText("The quanties you enter might have contain letters:\n OR \n"
                            + "The needed quantity for the following are not"
                            + " available: \n" + nAvail);
                    nAvailPane.setVisible(true);

                }

            } catch (SQLException ex) {
                System.out.println("Not p");
                System.out.println(ex);
            }

        }

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
    private void display_Record_form(ActionEvent event) {

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
        Stage stage = (Stage) phoneNumber.getScene().getWindow();
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
    private void select_item(ActionEvent event) throws SQLException {
        hm.clear();
        items_to_hire_display.getChildren().clear();
        item_sp.setVisible(true);
        java.sql.Statement s = conn.createStatement();
        java.sql.ResultSet r = s.executeQuery("SELECT * FROM Items");
        while (r.next()) {
            CheckBox cb = new CheckBox(r.getString("Name"));
            cb.setPrefWidth(200);
            TextField tf = new TextField();
            tf.setPromptText("Enter quantity of " + r.getString("Name"));
            tf.setVisible(false);
            items_to_hire_display.getChildren().add(cb);
            items_to_hire_display.getChildren().add(tf);
            hm.put(cb, tf);
        }

        for (CheckBox c : hm.keySet()) {
            c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (c.isSelected()) {
                        hm.get(c).setVisible(true);
                    } else {
                        hm.get(c).setVisible(false);
                        hm.get(c).setText("");
                    }
                }
            });
        }
    }

    @FXML
    private void Ok(ActionEvent event) throws IOException {
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("TransactionRecord.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        TransactionRecordController al = fxmlloader.<TransactionRecordController>getController();
        stage.show();
    }
}
