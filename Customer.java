/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Study
 */
public class Customer {

    private final StringProperty name;// = new SimpleStringProperty();

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }
    private final StringProperty LName = new SimpleStringProperty();

    public String getLName() {
        return LName.get();
    }

    public void setLName(String value) {
        LName.set(value);
    }

    public StringProperty LNameProperty() {
        return LName;
    }
    private final StringProperty Phone;// = new SimpleStringProperty();

    public String getPhone() {
        return Phone.get();
    }

    public void setPhone(String value) {
        Phone.set(value);
    }

    public StringProperty PhoneProperty() {
        return Phone;
    }
    private final StringProperty address;// = new SimpleStringProperty();

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String value) {
        address.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }
    public Customer(String fname, String phone, String adres){
        this.name = new SimpleStringProperty(fname);
        this.Phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(adres);
    }
}
