package moradi.ramin;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class HesabActivity extends Activity {

    ListView             listViewHesab;
    ArrayAdapter<String> listAdapter;

    DatabaseManager      db;
    List<Hesab>          list_hesabha;

    EditText             editTextHesabJadid;
    EditText             editTextMojodiJadid;
    Button               buttonHesabJadid;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        setContentView(R.layout.hesab_layout);

        editTextHesabJadid = (EditText) findViewById(R.id.editTextHesabJadid);
        editTextMojodiJadid = (EditText) findViewById(R.id.editTextMojodiJadid);
        buttonHesabJadid = (Button) findViewById(R.id.buttonHesabJadid);

        /********************************************************** مقداردهی لیست ویو ****/
        listViewHesab = (ListView) findViewById(R.id.listViewHesab);
        db = new DatabaseManager(this);
        bindHesab();

        /********************************************************** دکمه ذخیره حساب جدید ****/
        buttonHesabJadid.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (editTextHesabJadid.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "لطفا نام حساب را وارد کنید", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Double mojodi = 0d;

                    try
                    {
                        mojodi = Double.parseDouble(editTextMojodiJadid.getText().toString());
                    }
                    catch (Exception ex)
                    {}

                    db.HesabJadid(editTextHesabJadid.getText().toString(), mojodi);
                    bindHesab();
                }

            }
        });

        /********************************************************** نمایش دیالوگ گزینه های حذف و ویرایش ****/
        registerForContextMenu(listViewHesab);

        listViewHesab.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub

                String id_hesab = list_hesabha.get(position).id.toString();
                String name_hesab = list_hesabha.get(position).nameHesab.toString();

                Intent intent = new Intent();
                intent.putExtra("idhesab", id_hesab);
                intent.putExtra("nameHesab", name_hesab);

                setResult(2, intent);
                finish();
            }

        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add("ویرایش");
        menu.add("حذف");

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(list_hesabha.get(info.position).nameHesab);
    }


    // مدیریت ویرایش و حذف
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "حذف")
        {
            try
            {
                final Dialog dialog = new Dialog(HesabActivity.this);
                dialog.setContentView(R.layout.sola_dialog);

                dialog.setTitle("حذف حساب");

                TextView textViewSoal = (TextView) dialog.findViewById(R.id.textViewSoal);
                final int id = list_hesabha.get(info.position).id;

                textViewSoal.setText("با حذف این حساب تمام تراکنش های انجام شده با آن حذف می شوند. آیا با انجام این عملیات موافق هستید؟");

                dialog.show();

                Button buttonEnseraf = (Button) dialog.findViewById(R.id.buttonEnseraf);
                Button buttonBale = (Button) dialog.findViewById(R.id.buttonBale);

                buttonBale.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        db.deleteHesab(id);
                        bindHesab();
                        dialog.dismiss();
                    }
                });

                buttonEnseraf.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
            }
            catch (Exception ex)
            {
                String msg = ex.getMessage();
            }

        }
        else if (item.getTitle() == "ویرایش")
        {
            final Dialog dialog = new Dialog(HesabActivity.this);
            dialog.setContentView(R.layout.virayesh_dialog);
            dialog.setTitle("ویرایش حساب");

            final EditText editTextNameHesab = (EditText) dialog.findViewById(R.id.editTextNameHesab);

            // آی دی حسابی که کاربر تصمیم به ویرایش آن دارد
            final Integer id_hesab = list_hesabha.get(info.position).id;
            String name_hesab = list_hesabha.get(info.position).nameHesab;

            editTextNameHesab.setText(name_hesab);
            dialog.show();

            Button buttonEnseraf = (Button) dialog.findViewById(R.id.buttonEnseraf);
            Button buttonBerozresani = (Button) dialog.findViewById(R.id.buttonBerozresani);

            buttonEnseraf.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });

            buttonBerozresani.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (editTextNameHesab.getText().length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "لطفا نام حساب را وارد کنید", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        db.updateHesab(editTextNameHesab.getText().toString(), id_hesab);
                        dialog.dismiss();
                        bindHesab();
                    }
                }
            });
        }
        return true;
    }


    // متد بایند (متصل) کردن سطرهای داخل (tbl_hesab) به لیست ویو
    public void bindHesab()
    {
        list_hesabha = db.getHesab();

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < list_hesabha.size(); i++)
        {
            Hesab h = list_hesabha.get(i);
            list.add(h.nameHesab);
        }

        // list.add("کیف پول");
        // list.add("بانک ملی");
        //list.add("بانک صادرات");

        listAdapter = new ArrayAdapter<String>(this, R.layout.hesab_row, list);
        listViewHesab.setAdapter(listAdapter);
    }

}
