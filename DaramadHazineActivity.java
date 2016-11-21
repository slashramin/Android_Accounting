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
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DaramadHazineActivity extends Activity {

    EditText        editTextMablagh;
    EditText        editTextDastebandi;
    EditText        editTextHesab;
    EditText        editTextTarikh;
    EditText        editTextTozih;

    Button          buttonEnseraf;
    Button          buttonZakhire;

    String          ID_Hesab = "";
    String          ID_Fari  = "";

    DatabaseManager db;
    String          Kind     = "";

    TextView        textViewHeaderTitle;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        setContentView(R.layout.daramad_hazine_layout);

        textViewHeaderTitle = (TextView) findViewById(R.id.textViewHeaderTitle);

        Intent intent = getIntent();
        Kind = intent.getStringExtra("Kind");

        if (Kind.equals("D"))
            textViewHeaderTitle.setText("ثبت درآمد");
        else if (Kind.equals("H"))
            textViewHeaderTitle.setText("ثبت هزینه");

        db = new DatabaseManager(this);

        editTextMablagh = (EditText) findViewById(R.id.editTextMablagh);
        editTextDastebandi = (EditText) findViewById(R.id.editTextDastebandi);
        editTextHesab = (EditText) findViewById(R.id.editTextHesab);
        editTextTarikh = (EditText) findViewById(R.id.editTextTarikh);
        editTextTozih = (EditText) findViewById(R.id.editTextTozih);

        buttonEnseraf = (Button) findViewById(R.id.ButtonEnseraf);
        buttonZakhire = (Button) findViewById(R.id.buttonZakhire);

        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ANegaarBold.ttf");

        editTextMablagh.setTypeface(tf);
        editTextDastebandi.setTypeface(tf);
        editTextHesab.setTypeface(tf);
        editTextTarikh.setTypeface(tf);
        editTextTozih.setTypeface(tf);

        buttonEnseraf.setTypeface(tf);
        buttonZakhire.setTypeface(tf);

        editTextMablagh.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean isFocus) {
                // TODO Auto-generated method stub
                if (editTextMablagh.getText().length() == 0)
                    return;

                if (isFocus == false)
                {

                    // جدا کردن ارقام به صورت سه رقم سه رقم
                    Double mablagh = Double.parseDouble(editTextMablagh.getText().toString());

                    editTextMablagh.setText(NumberFormat.getNumberInstance(Locale.US).format(mablagh));
                }
                else
                {
                    String mablaghBedoneJodaKonande = editTextMablagh.getText().toString().replace(",", "");
                    editTextMablagh.setText(mablaghBedoneJodaKonande);
                }
            }
        });

        editTextHesab.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                editTextHesab.setText("");
                ID_Hesab = "";
                Intent intent = new Intent(DaramadHazineActivity.this, HesabActivity.class);

                startActivityForResult(intent, 0);
            }
        });

        editTextDastebandi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                editTextDastebandi.setText("");
                ID_Fari = "";

                Intent intent = new Intent(DaramadHazineActivity.this, DastebandiAsliActivity.class);
                intent.putExtra("Kind", Kind);
                startActivityForResult(intent, 0);
            }
        });

        editTextTarikh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final Dialog d = new Dialog(DaramadHazineActivity.this);

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

                        editTextTarikh.setText(tarikheEntekhabShode);
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

        buttonZakhire.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (editTextMablagh.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "لطفا مبلغ را مشخص نمایید", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Double mablagh;

                    try
                    {
                        mablagh = Double.parseDouble(editTextMablagh.getText().toString());

                        if (mablagh == 0)
                        {
                            Toast.makeText(getApplicationContext(), "لطفا مبلغ معتبر وارد نمایید", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    catch (Exception ex)
                    {

                    }
                }

                if (ID_Fari == "")
                {
                    Toast.makeText(getApplicationContext(), "لطفا  دسته بندی را مشخص نمایید", Toast.LENGTH_LONG).show();
                    return;
                }

                if (ID_Hesab == "")
                {
                    Toast.makeText(getApplicationContext(), "لطفا حساب را مشخص نمایید", Toast.LENGTH_LONG).show();
                    return;
                }

                if (editTextTarikh.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "لطفا تاریخ را مشخص نمایید", Toast.LENGTH_LONG).show();
                    return;
                }

                Boolean result = db.DaramadHazineJadid(editTextMablagh.getText().toString().replace(",", ""), ID_Fari, ID_Hesab, editTextTarikh.getText().toString(), editTextTozih.getText().toString());

                if (result == true)
                {
                    Toast.makeText(getApplicationContext(), "عملیات ثبت با موفقیت انجام شد", Toast.LENGTH_LONG).show();

                    Double mablagh = Double.parseDouble(editTextMablagh.getText().toString().replace(",", ""));

                    if (Kind.equals("H"))
                        mablagh = mablagh * -1;

                    db.UpdateMojodiHesab(mablagh.toString(), ID_Hesab);

                    editTextMablagh.setText("");
                    editTextDastebandi.setText("");
                    ID_Fari = "";
                    editTextHesab.setText("");
                    ID_Hesab = "";
                    editTextTarikh.setText("");
                    editTextTozih.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "عملیات ثبت با شکست مواجه  شد", Toast.LENGTH_LONG).show();

                }
            }

        });

        buttonEnseraf.setOnClickListener(new OnClickListener() {

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

        if (resultCode == 1)
        {
            ID_Fari = data.getStringExtra("id_fari");
            String nameDsteAsliFari = db.NameDasteAsliFari(ID_Fari);

            editTextDastebandi.setText(nameDsteAsliFari);

        }
    }
}
