import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DatabaseConnect {
    Connection connect = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String tablename = " polestarcontent";


    public boolean connectToDatabase(String url, String username, String password) {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url + "useSSL=false", username, password);


//            statement = connect.createStatement();
//            Statement statement2 = connect.createStatement();
////            statement2.executeUpdate("create database newJavaDB");
//
//
//            ResultSet queryTest= statement.executeQuery("show databases");
//            while (queryTest.next()){
//                System.out.println(queryTest.getString(1));
//            }
//            statement.close();
//
//
//            statement2.close();
            return true;


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Didnt work. No connection");
            return false;
        }

    }

    public String setNewDatabaseName(String databaseName) {
        try {
            if (databaseName != null) {

                connect.setCatalog(databaseName);
                return "success";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed";
        }
        return "failed";

    }

    public void createDatabase(String newDatabaseName) {
        try {
            String aNewDB = "CREATE DATABASE " + newDatabaseName;

            PreparedStatement createDatabase = connect.prepareStatement(aNewDB);
            System.out.println(aNewDB);
            createDatabase.executeUpdate();
            connect.setCatalog(newDatabaseName);
            createDatabase.close();
            System.out.println("newDB is called " + newDatabaseName);

            //if given real database params:
            //use JDBC as if a real database if given a connection
            //Create database,
            // Use database,
            //create table
            //else output params to file and system out println them
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("unable to make new DB");
        }
    }


    public void createTable(boolean printOnly) {
        String polestarTable = "CREATE TABLE" + tablename + "(\n" +
                "  `Unique_id` char(36) NOT NULL,\n" +
                "  `name` varchar(255) DEFAULT NULL,\n" +
                "  `peak_power` decimal(7,2) DEFAULT NULL,\n" +
                "  `nominal_power` int(6) DEFAULT NULL,\n" +
                "  `description` varchar(20) DEFAULT NULL,\n" +
                "  `Panel_Degradation_Correction_Coefficient` decimal(2,1) DEFAULT NULL,\n" +
                "  `Temperature_Correction_Coefficient` decimal(2,2) DEFAULT NULL,\n" +
                "  `Datasources` int(11) DEFAULT NULL,\n" +
                "  `Element_Count` int(11) DEFAULT NULL,\n" +
                "  `alert_icon` varchar(17) DEFAULT NULL,\n" +
                "  `Alarm_Colour` int(1) DEFAULT NULL,\n" +
                "  `id` int(11) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`Unique_id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n";
        if (printOnly!=true){
            try {

                preparedStatement = connect.prepareStatement(polestarTable);

                preparedStatement.executeUpdate(polestarTable);
                preparedStatement.close();
                System.out.println("Table created successfully ");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Table creation did NOT succeed");
            }
        }else{
            System.out.println(polestarTable);
        }

    }

    public void insertPolestarData(HashMap data, Boolean printOnly) {
        String insertStatemennt = "INSERT INTO " + tablename + "(Unique_id," +
                "name," +
                "peak_power," +
                "nominal_power," +
                "description," +
                "Panel_Degradation_Correction_Coefficient," +
                " Temperature_Correction_Coefficient," +
                "Datasources," +
                "Element_Count," +
                "alert_icon," +
                "Alarm_Colour," +
                "id)" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        if(data!=null){
            try {


                PreparedStatement pStatement = connect.prepareStatement(insertStatemennt);
                pStatement.setString(1, (String) data.get("uniqueID"));
                pStatement.setString(2, (String) data.get("name"));
                pStatement.setBigDecimal(3, new BigDecimal((String) data.get("Peak Power")));
                System.out.println(new BigDecimal((String) data.get("Peak Power")));

                if (data.get("Nominal Power") != null) {
                    pStatement.setInt(4, Integer.parseInt((String) data.get("Nominal Power")));
                } else {
                    pStatement.setString(4, null);
                }
                pStatement.setString(5, (String) data.get("Description"));
                pStatement.setString(6, (String) data.get("Panel Degradation Correction Coefficient"));
                pStatement.setString(7, (String) data.get("Temperature Correction Coefficient"));
                pStatement.setInt(8, Math.toIntExact((Long) data.get("datasourcesC")));
                pStatement.setInt(9, Math.toIntExact((Long) data.get("elementCount")));
                pStatement.setString(10, (String) data.get("alertIcon"));
                pStatement.setInt(11, Math.toIntExact((Long) data.get("alarmColour")));
                pStatement.setInt(12, Math.toIntExact((Long) data.get("ID")));
                pStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Insert didnt work");
            }
        }



    }

    public void queryTable() {
        try {
            statement = connect.createStatement();
            String query = "select * from " + tablename + ";";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Below this should be Unique ID's");
            while (rs.next()) {
                System.out.println(rs.getString(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("query failed");
        }

    }

}
