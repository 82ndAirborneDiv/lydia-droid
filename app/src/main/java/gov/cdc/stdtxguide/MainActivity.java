package gov.cdc.stdtxguide;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ConditionPickFragment.OnConditionSelectionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private BaseFragment activeFragment;
    private static int lastPosition;
    private ConditionContent conditionContent;
    private static final String LOG_TAG = "MainActivity";
    private int fragCount;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.conditionContent = new ConditionContent(this.getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        fragCount = 0;


    }

    @Override
    public void onNavigationDrawerItemSelected(int section) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        BaseFragment targetFragment = null;


        // populate the fragment
        switch (section) {
            case 0: {
                conditionContent.resetCurrentCondition();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ConditionPickFragment.newInstance(section +1, conditionContent.getCurrentConditionId(), conditionContent.getChildContentTitles(), conditionContent.getChildContentIds()))
                        .commit();
                break;
            }
            case 1: {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TermsFragment.newInstance(section +1))
                        .commit();
                break;
            }
            case 2: {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GuidelinesFragment.newInstance(section +1))
                        .commit();
                break;

            }
            case 3: {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HistoryFragment.newInstance(section +1))

                        .commit();
                break;
            }
            case 4: {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ReferencesFragment.newInstance(section + 1))
                        .commit();
                break;
            }
            case 5: {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AboutUsFragment.newInstance(section + 1))
                        .commit();
                break;
            }
            case 6: {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, EulaFragment.newInstance(section + 1))
                        .commit();
                break;
            }
        }
    }

    public void onSectionAttached(int sectionNumber) {
        switch (sectionNumber) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
        }
        Log.d(LOG_TAG, "onSectionAttached received from "+ mTitle);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // turn on the Navigation Drawer image;
        // this is called in the LowerLevelFragments
        if (fragCount != 0)
            fragCount--;

        if(fragCount == 0)
            mNavigationDrawerFragment.showNavActionBar();
        else
            mNavigationDrawerFragment.showUpCaratActionBar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.condition, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onConditionSelection(int conditionId) {

        Fragment newFragment = null;

        conditionContent.setCurrentCondition(conditionId);
        Condition condition = conditionContent.getCurrCondition();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Create new fragment and transaction
        if (condition.numberOfChildren() != 0) {

            if (condition.isRootCondition())
                mNavigationDrawerFragment.showNavActionBar();
            else
                mNavigationDrawerFragment.showUpCaratActionBar();


            newFragment = ConditionPickFragment.newInstance(condition.id, conditionContent.getChildContentTitles(), conditionContent.getChildContentIds());
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack("Condition Pick Fragment");


        } else {

            mNavigationDrawerFragment.showUpCaratActionBar();
            newFragment = ConditionTreatment.newInstance(condition.regimensPage, condition.dxtxPage);
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack("Condition Treatment Fragment");



        }

        // Commit the transaction
        transaction.commit();
        fragCount++;



    }
}