/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Study
 */
public class AvailableMatrialsController implements Initializable {

    @FXML
    private TableView<Item> table;
    @FXML
    private Label header_label1;
    @FXML
    private Text staff_name;
    @FXML
    private Button stock;
    @FXML
    private VBox addMateria_vbox;
    @FXML
    private TextField IName;
    @FXML
    private TextField IQuantity;
    java.sql.Connection conn = null;
    @FXML
    private Text error_display;
    @FXML
    private Text addMaterial_label;
    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_add_Change;
    @FXML
    private TableColumn<Item, String> itemCol;
    @FXML
    private TableColumn<Item, Integer> qAvailableCol;
    @FXML
    private TableColumn<Item, Integer> qRentedCol;
    @FXML
    private TableColumn<Item, String> lModifiedCol;
    @FXML
    private VBox charts_vbox;
    @FXML
    private HBox confirm;
    @FXML
    private Text Ask;
    @FXML
    private Button yes_btn;
    @FXML
    private Button no_btn;
    @FXML
    private Rectangle rect_for_confirm;
    @FXML
    private TextField tf_price;
    @FXML
    private TableColumn<Item, Double> price_field;
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
        itemCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        qAvailableCol.setCellValueFactory(cellData -> cellData.getValue().quantity_availableProperty().asObject());
        qRentedCol.setCellValueFactory(cellData -> cellData.getValue().quantity_rentedProperty().asObject());
        lModifiedCol.setCellValueFactory(cellData -> cellData.getValue().last_updatedProperty());
        price_field.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        ObservableList<Item> data = FXCollections.observableArrayList();
        try {
            java.sql.Statement s = conn.createStatement();
            java.sql.ResultSet r = s.executeQuery("SELECT * FROM Items");
            while (r.next()) {
                ObservableList<PieChart.Data> details = observableArrayList();
                data.add(new Item(r.getString("Name"), r.getInt("Quantity_Available"),
                        r.getInt("Quantity_Rented"),
                        r.getTimestamp("Last_Updated").toString(), r.getDouble("Price")));
                details.addAll(new PieChart.Data("Quantity Available", r.getInt("Quantity_Available")));
                details.addAll(new PieChart.Data("Quantity Rented", r.getInt("Quantity_Rented")));
                PieChart p = new PieChart();
                p.setData(details);
                p.setTitle(r.getString("Name"));
                p.setLegendSide(Side.LEFT);
                p.setLabelsVisible(true);
                charts_vbox.getChildren().add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AvailableMatrialsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(data);
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
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("TransactionRecord.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        TransactionRecordController al = fxmlloader.<TransactionRecordController>getController();
        stage.show();
    }

    @FXML
    private void display_AvailableItemForm(ActionEvent event) {
    }

    @FXML
    private void ShowTransactions(ActionEvent event) throws IOException {
        Stage stage = (Stage) staff_name.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Transations.fxml"));
        Parent root = (Parent) fxmlloader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        TransationsController al = fxmlloader.<TransationsController>getController();
        stage.show();
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
        stage.show();
    }

    @FXML
    private void add_material(ActionEvent event) {
        if (IName.isEditable()) {
            try {
                PreparedStatement p = conn.prepareStatement("Insert into Items set Name=?,"
                        + "Price=?, Quantity_Available=? ,Quantity_Rented=?");
                p.setString(1, IName.getText());
                p.setDouble(2, Double.parseDouble(tf_price.getText()));
                p.setInt(3, Integer.parseInt(IQuantity.getText()));
                p.setInt(4,0);
                //p.setTimestamp(5, Calender);
                
                
                p.execute();
                error_display.setText(IName.getText() + " Successfully added");
                btn_ok.setVisible(true);
                charts_vbox.getChildren().clear();
                loadData();

            } catch (SQLException ex) {
                error_display.setText(IName.getText() + " already exist");
                btn_ok.setVisible(true);
            }

        } else {
            try {

                int x = 0;
                java.sql.Statement s = conn.createStatement();
                java.sql.ResultSet r = s.executeQuery("SELECT * FROM Items");
                while (r.next()) {
                    if (r.getString("Name").equalsIgnoreCase(IName.getText())) {
                        PreparedStatement p = conn.prepareStatement("Update Items set "
                                + "Quantity_Available=?, Price=?"
                                + " where Name= " + "\'" + IName.getText() + "\'");
                        p.setInt(1, Integer.parseInt(IQuantity.getText()));
                        p.setDouble(2, Double.parseDouble(tf_price.getText()));
                        p.execute(); //use execute if no results expected back*/
                        error_display.setText("Quantity/Price of " + IName.getText() + " Successfully changed");
                        btn_ok.setVisible(true);
                        x = 1;
                        charts_vbox.getChildren().clear();
                        loadData();
                    }
                }
                if (x == 0) {
                    error_display.setText("Item does not exist");
                    btn_ok.setVisible(true);
                }
            } catch (Exception e) {
                System.out.println("Error" + e.toString());
            }
        }

    }

    @FXML
    private void add_material_first(ActionEvent event) {
        IName.setEditable(true);
        IName.setText("");
        IQuantity.setText("");
        tf_price.setText("");
        addMaterial_label.setText("Add Materials");
        btn_add_Change.setText("Add");
        addMateria_vbox.setVisible(true);
        
    }

    @FXML
    private void change_Quant(ActionEvent event) {
        try {
            TablePosition pos1 = table.getSelectionModel().getSelectedCells().get(0);
            int row = pos1.getRow();
            
            Item item = table.getItems().get(row);
            IName.setText(item.getName());
            IName.setEditable(false);
            IQuantity.setText(item.getQuantity_available()+"");
            tf_price.setText(item.getPrice()+"");
            addMaterial_label.setText("Change Quant/Price");
            btn_add_Change.setText("Change Quant/Price");
            addMateria_vbox.setVisible(true);
        } catch (Exception e) {
            error_display.setText("You have not selected Anything");
            btn_ok.setVisible(true);
        }

    }

    @FXML
    private void ok(ActionEvent event) {
        error_display.setText("");
        addMateria_vbox.setVisible(false);
        btn_ok.setVisible(false);
    }

    @FXML
    private void delete_material(ActionEvent event) {
        try {
        TablePosition pos1 = table.getSelectionModel().getSelectedCells().get(0);
        rect_for_confirm.setVisible(true);
        confirm.setVisible(true);
        Ask.setVisible(true); 
        }catch(Exception e){
            error_display.setText("No item was selected");
            btn_ok.setVisible(true);
        }
            
        
    }

    @FXML
    private void yes(ActionEvent event) {
        try {
            TablePosition pos1 = table.getSelectionModel().getSelectedCells().get(0);
            int row = pos1.getRow();
            Item item = table.getItems().get(row);
            java.sql.Statement s = conn.createStatement();
            s.executeUpdate("delete from Items where Name = "+"'"+item.getName()+"'");
            error_display.setText(item.getName()+" was deleted");
            charts_vbox.getChildren().clear();
            loadData();
            btn_ok.setVisible(true);
            rect_for_confirm.setVisible(false);
            confirm.setVisible(false);
            Ask.setVisible(false);
            
        } catch (Exception ex) {
            error_display.setText("No item was selected");
            btn_ok.setVisible(true);
        }
    }

    @FXML
    private void no(ActionEvent event) {
        rect_for_confirm.setVisible(false);
        confirm.setVisible(false);
        Ask.setVisible(false);
        error_display.setText("cancelled");
        btn_ok.setVisible(true);
    }

}
