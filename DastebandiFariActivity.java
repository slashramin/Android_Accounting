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


public class DastebandiFariActivity extends Activity {

    ListView             listViewDastebandiFari;
    DatabaseManager      db;
    ArrayAdapter<String> listAdapter;
    List<Dastebandi>     list_dastehaye_fari;
    String               id_dastebandi_asli;

    Button               buttonDasteFariJadid;
    EditText             editTextDasteFariJadid;
    String               id_Fari;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dastebandi_fari_layout);

        Intent intent = getIntent();

        id_dastebandi_asli = intent.getStringExtra("IDDastebandiAsli");

        buttonDasteFariJadid = (Button) findViewById(R.id.buttonDasteFariJadid);
        editTextDasteFariJadid = (EditText) findViewById(R.id.editTextDasteFariJadid);

        listViewDastebandiFari = (ListView) findViewById(R.id.listViewDastebandiFari);

        db = new DatabaseManager(this);

        bindDastebandiFari();

        listViewDastebandiFari.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                String id_dastebandi_fari = list_dastehaye_fari.get(position).id.toString();

                Intent intent = new Intent();
                intent.putExtra("id_fari", id_dastebandi_fari);

                setResult(1, intent);
                finish();
            }

        });

        buttonDasteFariJadid.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (editTextDasteFariJadid.length() > 0)
                {
                    db.DasteFariJadid(editTextDasteFariJadid.getText().toString(), id_dastebandi_asli);
                    bindDastebandiFari();
                }
            }
        });

        registerForContextMenu(listViewDastebandiFari);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list_dastehaye_fari.get(info.position).Onvan);

        menu.add(0, 0, 0, "ویرایش");
        menu.add(0, 0, 0, "حذف");

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "حذف")
        {
            final Dialog dialog = new Dialog(DastebandiFariActivity.this);

            dialog.setContentView(R.layout.sola_dialog);

            dialog.setTitle("حذف حساب");

            TextView textViewMatneSoal = (TextView) dialog.findViewById(R.id.textViewSoal);
            textViewMatneSoal.setText("با حذف این دسته، تمام تراکنش های انجام شده با آن حذف می شوند. آیا با انجام این عملیات موافق هستید؟");

            dialog.show();

            Button buttonBale = (Button) dialog.findViewById(R.id.buttonBale);
            Button buttonEnseraf = (Button) dialog.findViewById(R.id.buttonEnseraf);

            buttonBale.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    db.deleteDasteFari(list_dastehaye_fari.get(info.position).id);
                    bindDastebandiFari();
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
            id_Fari = list_dastehaye_fari.get(info.position).id.toString();
            final Dialog dialog = new Dialog(DastebandiFariActivity.this);

            dialog.setContentView(R.layout.virayesh_dialog);

            dialog.setTitle("ویرایش");

            final EditText text = (EditText) dialog.findViewById(R.id.editTextNameHesab);
            text.setText(list_dastehaye_fari.get(info.position).Onvan);

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
                        db.updateDasteFari(text.getText().toString(), id_Fari);
                        dialog.dismiss();
                        bindDastebandiFari();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "نام گروه را وارد نمایید", Toast.LENGTH_LONG).show();

                }
            });

        }

        return true;
    }


    void bindDastebandiFari()
    {
        list_dastehaye_fari = db.getDastebandiFari(id_dastebandi_asli);

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < list_dastehaye_fari.size(); i++)
        {
            list.add(list_dastehaye_fari.get(i).Onvan);
        }

        listAdapter = new ArrayAdapter(this, R.layout.dastebandi_row, list);

        listViewDastebandiFari.setAdapter(listAdapter);
    }
}
