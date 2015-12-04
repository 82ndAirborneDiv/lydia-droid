package gov.cdc.stdtxguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class EulaActivity extends AppCompatActivity {
    private Button mAgreeButton;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);

        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.loadUrl("file:///android_asset/eula.html");

        mAgreeButton = (Button) findViewById(R.id.btnAgree);
        mAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.editor.putBoolean(STDTxGuidePreferences.AGREED_TO_EULA, true).commit();

                //Track event - Accepted EULA
                AppManager.sc.trackEvent(Constants.SC_EVENT_AGREE_TO_EULA, Constants.SC_PAGE_TITLE_EULA, Constants.SC_SECTION_EULA);

                Intent intent = new Intent(getApplicationContext(), ConditionListActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        //Track navigation event - EULA Page Launched
        AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_EULA, Constants.SC_SECTION_EULA);
    }

}
