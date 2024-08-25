package com.example.pennywise.Fragments;

import android.database.Cursor;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennywise.Adapters.ParentMonthlyAdapter;
import com.example.pennywise.Models.IncomeModel;
import com.example.pennywise.Models.MonthlyChildModel;
import com.example.pennywise.Models.MonthlyParentModel;
import com.example.pennywise.R;
import com.example.pennywise.Tools.DBHelper;
import com.example.pennywise.Tools.Constraints;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MonthlyFragment extends Fragment {
    ImageView back,forward;
    int numberMonth,numberYear;
    TextView date;
    TextView credit,debit,balance;
    RecyclerView parentRecyclerView;
    FloatingActionButton pdfButton;
    ParentMonthlyAdapter parentmonthlyAdapter;
    List<MonthlyParentModel> parentList=new ArrayList<>();
    List<MonthlyChildModel> childList=new ArrayList<>();
    DBHelper dbHelper;
    List<IncomeModel> listIncome=new ArrayList<>();
    List<IncomeModel> listExpense=new ArrayList<>();
    final String[] Selected = new String[]{"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonthlyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthlyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyFragment newInstance(String param1, String param2) {
        MonthlyFragment fragment = new MonthlyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_monthly, container, false);
        back=v.findViewById(R.id.imageView6);
        forward=v.findViewById(R.id.imageView7);
        date=v.findViewById(R.id.textView7);
        pdfButton=v.findViewById(R.id.action_pdf);
        credit=v.findViewById(R.id.txt_credit);
        debit=v.findViewById(R.id.txt_debit);
        balance=v.findViewById(R.id.txt_balance);
        parentRecyclerView=v.findViewById(R.id.parentMonthlyRecyclerView);

        final Calendar c = Calendar.getInstance();
        numberMonth = c.get(Calendar.MONTH);
        numberYear=c.get(Calendar.YEAR);
        setDate();

        setListeners();




        return v;
    }

    private void getTransactionsCount(int numberMonth,int numberYear) {
        parentList.clear();
        childList.clear();
        dbHelper=new DBHelper(getContext());
        Cursor cursor=dbHelper.countTransactions(String.valueOf(numberMonth),String.valueOf(numberYear));
        int sum=0;
        while (cursor.moveToNext()){
            childList= fillUpChildList(cursor.getString(0));

            MonthlyParentModel model=new MonthlyParentModel(cursor.getString(0),childList);
            parentList.add(model);

        }

        cursor.close();
        parentRecyclerView.setHasFixedSize(true);
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        parentmonthlyAdapter=new ParentMonthlyAdapter(parentList,getContext());
        parentRecyclerView.setAdapter(parentmonthlyAdapter);


    }

    private List<MonthlyChildModel> fillUpChildList(String date) {
        List<MonthlyChildModel> listIncome=new ArrayList<>();
        List<MonthlyChildModel> listExpense=new ArrayList<>();
        List<MonthlyChildModel> finalList=new ArrayList<>();

        Cursor cursor=dbHelper.getIncomeTransaction(date,"Income");
        float sumincome=0;

        while (cursor.moveToNext()){
            sumincome+=Float.parseFloat(cursor.getString(5));
            MonthlyChildModel model=new MonthlyChildModel(cursor.getString(6),"",cursor.getString(5),"",String.valueOf(sumincome),"","");
            listIncome.add(model);
        }
        cursor.close();

        Cursor cursor2=dbHelper.getIncomeTransaction(date,"Expense");
        float sumexpense=0;

        while (cursor2.moveToNext()){
            sumexpense+=Float.parseFloat(cursor2.getString(5));
            MonthlyChildModel model=new MonthlyChildModel("",cursor2.getString(6),"",cursor2.getString(5),"",String.valueOf(sumexpense),"");
            listExpense.add(model);
        }
        cursor2.close();
        listIncome.addAll(listExpense);



        return listIncome;


    }

    private void setListeners() {
        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdf();
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberMonth==0){
                    numberMonth=11;
                    numberYear--;
                }else{
                    numberMonth--;
                }
                setDate();
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberMonth==11){
                    numberMonth=0;
                    numberYear++;
                }else{
                    numberMonth++;
                }
                setDate();

            }
        });
    }

    private List<String[]> getSampleData() {


        int count = 20;


        List<String[]> temp = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            temp.add(new String[] {"C1-R"+ (i+1),"C2-R"+ (i+1)});
        }
        return  temp;


    }

    private void setDate() {
        final String[] Selected = new String[]{"January", "February", "March", "April",
                "May", "June", "July", "Augest", "September", "October", "November", "December"};
        date.setText(Selected[numberMonth]+" "+numberYear);
        getIncomeData(numberMonth,numberYear,"Income");
        getExpenseData(numberMonth,numberYear,"Expense");
        getTransactionsCount(numberMonth,numberYear);
        float cred=Float.parseFloat(credit.getText().toString());
        float deb=Float.parseFloat(debit.getText().toString());
        float sum=cred-deb;
        balance.setText(""+sum);

    }

    private void getExpenseData(int numberMonth, int numberYear, String expense) {
        listExpense.clear();
        dbHelper=new DBHelper(getContext());
        Cursor cursor=dbHelper.getIncomeMonthly(String.valueOf(numberMonth),String.valueOf(numberYear),expense);
        float sum=0;
        while (cursor.moveToNext()){
            IncomeModel model=new IncomeModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
            listIncome.add(model);
            sum+=Float.parseFloat(cursor.getString(5));
        }
        cursor.close();
        debit.setText(""+sum);

    }

    private void getIncomeData(int numberMonth, int numberYear, String income) {
        listIncome.clear();

        dbHelper=new DBHelper(getContext());
        Cursor cursor=dbHelper.getIncomeMonthly(String.valueOf(numberMonth),String.valueOf(numberYear),income);
        float sum=0;
        while (cursor.moveToNext())
        {
            IncomeModel model=new IncomeModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
            listIncome.add(model);
            sum+=Float.parseFloat(cursor.getString(5));
        }
        cursor.close();
        credit.setText(""+sum);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            setDate();

        }
    }
    private void createPdf() throws IOException {
        // Create the necessary fonts
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        Color blackColor = new DeviceRgb(0, 0, 0);

        // Create the output file
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pdfs");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            Log.i("MonthlyFragment", "Pdf Directory created");
        }

        // Create time stamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File myFile = new File(pdfFolder, timeStamp + ".pdf");
        OutputStream output = new FileOutputStream(myFile);

        // Step 1: Initialize PDF Writer and Document
        PdfWriter writer = new PdfWriter(output);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.LETTER);
        document.setMargins(36, 36, 36, 36);
        document.add(new Paragraph("Monthly Report").setFont(boldFont).setFontSize(16));

        // DecimalFormat for currency formatting
        DecimalFormat df = new DecimalFormat("0.00");

        // Specify column widths
        float[] columnWidths = {1.5f, 2f, 3f};
        // Create PDF table with the given widths
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(90));

        // Insert content into the table
        for (int i = 0; i < parentList.size(); i++) {
            String s1 = parentList.get(i).getParentdate();
            String[] words = s1.split("\\s");
            float expensesum = 0;
            float incomesum = 0;

            insertCell(table, words[0] + " " + Selected[Integer.parseInt(words[1])] + " " + words[2], TextAlignment.LEFT, 4, boldFont);
            insertCell(table, "Title", TextAlignment.CENTER, 1, boldFont);
            insertCell(table, "Type", TextAlignment.CENTER, 1, boldFont);
            insertCell(table, "Transaction Amount", TextAlignment.CENTER, 1, boldFont);
            table.setMarginTop(10f);

            for (int j = 0; j < parentList.get(i).getChildModelList().size(); j++) {
                MonthlyChildModel child = parentList.get(i).getChildModelList().get(j);
                if (child.getIncomeTitle().isEmpty()) {
                    insertCell(table, child.getExpenseTitle(), TextAlignment.LEFT, 1, regularFont);
                    insertCell(table, "Expense", TextAlignment.LEFT, 1, regularFont);
                    expensesum += Float.parseFloat(child.getExpenseAmount());
                    insertCell(table, child.getExpenseAmount(), TextAlignment.LEFT, 1, regularFont);
                } else {
                    insertCell(table, child.getIncomeTitle(), TextAlignment.LEFT, 1, regularFont);
                    insertCell(table, "Income", TextAlignment.LEFT, 1, regularFont);
                    incomesum += Float.parseFloat(child.getIncomeAmount());
                    insertCell(table, child.getIncomeAmount(), TextAlignment.LEFT, 1, regularFont);
                }
            }

            insertCell(table, "Total Income: " + df.format(incomesum), TextAlignment.RIGHT, 3, boldFont);
            insertCell(table, "Total Expense: " + df.format(expensesum), TextAlignment.RIGHT, 3, boldFont);
            float sum = incomesum - expensesum;
            insertCell(table, "Balance: " + df.format(sum), TextAlignment.RIGHT, 3, boldFont);
        }

        // Add the table to the document
        document.add(table);

        // Add creation date
        String timeStampNew = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        document.add(new Paragraph("Created: " + timeStampNew).setFont(regularFont).setFontSize(10));

        // Step 5: Close the document
        document.close();

        Toast.makeText(getContext(), "Document Saved to Internal Storage/Documents/" + timeStamp + ".pdf", Toast.LENGTH_SHORT).show();
    }

    private void insertCell(Table table, String text, TextAlignment align, int colspan, PdfFont font) {
        Cell cell = new Cell(1, colspan)
                .add(new Paragraph(text.trim()).setFont(font))
                .setTextAlignment(align)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addCell(cell);
    }

}