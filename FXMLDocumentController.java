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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Study
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Rectangle rect;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_logIn;
    @FXML
    private Button btn_forgotPassword;
    @FXML
    private Label header_label;
    @FXML
    private ImageView logo;
    @FXML
    private Text Companyname;
    @FXML
    private ImageView logo1;
    @FXML
    private ImageView circ1;
    @FXML
    public TextArea clock;
    Thread th;
    private boolean exit = false;
    @FXML
    private Text txt_date;
    @FXML
    private Rectangle quote_rect;
    java.sql.Connection conn = null;
    @FXML
    private Text error_display;
    @FXML
    private VBox edit_vbox;
    @FXML
    private TextField name_edit;
    @FXML
    private TextField surname_edit;
    @FXML
    private TextField username_edit;
    @FXML
    private TextField secuirityq_edit;
    @FXML
    private PasswordField password_edit;
    @FXML
    private TextField secuirityA_edit;
    @FXML
    private Text edit_header;
    private String oldPassword="";
    @FXML
    private VBox forgot_pass_view;
    @FXML
    private TextField forgot_sQuestion;
    @FXML
    private TextField forgot_sAnswer;
    @FXML
    private TextField forgot_pass;
    @FXML
    private TextField forgot_ChangePass;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void log_in(ActionEvent event) throws IOException {
        int x = 0;
        try {
            java.sql.Statement s = conn.createStatement();
            java.sql.ResultSet r = s.executeQuery("SELECT * FROM Staff");
            while (r.next()) {
                if (r.getString("Username").equalsIgnoreCase(txt_username.getText())
                        && r.getString("Password").equalsIgnoreCase(txt_password.getText())) {
                    if (r.getString("SecurityQuestion").equalsIgnoreCase("")) {
                        edit_vbox.setVisible(true);
                        edit_header.setText("One Time Edit");
                        name_edit.setText(r.getString("Name"));
                        name_edit.setPromptText("Name");
                        surname_edit.setText(r.getString("Surname"));
                        surname_edit.setPromptText("Surname");
                        username_edit.setText(r.getString("Username"));
                        username_edit.setPromptText("Username");
                        secuirityq_edit.setText(r.getString("SecurityQuestion"));
                        secuirityq_edit.setPromptText("SecurityQuestion");
                        secuirityA_edit.setText(r.getString("SecurityAnswer"));
                        secuirityA_edit.setPromptText("SecurityAnswer");
                        oldPassword = r.getString("Password");
                        password_edit.setText(oldPassword);
                        password_edit.setPromptText("Password");

                    } else {
                        exit = true;
                        Stage stage = (Stage) logo.getScene().getWindow();
                        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("TransactionRecord.fxml"));
                        Parent root = (Parent) fxmlloader.load();
                        Scene scene1 = new Scene(root);
                        stage.setScene(scene1);
                        TransactionRecordController tr = fxmlloader.<TransactionRecordController>getController();
                        stage.show();
                    }
                    x = 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (x == 0) {
            error_display.setText("Username or Password Does not exist. See Administrator.");
        }

    }

    @FXML
    private void forgetPassword(ActionEvent event) throws SQLException {
        int x = 0;
        if(txt_username.getText().equalsIgnoreCase("")){
            error_display.setText("Enter username first");
        }
        else{
            java.sql.Statement s = conn.createStatement();
            java.sql.ResultSet r = s.executeQuery("SELECT * FROM Staff");
            while(r.next()){
                if(r.getString("Username").equals(txt_username.getText())){
                    forgot_pass_view.setVisible(true);
                    forgot_sQuestion.setText(r.getString("SecurityQuestion"));
                    forgot_sQuestion.setPromptText("SecurityQuestion");
                    forgot_sAnswer.setPromptText("Put answer to Question");
                    forgot_pass.setPromptText("Password will appear here");
                    forgot_ChangePass.setPromptText("Change Password?");
                    x=1;
                }
                
            }
            if(x==0){
                    error_display.setText("Username is incorrect/ Note it's case sensitive");
            }
        } 
        
    }

    @FXML
    public void Exit(ActionEvent event) {
        exit = true;
        Platform.exit();
    }

    @FXML
    private void Done_Editing(ActionEvent event) throws IOException {
        if(password_edit.getText().equals(oldPassword))
            error_display.setText("You have not changed your password");
        else if(password_edit.getText().length()<4)
            error_display.setText("Password should be more than 4 Characters");
        else if(secuirityq_edit.getText().equalsIgnoreCase(""))
            error_display.setText("Please fill security question");
        else if(secuirityA_edit.getText().equalsIgnoreCase(""))
            error_display.setText("Please fill security Answer");
        else{
            try {
                    PreparedStatement p = conn.prepareStatement("Update Staff set Name=?,"
                            + " Surname=? ,Username=?,SecurityQuestion=?,SecurityAnswer=?,"
                            + " Password=? where Username= "+"\'"+username_edit.getText()+"\'");
                    p.setString(1, name_edit.getText());
                    p.setString(2, surname_edit.getText());
                    p.setString(3, username_edit.getText());
                    p.setString(4, secuirityq_edit.getText());
                    p.setString(5, secuirityA_edit.getText());
                    p.setString(6, password_edit.getText());
                    p.execute(); //use execute if no results expected back
                } catch (Exception e) {
                    System.out.println("Error" + e.toString());
                }
            Stage stage = (Stage) logo.getScene().getWindow();
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("TransactionRecord.fxml"));
            Parent root = (Parent) fxmlloader.load();
            Scene scene1 = new Scene(root);
            stage.setScene(scene1);
            TransactionRecordController al = fxmlloader.<TransactionRecordController>getController();
            stage.show();
        }
    }

    @FXML
    private void show_Change_pass(ActionEvent event) throws SQLException {
        int x=0;
            java.sql.Statement s = conn.createStatement();
            java.sql.ResultSet r = s.executeQuery("SELECT * FROM Staff");
            while(r.next()){
                if(r.getString("Username").equals(txt_username.getText())){
                    if(r.getString("SecurityAnswer").equalsIgnoreCase(forgot_sAnswer.getText())){
                        forgot_pass.setText(r.getString("Password"));
                        error_display.setText("");
                    }
                    else{
                        error_display.setText("The answer you provided is wrong");
                    }
                    if(!forgot_ChangePass.getText().equalsIgnoreCase("")){
                        if(forgot_ChangePass.getText().length()<4){
                            error_display.setText("The password should be more than 4 characters");
                        }
                        else{
                            PreparedStatement p = conn.prepareStatement("Update Staff set"
                                + " Password=? where Username= "+"\'"+txt_username.getText()+"\'");
                            p.setString(1, forgot_ChangePass.getText());
                            p.execute();
                            error_display.setText("Password Changed");
                        }
                        
                    }
                  x=1;      
                }
                
            }
            if(x==0){
                    error_display.setText("Incorrect Username/ Note it's case sensitive");
            }
    }

    @FXML
    private void when_start(MouseEvent event) {
        error_display.setText("");
        forgot_pass_view.setVisible(false);
        edit_vbox.setVisible(false);
    }
     public void setThread(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://localhost/alimatou?user=root&password=");
            System.out.println("Connection established");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        th = new Thread(new Runnable() {
            public void run() {
                while (!exit) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                    String time = formatter.format(date);
                    clock.setText(time);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        th.start();
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String day = formatter.format(date);
        txt_date.setText(day);
    }
}
