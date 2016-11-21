package moradi.ramin;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class GozareshHazineDaramadActivity extends Activity {

    EditText        editTextAzTarikh;
    EditText        editTextTaTarikh;

    Button          buttonNemayeshGozaresh;
    Button          buttonBazgasht;

    String          Kind = "";
    DatabaseManager db;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        setContentView(R.layout.gozaresh_hazine_daramad_layout);

        Intent intent = getIntent();
        Kind = intent.getStringExtra("Kind");

        db = new DatabaseManager(this);

        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ANegaarBold.ttf");

        editTextAzTarikh = (EditText) findViewById(R.id.EditTextAzTarikh);
        editTextTaTarikh = (EditText) findViewById(R.id.editTextTaTarikh);

        editTextAzTarikh.setTypeface(tf);
        editTextTaTarikh.setTypeface(tf);

        buttonNemayeshGozaresh = (Button) findViewById(R.id.buttonNemayeshGozaresh);
        buttonBazgasht = (Button) findViewById(R.id.ButtonBazgasht);

        buttonNemayeshGozaresh.setTypeface(tf);
        buttonBazgasht.setTypeface(tf);

        editTextAzTarikh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final Dialog d = new Dialog(GozareshHazineDaramadActivity.this);

                d.setContentView(R.layout.tarikh_dialog);

                final TextView textViewRoz = (TextView) d.findViewById(R.id.TextViewRoz);
                final TextView textViewMah = (TextView) d.findViewById(R.id.TextViewMah);
                final TextView textViewSal = (TextView) d.findViewById(R.id.textViewSal);

                textViewRoz.setTypeface(tf);
                textViewMah.setTypeface(tf);
                textViewSal.setTypeface(tf);

                final Locale loc = new Locale("en_US");

                final SolarCalendar sc = new SolarCalendar();

                String tarikhEmroz = String.valueOf(sc.year) + "/" +
                        String.format(loc, "%02d", sc.month) + "/" +
                        String.format(loc, "%02d", sc.date);

                d.setTitle(sc.strWeekDay + " " + tarikhEmroz);

                textViewRoz.setText(String.format(loc, "%02d", sc.date));
                textViewMah.setText(String.format(loc, "%02d", sc.month));
                textViewSal.setText(String.valueOf(sc.year));

                Button buttonUpRoz = (Button) d.findViewById(R.id.ButtonUpRoz);
                Button buttonUpMah = (Button) d.findViewById(R.id.ButtonUpMah);
                Button buttonUpSal = (Button) d.findViewById(R.id.buttonUpSal);

                buttonUpRoz.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer day = Integer.parseInt(textViewRoz.getText().toString());
                        day += 1;

                        if (day < 32)
                        {
                            textViewRoz.setText(String.format(loc, "%02d", day));
                        }
                    }
                });

                buttonUpMah.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer month = Integer.parseInt(textViewMah.getText().toString());
                        month += 1;

                        if (month < 13)
                        {
                            textViewMah.setText(String.format(loc, "%02d", month));
                        }
                    }
                });

                buttonUpSal.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer year = Integer.parseInt(textViewSal.getText().toString());
                        year += 1;

                        textViewSal.setText(year.toString());

                    }
                });

                Button buttonDownRoz = (Button) d.findViewById(R.id.ButtonDownRoz);
                Button buttonDownMah = (Button) d.findViewById(R.id.ButtonDownMah);
                Button buttonDownSal = (Button) d.findViewById(R.id.ButtonDownSal);

                buttonDownRoz.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer day = Integer.parseInt(textViewRoz.getText().toString());
                        day -= 1;

                        if (day > 0)
                        {
                            textViewRoz.setText(String.format(loc, "%02d", day));
                        }
                    }
                });

                buttonDownMah.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer month = Integer.parseInt(textViewMah.getText().toString());
                        month -= 1;

                        if (month > 0)
                        {
                            textViewMah.setText(String.format(loc, "%02d", month));
                        }
                    }
                });

                buttonDownSal.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer year = Integer.parseInt(textViewSal.getText().toString());
                        year -= 1;

                        if (year > 0)
                            textViewSal.setText(year.toString());
                    }
                });

                Button buttonTaeid = (Button) d.findViewById(R.id.buttonTaeid);
                Button buttonEnseraf = (Button) d.findViewById(R.id.buttonEnseraf);

                buttonTaeid.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        String tarikheEntekhabShode = textViewSal.getText() + "/" +
                                textViewMah.getText() + "/" +
                                textViewRoz.getText();

                        editTextAzTarikh.setText(tarikheEntekhabShode);
                        d.dismiss();

                    }
                });

                buttonEnseraf.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        d.dismiss();
                    }
                });

                d.show();
            }
        });

        editTextTaTarikh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final Dialog d = new Dialog(GozareshHazineDaramadActivity.this);

                d.setContentView(R.layout.tarikh_dialog);

                final TextView textViewRoz = (TextView) d.findViewById(R.id.TextViewRoz);
                final TextView textViewMah = (TextView) d.findViewById(R.id.TextViewMah);
                final TextView textViewSal = (TextView) d.findViewById(R.id.textViewSal);

                textViewRoz.setTypeface(tf);
                textViewMah.setTypeface(tf);
                textViewSal.setTypeface(tf);

                final Locale loc = new Locale("en_US");

                final SolarCalendar sc = new SolarCalendar();

                String tarikhEmroz = String.valueOf(sc.year) + "/" +
                        String.format(loc, "%02d", sc.month) + "/" +
                        String.format(loc, "%02d", sc.date);

                d.setTitle(sc.strWeekDay + " " + tarikhEmroz);

                textViewRoz.setText(String.format(loc, "%02d", sc.date));
                textViewMah.setText(String.format(loc, "%02d", sc.month));
                textViewSal.setText(String.valueOf(sc.year));

                Button buttonUpRoz = (Button) d.findViewById(R.id.ButtonUpRoz);
                Button buttonUpMah = (Button) d.findViewById(R.id.ButtonUpMah);
                Button buttonUpSal = (Button) d.findViewById(R.id.buttonUpSal);

                buttonUpRoz.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer day = Integer.parseInt(textViewRoz.getText().toString());
                        day += 1;

                        if (day < 32)
                        {
                            textViewRoz.setText(String.format(loc, "%02d", day));
                        }
                    }
                });

                buttonUpMah.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer month = Integer.parseInt(textViewMah.getText().toString());
                        month += 1;

                        if (month < 13)
                        {
                            textViewMah.setText(String.format(loc, "%02d", month));
                        }
                    }
                });

                buttonUpSal.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer year = Integer.parseInt(textViewSal.getText().toString());
                        year += 1;

                        textViewSal.setText(year.toString());

                    }
                });

                Button buttonDownRoz = (Button) d.findViewById(R.id.ButtonDownRoz);
                Button buttonDownMah = (Button) d.findViewById(R.id.ButtonDownMah);
                Button buttonDownSal = (Button) d.findViewById(R.id.ButtonDownSal);

                buttonDownRoz.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer day = Integer.parseInt(textViewRoz.getText().toString());
                        day -= 1;

                        if (day > 0)
                        {
                            textViewRoz.setText(String.format(loc, "%02d", day));
                        }
                    }
                });

                buttonDownMah.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer month = Integer.parseInt(textViewMah.getText().toString());
                        month -= 1;

                        if (month > 0)
                        {
                            textViewMah.setText(String.format(loc, "%02d", month));
                        }
                    }
                });

                buttonDownSal.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Integer year = Integer.parseInt(textViewSal.getText().toString());
                        year -= 1;

                        if (year > 0)
                            textViewSal.setText(year.toString());
                    }
                });

                Button buttonTaeid = (Button) d.findViewById(R.id.buttonTaeid);
                Button buttonEnseraf = (Button) d.findViewById(R.id.buttonEnseraf);

                buttonTaeid.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        String tarikheEntekhabShode = textViewSal.getText() + "/" +
                                textViewMah.getText() + "/" +
                                textViewRoz.getText();

                        editTextTaTarikh.setText(tarikheEntekhabShode);
                        d.dismiss();

                    }
                });

                buttonEnseraf.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        d.dismiss();
                    }
                });

                d.show();
            }
        });

        buttonNemayeshGozaresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                List<HazineDaramad> list_hazine_daramad =
                        db.getHazineDaramadbyTarikhAndKind(editTextAzTarikh.getText().toString(), editTextTaTarikh.getText().toString(), Kind);

                ListViewAdapter adapter;
                ArrayList<HazineDaramad> arraylist = new ArrayList<HazineDaramad>();

                for (int i = 0; i < list_hazine_daramad.size(); i++)
                {

                    HazineDaramad h = new HazineDaramad();

                    Double mablagh = Double.parseDouble(list_hazine_daramad.get(i).mablagh);

                    h.mablagh = NumberFormat.getNumberInstance(Locale.US).format(mablagh);
                    h.nameDastebandi = list_hazine_daramad.get(i).nameDastebandi;
                    h.Tarikh = list_hazine_daramad.get(i).Tarikh;

                    arraylist.add(h);
                }

                ListView listViewGozareshHazineDarmad = (ListView) findViewById(R.id.listViewGozareshHazineDarmad);

                adapter = new ListViewAdapter(GozareshHazineDaramadActivity.this, arraylist, Kind, getAssets());

                listViewGozareshHazineDarmad.setAdapter(adapter);

            }
        });

    }
}
