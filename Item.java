/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Study
 */
public class Item {

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
    private final IntegerProperty quantity_available;

    public int getQuantity_available() {
        return quantity_available.get();
    }

    public void setQuantity_available(int value) {
        quantity_available.set(value);
    }

    public IntegerProperty quantity_availableProperty() {
        return quantity_available;
    }
    private final IntegerProperty quantity_rented;

    public int getQuantity_rented() {
        return quantity_rented.get();
    }

    public void setQuantity_rented(int value) {
        quantity_rented.set(value);
    }

    public IntegerProperty quantity_rentedProperty() {
        return quantity_rented;
    }
    private final StringProperty last_updated;// = new SimpleStringProperty();

    public String getLast_updated() {
        return last_updated.get();
    }

    public void setLast_updated(String value) {
        last_updated.set(value);
    }

    public StringProperty last_updatedProperty() {
        return last_updated;
    }
    private final DoubleProperty price;// = new SimpleDoubleProperty();

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double value) {
        price.set(value);
    }

    public DoubleProperty priceProperty() {
        return price;
    }
    
    
    public Item(){
        this(null,0,0,null,0.0);
    }
    public Item(String name, int qa, int qr, String lu, double p) {
        this.name = new SimpleStringProperty(name);
        this.quantity_available= new SimpleIntegerProperty(qa);
        this.quantity_rented= new SimpleIntegerProperty(qr);
        this.last_updated = new SimpleStringProperty(lu);
        this.price = new SimpleDoubleProperty(p);
    }
}
