package com.example.pennywise;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import es.voghdev.pdfviewpager.library.PDFViewPager;
import com.example.pennywise.Tools.Constraints;

public class PDFViewer extends AppCompatActivity {

    private PDFViewPager pdfViewer; // Declare pdfViewer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        pdfViewer = findViewById(R.id.pdfViewPager);
        pdfViewer = new PDFViewPager(PDFViewer.this, Constraints.path);
    }
}
