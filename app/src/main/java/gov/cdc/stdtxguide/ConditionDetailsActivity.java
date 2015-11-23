package gov.cdc.stdtxguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;


public class ConditionDetailsActivity extends BaseActivity {
    private String regimensPage;
    private String dxtxPage;
    private ViewPager viewPager;

    public static Intent newIntent(Context packageContext, Condition condition){
        Intent intent = new Intent(packageContext, ConditionDetailsActivity.class);
        intent.putExtra("id", condition.id);
        intent.putExtra("parentId", condition.parentId);
        intent.putExtra("title", condition.title);
        intent.putExtra("regimensPage", condition.regimensPage);
        intent.putExtra("dxtxPage", condition.dxtxPage);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_details);
        setupToolbar();
        initNavigationDrawer();
        regimensPage = getIntent().getStringExtra("regimensPage");
        dxtxPage = getIntent().getStringExtra("dxtxPage");

        viewPager = (ViewPager) findViewById(R.id.pager);
        ContentPagerAdapter adapter = new ContentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_host);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }



    private class ContentPagerAdapter extends FragmentStatePagerAdapter{
        private String [] tabTitles = {"Treatments", "More Info"};

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
            if(dxtxPage.equals("")){
                tabTitles = new String [] {"Treatments"};
            }
            if(regimensPage.equals("")){
                tabTitles = new String[] {"More Info"};
            }
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if(tabTitles.length == 1){
                if(tabTitles[0].equals("Treatments")){
                    return new ConditionDetailsFragment().newInstance(regimensPage);
                }
                else if(tabTitles[0].equals("More Info")){
                    return new ConditionDetailsFragment().newInstance(dxtxPage);
                }
            } else {
                if(position == 0){
                    return new ConditionDetailsFragment().newInstance(regimensPage);
                }
                else
                    return new ConditionDetailsFragment().newInstance(dxtxPage);
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}
