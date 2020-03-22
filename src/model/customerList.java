package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class customerList
{
    private static ObservableList<customerObj> listCustomers = FXCollections.observableArrayList();

    // getter
    public static ObservableList<customerObj> getListCustomers() { return listCustomers; }

    public static void addToList(customerObj cust)
    {
        listCustomers.add(cust);
    }

    public static void clearList()
    {
        listCustomers.clear();
    }
}
