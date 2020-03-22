package viewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.util.connectionConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.appointmentObj;

public class calenderScreenController implements Initializable
{
    @FXML private TableView<appointmentObj> weeklyAppointmentsTV;
    @FXML private TableColumn<appointmentObj, String> weeklyCustomerNameColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentTitleColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentDescriptionColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentLocationColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentContactColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentTypeColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentURLColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentStartColumn;
    @FXML private TableColumn<appointmentObj, String> weeklyAppointmentEndColumn;
    @FXML private TableView<appointmentObj> monthlyAppointmentTV;
    @FXML private TableColumn<appointmentObj, String> monthlyCustomerNameColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentTitleColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentDescriptionColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentLocationColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentContactColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentTypeColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentURLColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentStartColumn;
    @FXML private TableColumn<appointmentObj, String> monthlyAppointmentEndColumn;

    @FXML void back(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("appScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void getAppointments()
    {
        //use of lambda to set values to the tableview
        weeklyCustomerNameColumn.setCellValueFactory(cellData -> { return cellData.getValue().customerNameProperty(); });
        weeklyAppointmentTitleColumn.setCellValueFactory(cellData -> { return cellData.getValue().titleProperty(); });
        weeklyAppointmentDescriptionColumn.setCellValueFactory(cellData -> { return cellData.getValue().descriptionProperty(); });
        weeklyAppointmentLocationColumn.setCellValueFactory(cellData -> { return cellData.getValue().locationProperty(); });
        weeklyAppointmentContactColumn.setCellValueFactory(cellData -> { return cellData.getValue().contactProperty(); });
        weeklyAppointmentTypeColumn.setCellValueFactory(cellData -> { return cellData.getValue().typeProperty(); });
        weeklyAppointmentURLColumn.setCellValueFactory(cellData -> { return cellData.getValue().urlProperty(); });
        weeklyAppointmentStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart()));
        weeklyAppointmentEndColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getEnd()));
        weeklyAppointmentsTV.setItems(connectionConfig.getAppointmentByWeek());
        monthlyCustomerNameColumn.setCellValueFactory(cellData -> { return cellData.getValue().customerNameProperty(); });
        monthlyAppointmentTitleColumn.setCellValueFactory(cellData -> { return cellData.getValue().titleProperty(); });
        monthlyAppointmentDescriptionColumn.setCellValueFactory(cellData -> { return cellData.getValue().descriptionProperty(); });
        monthlyAppointmentLocationColumn.setCellValueFactory(cellData -> { return cellData.getValue().locationProperty(); });
        monthlyAppointmentContactColumn.setCellValueFactory(cellData -> { return cellData.getValue().contactProperty(); });
        monthlyAppointmentTypeColumn.setCellValueFactory(cellData -> { return cellData.getValue().typeProperty(); });
        monthlyAppointmentURLColumn.setCellValueFactory(cellData -> { return cellData.getValue().urlProperty(); });
        monthlyAppointmentStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart()));
        monthlyAppointmentEndColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getEnd()));
        monthlyAppointmentTV.setItems(connectionConfig.getApptsByMonth());
    }

    @Override public void initialize(URL url, ResourceBundle rb)
    {
        getAppointments();
    }
}
