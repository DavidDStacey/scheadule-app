package viewController;

import com.sun.org.apache.xml.internal.utils.res.XResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginScreenController
{
    @FXML private TextField usernameTxt;
    @FXML private TextField passwordTxt;
    @FXML private ChoiceBox<String> languageSelector;
    @FXML private Label locationLbl;
    @FXML private Button loginBtn;
    @FXML private Button cancelBtn;
    @FXML private Label usernameLbl;
    @FXML private Label passwordLbl;
    @FXML private Label languageLbl;
    @FXML private Label header;

    String loginEmpty = "Username can not be empty";
    String invalid = "invalid username or password";

    private final String currentLocale =  Locale.getDefault().toString();
    // for testing of auto check for not en_us
    //private final String currentLocale =  "es";

    private final boolean locality = currentLocale.equalsIgnoreCase("en_US") || currentLocale.equalsIgnoreCase("en");

    ObservableList<String> languageSelected = FXCollections.observableArrayList("English", "Spanish");

    public void initialize()
    {
        // gets country of user and labels it at top right
        locationLbl.setText(currentLocale);

        // if not en_us or en switches to spanish
        if(!locality)
        {
            languageSelector.setValue("Spanish");
            changeToSpansh();
        }
        if(locality)
        {
            languageSelector.setValue("English");
        }
        // can also use choice box to change language
        languageSelector.setItems(languageSelected);
    }

    private void changeToSpansh()
    {
        setLangChange(ResourceBundle.getBundle("com.util/Lang_es"));
    }

    //------------------------------ START changing language using resource bundle property ---------------------------------------------
    public void onLangChange()
    {
        if(languageSelector.getValue().equals("English"))
        {
            setLangChange(ResourceBundle.getBundle("com.util/Lang_en"));
        }
        else if (languageSelector.getValue().equals("Spanish"))
        {
            setLangChange(ResourceBundle.getBundle("com.util/Lang_es"));
        }
        locationLbl.setText(currentLocale);
    }
    
    public void setLangChange(ResourceBundle rb)
    {
        usernameLbl.setText(rb.getString("Username"));
        passwordLbl.setText(rb.getString("Password"));
        locationLbl.setText(rb.getString("Your_location_is"));
        languageLbl.setText(rb.getString("Language"));
        header.setText(rb.getString("Appointment_Schedule"));
        loginBtn.setText(rb.getString("Login"));
        cancelBtn.setText(rb.getString("Cancel"));
        loginEmpty = rb.getString("Username_can_not_be_empty");
        invalid = rb.getString(("invalid_username_or_password"));
    }


    //-------------------------------------- END language cahgne -------------------------------------------------------------------------------+
    //---------------------------------------BUTTONS START -------------------------------------------------------------------------------------
    @FXML public void onLoginClick(ActionEvent event) throws IOException
    {
        if(usernameTxt.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, loginEmpty);
        }
        else
        {
            // this is where ill check username and password on sql to whats entered
            if (com.util.connectionConfig.checkLoginCredentials(usernameTxt.getText(), passwordTxt.getText(), invalid))
            {
                try
                {
                    FileWriter writer = new FileWriter("logFile.txt", true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    bufferedWriter.newLine();
                    bufferedWriter.write("Login Time: " + loginTime);

                    bufferedWriter.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                Parent parent = FXMLLoader.load(getClass().getResource("/viewController/appScreen.fxml"));
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
    }


    public void onCancelClick()
    {
        System.exit(0);
    }

    //------------------------------------------------------BUTTONS END---------------------------------------------------------------
}
