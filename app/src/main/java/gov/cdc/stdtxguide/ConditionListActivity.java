package gov.cdc.stdtxguide;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;

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
    protected void onStart() {
        super.onStart();
        if(!AppManager.pref.getBoolean(STDTxGuidePreferences.DISPLAY_NAV_DRAWER_ON_FIRST_LAUNCH, false)){
            mDrawerLayout.openDrawer(Gravity.START);
            AppManager.editor.putBoolean(STDTxGuidePreferences.DISPLAY_NAV_DRAWER_ON_FIRST_LAUNCH, true).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigationView.setCheckedItem(R.id.nav_condition_quick_pick);
    }

    private void addConditionListFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ConditionListFragment fragment =
                ConditionListFragment
                        .newInstance(AppManager.conditionContent.getChildContentTitles(),
                                AppManager.conditionContent.getChildContentIds());
        transaction.replace(R.id.fragment_container, fragment, "Condition Pick Fragment");
        transaction.commit();
    }
}
