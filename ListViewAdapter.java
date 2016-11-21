package moradi.ramin;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListViewAdapter extends BaseAdapter {

    Context                          mContext;
    LayoutInflater                   inflater;

    // این فیلدها برای نگهداری مقادیری استفاده می شوند که از منبع داده دریافت شده است
    private List<HazineDaramad>      hazineDaramadList = null;
    private ArrayList<HazineDaramad> arraylist;
    String                           Kind              = "";
    Typeface                         tf;


    // متد سازنده که اطلاعات را از منبع داده گرفته و داخل فیلدها ذخیره می کند 
    public ListViewAdapter(Context context, List<HazineDaramad> hazineDaramadList, String Kind, AssetManager asset) {
        mContext = context;
        this.hazineDaramadList = hazineDaramadList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<HazineDaramad>();
        this.arraylist.addAll(hazineDaramadList);
        this.Kind = Kind;

        tf = Typeface.createFromAsset(asset, "fonts/ANegaarBold.ttf");
    }


    public class ViewHolder {

        TextView nameDastebandi;
        TextView mablagh;
        TextView tarikh;

    }


    @Override
    public int getCount() {
        return hazineDaramadList.size();
    }


    @Override
    public HazineDaramad getItem(int position) {
        return hazineDaramadList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            // کپی گرفتن از روی الگو
            view = inflater.inflate(R.layout.gozaresh_hazine_daramad_row, null);

            // ویوهای مرتبط را متصل و شناسایی می کند
            holder.nameDastebandi = (TextView) view.findViewById(R.id.textViewNameDastebandi);
            holder.mablagh = (TextView) view.findViewById(R.id.textViewMablagh);
            holder.tarikh = (TextView) view.findViewById(R.id.textViewTarikh);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // پر کردن ویوها بر اساس مقداری که داخل پوزیشن است
        holder.nameDastebandi.setText(hazineDaramadList.get(position).nameDastebandi);
        holder.mablagh.setText(hazineDaramadList.get(position).mablagh);
        holder.tarikh.setText(hazineDaramadList.get(position).Tarikh);

        holder.nameDastebandi.setTypeface(tf);
        holder.mablagh.setTypeface(tf);
        holder.tarikh.setTypeface(tf);

        if (Kind.equals("D"))
        {
            holder.mablagh.setTextColor(ColorStateList.valueOf(Color.GREEN));
        }
        else
            holder.mablagh.setTextColor(ColorStateList.valueOf(Color.RED));

        return view;
    }

}
