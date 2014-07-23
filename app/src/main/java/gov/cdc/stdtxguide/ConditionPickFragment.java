package gov.cdc.stdtxguide;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConditionPickFragment extends ListFragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String LOG_TAG = "ConditionPickFragment";
    private OnConditionSelectionListener mListener;
    private int currSectionNumber;
    private int currConditionId;
    private ArrayList<String> conditionTitles;
    private ArrayList<Integer> conditionIds;

//    String[] conditions = new String[] {
//            "Assault - Sexual",
//            "Bacterial Vaginosis",
//            "Candidiasis - Vulvovaginal",
//            "Cervicitis",
//            "Chancroid",
//            "Chlamydia",
//            "Epididymitis",
//            "Gonorrhea",
//            "Granuloma Inguinale",
//            "Hepatitis",
//            "Herpes - Genital",
//            "HIV",
//            "Human Papilloma Virus",
//            "Lymphogranuloma Venereum",
//            "Pediculosis Pubis",
//            "Pelvic Inflammatory Disease",
//            "Proctitis/Proctocolitis/Enteritis",
//            "Scabies",
//            "Syphilis",
//            "Trichomoniasis",
//            "Urethritis",
//            "Warts - Genital",
//
//    };


    public static ConditionPickFragment newInstance(Context context) {
        ConditionPickFragment cpf = new ConditionPickFragment();
        return cpf;
    }


    public static ConditionPickFragment newInstance(ArrayList<String> conditions, ArrayList<Integer> ids) {

        ConditionPickFragment fragment = new ConditionPickFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(BaseFragment.ARG_CONDITION_CONTENT, conditions);
        args.putIntegerArrayList(BaseFragment.ARG_CONDITION_IDS, ids);
        fragment.setArguments(args);

        return fragment;
    }

    public static ConditionPickFragment newInstance(int position) {

        ConditionPickFragment fragment = new ConditionPickFragment();
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);

        return fragment;
    }

    public static ConditionPickFragment newInstance(int position, ArrayList<String> conditions, ArrayList<Integer> ids) {

        ConditionPickFragment fragment = new ConditionPickFragment();
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARG_SECTION_NUMBER, position);
        args.putStringArrayList(BaseFragment.ARG_CONDITION_CONTENT, conditions);
        args.putIntegerArrayList(BaseFragment.ARG_CONDITION_IDS, ids);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        /** Creating an array adapter to store the list of conditions **/
        ArrayAdapter<String> adapter;

        conditionIds = getArguments().getIntegerArrayList(BaseFragment.ARG_CONDITION_IDS);
        Log.d(LOG_TAG, "In onCreateView loading condition IDs with count of " + conditionIds.size());

        conditionTitles = getArguments().getStringArrayList(BaseFragment.ARG_CONDITION_CONTENT);
        Log.d(LOG_TAG, "In onCreateView loading conditions with count of " + conditionTitles.size());

        adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, conditionTitles);

        /** Setting the list adapter for the ListFragment */
        setListAdapter(adapter);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_condition_pick, null);
        return root;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(LOG_TAG,"ListView item at position " + Integer.toString(position) + " has been selected.");
        Log.d(LOG_TAG,"Selected condition has title " + conditionTitles.get(position));
        int conditionId = conditionIds.get(position);
        Log.d(LOG_TAG,"Selected condition has id " + conditionIds.get(position));

        if (mListener != null)
            mListener.onConditionSelection(conditionId);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(BaseFragment.ARG_SECTION_NUMBER));

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnConditionSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnConditionSelectionListener");
        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnConditionSelectionListener {
        // TODO: Update argument type and name
        public void onConditionSelection(int position);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onConditionSelection(position);
        }
    }

}
