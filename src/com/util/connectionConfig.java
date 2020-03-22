package com.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.appointmentObj;
import model.appointmentList;
import model.customerList;
import model.customerObj;

import javax.swing.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class connectionConfig
{

    public static Connection getConnection()
    {
        String url = "jdbc:mysql://3.227.166.251/U06va5";
        String username = "U06va5";
        String password = "53688881861";

        Connection connection = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection
                    (url, username, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean checkLoginCredentials(String user, String pass, String invalid)
    {
        boolean loggedin = false;
        String qs = "SELECT * FROM user WHERE userName = '" + user + "' and password = '" + pass +"';";
        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qs);

            if(rs.next())
            {
                System.out.println("Username and password exist going to new page");
                loggedin = true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, invalid);
            }
            rs.close();
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return loggedin;
    }

    public static void updateCustomerObjList()
    {
        try
        {
            // connect to db and use query to get customer with info
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customer.customerId, customer.customerName, address.address, " +
                    "address.address2, address.addressId, address.postalCode, city.cityId, city.city, country.country, " +
                    "country.countryID, address.phone FROM customer, address, city, country WHERE " +
                    "customer.addressId = address.addressId AND address.cityId = city.cityId AND " +
                    "city.countryId = country.countryId ORDER BY customer.customerName;");

            customerList.clearList();

            while (rs.next())
            {
                // new customer object
                customerObj cust = new customerObj();
                // get info from mysql
                int CustomerId = rs.getInt("customer.customerId");
                String CustomerName = rs.getString("customer.customerName");
                String Address = rs.getString("address.address");
                String Address2 = rs.getString("address.address2");
                int CityID = rs.getInt("city.cityId");
                String City = rs.getString("city.city");
                String Country = rs.getString("country.country");
                String PostalCode = rs.getString("address.postalCode");
                String Phone = rs.getString("address.phone");
                int CountryID = rs.getInt("country.countryId");
                int AddressID = rs.getInt("address.addressId");
                //set customers info
                cust.setCustomerId(CustomerId);
                cust.setCustomerName(CustomerName);
                cust.setAddress(Address);
                cust.setAddress2(Address2);
                cust.setCity(City);
                cust.setCityId(CityID);
                cust.setCountry(Country);
                cust.setCountryId(CountryID);
                cust.setPostalCode(PostalCode);
                cust.setPhone(Phone);
                cust.setAddressId(AddressID);

                // to make sure that customers are being pout together
                //System.out.println(cust);

                customerList.addToList(cust);
            }

            // check if added to list
            //System.out.println(customerList.getListCustomers());

            rs.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void updateAppointmentObjList()
    {
        try
        {
            // connect to db and use query to get customer with info
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customer.customerName, appointment.appointmentId, appointment.contact, appointment.customerId, " +
                    "appointment.description, appointment.end, appointment.type, appointment.start, appointment.location, appointment.title, appointment.url FROM " +
                    "appointment, customer WHERE customer.customerId = appointment.customerId;");

            appointmentList.clearList();

            while (rs.next())
            {
                // new customer object
                appointmentObj app = new appointmentObj();
                // get info from mysql
                String customerName = rs.getString("customer.customerName");
                int appointmentId = rs.getInt("appointment.appointmentId");
                String contact = rs.getString("appointment.contact");
                int customerId = rs.getInt("appointment.customerId");
                String description = rs.getString("appointment.description");
                String end = rs.getString("appointment.end");
                String start = rs.getString("appointment.start");
                String location = rs.getString("appointment.location");
                String title = rs.getString("appointment.title");
                String url = rs.getString("appointment.url");
                String type = rs.getString("appointment.type");

                // convert start andx end time to user timezone
               // Date s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa").parse(start);
               // s = TimeConversion.UTCtoLocalDate(s);
               // String newStart = s.toString();

               // Date end1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa").parse(end);
               // end1 = TimeConversion.UTCtoLocalDate(end1);
               // String newEnd = end1.toString();

                //set customers info
                app.setCustomerName(customerName);
                app.setAppointmentId(appointmentId);
                app.setContact(contact);
                app.setCustomerId(customerId);
                app.setDescription(description);
                //app.setEnd(newEnd);
                //app.setStart(newStart);
                app.setEnd(end);
                app.setStart(start);
                app.setLocation(location);
                app.setTitle(title);
                app.setUrl(url);
                app.setType(type);
                // to make sure that customers are being pout together
                //System.out.println(app);

                appointmentList.addToList(app);
            }

            // check if added to list
            //System.out.println(appointmentList.getListAppointments());

            rs.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteAppointmentSQL(appointmentObj a)
    {
        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate("DELETE appointment.* FROM appointment WHERE appointment.appointmentId = '" + a.getAppointmentId() + "'");

            //appointmentList.clearList();

            System.out.println("Number of effected rows: " + rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteCustomerSQL(customerObj c)
    {
        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            int as = stmt.executeUpdate("DELETE FROM appointment WHERE customerId = " + c.getCustomerId() + ";");
            int cs = stmt.executeUpdate("DELETE FROM customer WHERE customerId = " + c.getCustomerId() + ";");
            int adds = stmt.executeUpdate("DELETE FROM address WHERE addressId = " + c.getAddressId() + ";");

            System.out.println("Number of appointments effected: " + as);
            System.out.println("Number of customers effected: " + cs);
            System.out.println("Number of addresses effected: " + adds);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void addAppointmentSQL(customerObj c, ZonedDateTime startUTC, ZonedDateTime endUTC, String titleTxt, String descriptionTxt, String locationTxt, String contactTxt, String typeTxt, String urltxt)
    {
        int aptId;
        try
        {

            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT appointmentId FROM appointment ORDER BY appointmentId;");

            if(rs.last())
            {
                aptId = rs.getInt(1)+1;
                rs.close();
            }
            else
            {
                rs.close();
                aptId = 1;
            }

            String startUTCString = startUTC.toString();
            startUTCString = startUTCString.substring(0,10) + " " + startUTCString.substring(11,16) + ":00";
            Timestamp startTimestamp = Timestamp.valueOf(startUTCString);
            String endUTCString = endUTC.toString();
            endUTCString = endUTCString.substring(0,10) + " " + endUTCString.substring(11,16) + ":00";
            Timestamp endTimestamp = Timestamp.valueOf(endUTCString);

            System.out.println(endTimestamp);

            stmt.executeUpdate("INSERT INTO `appointment` VALUES (" + aptId + ", " + c.getCustomerId() + "," +
                    " 1, '" + titleTxt + "', '" + descriptionTxt + "', '" + locationTxt + "', '" + contactTxt + "', '" +
                    typeTxt + "', '" + urltxt + "', '" + startTimestamp + "', '" + endTimestamp + "', CURRENT_DATE, 'test', " +
                    "CURRENT_TIMESTAMP, 'test');");

            //System.out.println();

            rs.close();
            conn.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean checkAppointmentConflictSQL(ZonedDateTime startUTC, ZonedDateTime endUTC) throws SQLException
    {
        String startUTCString = startUTC.toString();
        startUTCString = startUTCString.substring(0,10) + " " + startUTCString.substring(11,16) + ":00";
        String endUTCString = endUTC.toString();
        endUTCString = endUTCString.substring(0,10) + " " + endUTCString.substring(11,16) + ":00";

        System.out.println("Start: " + startUTCString + " end: " + endUTCString);

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM appointment " +
                "JOIN customer ON appointment.customerId = customer.customerId WHERE " +
                "(appointment.start >=  '" + startUTCString + "' AND appointment.end <= '" + endUTCString + "' OR " +
                "(appointment.start <= '" + startUTCString + "' AND appointment.end >= '" + endUTCString + "') OR " +
                "(appointment.start BETWEEN '" + startUTCString + "' AND '" + endUTCString + "' OR " +
                "appointment.end BETWEEN '" + startUTCString + "' AND '" + endUTCString + "'));");

        System.out.println(rs);

        if(rs.next())
        {
            return true;
        }
        else
        {
            System.out.println("Your appointment is being scheduled!");
            return false;
        }
    }

    public static boolean checkAppointmentUpdateConflictSQL(ZonedDateTime startUTC, ZonedDateTime endUTC, int appId) throws SQLException
    {
        String startUTCString = startUTC.toString();
        startUTCString = startUTCString.substring(0,10) + " " + startUTCString.substring(11,16) + ":00";
        String endUTCString = endUTC.toString();
        endUTCString = endUTCString.substring(0,10) + " " + endUTCString.substring(11,16) + ":00";

        System.out.println("Start: " + startUTCString + " end: " + endUTCString);

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM appointment " +
                "JOIN customer ON appointment.customerId = customer.customerId WHERE " +
                "(appointment.start >=  '" + startUTCString + "' AND appointment.end <= '" + endUTCString + "' OR " +
                "(appointment.start <= '" + startUTCString + "' AND appointment.end >= '" + endUTCString + "') OR " +
                "(appointment.start BETWEEN '" + startUTCString + "' AND '" + endUTCString + "' OR " +
                "appointment.end BETWEEN '" + startUTCString + "' AND '" + endUTCString + "'));");

        System.out.println(rs);


        if(rs.next() && Integer.parseInt(rs.getString("appointment.appointmentId")) != appId)
        {
            return true;
        }
        else
        {
            System.out.println("Your appointment is being scheduled!");
            return false;
        }
    }

    public static void updateAppointment(int appointmentId, String uTitle, String description, String uLocation, String uContact, String uType, String uURL, String uSYear, String uSMonth, String uSDay, String uSHour, String uSMinute, String uSAmPm, String eSYear, String eSMonth, String eSDay, String eSHour, String eSMinute, String eSAmPm) throws SQLException
    {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        localDateFormat.setTimeZone(TimeZone.getDefault());

        Date startLocal = null;
        Date endLocal = null;
        try
        {
            startLocal = localDateFormat.parse(uSYear + "-" + uSMonth + "-" + uSDay + " " + uSHour + ":" + uSMinute + " " + uSAmPm);
            endLocal = localDateFormat.parse(eSYear + "-" + eSMonth + "-" + eSDay + " " + eSHour + ":" + eSMinute + " " + eSAmPm);
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

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        //Date createDate = new Date();

        String startUTCString = startUTC.toString();
        startUTCString = startUTCString.substring(0,10) + " " + startUTCString.substring(11,16) + ":00";
        Timestamp startTimestamp = Timestamp.valueOf(startUTCString);
        String endUTCString = endUTC.toString();
        endUTCString = endUTCString.substring(0,10) + " " + endUTCString.substring(11,16) + ":00";
        Timestamp endTimestamp = Timestamp.valueOf(endUTCString);

        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE appointment SET title = '"+uTitle+"', description = '"+description+"'" +
                    ", location = '"+uLocation+"', contact = '"+uContact+"', type = '"+uType+"', " +
                    "url = '"+uURL+"', start = '"+startTimestamp+"', end = '"+endTimestamp+"', " +
                    "lastUpdate = CURRENT_TIMESTAMP WHERE appointmentId = "+appointmentId+";");
            stmt.close();
            conn.close();
        }
        catch (SQLException e) { e.printStackTrace(); }

    }

    public static void checkAppointment15Away() throws SQLException
    {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT start FROM appointment");

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        String nowTest = String.valueOf(now);
        String currentDate = nowTest.substring(0,10);
        String currentHour = nowTest.substring(11,13);
        String currentMinute = nowTest.substring(14,16);
        System.out.println("Current Date in UTC : " + currentDate + " Current Time in UTC: " + currentHour + ":" + currentMinute);

        while(rs.next())
        {
            String startTimeCheck = rs.getString("appointment.start");
            System.out.println("Appointment at: " + startTimeCheck);

            String startDate = startTimeCheck.substring(0,10);
            String startHour = startTimeCheck.substring(11,13);
            String startMinute = startTimeCheck.substring(14,16);

            if(startDate.equals(currentDate))
            {
                if(startHour.equals(currentHour))
                {
                    if ((Integer.parseInt(startMinute) - Integer.parseInt(currentMinute)) <= 15)
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Appointment Soon");
                        alert.setHeaderText("Appointment soon!");
                        alert.setContentText("Appointment less than or equal to 15 minutes away at: " + startTimeCheck);
                        alert.showAndWait();
                    }
                }
                else if((Integer.parseInt(startHour) - Integer.parseInt(currentHour) == 1) && ((Integer.parseInt(startMinute)+60)-Integer.parseInt(currentMinute) <= 15))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Soon");
                    alert.setHeaderText("Appointment soon!");
                    alert.setContentText("Appointment less than or equal to 15 minutes away at: " + startTimeCheck);
                    alert.showAndWait();
                }
            }
        }

        rs.close();
        conn.close();
    }

    public static void addCustomerSQL(String name, String address, String address2, String city, String country, String zip, String phone)
    {
        int customerId;
        int addressId;
        int cityId = 0;
        int countryId = 0;
        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            ResultSet cs = stmt.executeQuery("SELECT country, countryId FROM country");
            while(cs.next())
            {
                if(cs.getString("country.country").equals(country))
                {
                    countryId = cs.getInt("country.countryId");
                }
            }
            cs.close();
            ResultSet rs = stmt.executeQuery("SELECT countryId FROM country ORDER BY countryId;");
            if (countryId == 0)
            {
                if(rs.last())
                {
                    countryId = rs.getInt(1)+1;
                }
                else
                {
                    countryId = 1;
                }
                stmt.executeUpdate("INSERT INTO country VALUES("+countryId+", '"+country+"', CURRENT_DATE, " +
                        "'test', CURRENT_TIMESTAMP, 'test');");

            }
            rs.close();

            cs = stmt.executeQuery("SELECT city, cityId FROM city;");
            while(cs.next())
            {
                if (cs.getString("city.city").equals(city))
                {
                    cityId = cs.getInt("city.cityId");
                }
            }
            cs.close();
            rs = stmt.executeQuery("SELECT cityId FROM city ORDER BY cityId;");
            if(cityId == 0)
            {
                if(rs.last())
                {
                    cityId = rs.getInt(1)+1;
                }
                else
                {
                    cityId = 1;
                }
                stmt.executeUpdate("INSERT INTO city VALUES("+cityId+", '"+city+"', "+countryId+", CURRENT_DATE, " +
                        "'test', CURRENT_TIMESTAMP, 'test');");
            }
            rs.close();

            rs = stmt.executeQuery("SELECT addressId FROM address ORDER BY addressId;");
            if(rs.last())
            {
                addressId = rs.getInt(1)+1;
            }
            else
            {
                addressId = 1;
            }
            stmt.executeUpdate("INSERT INTO address VALUES("+addressId+", '"+address+"', '"+address2+"', " +
                    ""+cityId+", '"+zip+"', '"+phone+"', CURRENT_DATE, 'test', CURRENT_TIMESTAMP, 'test');");

            rs.close();

            rs = stmt.executeQuery("SELECT customerId FROM customer ORDER BY customerId;");
            if(rs.last())
            {
                customerId = rs.getInt(1)+1;
            }
            else
            {
                customerId = 1;
            }
            stmt.executeUpdate("INSERT INTO customer VALUES("+customerId+", '"+name+"', "+addressId+", " +
                    "1, CURRENT_DATE, 'test', CURRENT_TIMESTAMP, 'test');");

            rs.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void updateCustomerSQL(customerObj c, int customerId, String nameString, String addressString, String address2String, String cityString, String countryString, String zipString, String phoneString)
    {
        int addressId = 0;
        int cityId = 0;
        int countryId = 0;
        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            ResultSet cs = stmt.executeQuery("SELECT country, countryId FROM country");
            while(cs.next())
            {
                if(cs.getString("country.country").equals(countryString))
                {
                    countryId = cs.getInt("country.countryId");
                }
            }
            cs.close();
            ResultSet rs = stmt.executeQuery("SELECT countryId FROM country ORDER BY countryId;");
            if (countryId == 0)
            {
                if(rs.last())
                {
                    countryId = rs.getInt(1)+1;
                }
                else
                {
                    countryId = 1;
                }
                stmt.executeUpdate("INSERT INTO country VALUES("+countryId+", '"+countryString+"', CURRENT_DATE, " +
                        "'test', CURRENT_TIMESTAMP, 'test');");
                rs.close();
            }

            cs = stmt.executeQuery("SELECT city, cityId FROM city;");
            while(cs.next())
            {
                if (cs.getString("city.city").equals(cityString))
                {
                    cityId = cs.getInt("city.cityId");
                }
            }
            cs.close();
            rs = stmt.executeQuery("SELECT cityId FROM city ORDER BY cityId;");
            if(cityId == 0)
            {
                if(rs.last())
                {
                    cityId = rs.getInt(1)+1;
                }
                else
                {
                    cityId = 1;
                }
                stmt.executeUpdate("INSERT INTO city VALUES("+cityId+", '"+cityString+"', "+countryId+", CURRENT_DATE, " +
                        "'test', CURRENT_TIMESTAMP, 'test');");
                rs.close();
            }

            cs = stmt.executeQuery("SELECT address, addressId FROM address;");
            while(cs.next())
            {
                if (cs.getString("address.address").equals(addressString))
                {
                    addressId = cs.getInt("address.addressId");
                }
            }
            cs.close();
            rs = stmt.executeQuery("SELECT addressId FROM address ORDER BY addressId;");
            if(addressId == 0)
            {
                if (rs.last())
                {
                    addressId = rs.getInt(1) + 1;
                }
                else
                {
                    addressId = 1;
                }
                stmt.executeUpdate("INSERT INTO address VALUES("+addressId+", '"+addressString+"', '"+address2String+"', " +
                        ""+cityId+", '"+zipString+"', '"+phoneString+"', CURRENT_DATE, 'test', CURRENT_TIMESTAMP, 'test');");
                rs.close();
            }

            stmt.executeUpdate("UPDATE customer SET customerName = '"+nameString+"', " +
                    "addressId = "+addressId+", lastUpdate = CURRENT_TIMESTAMP" +
                    " WHERE customerId = "+customerId+";");

            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /*
    public static ObservableList<appointmentObj> getAppointments(int customerID, String weekormonth) throws SQLException
    {
        ObservableList<appointmentObj> appointmentList = FXCollections.observableArrayList();


        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        LocalDate sDate = LocalDate.now();
        LocalDate eDate = LocalDate.now();

        sDate = LocalDate.now();

        if (weekormonth.equals("Weekly"))
        {
            eDate = LocalDate.now().plusDays(7);
        }
        else if(weekormonth.equals("Monthly"))
        {
            eDate = LocalDate.now().plusMonths(1);
        }
        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointment WHERE " +
                    "customerId = "+customerID+" and start >= '"+sDate+"' and end <= '"+eDate+"'; ");

            appointmentObj a;

            //build current address
            while (rs.next())
            {
                a = new appointmentObj();

                a.setAppointmentId(rs.getInt("appointmentId"));
                a.setCustomerId(rs.getInt("customerId"));
                //currentAppointment.setUserID(rs.getInt("addressId"));
                a.setTitle(rs.getString("title"));
                a.setDescription(rs.getString("description"));
                a.setLocation(rs.getString("location"));
                a.setContact(rs.getString("contact"));
                //a.setType(rs.getString("type"));
                a.setUrl(rs.getString("url"));
                a.setStart(rs.getString("start"));
                a.setEnd(rs.getString("end"));

                appointmentList.add(a);
            }
            rs.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return appointmentList;
    }

     */

    public static String getConsultantSchedule() throws SQLException
    {
        StringBuilder returnString = new StringBuilder();

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT userName, start, end FROM appointment " +
                "JOIN user ON appointment.userId = user.userId ORDER BY appointment.userId, start;");

        while (rs.next())
        {
            String addToReturnString = "Consultant: " + rs.getString("user.userName") + " Start Appointment: "
                    + rs.getString("appointment.start") + " End Appointment: " + rs.getString("appointment.end") + "\n";

            returnString.append(addToReturnString);
        }
        rs.close();
        conn.close();

        returnString.append("\n\nNOTE TIMES IN UTC");
        return returnString.toString();
    }

    public static String getNumByType() throws SQLException
    {
        StringBuilder returnString = new StringBuilder();

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MONTHNAME(start) AS Month, type as Type, COUNT(*) as Amount " +
                "FROM appointment GROUP BY MONTHNAME(start), Type ORDER BY start;");

        while (rs.next())
        {
            String addToReturnString = "There are " + rs.getString("Amount") +
                    " during the month of " + rs.getString("Month") + " with the appointment " +
                    "type being " + rs.getString("Type") + ".\n";

            returnString.append(addToReturnString);
        }
        rs.close();
        conn.close();

        return returnString.toString();
    }

    public static String getAdditionalReport() throws SQLException
    {
        StringBuilder returnString = new StringBuilder();

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT customerName AS Name, COUNT(*) AS Amount " +
                "FROM appointment JOIN customer ON appointment.customerId = customer.customerId GROUP BY NAME; ");

        while (rs.next())
        {
            String addToReturnString = rs.getString("Name") + " has " + rs.getString("Amount")
                    + " appointments.\n";

            returnString.append(addToReturnString);
        }
        rs.close();
        conn.close();

        return returnString.toString();
    }

    public static ObservableList<appointmentObj> getAppointmentByWeek() 
    {
        ZoneId zId = ZoneId.systemDefault();
        ObservableList<appointmentObj> appointmentsByWeek = FXCollections.observableArrayList();

        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customer.*, appointment.* FROM customer "
                    + "RIGHT JOIN appointment ON customer.customerId = appointment.customerId "
                    + "WHERE start BETWEEN NOW() AND (SELECT ADDDATE(NOW(), INTERVAL 7 DAY))");

            while(rs.next())
            {
                appointmentObj getWeeklyAppts = new appointmentObj();
                getWeeklyAppts.setCustomerName(rs.getString("customerName"));
                getWeeklyAppts.setAppointmentId(rs.getInt("appointmentId"));
                getWeeklyAppts.setCustomerId(rs.getInt("customerId"));
                getWeeklyAppts.setTitle(rs.getString("title"));
                getWeeklyAppts.setDescription(rs.getString("description"));
                getWeeklyAppts.setLocation(rs.getString("location"));
                getWeeklyAppts.setContact(rs.getString("contact"));
                getWeeklyAppts.setType(rs.getString("type"));
                getWeeklyAppts.setUrl(rs.getString("url"));

                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);

                getWeeklyAppts.setStart(String.valueOf(startLocal));
                getWeeklyAppts.setEnd(String.valueOf(endLocal));
                appointmentsByWeek.add(getWeeklyAppts);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return appointmentsByWeek;
    }

    public static ObservableList<appointmentObj> getApptsByMonth()
    {
        ZoneId zId = ZoneId.systemDefault();
        ObservableList<appointmentObj> apptsByMonth = FXCollections.observableArrayList();

        try
        {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customer.*, appointment.* FROM customer JOIN appointment " +
                    "ON customer.customerId = appointment.customerId WHERE " +
                    "start >= DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) " +
                    "AND start <=  DATE_ADD(LAST_DAY(NOW()), INTERVAL +1 DAY);");

            while (rs.next())
            {
                appointmentObj getMonthlyAppts = new appointmentObj();
                getMonthlyAppts.setCustomerName(rs.getString("customerName"));
                getMonthlyAppts.setAppointmentId(rs.getInt("appointmentId"));
                getMonthlyAppts.setCustomerId(rs.getInt("customerId"));
                getMonthlyAppts.setTitle(rs.getString("title"));
                getMonthlyAppts.setDescription(rs.getString("description"));
                getMonthlyAppts.setLocation(rs.getString("location"));
                getMonthlyAppts.setContact(rs.getString("contact"));
                getMonthlyAppts.setType(rs.getString("type"));
                getMonthlyAppts.setUrl(rs.getString("url"));

                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);

                getMonthlyAppts.setStart(String.valueOf(startLocal));
                getMonthlyAppts.setEnd(String.valueOf(endLocal));
                apptsByMonth.add(getMonthlyAppts);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return apptsByMonth;
    }
}
