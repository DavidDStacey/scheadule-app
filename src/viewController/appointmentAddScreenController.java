package viewController;

import com.util.InvalidAppointmentException;
import com.util.connectionConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.customerObj;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.TimeZone;

import static model.customerList.getListCustomers;

public class appointmentAddScreenController
{
    @FXML private TableView<customerObj> customerObjTableView;
    @FXML private TableColumn<customerObj, String> customerName;
    @FXML private Button exitBtn;
    @FXML private Button addBtn;
    @FXML private TextField titleTxt;
    @FXML private TextField descriptionTxt;
    @FXML private TextField locationTxt;
    @FXML private TextField contactTxt;
    @FXML private TextField typeTxt;
    @FXML private TextField urltxt;
    @FXML private TextField startYear;
    @FXML private TextField startMonth;
    @FXML private TextField startDay;
    @FXML private TextField startH;
    @FXML private TextField startM;
    @FXML private TextField startAmPm;
    @FXML private TextField endYear;
    @FXML private TextField endMonth;
    @FXML private TextField endDay;
    @FXML private TextField endH;
    @FXML private TextField endM;
    @FXML private TextField endAmPm;

    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    @FXML public void onAddClick(ActionEvent event) throws IOException, SQLException
    {
        customerObj c = customerObjTableView.getSelectionModel().getSelectedItem();


        if(typeTxt.getLength() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Please give type!");
            alert.showAndWait();
        }
        else if (!tryCatchException())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Outside of buisness hours");
            //alert.showAndWait();
        }
        else if (startDay.getLength() != 2 || startYear.getLength() != 4 || startMonth.getLength() != 2 || startH.getLength() != 2 || startM.getLength() != 2 || startAmPm.getLength() != 2)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Please give a valid start time! yyyy-MM-dd hh:mm");
            alert.showAndWait();
        }
        else if(endDay.getLength() != 2 || endYear.getLength() != 4 || endMonth.getLength() != 2 || endH.getLength() != 2 || endM.getLength() != 2 || endAmPm.getLength() != 2)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Please give valid end time! yyyy-MM-dd hh:mm");
            alert.showAndWait();
        }
        /*
        else if((  Integer.parseInt(startH.getText()) <= 10 && Integer.parseInt(endH.getText()) >= 8 && endAmPm.getText().contains("am") && startAmPm.getText().contains("pm") ) ||
                ( startAmPm.getText().contains("am")  && Integer.parseInt(startH.getText()) < 8) ||
                (Integer.parseInt(endH.getText()) > 10 && endAmPm.getText().contains("pm")) ||
                (Integer.parseInt(endH.getText()) == 10 && Integer.parseInt(endM.getText()) != 0 && endAmPm.getText().contains("pm") ) )
        {
            // if using 24 hour it will convert on update
            // ex give 13 as start hour
            // update wraps around and shows 1
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Sorry We open at 8AM and close at 10PM!");
            alert.showAndWait();
        }

         */
        else if(c == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Please select a customer!");
            alert.showAndWait();
        }
        else
        {
            SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            localDateFormat.setTimeZone(TimeZone.getDefault());

            Date startLocal = null;
            Date endLocal = null;
            try
            {
                startLocal = localDateFormat.parse(startYear.getText() + "-" + startMonth.getText() + "-" + startDay.getText() + " " + startH.getText() + ":" + startM.getText() + " " + startAmPm.getText());
                endLocal = localDateFormat.parse(endYear.getText() + "-" + endMonth.getText() + "-" + endDay.getText() + " " + endH.getText() + ":" + endM.getText() + " " + endAmPm.getText());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            assert startLocal != null;
            ZonedDateTime startUTC = ZonedDateTime.ofInstant(startLocal.toInstant(), ZoneId.of("UTC"));
            assert endLocal != null;
            ZonedDateTime endUTC = ZonedDateTime.ofInstant(endLocal.toInstant(), ZoneId.of("UTC"));

            System.out.println("Local time start " + startLocal + " end " + endLocal + " utc start " + startUTC + " end " + endUTC);

            if (Integer.parseInt(startH.getText()) > Integer.parseInt(endH.getText()) && endAmPm.getText().equalsIgnoreCase("am"))
            {
                System.out.println("Have to start before you can end!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("errorAddingAppointment");
                alert.setContentText("You need start before you can end!");
                alert.showAndWait();
            }
            else if (Integer.parseInt(startH.getText()) == Integer.parseInt(endH.getText())
                    && endAmPm.getText().equalsIgnoreCase(startAmPm.getText()) && Integer.parseInt(startM.getText()) >= Integer.parseInt(endM.getText()))
            {
                System.out.println("Have to start before you can end!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("errorAddingAppointment");
                alert.setContentText("You need start before you can end!");
                alert.showAndWait();
            }
            else if (connectionConfig.checkAppointmentConflictSQL(startUTC, endUTC))
            {
                System.out.println("Sorry another appointment is at that time!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("errorAddingAppointment");
                alert.setContentText("You need to start before you can end!");
                alert.showAndWait();
            }
            else
            {
                connectionConfig.addAppointmentSQL(c, startUTC, endUTC, titleTxt.getText(), descriptionTxt.getText(), locationTxt.getText(), contactTxt.getText(), typeTxt.getText(), urltxt.getText());

                System.out.println("Your appointment is scheduled! Home screen is loading!");

                Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
    }

    public boolean tryCatchException()
    {
        String uSHour = startH.getText();
        String eSHour = endH.getText();
        String uSAmPm = startAmPm.getText();
        String eSAmPm = endAmPm.getText();
        try
        {
            if( (Integer.parseInt(uSHour)<8 && uSAmPm.equals("am")) ||
                    (Integer.parseInt(eSHour)>10 && eSAmPm.equals("pm")) ||
                    (Integer.parseInt(uSHour)<10 && Integer.parseInt(eSHour)>8 && uSAmPm.equals("pm") && eSAmPm.equals("am")) )
            {
                throw new InvalidAppointmentException("Outside of buisness hours 8am-10pm");
            }
        }
        catch (InvalidAppointmentException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointmentException");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML public void onExitClick(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML private void updateCustomerTableView() { customerObjTableView.setItems(getListCustomers()); }

    public void initialize()
    {
        connectionConfig.updateCustomerObjList();
        //use of lambda to set values to the tableview
        customerName.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        updateCustomerTableView();
    }
}
