

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


        System.out.println("Welcome to Ayo's Polestar CLi in java");
        Scanner scanner = new Scanner(System.in);

            try {//use for import statements
                System.out.println("please input json data location (use forward slash '/' and start from drive letter)");
                JSONParser parser = new JSONParser();
                String jsonLocation =scanner.next();
                Object jsonInput = parser.parse(new FileReader(jsonLocation));
                JSONArray fromJson = (JSONArray) jsonInput;
                int  arrayIndexAt =0;
                do {
                    JSONObject singleUnique = (JSONObject) fromJson.get(arrayIndexAt); //for each pulled json get their values and put it in the right place in the table.
                    System.out.println(singleUnique);
                    System.out.println(singleUnique.get("UniqueID"));
                    //Get everthing not from parameters for insert statement here.
                   JSONArray parameters = (JSONArray) singleUnique.get("Parameters");
                   HashMap  individualParameters = getParameters(parameters);
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

    public  static HashMap getParameters (JSONArray jsonparam){
        HashMap <String, String> datapairs = new HashMap<>();
        int parametersArray = 0;
                    do {
                        JSONObject aParameter = (JSONObject) jsonparam.get(parametersArray);
                        datapairs.put((String)aParameter.get("Key"),(String) aParameter.get("Value"));
                        System.out.println(aParameter + "A parameter");
                        parametersArray++;
                    }while (parametersArray < jsonparam.size() );

        return  datapairs;
    }

    public static void createDatabase(){
        try {
            //if given real database params:
            //use JDBC as if a real database if given a connection
            //Create database,
            // Use database,
            //create table
            //else output params to file and system out println them
        }catch (Exception e){

        }
    }
    public static String createInserts(){
    //To create the insert statements

    return null;
    }
}