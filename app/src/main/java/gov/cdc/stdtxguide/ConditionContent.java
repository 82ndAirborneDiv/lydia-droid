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
import java.util.Hashtable;
import java.util.List;

public class ConditionContent {

    public static final String CONTENT_MAP_FILENAME = "content/condition-content-map.txt";
    private Context context;
    private Condition rootCondition;
    private Condition currCondition;
    private List<Condition> allConditions;

    public ConditionContent(Context context) {

        this.context = context;
        this.allConditions = new ArrayList<Condition>();

        InputStream stream = readContentMapFromFile();
        this.rootCondition = readJsonStream(stream);

        dumpIndex();

        currCondition = rootCondition;

    }

    public void resetCurrentCondition() {
        currCondition = rootCondition;
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

    public Condition readJsonStream(InputStream in) {

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
        int parentId = 0;

        String text = null;
        String regimensPage = null;
        String dxtxPage = null;

        boolean hasChildren = false;
        List childrenConditions = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
                Log.d("ConditionContent", "For JSON key id value = " + Integer.toString(id));
            } else if (name.equals("parent")) {
                if (reader.peek() == JsonToken.NULL)
                    reader.skipValue();
                else {
                    id = reader.nextInt();
                    Log.d("ConditionContent", "For JSON key parent value = " + Integer.toString(id));
                }
            } else if (name.equals("text")) {
                text = reader.nextString();
                Log.d("ConditionContent", "For JSON key text value = " + text);
            } else if (name.equals("regimensPage")) {
                regimensPage = reader.nextString();
                Log.d("ConditionContent", "For JSON key regimensPage value = " + regimensPage);
            } else if (name.equals("dxtxPage")) {
                dxtxPage = reader.nextString();
                Log.d("ConditionContent", "For JSON key dxtxPage value = " + dxtxPage);
            } else if (name.equals("hasChildren")) {
                hasChildren = reader.nextBoolean();
                Log.d("ConditionContent", "For JSON key hasChildren value = " + hasChildren);
            } else if (name.equals("children") && reader.peek() != JsonToken.NULL) {
                childrenConditions = readChildrenArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        Condition newCondition = new Condition(id, parentId, text, regimensPage, dxtxPage, childrenConditions);
        addConditionToIndex(newCondition);
        return newCondition;
    }

    public List readChildrenArray(JsonReader reader) throws IOException {

        List childrenConditions = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {

            Log.d("ConditionContent", "Making recursive readCondition() call for children.");
            childrenConditions.add(readCondition(reader));
        }
        reader.endArray();

        return childrenConditions;

    }


    private void addConditionToIndex(Condition newCondition) {
        this.allConditions.add(newCondition);

    }

    public void dumpIndex() {

        int i;
        Condition c;
        for (i = 0; i < this.allConditions.size(); i++) {
            c = this.allConditions.get(i);
            Log.d("ConditionContent", "Condition id = " + Integer.toString(c.id) + " is at index = " + Integer.toString(i));
        }
    }

    public ArrayList<String> getChildContentTitles() {

        ArrayList<String> titles = new ArrayList<String>();

        for (Condition aCondition:this.currCondition.childrenConditions) {
            titles.add(aCondition.title);

        }
        return titles;
    }

    public ArrayList<Integer> getChildContentIds() {

        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (Condition aCondition:this.currCondition.childrenConditions) {
            ids.add(aCondition.id);

        }
        return ids;
    }

    public Condition getConditionWithId(int id) {

        // iterate through list of conditions and re
        // return condition with input id
        for (Condition c:this.allConditions) {
            if (c.id == id )
                return c;
        }
        return null;
    }

    public void setCurrentCondition(int id)  {
        this.currCondition = getConditionWithId(id);
    }

    public Condition getCurrCondition() {
        return currCondition;
    }

}
