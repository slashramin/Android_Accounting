package moradi.ramin;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class HesabdariActivity extends Activity {

    TextView        textViewHeaderTitle;
    TextView        textViewMabnayeTarikhiNemodar;
    TextView        textViewNoeNemodar;
    TextView        textViewMablagh;
    TextView        textViewAdadiMablagh;

    Button          buttonHazine;
    Button          buttonDaramad;

    PieChart        pieChart1;

    Date            tarikheMohasebe;
    String          Kind = "D";

    ImageButton     imageButtonBadi;
    ImageButton     imageButtonGhabli;

    TextView        textViewAdameSabteTarakonesh;

    DatabaseManager db;

    ImageButton     imageButtonSetting;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new DatabaseManager(this);

        tarikheMohasebe = new Date();

        /************************************************************ اتصال ویوها ****/
        textViewHeaderTitle = (TextView) findViewById(R.id.textViewHeaderTitle);
        textViewMabnayeTarikhiNemodar = (TextView) findViewById(R.id.textViewMabnayeTarikhiNemodar);
        textViewNoeNemodar = (TextView) findViewById(R.id.textViewNoeNemodar);
        textViewMablagh = (TextView) findViewById(R.id.textViewMablagh);
        textViewAdadiMablagh = (TextView) findViewById(R.id.textViewAdadiMablagh);

        buttonHazine = (Button) findViewById(R.id.ButtonHazine);
        buttonDaramad = (Button) findViewById(R.id.buttonDaramad);

        pieChart1 = (PieChart) findViewById(R.id.pieChart1);

        imageButtonBadi = (ImageButton) findViewById(R.id.imageButtonBadi);
        imageButtonGhabli = (ImageButton) findViewById(R.id.imageButtonGhabli);

        textViewAdameSabteTarakonesh = (TextView) findViewById(R.id.textViewAdameSabteTarakonesh);

        imageButtonSetting = (ImageButton) findViewById(R.id.imageButtonSetting);
        /************************************************************ اختصاص فونت به ویوها ****/
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ANegaarBold.ttf");

        textViewHeaderTitle.setTypeface(tf);
        textViewNoeNemodar.setTypeface(tf);
        textViewMablagh.setTypeface(tf);
        textViewAdadiMablagh.setTypeface(tf);

        buttonHazine.setTypeface(tf);
        buttonDaramad.setTypeface(tf);

        tf = Typeface.createFromAsset(getAssets(), "fonts/BZARBD.TTF");
        textViewMabnayeTarikhiNemodar.setTypeface(tf);

        /*********************************************************** باز کردن اکتیویتی ثبت درآمد ****/
        buttonDaramad.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(HesabdariActivity.this, DaramadHazineActivity.class);
                intent.putExtra("Kind", "D");
                startActivityForResult(intent, 3);
            }
        });

        buttonHazine.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(HesabdariActivity.this, DaramadHazineActivity.class);
                intent.putExtra("Kind", "H");
                startActivityForResult(intent, 3);
            }
        });

        /*****************************************************  ***/
        NemayeshEtelaateHazineDaramad();

        imageButtonGhabli.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Calendar c = new GregorianCalendar();
                c.setTime(tarikheMohasebe);

                c.add(Calendar.DATE, -1);

                tarikheMohasebe = c.getTime();

                NemayeshEtelaateHazineDaramad();
            }
        });

        imageButtonBadi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar c = new GregorianCalendar();
                c.setTime(tarikheMohasebe);

                c.add(Calendar.DATE, 1);

                tarikheMohasebe = c.getTime();

                NemayeshEtelaateHazineDaramad();
            }
        });

        imageButtonSetting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                registerForContextMenu(imageButtonSetting);
                openContextMenu(imageButtonSetting);
                unregisterForContextMenu(imageButtonSetting);
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("تنظیمات");
        menu.add(0, 0, 0, "گزارش درآمد");
        menu.add(0, 0, 0, "گزارش هزینه");
        menu.add(0, 0, 0, "گزارش حساب");
        menu.add(0, 0, 0, "نمودار درآمد");
        menu.add(0, 0, 0, "نمودار هزینه");
        menu.add(0, 0, 0, "درباره ما");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        if (item.getTitle() == "گزارش درآمد")
        {
            Intent intent = new Intent(HesabdariActivity.this, GozareshHazineDaramadActivity.class);
            intent.putExtra("Kind", "D");
            startActivity(intent);
        }

        else if (item.getTitle() == "گزارش هزینه")
        {
            Intent intent = new Intent(HesabdariActivity.this, GozareshHazineDaramadActivity.class);
            intent.putExtra("Kind", "H");
            startActivity(intent);
        }

        else if (item.getTitle() == "گزارش حساب")
        {
            Intent intent = new Intent(HesabdariActivity.this, GozareshHesabActivity.class);

            startActivity(intent);
        }

        else if (item.getTitle() == "نمودار درآمد")
        {
            textViewNoeNemodar.setText("نمودار درآمد");
            Kind = "D";
            NemayeshEtelaateHazineDaramad();
        }
        else if (item.getTitle() == "نمودار هزینه")
        {
            textViewNoeNemodar.setText("نمودار هزینه");
            Kind = "H";
            NemayeshEtelaateHazineDaramad();
        }

        return true;
    }


    void NemayeshEtelaateHazineDaramad()
    {
        pieChart1.Clean();
        SolarCalendar sc = new SolarCalendar(tarikheMohasebe);
        Locale loc = new Locale("en_US");

        String tarikh = String.valueOf(sc.year) + "/" + String.format(loc, "%02d", sc.month) +
                "/" + String.format(loc, "%02d", sc.date);

        textViewMabnayeTarikhiNemodar.setText(sc.strWeekDay + " " + tarikh);

        List<HazineDaramad> list_hazineDaramad = db.getHazineDaramadbyTarikhAndKind(tarikh, tarikh, Kind);

        /************************************************************ مقداردهی اولیه نمودار ****/

        Double jameKolDasteha = 0d; // برای نمایش جمع کل هزینه یا در آمد در تاریخ انتخاب شده

        int jameDarsade = 0; // برای نگهداری جمع  درصد چهار هزینه یشتر و یا چهار درآمد بیشتر

        for (int i = 0; i < list_hazineDaramad.size(); i++)
        {
            jameKolDasteha += list_hazineDaramad.get(i).JameKol;
        }

        /*****************/

        if (list_hazineDaramad.size() == 0)
        {
            textViewAdameSabteTarakonesh.setVisibility(View.VISIBLE);
            pieChart1.setVisibility(View.INVISIBLE);

        }
        else
        {
            textViewAdameSabteTarakonesh.setVisibility(View.INVISIBLE);
            pieChart1.setVisibility(View.VISIBLE);

        }

        int[] datas = new int[5];
        String[] labels = new String[5];

        for (int i = 0; i < list_hazineDaramad.size(); i++)
        {
            if (i < 4)
            {
                int darsadeDaste = (int) (100 * list_hazineDaramad.get(i).JameKol / jameKolDasteha);
                datas[i] = darsadeDaste;
                labels[i] = String.valueOf(darsadeDaste) + "%" + list_hazineDaramad.get(i).nameDastebandi;

                jameDarsade += darsadeDaste;
            }
        }

        if (list_hazineDaramad.size() > 4)
        {
            datas[4] = 100 - jameDarsade;
            labels[4] = String.valueOf(100 - jameDarsade) + "% سایر";
        }

        pieChart1.setData(datas, getAssets(), Kind);
        pieChart1.setLabels(labels);

        textViewAdadiMablagh.setText(jameKolDasteha.toString());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3)
        {
            NemayeshEtelaateHazineDaramad();
        }
    }

}
