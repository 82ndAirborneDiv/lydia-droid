package gov.cdc.stdtxguide;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Condition {

    private int parent;
    private String title;
    private List childrenConditions;

    public Condition(int parent, String title, List<Condition> children) {

        this.parent = parent;
        this.title = title;
        childrenConditions = children;
        Log.d("Condition", "Condition object created with name = " + title + " has " + String.valueOf(numberOfChildren()) + " children.");

    }

    public int numberOfChildren() {
        return childrenConditions.size();
    }


}
