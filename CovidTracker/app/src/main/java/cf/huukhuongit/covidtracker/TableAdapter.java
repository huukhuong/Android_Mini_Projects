package cf.huukhuongit.covidtracker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

public class TableAdapter extends TableDataAdapter<Area> {

    private Context context;
    private List<Area> data;

    public TableAdapter(Context context, List<Area> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Area area = getRowData(rowIndex);
        View renderView = null;
        DecimalFormat dcf = new DecimalFormat("###,###");
        switch (columnIndex) {
            case 0:
                TextView name = new TextView(this.context);
                name.setText(area.getName());
                name.setTextSize(16f);
                name.setTypeface(null, Typeface.BOLD);
                name.setPadding(5,10,0,10);
                renderView = name;
                break;
            case 1:
                TextView total = new TextView(this.context);
                total.setText(dcf.format(area.getTotal()));
                total.setTextSize(16f);
                total.setTextColor(Color.parseColor("#ff8c00"));
                total.setGravity(Gravity.CENTER);
                renderView = total;
                break;
            case 2:
                TextView today = new TextView(this.context);
                String todaystr = area.getToday() > 0 ? "+" + dcf.format(area.getToday()) : dcf.format(area.getToday());
                today.setText(todaystr);
                today.setTextSize(16f);
                today.setTextColor(Color.parseColor("#ff2952"));
                today.setGravity(Gravity.CENTER);
                renderView = today;
                break;
            case 3:
                TextView death = new TextView(this.context);
                death.setText(dcf.format(area.getDeath()));
                death.setTextSize(16f);
                death.setGravity(Gravity.CENTER);
                renderView = death;
                break;
        }
        return renderView;
    }

}
