package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class appointmentObj
{
    private final IntegerProperty appointmentId;
    private final StringProperty contact;
    private final IntegerProperty customerId;
    private final StringProperty customerName;
    private final StringProperty description;
    private final StringProperty end;
    private final StringProperty location;
    private final StringProperty start;
    private final StringProperty title;
    private final StringProperty url;
    private final StringProperty type;


    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    //cosntructor
    public appointmentObj()
    {
        appointmentId = new SimpleIntegerProperty();
        contact = new SimpleStringProperty();
        customerId = new SimpleIntegerProperty();
        customerName = new SimpleStringProperty();
        description = new SimpleStringProperty();
        end = new SimpleStringProperty();
        location = new SimpleStringProperty();
        start = new SimpleStringProperty();
        title = new SimpleStringProperty();
        url = new SimpleStringProperty();
        type = new SimpleStringProperty();
    }

    //getter setters
    public int getAppointmentId() {
        return appointmentId.get();
    }

    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getEnd() {
        return end.get();
    }

    public StringProperty endProperty() {
        return end;
    }

    public void setEnd(String end) {
        this.end.set(end);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getStart() {
        return start.get();
    }

    public StringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public StringProperty startDateProperty()
    {
        Object startString = start.toString().substring(22,33);
        return new SimpleStringProperty((String) startString);
    }



}
