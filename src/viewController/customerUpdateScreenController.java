package viewController;

import com.util.connectionConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.customerObj;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static model.customerList.getListCustomers;
import static viewController.appScreenController.appUpdateIndex;
import static viewController.appScreenController.custUpdateIndex;

public class customerUpdateScreenController
{
    @FXML private TextField name;
    @FXML private TextField address;
    @FXML private TextField address2;
    @FXML private TextField city;
    @FXML private TextField country;
    @FXML private TextField zip;
    @FXML private TextField phone;
    @FXML private Button exit;
    @FXML private Button update;

    int customerIndex = custUpdateIndex();
    private int customerId;

    @FXML public void onExitClick(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML public void initialize()
    {
        customerObj c = getListCustomers().get(customerIndex);

        customerId = c.getCustomerId();
        name.setText(c.getCustomerName());
        address.setText(c.getAddress());
        address2.setText(c.getAddress2());
        city.setText(c.getCity());
        country.setText(c.getCountry());
        zip.setText(c.getPostalCode());
        phone.setText(c.getPhone());
    }

    @FXML public void onUpdateClick(ActionEvent event) throws IOException
    {
        String nameString = name.getText();
        String addressString = address.getText();
        String address2String = address2.getText();
        String cityString = city.getText();
        String countryString = country.getText();
        String zipString = zip.getText();
        String phoneString = phone.getText();

        if(nameString.length() == 0 || addressString.length() == 0 || cityString.length() == 0 || countryString.length() == 0 || zipString.length() == 0 || phoneString.length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorUpdatingCustomer");
            alert.setContentText("Invalid customer info. Name, address, city, zip, country, and phone are require. \nPlease try again!");
            alert.showAndWait();
        }
        else
        {
            customerObj c = getListCustomers().get(customerIndex);

            connectionConfig.updateCustomerSQL(c, customerId, nameString, addressString, address2String, cityString, countryString, zipString, phoneString);

            System.out.println("Your customer is updated! Home screen is loading!");

            Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }


    }
}
