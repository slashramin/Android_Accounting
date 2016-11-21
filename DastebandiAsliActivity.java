package  moradi.ramin;

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


public class DastebandiAsliActivity extends Activity {

    ListView             listViewDastebandiAsli;
    ArrayAdapter<String> listAdapter;
    DatabaseManager      db;
    List<Dastebandi>     list_dastehaye_asli;
    String               Kind = "";

    Button               buttonDasteAsliJadid;
    EditText             editTextDasteAsliJadid;
    Integer              id_dasteAsli;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        setContentView(R.layout.dastebandi_asli_layout);

        Intent intent = getIntent();
        Kind = intent.getStringExtra("Kind");

        listViewDastebandiAsli = (ListView) findViewById(R.id.listViewDasteAsli);

        db = new DatabaseManager(this);

        bindDastebandi();

        listViewDastebandiAsli.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                Integer id_daste_asli = list_dastehaye_asli.get(position).id;

                Intent intent = new Intent(DastebandiAsliActivity.this, DastebandiFariActivity.class);

                intent.putExtra("IDDastebandiAsli", id_daste_asli.toString());
                startActivityForResult(intent, 0);

            }

        });

        buttonDasteAsliJadid = (Button) findViewById(R.id.buttonDasteAsliJadid);
        editTextDasteAsliJadid = (EditText) findViewById(R.id.editTextDasteAsliJadid);

        buttonDasteAsliJadid.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (editTextDasteAsliJadid.length() > 0)
                {
                    db.DasteAsliJadid(editTextDasteAsliJadid.getText().toString(), "D");
                    dasteAsliDaramad();
                }
            }
        });

        registerForContextMenu(listViewDastebandiAsli);
    }


    void bindDastebandi()
    {
        if (Kind.equals("D"))
            list_dastehaye_asli = db.getDastebandiAsli("D");
        else if (Kind.equals("H"))
            list_dastehaye_asli = db.getDastebandiAsli("H");

        //  list_dastehaye_asli = db.getDastebandiAsli(Kind);

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < list_dastehaye_asli.size(); i++)
        {
            list.add(list_dastehaye_asli.get(i).Onvan);
        }

        listAdapter = new ArrayAdapter<String>(this, R.layout.dastebandi_row, list);

        listViewDastebandiAsli.setAdapter(listAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1)
        {
            String id_fari = data.getStringExtra("id_fari");
            Intent intent = new Intent();
            intent.putExtra("id_fari", id_fari);
            setResult(1, intent);
            finish();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list_dastehaye_asli.get(info.position).Onvan);

        menu.add(0, 0, 0, "ویرایش");
        menu.add(0, 0, 0, "حذف");

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "حذف")
        {
            final Dialog dialog = new Dialog(DastebandiAsliActivity.this);

            dialog.setContentView(R.layout.sola_dialog);

            dialog.setTitle("حذف دسته بندی");

            TextView textViewMatneSoal = (TextView) dialog.findViewById(R.id.textViewSoal);
            textViewMatneSoal.setText("با حذف این گروه، تمام دسته های فرعی دراین گروه و تراکنش های انجام شده با آنها حذف می شوند. آیا با انجام این عملیات موافق هستید؟");

            dialog.show();

            Button buttonBale = (Button) dialog.findViewById(R.id.buttonBale);
            Button buttonEnseraf = (Button) dialog.findViewById(R.id.buttonEnseraf);

            buttonBale.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    db.deleteDasteAsli(list_dastehaye_asli.get(info.position).id);
                    dasteAsliDaramad();
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
        else if (item.getTitle() == "ویرایش")
        {
            id_dasteAsli = list_dastehaye_asli.get(info.position).id;
            final Dialog dialog = new Dialog(DastebandiAsliActivity.this);

            dialog.setContentView(R.layout.virayesh_dialog);

            dialog.setTitle("ویرایش");

            final EditText text = (EditText) dialog.findViewById(R.id.editTextNameHesab);
            text.setText(list_dastehaye_asli.get(info.position).Onvan);

            dialog.show();

            Button buttonAnjameVirayesh = (Button) dialog.findViewById(R.id.buttonBerozresani);
            Button buttonLaghveVirayesh = (Button) dialog.findViewById(R.id.buttonEnseraf);

            buttonLaghveVirayesh.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });

            buttonAnjameVirayesh.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (text.getText().length() > 0)
                    {
                        db.updateDasteAsli(text.getText().toString(), id_dasteAsli);
                        dialog.dismiss();
                        dasteAsliDaramad();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "نام گروه را وارد نمایید", Toast.LENGTH_LONG).show();

                }
            });
        }

        return true;
    }


    private void dasteAsliDaramad() {

        list_dastehaye_asli = db.getDastebandiAsli(Kind);

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < list_dastehaye_asli.size(); i++)
        {
            list.add(list_dastehaye_asli.get(i).Onvan);
        }

        listAdapter = new ArrayAdapter<String>(this, R.layout.dastebandi_row, list);

        listViewDastebandiAsli.setAdapter(listAdapter);
    }
}
