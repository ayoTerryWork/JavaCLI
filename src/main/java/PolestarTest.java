

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

class PolestarTest {
    public static String url;
    public static String usernname;
    public static String password;
    public static PolestarJsonParser pjp = new PolestarJsonParser();
    public static DatabaseConnect aConnection = new DatabaseConnect();
    public static boolean successFail;
    public static boolean newDB = true;
    public static String aNewDBname;
    public static final boolean PRINT_ONLY =true;

    public static void main(String args[]) {
        System.out.println("Welcome to Ayo's Polestar CLi in java");
        Scanner scanner = new Scanner(System.in);
        System.out.println("if you would like to use a real local database press 'y' and hit enter ");
        String yesElse = scanner.next();

        if (yesElse.contains("y")) {
            System.out.println("Please input JDBC String url e.g. jdbc:mysql://localhost:3306/aDatabaseName? ");
            url = scanner.next();
            System.out.println("Please input username");
            usernname = scanner.next();
            System.out.println("Please input password");
            password = scanner.next();

            successFail = aConnection.connectToDatabase(url, usernname, password);
            if (successFail == true) {
                System.out.println("Please enter the name of the database you would like to create or type 'no-anExistingDBname' and hit enter if you want me to use an existing one ");
                 aNewDBname = scanner.next();

                if (aNewDBname.contentEquals("no")) {
                    String[] existingDB = aNewDBname.split("-");
                    aConnection.setNewDatabaseName(existingDB[1]);//switches to db that exists
                    newDB = false;
                }
            }
            if (!newDB == false) {
                //create new DB here
                aConnection.createDatabase(aNewDBname);
            }
            System.out.println("please input json data location (use forward slash '/' and start from drive letter)");
            String jsonSource = scanner.next();
            aConnection.createTable(false);
            pjp.parseJson(jsonSource, aConnection);
            aConnection.queryTable();

        } else if (!yesElse.contains("y") || successFail == false) {
            System.out.println("Since you did not select a  functional DB the commands will be printed to the screen without a connection");
            //print commands to screen here using a method
            System.out.println("Create Table statement");
            aConnection.createTable(PRINT_ONLY);
            aConnection.insertPolestarData(null, PRINT_ONLY);
        }


//        String input = scanner.next();
//        System.out.println(input + " is the given JDBC string");
//        String url = input; //"jdbc:mysql://localhost/polestartest?useSSL=false"

//        try {//use for import statements
//            System.out.println("please input json data location (use forward slash '/' and start from drive letter)");
//            JSONParser parser = new JSONParser();
//            String jsonLocation = scanner.next();
//            Object jsonInput = parser.parse(new FileReader(jsonLocation));
//            JSONArray fromJson = (JSONArray) jsonInput;
//            int arrayIndexAt = 0;
//            do {
//                JSONObject singleUnique = (JSONObject) fromJson.get(arrayIndexAt); //for each pulled json get their values and put it in the right place in the table.
//                System.out.println(singleUnique);
//                System.out.println(singleUnique.get("UniqueID"));
//                //Get everything not from parameters for insert statement here.
//                JSONArray parameters = (JSONArray) singleUnique.get("Parameters");
//                HashMap individualParameters = getParameters(parameters);
//                System.out.println(individualParameters.get("Name") + "Hashmaps!!");
//                //Insert statements to take from the hashmap and the above ARRAY
//
//
//                arrayIndexAt++;
//
//            } while (arrayIndexAt < fromJson.size());
//
//
////                System.out.println(fromJson.get(1)+"What is this?");
//        } catch (ParseException | IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

//    public static HashMap getParameters(JSONArray jsonparam) {
//        HashMap<String, String> datapairs = new HashMap<>();
//        int parametersArray = 0;
//        do {
//            JSONObject aParameter = (JSONObject) jsonparam.get(parametersArray);
//            datapairs.put((String) aParameter.get("Key"), (String) aParameter.get("Value"));
//            System.out.println(aParameter + "PolestarTest parameter");
//            parametersArray++;
//        } while (parametersArray < jsonparam.size());
//
//        return datapairs;
//    }


    }
}