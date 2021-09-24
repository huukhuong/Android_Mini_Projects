package huukhuongtran.gameduoihinhbatchu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import huukhuongtran.gameduoihinhbatchu.R;

public class AdapterDapAn extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<String> objects;


    public AdapterDapAn(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_dap_an, null);
        }
        TextView txtText = convertView.findViewById(R.id.txtText);
        txtText.setText(objects.get(position)+"");
        return convertView;
    }
}
