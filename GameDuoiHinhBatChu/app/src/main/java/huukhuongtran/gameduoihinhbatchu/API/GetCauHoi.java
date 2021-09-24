package huukhuongtran.gameduoihinhbatchu.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import huukhuongtran.gameduoihinhbatchu.DTO.CauHoi;
import huukhuongtran.gameduoihinhbatchu.MainActivity;

public class GetCauHoi extends AsyncTask<Void, Void, Void> {

    private String data;
    private final String url = "http://huukhuong.ml/DuoiHinhBatChu/";

    private ProgressDialog dl;
    private Context context;

    public GetCauHoi(MainActivity mainActivity) {
        this.context = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dl = new ProgressDialog(context);
        dl.setMessage("Đang tải");
        dl.setCanceledOnTouchOutside(false);
        dl.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            data = responseBody.string();
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (data != null) {
            try {
                DATA.getData().arrCauHoi.clear();
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    CauHoi cauHoi = new CauHoi(
                            object.getString("tenMan"),
                            object.getString("dapAn"),
                            object.getString("chiTiet"),
                            object.getString("anh"));
                    Log.e("", cauHoi.toString());
                    DATA.getData().arrCauHoi.add(cauHoi);
                }
            } catch (Exception e) {
            }
        } else {
        }
        dl.dismiss();
    }


}
