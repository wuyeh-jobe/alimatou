/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Study
 */
public class Transaction {

    private final StringProperty cname;// = new SimpleStringProperty();

    public String getCname() {
        return cname.get();
    }

    public void setCname(String value) {
        cname.set(value);
    }

    public StringProperty cnameProperty() {
        return cname;
    }
    private final StringProperty items;// = new SimpleStringProperty();

    public String getItems() {
        return items.get();
    }

    public void setItems(String value) {
        items.set(value);
    }

    public StringProperty itemsProperty() {
        return items;
    }
    private final StringProperty quantities;// = new SimpleStringProperty();

    public String getQuantities() {
        return quantities.get();
    }

    public void setQuantities(String value) {
        quantities.set(value);
    }

    public StringProperty quantitiesProperty() {
        return quantities;
    }
    private final StringProperty prices;// = new SimpleStringProperty();

    public String getPrices() {
        return prices.get();
    }

    public void setPrices(String value) {
        prices.set(value);
    }

    public StringProperty pricesProperty() {
        return prices;
    }
    private final StringProperty tAmount;// = new SimpleStringProperty();

    public String gettAmount() {
        return tAmount.get();
    }

    public void settAmount(String value) {
        tAmount.set(value);
    }

    public StringProperty tAmountProperty() {
        return tAmount;
    }
    private final StringProperty amountP;// = new SimpleStringProperty();

    public String getAmountP() {
        return amountP.get();
    }

    public void setAmountP(String value) {
        amountP.set(value);
    }

    public StringProperty amountPProperty() {
        return amountP;
    }
    private final StringProperty amountD;// = new SimpleStringProperty();

    public String getAmountD() {
        return amountD.get();
    }

    public void setAmountD(String value) {
        amountD.set(value);
    }

    public StringProperty amountDProperty() {
        return amountD;
    }
    private final ObjectProperty<Date> dtaken;// = new SimpleObjectProperty<>();

    public Date getDtaken() {
        return dtaken.get();
    }

    public void setDtaken(Date value) {
        dtaken.set(value);
    }

    public ObjectProperty dtakenProperty() {
        return dtaken;
    }
    private final ObjectProperty<Date> ddue;// = new SimpleObjectProperty<>();

    public Date getDdue() {
        return ddue.get();
    }

    public void setDdue(Date value) {
        ddue.set(value);
    }

    public ObjectProperty ddueProperty() {
        return ddue;
    }
    private final StringProperty returned;// = new SimpleStringProperty();

    public String getReturned() {
        return returned.get();
    }

    public void setReturned(String value) {
        returned.set(value);
    }

    public StringProperty returnedProperty() {
        return returned;
    }
    
    
    
    
    public Transaction(String cname, String items,String quantities,String prices,
    String tAmount, String amountP, String amountD, Date dtaken,Date ddue, String r){
        this.cname= new SimpleStringProperty(cname);
        this.items=new SimpleStringProperty(items);
        this.quantities=new SimpleStringProperty(quantities);
        this.prices = new SimpleStringProperty(prices);
        this.tAmount = new SimpleStringProperty(tAmount);
        this.amountP = new SimpleStringProperty(amountP);
        this.amountD = new SimpleStringProperty(amountD);
        this.dtaken = new SimpleObjectProperty(dtaken);
        this.ddue = new SimpleObjectProperty(ddue);
        this.returned= new SimpleStringProperty(r);
        
    }
}
