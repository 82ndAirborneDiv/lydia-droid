package gov.cdc.stdtxguide;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ConditionPickFragment extends ListFragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnConditionSelectionListener mListener;

    String[] conditions = new String[] {
            "Assault - Sexual",
            "Bacterial Vaginosis",
            "Candidiasis - Vulvovaginal",
            "Cervicitis",
            "Chancroid",
            "Chlamydia",
            "Epididymitis",
            "Gonorrhea",
            "Granuloma Inguinale",
            "Hepatitis",
            "Herpes - Genital",
            "HIV",
            "Human Papilloma Virus",
            "Lymphogranuloma Venereum",
            "Pediculosis Pubis",
            "Pelvic Inflammatory Disease",
            "Proctitis/Proctocolitis/Enteritis",
            "Scabies",
            "Syphilis",
            "Trichomoniasis",
            "Urethritis",
            "Warts - Genital",

    };


    public static ConditionPickFragment newInstance(Context context) {
        ConditionPickFragment cpf = new ConditionPickFragment();
        return cpf;
    }

    public static ConditionPickFragment newInstance(int sectionNumber) {
        ConditionPickFragment fragment = new ConditionPickFragment();
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        /** Creating an array adapter to store the list of conditions **/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, conditions);

        /** Setting the list adapter for the ListFragment */
        setListAdapter(adapter);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_condition_pick, null);
        return root;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
//        Toast.makeText(this.getActivity().getApplicationContext(),
//                "Click ListItem Number " + position, Toast.LENGTH_SHORT)
//                .show();
       // Toast.makeText(this.getActivity(),
       //         "Click ListItem Number " + position, Toast.LENGTH_SHORT)
       //         .show();
        if (mListener != null) {
            mListener.onConditionSelection(position);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

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
