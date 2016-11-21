package moradi.ramin;

import java.text.NumberFormat;
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
import android.widget.TextView;
import android.widget.Toast;


public class GozareshHesabActivity extends Activity {

    EditText        EditTextAzTarikh;
    EditText        EditTextTaTarikh;
    EditText        editTextHesab;

    TextView        TextViewMablaghBardashti;
    TextView        TextViewBardasht;

    TextView        textViewMablaghVarizi;
    TextView        TextViewVariz;

    TextView        TextViewMablaghMojodi;
    TextView        TextViewMojodi;

    Button          ButtonBazgasht;
    Button          buttonNemayeshGozaresh;

    String          ID_Hesab = "";

    DatabaseManager db;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        setContentView(R.layout.gozaresh_hesab_layout);

        db = new DatabaseManager(this);

        EditTextAzTarikh = (EditText) findViewById(R.id.EditTextAzTarikh);
        EditTextTaTarikh = (EditText) findViewById(R.id.EditTextTaTarikh);
        editTextHesab = (EditText) findViewById(R.id.editTextHesab);

        ButtonBazgasht = (Button) findViewById(R.id.ButtonBazgasht);
        buttonNemayeshGozaresh = (Button) findViewById(R.id.buttonNemayeshGozaresh);

        TextViewMablaghBardashti = (TextView) findViewById(R.id.TextViewMablaghBardashti);
        TextViewBardasht = (TextView) findViewById(R.id.TextViewBardasht);

        textViewMablaghVarizi = (TextView) findViewById(R.id.textViewMablaghVarizi);
        TextViewVariz = (TextView) findViewById(R.id.TextViewVariz);

        TextViewMablaghMojodi = (TextView) findViewById(R.id.TextViewMablaghMojodi);
        TextViewMojodi = (TextView) findViewById(R.id.TextViewMojodi);

        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ANegaarBold.ttf");
        EditTextAzTarikh.setTypeface(tf);
        EditTextTaTarikh.setTypeface(tf);
        editTextHesab.setTypeface(tf);

        ButtonBazgasht.setTypeface(tf);
        buttonNemayeshGozaresh.setTypeface(tf);

        TextViewMablaghBardashti.setTypeface(tf);
        TextViewBardasht.setTypeface(tf);

        textViewMablaghVarizi.setTypeface(tf);
        TextViewVariz.setTypeface(tf);

        TextViewMablaghMojodi.setTypeface(tf);
        TextViewMojodi.setTypeface(tf);

        EditTextAzTarikh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final Dialog d = new Dialog(GozareshHesabActivity.this);

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

                        EditTextAzTarikh.setText(tarikheEntekhabShode);
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

        EditTextTaTarikh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final Dialog d = new Dialog(GozareshHesabActivity.this);

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

                        EditTextTaTarikh.setText(tarikheEntekhabShode);
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

        editTextHesab.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                editTextHesab.setText("");
                ID_Hesab = "";
                Intent intent = new Intent(GozareshHesabActivity.this, HesabActivity.class);

                startActivityForResult(intent, 0);
            }
        });

        buttonNemayeshGozaresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (EditTextAzTarikh.getText().length() == 0 || EditTextTaTarikh.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "تاریخ را وارد نمایید", Toast.LENGTH_LONG).show();
                    return;
                }

                if (ID_Hesab.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "حساب را وارد نمایید", Toast.LENGTH_LONG).show();
                    return;
                }

                Double mablaghBardashti = db.getBardashti(ID_Hesab,
                        EditTextAzTarikh.getText().toString(), EditTextTaTarikh.getText().toString());

                TextViewBardasht.setText(NumberFormat.getNumberInstance(Locale.US).format(mablaghBardashti));

                Double mablaghVarizi = db.getVarizi(ID_Hesab,
                        EditTextAzTarikh.getText().toString(), EditTextTaTarikh.getText().toString());

                TextViewVariz.setText(NumberFormat.getNumberInstance(Locale.US).format(mablaghVarizi));

                Double mojodi = db.getMojodi(ID_Hesab);

                TextViewMojodi.setText(NumberFormat.getNumberInstance(Locale.US).format(mojodi));

            }
        });

        ButtonBazgasht.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (resultCode == 2)
        {
            String id_hesab = data.getStringExtra("idhesab");
            String name_hesab = data.getStringExtra("nameHesab");

            editTextHesab.setText(name_hesab);
            ID_Hesab = id_hesab;

        }

    }

}
