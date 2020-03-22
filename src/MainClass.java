import java.sql.Connection;
import java.sql.SQLException;

import com.util.connectionConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/viewController/loginScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String [] args)
    {
        setConnection();
        launch(args);
    }

    public static void setConnection()
    {
        Connection connection = null;
        try
        {
            connection = connectionConfig.getConnection();
            if(connection != null)
            {
                System.out.println("Connection Established!");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
