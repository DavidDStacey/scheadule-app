package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class appointmentList
{
    private static ObservableList<appointmentObj> listAppointments = FXCollections.observableArrayList();

    //getter
    public static ObservableList<appointmentObj> getListAppointments() { return listAppointments; }

    public static void clearList() { listAppointments.clear();}

    public static void addToList(appointmentObj app) { listAppointments.add(app); }
}
