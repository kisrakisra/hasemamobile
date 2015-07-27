package mysources.ImageLoading;

/**
 * Created by Verisun on 22.7.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import vizyonmedya.myapplication.R;

public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private String[] data;
    private String[] textdata;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public LazyAdapter(Activity a, String[] d,String[] t) {
        activity = a;
        data = d;
        textdata=t;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row_listview_item, null);

        TextView text = (TextView) vi.findViewById(R.id.text);
        ImageView image = (ImageView) vi.findViewById(R.id.image);
        text.setText(textdata[position]);
        imageLoader.DisplayImage(data[position], image);
        return vi;
    }
}
