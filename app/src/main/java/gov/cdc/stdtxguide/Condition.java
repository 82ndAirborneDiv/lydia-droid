package gov.cdc.stdtxguide;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Condition {

    public int id;
    public int parentId;
    public String title;
    public List<Condition> childrenConditions;

    public Condition(int id, int parentId, String title, List<Condition> children) {

        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.childrenConditions = children;
        Log.d("Condition", "Condition object created with name = " + title + " has " + String.valueOf(numberOfChildren()) + " children.");

    }

    public int numberOfChildren() {
        return childrenConditions.size();
    }

}
