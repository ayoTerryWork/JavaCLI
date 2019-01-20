

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

class A {
    public static void main(String args[]) {

        connectToDatabase();
        System.out.println("Welcome to Ayo's Polestar CLi in java");
        Scanner scanner = new Scanner(System.in);

        try {//use for import statements
            System.out.println("please input json data location (use forward slash '/' and start from drive letter)");
            JSONParser parser = new JSONParser();
            String jsonLocation = scanner.next();
            Object jsonInput = parser.parse(new FileReader(jsonLocation));
            JSONArray fromJson = (JSONArray) jsonInput;
            int arrayIndexAt = 0;
            do {
                JSONObject singleUnique = (JSONObject) fromJson.get(arrayIndexAt); //for each pulled json get their values and put it in the right place in the table.
                System.out.println(singleUnique);
                System.out.println(singleUnique.get("UniqueID"));
                //Get everthing not from parameters for insert statement here.
                JSONArray parameters = (JSONArray) singleUnique.get("Parameters");
                HashMap individualParameters = getParameters(parameters);
                System.out.println(individualParameters.get("Name") + "Hashmaps!!");
                //Insert statements to take from the hashmap and the above ARRAY


                arrayIndexAt++;

            } while (arrayIndexAt < fromJson.size());


//                System.out.println(fromJson.get(1)+"What is this?");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


        System.out.println("Please input JDBC String e.g. jdbc:mysql://localhost:3306/testdb?useSSL=false ");
        String input = scanner.next();
        System.out.println(input + " is the given JDBC string");
        String url = input;
    }

    public static HashMap getParameters(JSONArray jsonparam) {
        HashMap<String, String> datapairs = new HashMap<>();
        int parametersArray = 0;
        do {
            JSONObject aParameter = (JSONObject) jsonparam.get(parametersArray);
            datapairs.put((String) aParameter.get("Key"), (String) aParameter.get("Value"));
            System.out.println(aParameter + "A parameter");
            parametersArray++;
        } while (parametersArray < jsonparam.size());

        return datapairs;
    }

    public static void createDatabase() {
        try {
            //if given real database params:
            //use JDBC as if a real database if given a connection
            //Create database,
            // Use database,
            //create table
            //else output params to file and system out println them
        } catch (Exception e) {

        }
    }

    public static String connectToDatabase() {
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/test?useSSL=false","root", "nataku"
                     );
            statement = connect.createStatement();
            ResultSet queryTest= statement.executeQuery("select * from test.testdata1");
            while (queryTest.next()){
                System.out.println(queryTest.getString(1));
            }

//            PreparedStatement query = connect.prepareStatement("select * from test.testdata1");
//           query.executeQuery();
//            System.out.println(query);
//            System.out.println(queryTest);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Didnt work");
        }
        return "failure";
    }

    public static String createInserts() {
        //To create the insert statements


        return null;
    }

    public static String getContents(){
        return  null;
    }
}