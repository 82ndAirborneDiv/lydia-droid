package gov.cdc.stdtxguide;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ConditionListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setupToolbar();
        initNavigationDrawer();
        addConditionListFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigationView.setCheckedItem(R.id.nav_condition_quick_pick);
    }

    private void addConditionListFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ConditionListActivityFragment fragment =
                ConditionListActivityFragment
                        .newInstance(AppManager.conditionContent.getChildContentTitles(),
                                AppManager.conditionContent.getChildContentIds());
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
