package viewController;

import com.util.InvalidAppointmentException;
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
import model.appointmentObj;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import static model.appointmentList.getListAppointments;
import static viewController.appScreenController.appUpdateIndex;

public class appointmentUpdateScreenController
{
    @FXML private Button exitBtn;
    @FXML private Button updateBtn;
    @FXML private TextField titleTxt;
    @FXML private TextField descriptionTxt;
    @FXML private TextField locationTxt;
    @FXML private TextField contactTxt;
    @FXML private TextField typeTxt;
    @FXML private TextField URL_txt;
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

    int appIndex = appUpdateIndex();
    private int appointmentId;
    private static final DateTimeFormatter FMT_24 = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FMT_12 = DateTimeFormatter.ofPattern("hh:mm a");

    @FXML public void onExitClick(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void initialize() throws ParseException
    {
        appointmentObj a = getListAppointments().get(appIndex);

        String startUTCString = a.getStart();
        String endUTCString = a.getEnd();

        startUTCString = startUTCString.substring(0,19);
        endUTCString = endUTCString.substring(0,19);

        System.out.println(startUTCString + " end: " + endUTCString);

        ZonedDateTime gmtStartTime = LocalDateTime.parse(startUTCString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.of("GMT"));
        LocalDateTime localStartTime = gmtStartTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

        ZonedDateTime gmtEndTime = LocalDateTime.parse(endUTCString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.of("GMT"));
        LocalDateTime localEndTime = gmtEndTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

        System.out.println("UTC Start time: " + gmtStartTime + " end: " + gmtEndTime);
        System.out.println("Local 24 start time: " + localStartTime + " 24 hr  end " + localEndTime);

        String localStartTimeFinal = convertTo12HourFormat(localStartTime);
        String localEndTimeFinal = convertTo12HourFormat(localEndTime);

        System.out.println("Local start time: " + localStartTimeFinal + " end " + localEndTimeFinal);

        appointmentId = getListAppointments().get(appIndex).getAppointmentId();
        titleTxt.setText(a.getTitle());
        descriptionTxt.setText(a.getDescription());
        locationTxt.setText(a.getLocation());
        contactTxt.setText(a.getContact());
        typeTxt.setText(a.getType());
        URL_txt.setText(a.getUrl());
        startYear.setText(startUTCString.substring(0,4));
        startMonth.setText(startUTCString.substring(5,7));
        startDay.setText(startUTCString.substring(8,10));
        endYear.setText(endUTCString.substring(0,4));
        endMonth.setText(endUTCString.substring(5,7));
        endDay.setText(endUTCString.substring(8,10));
        startH.setText(localStartTimeFinal.substring(0,2));
        endH.setText(localEndTimeFinal.substring(0,2));
        startM.setText(localStartTimeFinal.substring(3,5));
        endM.setText(localEndTimeFinal.substring(3,5));
        startAmPm.setText(localStartTimeFinal.substring(6));
        endAmPm.setText(localEndTimeFinal.substring(6));
    }

    private String convertTo12HourFormat(LocalDateTime t)
    {

        String time = t.toString();
        time = time.substring(11);
        return convertFormats(time);
    }

    private static String convertFormats(String fromTime)
    {
        LocalTime time = LocalTime.parse(fromTime, appointmentUpdateScreenController.FMT_24);
        return appointmentUpdateScreenController.FMT_12.format(time);
    }

    public void onUpdateBtnClick(ActionEvent event) throws SQLException, IOException
    {
        appointmentObj app = getListAppointments().get(appIndex);
        appointmentId = getListAppointments().get(appIndex).getAppointmentId();
        String uTitle = titleTxt.getText();
        String uDes = descriptionTxt.getText();
        String uLocation = locationTxt.getText();
        String uContact = contactTxt.getText();
        String uType = typeTxt.getText();
        String uURL = URL_txt.getText();
        String uSYear = startYear.getText();
        String uSMonth = startMonth.getText();
        String uSDay = startDay.getText();
        String uSHour = startH.getText();
        String uSMinute = startM.getText();
        String uSAmPm = startAmPm.getText();
        String eSYear = endYear.getText();
        String eSMonth = endMonth.getText();
        String eSDay = endDay.getText();
        String eSHour = endH.getText();
        String eSMinute = endM.getText();
        String eSAmPm = endAmPm.getText();


        if(typeTxt.getLength() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Please give type!");
            alert.showAndWait();
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
        else if (!tryCatchException())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("errorAddingAppointment");
            alert.setContentText("Outside of buisness hours");
            //alert.showAndWait();
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

            //        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            //        Date createDate = new Date();

            if (Integer.parseInt(startH.getText()) > Integer.parseInt(endH.getText()) && endAmPm.getText().equals("am"))
            {
                System.out.println("Have to start before you can end!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("errorAddingAppointment");
                alert.setContentText("You need start before you can end!");
                alert.showAndWait();
            }
            else if (Integer.parseInt(startH.getText()) == Integer.parseInt(endH.getText())
                    && endAmPm.getText().equals(startAmPm.getText()) && Integer.parseInt(startM.getText()) >= Integer.parseInt(endM.getText()))
            {
                System.out.println("Have to start before you can end!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("errorAddingAppointment");
                alert.setContentText("You need start before you can end!");
                alert.showAndWait();
            }
            else if (connectionConfig.checkAppointmentUpdateConflictSQL(startUTC, endUTC, appointmentId))
            {
                System.out.println("Sorry another appointment is at that time!");
            }

            else
            {
                connectionConfig.updateAppointment(appointmentId, uTitle, uDes, uLocation, uContact, uType, uURL, uSYear, uSMonth, uSDay, uSHour, uSMinute, uSAmPm, eSYear, eSMonth, eSDay, eSHour, eSMinute, eSAmPm);

                System.out.println("Your appointment is scheduled! Home screen is loading!");

                Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
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

    }

    public boolean tryCatchException()
    {
        String uSHour = startH.getText();
        String eSHour = endH.getText();
        String uSAmPm = startAmPm.getText();
        String eSAmPm = endAmPm.getText();
        try
        {
            if( (Integer.parseInt(uSHour)<8 && uSAmPm.equalsIgnoreCase("AM")) ||
                    (Integer.parseInt(eSHour)>10 && eSAmPm.equalsIgnoreCase("PM")) ||
                    (Integer.parseInt(uSHour)<10 && Integer.parseInt(eSHour)>8 && uSAmPm.equalsIgnoreCase("PM") && eSAmPm.equalsIgnoreCase("AM")) )
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
}

