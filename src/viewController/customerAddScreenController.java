package viewController;

import com.util.InvalidAppointmentException;
import com.util.InvalidCutsomerException;
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

import javax.xml.soap.Text;
import java.io.IOException;

public class customerAddScreenController
{
    @FXML private TextField name;
    @FXML private TextField address;
    @FXML private TextField address2;
    @FXML private TextField city;
    @FXML private TextField country;
    @FXML private TextField zip;
    @FXML private TextField phone;
    @FXML private Button exit;
    @FXML private Button add;

    @FXML public void onExitClick(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML public void onAddClick(ActionEvent event) throws IOException
    {
        String nameString = name.getText();
        String addressString = address.getText();
        String address2String = address2.getText();
        String cityString = city.getText();
        String countryString = country.getText();
        String zipString = zip.getText();
        String phoneString = phone.getText();

        if( addressString.length() == 0 || cityString.length() == 0 || countryString.length() == 0 || zipString.length() == 0 || phoneString.length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingCustomer");
            alert.setContentText("Invalid customer info. Name, address, city, zip, country, and phone are require. \nPlease try again!");
            alert.showAndWait();
        }
        else if (tryCatchException())
        {

        }
        else
        {
            connectionConfig.addCustomerSQL(nameString, addressString, address2String, cityString, countryString, zipString, phoneString);

            System.out.println("Your customer is added! Home screen is loading!");

            Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    private boolean tryCatchException()
    {
        try
        {
            if(name.getText().length() == 0)
            {
                throw new InvalidCutsomerException("Invalid customer! Name is required!");
            }
        }
        catch(InvalidCutsomerException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingCustomerException");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return true;
        }
        return false;
    }


}
