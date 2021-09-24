package cf.huukhuongit.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private TextView txtUpdate, txtTotal, txtActive, txtRecover, txtDeath;
    private PieChart pieChart;
    private TableView tableView;
    private TableAdapter tableAdapter;
    static String adapterTable[] = {"Tỉnh/TP", "Tổng ca", "Hôm nay", "Tử vong"};

    private void addControls() {
        txtUpdate = findViewById(R.id.txtUpdate);
        txtTotal = findViewById(R.id.txtTotal);
        txtActive = findViewById(R.id.txtActive);
        txtRecover = findViewById(R.id.txtRecover);
        txtDeath = findViewById(R.id.txtDeath);
        pieChart = findViewById(R.id.pieChart);
        tableView = findViewById(R.id.tableView);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        txtUpdate.setText("Dữ liệu cập nhật ngày " + sdf.format(now));

        tableView.setHeaderBackgroundColor(Color.parseColor("#1F81D3"));

        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(MainActivity.this, adapterTable);
        simpleTableHeaderAdapter.setTextColor(Color.parseColor("#FFFFFF"));
        simpleTableHeaderAdapter.setTextSize(15);
        simpleTableHeaderAdapter.setGravity(Gravity.CENTER);

        tableView.setHeaderAdapter(simpleTableHeaderAdapter);
        tableView.setColumnCount(4);

        TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(MainActivity.this, 4, 100);
        columnModel.setColumnWidth(0, 150);
        tableView.setColumnModel(columnModel);

        DataCovidTask task = new DataCovidTask();
        task.execute();
    }

    class DataCovidTask extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Thông báo");
            dialog.setMessage("Đang tải dữ liệu...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null) {
                parseAll(jsonArray);
                parseArena(jsonArray);
            }
            dialog.dismiss();
        }

        private void parseArena(JSONArray jsonArray) {
            ArrayList<Area> listArea = new ArrayList<>();
            try {
                for (int i = 1; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Area area = new Area();

                    if (object.has("area")) {
                        area.setName(object.getString("area"));
                    }
                    if (object.has("total")) {
                        area.setTotal(object.getInt("total"));
                    }
                    if (object.has("today")) {
                        area.setToday(object.getInt("today"));
                    }
                    if (object.has("death")) {
                        area.setDeath(object.getInt("death"));
                    }
                    listArea.add(area);
                }
            } catch (Exception e) {

            }


            tableAdapter = new TableAdapter(MainActivity.this, listArea);
            tableView.setDataAdapter(tableAdapter);
        }

        private void parseAll(JSONArray jsonArray) {
            try {
                JSONObject object = jsonArray.getJSONObject(0);
                DataCovid data = new DataCovid();
                if (object.has("AllTotal")) {
                    data.setTotal(object.getInt("AllTotal"));
                }
                if (object.has("AllActive")) {
                    data.setActive(object.getInt("AllActive"));
                }
                if (object.has("AllRecover")) {
                    data.setRecover(object.getInt("AllRecover"));
                }
                if (object.has("AllDeath")) {
                    data.setDeath(object.getInt("AllDeath"));
                }

                DecimalFormat df = new DecimalFormat("###,###");
                txtTotal.setText(df.format(data.getTotal()));
                txtActive.setText(df.format(data.getActive()));
                txtRecover.setText(df.format(data.getRecover()));
                txtDeath.setText(df.format(data.getDeath()));

                pieChart.addPieSlice(new PieModel("Đang điều trị", data.getActive(), Color.parseColor("#5a52d6")));
                pieChart.addPieSlice(new PieModel("Đã hồi phục", data.getRecover(), Color.parseColor("#43B020")));
                pieChart.addPieSlice(new PieModel("Tử vong", data.getDeath(), Color.parseColor("#ff2952")));
                pieChart.startAnimation();
            } catch (Exception e) {

            }
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            try {
                URL url = new URL("http://covid.huukhuongit.tk/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(isr);

                StringBuffer stringBuffer = new StringBuffer();
                String line = bufferedReader.readLine();

                while (line != null) {
                    stringBuffer.append(line);
                    line = bufferedReader.readLine();
                }

                return new JSONArray(stringBuffer.toString());
            } catch (Exception ex) {
                Log.e("ERROR JSON", ex.toString());
            }
            return null;
        }
    }

}