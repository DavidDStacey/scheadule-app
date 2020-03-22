package viewController;

import com.util.connectionConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.appointmentObj;
import model.customerObj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import static com.util.connectionConfig.deleteAppointmentSQL;
import static com.util.connectionConfig.deleteCustomerSQL;
import static model.appointmentList.getListAppointments;
import static model.customerList.getListCustomers;

public class appScreenController
{
    @FXML private TableView<customerObj> customerTV;
    @FXML private TableColumn<customerObj, String> nameColumn;
    @FXML private TableColumn<customerObj, String> cityColumn;
    @FXML private TableColumn<customerObj, String> countryColumn;
    @FXML private TableColumn<customerObj, String> phoneColumn;
    @FXML private TableView<appointmentObj> appointmentTV;
    @FXML private TableColumn<appointmentObj, String> appNameColumn;
    @FXML private TableColumn<appointmentObj, String> startColumn;
    @FXML private Button appAdd;
    @FXML private Button appUpdate;
    @FXML private Button appDelete;
    @FXML private Button custAdd;
    @FXML private Button custUpdate;
    @FXML private Button custDelete;
    @FXML private Button monthlyCalenderView;
    @FXML private Button exitBtn;

    private static customerObj updateCust;
    private static int updateCustIndex;

    private static appointmentObj updateApp;
    private static int updateAppIndex;

    public static int appUpdateIndex()
    {
        return updateAppIndex;
    }

    public static int custUpdateIndex()
    {
        return updateCustIndex;
    }

    //---------------------- appointment view start -----------------------------------------------------------------------------

    @FXML private void updateAppointmentTableView() { appointmentTV.setItems(getListAppointments()); }

    @FXML public void appAddClick(javafx.event.ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("appointmentAddScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML public void appUpdateClick(javafx.event.ActionEvent event) throws IOException
    {
        updateApp = appointmentTV.getSelectionModel().getSelectedItem();
        updateAppIndex = getListAppointments().indexOf(updateApp);
        if (updateApp == null)
        {
            System.out.println("Please select appointment to update.");
        }
        else
        {
            Parent parent = FXMLLoader.load(getClass().getResource("appointmentUpdateScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @FXML public void appDeleteClick() throws SQLException
    {
        appointmentObj a = appointmentTV.getSelectionModel().getSelectedItem();
        deleteAppointmentSQL(a);
        initialize();
    }

    //------------------------- appointment ciew end ---------------------------------------------------------------------------=
    //--------------------------------------------- customer view start -0--------------------------------------------------------

    @FXML private void updateCustomerTableView() { customerTV.setItems(getListCustomers()); }

    @FXML public void custAddClick(javafx.event.ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("customerAddScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML public void custUpdateClick(javafx.event.ActionEvent event) throws IOException
    {
        updateCust = customerTV.getSelectionModel().getSelectedItem();
        updateCustIndex = getListCustomers().indexOf(updateCust);
        if (updateCust == null)
        {
            System.out.println("Please select customer to update.");
        }
        else
        {
            Parent parent = FXMLLoader.load(getClass().getResource("customerUpdateScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    @FXML public void custDeleteClick() throws SQLException
    {
        customerObj c = customerTV.getSelectionModel().getSelectedItem();
        deleteCustomerSQL(c);
        initialize();
    }

    //--------------------------------------------- customer end ------------------------------------------------------------------
    // ----------------------------------------------- additikonal methods --------------------------------------------------------
/*
    @FXML public void weeklyCalenderViewClick(javafx.event.ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("calenderWeeklyScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

 */

    @FXML public void monthlyCalenderViewClick(javafx.event.ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("calenderScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML public void onExitClick()  { System.exit(0); }

    public void generateReports() throws IOException, SQLException
    {
        String numByTypeReport = connectionConfig.getNumByType();
        String scheaduleOfConsultantReport = connectionConfig.getConsultantSchedule();
        String additionalReport = connectionConfig.getAdditionalReport();

        System.out.println("reports generating");

        BufferedWriter writerType = new BufferedWriter(new FileWriter("typeReport.txt"));
        writerType.write(numByTypeReport);
        writerType.close();

        BufferedWriter writerConsult = new BufferedWriter(new FileWriter("consultantReport.txt"));
        writerConsult.write(scheaduleOfConsultantReport);
        writerConsult.close();

        BufferedWriter writerAdditional = new BufferedWriter(new FileWriter("additionalReport.txt"));
        writerAdditional.write(additionalReport);
        writerAdditional.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reports");
        alert.setHeaderText("Reports Generated");
        alert.setContentText("Reports have been generated in a text file!");
        alert.showAndWait();
    }

    public void initialize() throws SQLException
    {
        connectionConfig.updateCustomerObjList();
        connectionConfig.updateAppointmentObjList();

        connectionConfig.checkAppointment15Away();

        //use of lambda to set values to the tableview
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        updateCustomerTableView();

        appNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        updateAppointmentTableView();
    }
}
