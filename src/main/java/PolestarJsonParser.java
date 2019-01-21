import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class PolestarJsonParser {

    public static void parseJson(/*location string as argument*/String jsonFrom, DatabaseConnect aConnection) {
        HashMap individualParameters = null;
        try {//use for import statements
            JSONParser parser = new JSONParser();
            String jsonLocation = jsonFrom;
            Object jsonInput = parser.parse(new FileReader(jsonLocation));
            JSONArray fromJson = (JSONArray) jsonInput;
            int arrayIndexAt = 0;
            do {
                JSONObject singleUnique = (JSONObject) fromJson.get(arrayIndexAt); //for each pulled json get their values and put it in the right place in the table.
                System.out.println(singleUnique + "a single full entry");
                System.out.println(singleUnique.get("UniqueID"));
                JSONArray parameters = (JSONArray) singleUnique.get("Parameters");
                individualParameters = getParameters(parameters);
                System.out.println(individualParameters.get("Name") + "Hashmaps!!");

                //keysabbreviated
                individualParameters.put("uniqueID", (String) singleUnique.get("UniqueID"));
                individualParameters.put("datasourcesC", (Long) singleUnique.get("DatasourcesCount"));
                individualParameters.put("alarmColour", (Long) singleUnique.get("AlarmColor"));
                individualParameters.put("alertIcon", (String) singleUnique.get("_alertIcon"));
                individualParameters.put("elementCount", (Long) singleUnique.get("ElementCount"));
                individualParameters.put("ID", (Long) singleUnique.get("Id"));
                individualParameters.put("name", (String) singleUnique.get("Name"));

                System.out.println(individualParameters.toString() + "THis is where the individual parameters will be inserted via method call");

                aConnection.insertPolestarData(individualParameters, null);
                arrayIndexAt++;

            } while (arrayIndexAt < fromJson.size());


//                System.out.println(fromJson.get(1)+"What is this?");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }


    public static HashMap getParameters(JSONArray jsonparam) {
        HashMap<String, String> datapairs = new HashMap<>();
        int parametersArray = 0;
        do {
            JSONObject aParameter = (JSONObject) jsonparam.get(parametersArray);
            datapairs.put((String) aParameter.get("Key"), (String) aParameter.get("Value"));
//            System.out.println(aParameter + "PolestarTest parameter");
            parametersArray++;
        } while (parametersArray < jsonparam.size());

        return datapairs;
    }
}
