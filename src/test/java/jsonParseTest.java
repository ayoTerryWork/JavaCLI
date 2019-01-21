import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class jsonParseTest {

    @Test
    public void parseJson (){

        try {//use for import statements
//            System.out.println("please input json data location (use forward slash '/' and start from drive letter)");
            JSONParser parser = new JSONParser();
            String jsonLocation = "e:/inputjson/input.json";
            Object jsonInput = parser.parse(new FileReader(jsonLocation));
            JSONArray fromJson = (JSONArray) jsonInput;
            int arrayIndexAt = 0;
            do {
                JSONObject singleUnique = (JSONObject) fromJson.get(arrayIndexAt); //for each pulled json get their values and put it in the right place in the table.
                System.out.println(singleUnique+"a single full entry");
                System.out.println(singleUnique.get("UniqueID"));
                //Get everything not from parameters for insert statement here.
                JSONArray parameters = (JSONArray) singleUnique.get("Parameters");
                HashMap individualParameters = getParameters(parameters);
                System.out.println(individualParameters.get("Name") + "Hashmaps!!");
                //Insert statements to take from the hashmap and the above ARRAY

                String uniqueID = (String) singleUnique.get("UniqueID");
                Long datasource = (Long) singleUnique.get("DatasourcesCount");
                Long alarmColour = (Long) singleUnique.get("AlarmColor");
                String alertIcon = (String) singleUnique.get("_alertIcon");
                Long elementCount = (Long) singleUnique.get("ElementCount");
                Long iD = (Long) singleUnique.get("Id");
                String name = (String) singleUnique.get("Name");

                String pPower = (String) individualParameters.get("Peak Power");
                String description = (String) individualParameters.get("Description");
                String pDCC = (String) individualParameters.get("Panel Degradation Correction Coefficient");
                String tCC = (String) individualParameters.get("Temperature Correction Coefficient");
                String nomPower = (String) individualParameters.get("Nominal Power");

//shortkeysabbreviated
                System.out.println(uniqueID+"LOL"+datasource+"LOL2"+iD+name+"Free and easy");
                System.out.println(nomPower);
                individualParameters.put("uniqueID",(String) singleUnique.get("UniqueID"));
                individualParameters.put("datasourcesC",(Long) singleUnique.get("DatasourcesCount"));
                individualParameters.put("alarmColour",(Long) singleUnique.get("AlarmColor"));
                individualParameters.put("alertIcon",(String) singleUnique.get("_alertIcon"));
                individualParameters.put("elementCount",(Long) singleUnique.get("ElementCount"));
                individualParameters.put("iD",(Long) singleUnique.get("Id"));
                individualParameters.put("name",(String) singleUnique.get("Name") );

                System.out.println(individualParameters.toString() + "THis is where the individual parameters will be inserted via method call");
                System.out.println(individualParameters.get("Nominal Power")+"Should be null sometimes");
                nomPower = null;

                arrayIndexAt++;

            } while (arrayIndexAt < fromJson.size());


//                System.out.println(fromJson.get(1)+"What is this?");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Should print 4 times but wont ");
    }

    public static HashMap getParameters(JSONArray jsonparam) {
        HashMap<String, String> datapairs = new HashMap<>();
        int parametersArray = 0;
        do {
            JSONObject aParameter = (JSONObject) jsonparam.get(parametersArray);
            datapairs.put((String) aParameter.get("Key"), (String) aParameter.get("Value"));
            System.out.println(aParameter + "PolestarTest parameter");
            parametersArray++;
        } while (parametersArray < jsonparam.size());

        return datapairs;
    }

}
