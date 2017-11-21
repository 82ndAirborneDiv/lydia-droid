package gov.cdc.stdtxguide;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends BaseActivity {


        public static final String WEB_VIEW_PAGE = "gov.cdc.mmwrexpress.WEB_VIEW_PAGE";

        private String mWebPage;
        private WebView mWebView;
        private String toolbarTitle = "";

        public static Intent newIntent(Context packageContext, String webPage) {

            Intent intent = new Intent(packageContext, WebViewActivity.class);
            intent.putExtra(WEB_VIEW_PAGE, webPage);
            return intent;

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_web_view);

            // navigationview setup
            setupToolbar();
            initNavigationDrawer();

            // Get the message from the intent
            Intent intent = getIntent();
            mWebPage = intent.getStringExtra(WEB_VIEW_PAGE);
            mWebView = (WebView)findViewById(R.id.webview);
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

            toolbarTitle = intent.getStringExtra("toolbarTitle");
            setActionBarTitle(toolbarTitle);

            mWebView.loadUrl("file:///android_asset/" + mWebPage);

            switch (toolbarTitle) {
                case "Help":
                    AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_HELP, Constants.SC_SECTION_HELP);
                    break;
                case "About":
                    AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_ABOUT, Constants.SC_SECTION_ABOUT);
                    break;
                case "User License Agreement":
                    AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_EULA, Constants.SC_SECTION_EULA);
                    break;
                case "Terms and Abbreviations":
                    AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_TERMS, Constants.SC_SECTION_TERMS);
                    break;
                case "Full Guidelines":
                    AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_FULL_GUIDELINES, Constants.SC_SECTION_FULL_GUIDELINES);
                    break;
                case "Taking a Sexual History":
                    AppManager.sc.trackNavigationEvent(Constants.SC_PAGE_TITLE_SEXUAL_HISTORY, Constants.SC_SECTION_SEXUAL_HISTORY);
                    break;
            }

        }

        @Override
        protected void onResume() {
            switch (toolbarTitle) {
                case "Help":
                    mNavigationView.setCheckedItem(R.id.nav_help);
                    break;
                case "About":
                    mNavigationView.setCheckedItem(R.id.nav_about_us);
                    break;
                case "User License Agreement":
                    mNavigationView.setCheckedItem(R.id.nav_license_agreement);
                    break;
                case "Terms and Abbreviations":
                    mNavigationView.setCheckedItem(R.id.nav_terms_and_abbreviations);
                    break;
                case "Full Guidelines":
                    mNavigationView.setCheckedItem(R.id.nav_full_guidelines);
                    break;
                case "Taking a Sexual History":
                    mNavigationView.setCheckedItem(R.id.nav_sexual_history);
                    break;
            }

            super.onResume();
        }

    public class WebAppInterface{
        Context context;

        WebAppInterface(Context c){
            context = c;
        }
        String version = getApplicationVersionName();

        @JavascriptInterface
        public String getVersion(){
            return version;
        }

        @JavascriptInterface
        public void getPDF(String url){
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                long enqueue = dm.enqueue(request);

        }
        @JavascriptInterface
        public void openPDF(){
            Intent intent = new Intent(getApplicationContext(), PDFActivity.class);
            intent.putExtra("pdfName", "sexualhistory.pdf");
            intent.putExtra("toolbarTitle", "Taking a Sexual History");
            startActivity(intent);

        }
    }
}
