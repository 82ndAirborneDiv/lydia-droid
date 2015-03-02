package gov.cdc.stdtxguide;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class GuidelinesFragment extends BaseFragment {

    private WebView wv;

    public static GuidelinesFragment newInstance(Context context) {
        GuidelinesFragment gf = new GuidelinesFragment();
        return gf;
    }

    public static GuidelinesFragment newInstance(int sectionNumber) {
        GuidelinesFragment fragment = new GuidelinesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_guidelines, container, false);

        this.wv = (WebView) v.findViewById(R.id.guidelinesWebView);
        wv.getSettings().setJavaScriptEnabled(false);
        wv.loadUrl("file:///android_asset/full.html");

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

    }

}
