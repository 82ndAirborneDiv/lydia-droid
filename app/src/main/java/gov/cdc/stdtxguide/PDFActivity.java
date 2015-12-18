package gov.cdc.stdtxguide;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;

@TargetApi(21)
public class PDFActivity extends BaseActivity implements View.OnClickListener{
    private String pdfName;
    private String toolbarTitle;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ImageView pageImage;
    private Button buttonPrevious;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Intent from = getIntent();
        pdfName = from.getStringExtra("pdfName");
        toolbarTitle = from.getStringExtra("toolbarTitle");
        setupToolbar();
        initNavigationDrawer();
        setActionBarTitle(toolbarTitle);

    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonNext = (Button) findViewById(R.id.btn_next);
        buttonNext.setOnClickListener(this);

        buttonPrevious = (Button) findViewById(R.id.btn_previous);
        buttonPrevious.setOnClickListener(this);

        pageImage = (ImageView) findViewById(R.id.pageImage);
        try {
            openRenderer();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("PDFActivity", "Error occurred!");
            Log.e("PDFActivity", e.getMessage());
            finish();
        }
        showPage(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigationView.setCheckedItem(R.id.nav_sexual_history);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_next){
            showPage(currentPage.getIndex() + 1);
        }
        else if (v.getId() == R.id.btn_previous){
            showPage(currentPage.getIndex() - 1);
        }
    }

    private void openRenderer() throws IOException{
        pdfRenderer = new PdfRenderer(ParcelFileDescriptor.open(AppManager.sexualHistoryPdf, ParcelFileDescriptor.MODE_READ_ONLY));
    }

    private void closeRenderer() throws IOException {
        if (null != currentPage) {
            currentPage.close();
        }
        pdfRenderer.close();
    }

    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != currentPage) {
            currentPage.close();
        }

        //open a specific page in PDF file
        currentPage = pdfRenderer.openPage(index);

        // Important: the destination bitmap must be ARGB (not RGB).
        Bitmap bitmap = Bitmap.createBitmap(4*currentPage.getWidth(), 4*currentPage.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Here, we render the page onto the Bitmap.
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // showing bitmap to an imageview
        pageImage.setImageBitmap(bitmap);
        updateUIData();
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    private void updateUIData() {
        int index = currentPage.getIndex();
        int pageCount = pdfRenderer.getPageCount();
        buttonPrevious.setEnabled(0 != index);
        if(!buttonPrevious.isEnabled()) buttonPrevious.setTextColor(Color.parseColor("#D0E4F9"));
        else buttonPrevious.setTextColor(Color.parseColor("#155eab"));
        buttonNext.setEnabled(index + 1 < pageCount);
        if(!buttonNext.isEnabled()) buttonNext.setTextColor(Color.parseColor("#D0E4F9"));
        else buttonNext.setTextColor(Color.parseColor("#155eab"));

    }
}
