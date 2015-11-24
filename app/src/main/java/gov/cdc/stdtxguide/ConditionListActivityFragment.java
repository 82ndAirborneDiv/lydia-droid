package gov.cdc.stdtxguide;

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
public class ConditionListActivityFragment extends Fragment{
    private ConditionAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> conditionTitles;
    private ArrayList<Integer> conditionIds;
    private TextView title;
    private View divider;

    public static ConditionListActivityFragment newInstance(ArrayList<String> conditionTitles, ArrayList<Integer> conditionIds) {
        ConditionListActivityFragment conditionListActivityFragment = new ConditionListActivityFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("conditionTitles", conditionTitles);
        args.putIntegerArrayList("conditionIds", conditionIds);
        conditionListActivityFragment.setArguments(args);
        return conditionListActivityFragment;
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
        divider = (View) view.findViewById(R.id.list_title_divider);

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
            title.setVisibility(View.GONE);
           divider.setVisibility(View.GONE);
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
        public void onBindViewHolder(ConditionHolder holder, int position) {
            final Condition condition = AppManager.conditionContent.getConditionWithId(conditionIds.get(position));
            holder.conditionTitle.setText(conditionTitles.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(condition.numberOfChildren()==0){
                        startActivity(ConditionDetailsActivity.newIntent(getContext(), condition));
                     } else {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                         // Create new fragment and transaction
                        ConditionListActivityFragment newFragment =
                            ConditionListActivityFragment
                                    .newInstance(condition.getChildrenConditionTitles(),
                                            condition.getChildrenConditionIds());
                        transaction.replace(R.id.fragment_container, newFragment);
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
