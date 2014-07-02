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
        Toast.makeText(this.getActivity(),
                "Click ListItem Number " + position, Toast.LENGTH_SHORT)
                .show();
        // Create new fragment and transaction
        Fragment newFragment = new ConditionTreatment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

    }

    public interface OnConditionSelectionListener {
        // TODO: Update argument type and name
        public void onConditionSelection(Uri uri);
    }



}
