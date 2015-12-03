package gov.cdc.stdtxguide;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**BaseActivity.java
 * lydia-droid
 *
 * Copyright (c) 2015 Informatics Research and Development Lab. All rights reserved.
 */

public class BaseActivity extends AppCompatActivity {

    protected ActionBarDrawerToggle mDrawerToggle;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;



    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    protected void initNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupActionBarDrawerToogle();
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }
    }

    /**
     * In case if you require to handle drawer open and close states
     */
    protected void setupActionBarDrawerToogle() {

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {

            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    protected void setupDrawerContent(final NavigationView navigationView) {
        //setting up selected item listener
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (menuItem.isChecked()) mDrawerLayout.closeDrawers();
                        else {
                            Intent intent = null;

                            /*TODO uncomment and implement nav drawer items
                            if (menuItem.getItemId() == R.id.nav_condition_quick_pick) {
                                Intent intent = new Intent(getApplicationContext(), ConditionListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }*/
                            if (menuItem.getItemId() == R.id.nav_condition_quick_pick) {
                                intent = new Intent(getApplicationContext(), ConditionListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                            if (menuItem.getItemId() == R.id.nav_terms_and_abbreviations) {
                                intent = WebViewActivity.newIntent(getApplicationContext(), "terms.html");
                                intent.putExtra("toolbarTitle", "Terms and Abbreviations");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                            if (menuItem.getItemId() == R.id.nav_full_guidelines) {
                                intent = WebViewActivity.newIntent(getApplicationContext(), "full.html");
                                intent.putExtra("toolbarTitle", "Full Guidelines");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                            if (menuItem.getItemId() == R.id.nav_sexual_history) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    intent = new Intent(getApplicationContext(), PDFActivity.class);
                                    intent.putExtra("pdfName", "sexualhistory.pdf");
                                    intent.putExtra("toolbarTitle", "Taking a Sexual History");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                } else {
                                    intent = WebViewActivity.newIntent(getApplicationContext(), "sexualhistory.html");
                                    intent.putExtra("toolbarTitle", "Taking a Sexual History");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }
                            }
                            if (menuItem.getItemId() == R.id.nav_about_us) {
                                intent = WebViewActivity.newIntent(getApplicationContext(), "about_us.html");
                                intent.putExtra("toolbarTitle", "About");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                            if (menuItem.getItemId() == R.id.nav_license_agreement) {
                                intent = WebViewActivity.newIntent(getApplicationContext(), "eula.html");
                                intent.putExtra("toolbarTitle", "User License Agreement");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                            if (menuItem.getItemId() == R.id.nav_settings) {
                                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                            if (intent != null) startActivity(intent);
                            mDrawerLayout.closeDrawers();
                        }
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO Implement menu if needed
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    public int getApplicationVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){}
        return 0;
    }

    public String getApplicationVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){}
        return "";
    }

    protected void setActionBarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

}