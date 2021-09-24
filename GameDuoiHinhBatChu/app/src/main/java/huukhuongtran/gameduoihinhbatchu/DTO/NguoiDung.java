package huukhuongtran.gameduoihinhbatchu.DTO;

import android.content.Context;
import android.content.SharedPreferences;

public class NguoiDung {

    private String fileName = "appData";
    private int tien, soCau;

    public void saveThongTin(Context context) {
        SharedPreferences setting = context.getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("tien", tien);
        editor.putInt("soCau", soCau);
        editor.commit();
    }

    public void getThongTin(Context context) {
        SharedPreferences setting = context.getSharedPreferences(fileName, 0);
        tien = setting.getInt("tien", 20);
        soCau = setting.getInt("soCau", -1);
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public int getSoCau() {
        return soCau;
    }

    public void setSoCau(int soCau) {
        this.soCau = soCau;
    }
}
