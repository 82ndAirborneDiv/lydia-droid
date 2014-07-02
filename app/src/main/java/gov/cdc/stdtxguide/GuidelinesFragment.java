package gov.cdc.stdtxguide;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GuidelinesFragment extends BaseFragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_guidelines, null);
        return root;
    }

}
