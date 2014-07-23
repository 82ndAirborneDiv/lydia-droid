package gov.cdc.stdtxguide;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Condition {

    public int id;
    public int parentId;
    public String title;
    public String regimensPage;
    public String dxtxPage;
    public List<Condition> childrenConditions;

    public Condition(int id, int parentId, String title, String regimensPage, String dxtxPage, List<Condition> children) {

        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.childrenConditions = children;
        this.regimensPage = regimensPage;
        this.dxtxPage = dxtxPage;
        Log.d("Condition", "Condition object created with name = " + title + " has " + String.valueOf(numberOfChildren()) + " children.");

    }

    public int numberOfChildren() {
        return childrenConditions.size();
    }

}
