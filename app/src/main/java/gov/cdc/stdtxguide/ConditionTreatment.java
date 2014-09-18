package gov.cdc.stdtxguide;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConditionTreatment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConditionTreatment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ConditionTreatment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REGIMENS_PAGE = "regimens_page";
    private static final String ARG_DXTX_PAGE = "dxtx_page";
    private static final String LOG_TAG = "ConditionTreatment";


    private String regimensPage;
    private String dxtxPage;
    private WebView wv;
    private Button regimensButton;
    private Button dxtxButton;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param regimens Regimens HTML file name.
     * @param dxtx Description and treatment HTML file name.
     * @return A new instance of fragment ConditionTreatment.
     */
    public static ConditionTreatment newInstance(int section, String regimens, String dxtx) {
        ConditionTreatment fragment = new ConditionTreatment();
        Bundle args = new Bundle();
        args.putInt(BaseFragment.ARG_SECTION_NUMBER, section);
        args.putString(ARG_REGIMENS_PAGE, regimens);
        args.putString(ARG_DXTX_PAGE, dxtx);
        fragment.setArguments(args);
        return fragment;
    }


    public static ConditionTreatment newInstance(String regimens, String dxtx) {
        ConditionTreatment fragment = new ConditionTreatment();
        Bundle args = new Bundle();
        args.putString(ARG_REGIMENS_PAGE, regimens);
        args.putString(ARG_DXTX_PAGE, dxtx);
        fragment.setArguments(args);
        return fragment;
    }


    public ConditionTreatment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            regimensPage = getArguments().getString(ARG_REGIMENS_PAGE);
            dxtxPage = getArguments().getString(ARG_DXTX_PAGE);
        }

        // like to add items to the Options Menu
        setHasOptionsMenu(true);
        // update the actionbar to show the up carat
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "--> got onResume.");

    }

    public void selectRegimensButton() {
        regimensButton.setBackgroundColor(Color.BLUE);
        regimensButton.setTextColor(Color.WHITE);
        dxtxButton.setBackgroundColor(Color.GRAY);
        dxtxButton.setTextColor(Color.BLACK);

    }

    public void selectDxTxButton() {
        dxtxButton.setBackgroundColor(Color.BLUE);
        dxtxButton.setTextColor(Color.WHITE);
        regimensButton.setBackgroundColor(Color.GRAY);
        regimensButton.setTextColor(Color.BLACK);

    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.regimensButton: {
                if (regimensPage != "") {
                    this.wv.loadUrl("file:///android_asset/content/" + regimensPage);
                    selectRegimensButton();
                }

                break;
            }

            case R.id.dxtxButton: {
                if (dxtxPage != "") {
                    this.wv.loadUrl("file:///android_asset/content/" + dxtxPage);
                    selectDxTxButton();
                }
                break;
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(BaseFragment.ARG_SECTION_NUMBER));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_condition_treatment, container, false);

        // set up buttons to switch between regimens and dxtx
        regimensButton = (Button)v.findViewById(R.id.regimensButton);
        regimensButton.setOnClickListener(this);
        if (regimensPage.equals(""))
            regimensButton.setEnabled(false);

        dxtxButton = (Button)v.findViewById(R.id.dxtxButton);
        dxtxButton.setOnClickListener(this);
        if (dxtxPage.equals(""))
            dxtxButton.setEnabled(false);

        // set up web view
        this.wv = (WebView) v.findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(false);
        if (regimensPage.equals("")) {
            wv.loadUrl("file:///android_asset/content/" + dxtxPage);
            selectDxTxButton();
        } else {
            wv.loadUrl("file:///android_asset/content/" + regimensPage);
            selectRegimensButton();
        }

        return v;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        switch (item.getItemId()) {
            case android.R.id.home:
                //called when the up carat in actionbar is pressed
                getActivity().onBackPressed();
                return true;
        }
        return false;
    }

}
