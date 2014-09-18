package gov.cdc.stdtxguide;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EulaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EulaFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EulaFragment extends Fragment {
    private static final String LOG_TAG = "EulaFragment";


    // TODO: Rename and change types of parameters
    private WebView wv;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param section Parameter 1.
     * @return A new instance of fragment AboutUsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EulaFragment newInstance(int section) {
        EulaFragment fragment = new EulaFragment();
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARG_SECTION_NUMBER, section);
        fragment.setArguments(args);
        return fragment;
    }


    public EulaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_eula, container, false);

        this.wv = (WebView) v.findViewById(R.id.eulaWebView);
        wv.getSettings().setJavaScriptEnabled(false);
        wv.loadUrl("file:///android_asset/eula.html");

        return v;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(BaseFragment.ARG_SECTION_NUMBER));
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}

