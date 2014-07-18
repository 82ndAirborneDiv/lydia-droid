package gov.cdc.stdtxguide;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConditionContent {

    public static final String CONTENT_MAP_FILENAME = "content/condition-content-map.txt";
    private Context context;
    private List conditionList;

    public ConditionContent(Context context) {

        this.context = context;
        InputStream stream = readContentMapFromFile();
        this.conditionList = readJsonStream(stream);

    }

    private InputStream readContentMapFromFile() {

        InputStream stream = null;

        try {

            AssetManager am = this.context.getAssets();
            stream = am.open(CONTENT_MAP_FILENAME);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return stream;

    }

    private boolean parseJsonString(String jsonString) {

        // create new JSON object from string
        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            int size = jsonObject.length();

            Log.d("ConditionContent", "Content Map JSON Array length = " + String.valueOf(size));
            return true;

            //JSONObject condition = obj.getJSONObject("0");

        } catch (JSONException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List readJsonStream(InputStream in)  {

        JsonReader reader = null;
        List conditionsArray = null;

        try {
            reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            conditionsArray = readConditionsArray(reader);
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return conditionsArray;
    }

    public List readConditionsArray(JsonReader reader) throws IOException {
        List conditions = new ArrayList();
        reader.setLenient(true);

        reader.beginArray();
        while (reader.hasNext()) {
            conditions.add(readCondition(reader));
        }
        reader.endArray();
        return conditions;
    }

    public Condition readCondition(JsonReader reader) throws IOException {
        int id = 255;
        String text = null;
        boolean hasChildren = false;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("parent")) {
                if (reader.peek() == JsonToken.NULL)
                    reader.skipValue();
                else {
                    id = reader.nextInt();
                    Log.d("ConditionContent", "For JSON key parent value = " + Integer.toString(id));
                }
            } else if (name.equals("text")) {
                text = reader.nextString();
                Log.d("ConditionContent", "For JSON key text value = " + text);
            }
            else if (name.equals("hasChildren")) {
                hasChildren = reader.nextBoolean();
                Log.d("ConditionContent", "For JSON key hasChildren value = " + hasChildren);
            }
            else if (name.equals("children") &&  reader.peek() != JsonToken.NULL) {
                reader.beginArray();
                while (reader.hasNext()) {
                    Log.d("ConditionContent", "Making recursive readCondition() call for children.");
                    readCondition(reader);
                }
                reader.endArray();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Condition(id, text);
    }

    public List readDoublesArray(JsonReader reader) throws IOException {
        List doubles = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

}
