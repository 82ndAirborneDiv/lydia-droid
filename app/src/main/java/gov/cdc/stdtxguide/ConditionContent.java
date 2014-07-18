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
    private Condition rootCondition;

    public ConditionContent(Context context) {

        this.context = context;
        InputStream stream = readContentMapFromFile();
        this.rootCondition = readJsonStream(stream);

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

            //Log.d("ConditionContent", "Content Map JSON Array length = " + String.valueOf(size));
            return true;

            //JSONObject condition = obj.getJSONObject("0");

        } catch (JSONException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Condition readJsonStream(InputStream in)  {

        JsonReader reader = null;
        Condition rootCondition = null;

        try {
            reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            rootCondition = readRootCondition(reader);
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rootCondition;
    }

    public Condition readRootCondition(JsonReader reader) throws IOException {

        Condition rootCondition = null;
        reader.setLenient(true);

        if (reader.hasNext() && reader.peek() != JsonToken.END_DOCUMENT) {
            rootCondition = readCondition(reader);
        }
        return rootCondition;

    }

    public Condition readCondition(JsonReader reader) throws IOException {
        int id = 255;
        String text = null;
        boolean hasChildren = false;
        List childrenConditions = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("parent")) {
                if (reader.peek() == JsonToken.NULL)
                    reader.skipValue();
                else {
                    id = reader.nextInt();
                    //Log.d("ConditionContent", "For JSON key parent value = " + Integer.toString(id));
                }
            } else if (name.equals("text")) {
                text = reader.nextString();
                //Log.d("ConditionContent", "For JSON key text value = " + text);
            }
            else if (name.equals("hasChildren")) {
                hasChildren = reader.nextBoolean();
                //Log.d("ConditionContent", "For JSON key hasChildren value = " + hasChildren);
            }
            else if (name.equals("children") &&  reader.peek() != JsonToken.NULL) {
                childrenConditions = readChildrenArray(reader);
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Condition(id, text, childrenConditions);
    }

    public List readChildrenArray(JsonReader reader) throws IOException {

        List childrenConditions = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            //Log.d("ConditionContent", "Making recursive readCondition() call for children.");
            childrenConditions.add(readCondition(reader));
        }
        reader.endArray();

        return childrenConditions;

    }

}
