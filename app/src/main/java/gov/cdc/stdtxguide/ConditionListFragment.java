package gov.cdc.stdtxguide;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConditionListFragment extends Fragment{
    private ConditionAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> conditionTitles;
    private ArrayList<Integer> conditionIds;
    private TextView title;
    private View divider;

    public static ConditionListFragment newInstance(ArrayList<String> conditionTitles, ArrayList<Integer> conditionIds) {
        ConditionListFragment conditionListFragment = new ConditionListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("conditionTitles", conditionTitles);
        args.putIntegerArrayList("conditionIds", conditionIds);
        conditionListFragment.setArguments(args);
        return conditionListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conditionTitles = getArguments().getStringArrayList("conditionTitles");
        conditionIds = getArguments().getIntegerArrayList("conditionIds");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_condition_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.condition_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        adapter = new ConditionAdapter();
        recyclerView.setAdapter(adapter);

        title = (TextView) view.findViewById(R.id.list_title);
        divider = view.findViewById(R.id.list_title_divider);

        title.setText(generatePageTitle());
        return view;
    }

    private String generatePageTitle(){
        String pageTitle = "";
        Condition condition = AppManager.conditionContent.getConditionWithId(conditionIds.get(0));
        if(condition.breadcrumbs.size() >= 1){
            pageTitle += condition.breadcrumbs.get(0);
            for(int i = 1; i < condition.breadcrumbs.size(); i++){
                pageTitle += " / " +condition.breadcrumbs.get(i);
            }
        }

        if(pageTitle.equals("")){
            AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_ALL_CONDITIONS, Constants.SC_SECTION_CONDITIONS);
            title.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }
        else {
            AppManager.sc.trackNavigationEvent(pageTitle, Constants.SC_SECTION_CONDITIONS);
        }
        return pageTitle;
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    private class ConditionAdapter extends RecyclerView.Adapter<ConditionHolder>{

        public ConditionAdapter(){
        }

        @Override
        public ConditionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.condition_item_layout, parent, false);
            return new ConditionHolder(view);
        }

        @Override
        public void onBindViewHolder(final ConditionHolder holder, int position) {
            final Condition condition = AppManager.conditionContent.getConditionWithId(conditionIds.get(position));
            holder.conditionTitle.setText(conditionTitles.get(position));
            final String transitionName = getString(R.string.title_transition);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    if(condition.numberOfChildren()==0){
                        Intent intent = ConditionDetailsActivity.newIntent(getContext(), condition);
                        ActivityCompat.startActivity(getActivity(), intent, null);
                     } else {


                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                         // Create new fragment and transaction
                        ConditionListFragment newFragment =
                            ConditionListFragment
                                    .newInstance(condition.getChildrenConditionTitles(),
                                            condition.getChildrenConditionIds());

                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack("Condition Pick Fragment");
                        transaction.commit();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return conditionTitles.size();
        }
    }

    private class ConditionHolder extends RecyclerView.ViewHolder {
        public TextView conditionTitle;

        public ConditionHolder(View itemView) {
            super(itemView);
            conditionTitle = (TextView) itemView.findViewById(R.id.condition_item_title);
        }
    }
}
