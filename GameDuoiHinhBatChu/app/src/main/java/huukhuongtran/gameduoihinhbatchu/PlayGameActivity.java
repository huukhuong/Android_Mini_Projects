package huukhuongtran.gameduoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

import huukhuongtran.gameduoihinhbatchu.Adapter.AdapterDapAn;
import huukhuongtran.gameduoihinhbatchu.DTO.CauHoi;
import huukhuongtran.gameduoihinhbatchu.DTO.NguoiDung;
import huukhuongtran.gameduoihinhbatchu.Model.PlayGameModel;

public class PlayGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        addControls();
        addEvents();
    }

    private PlayGameModel model;
    private CauHoi cauHoi;

    private ArrayList<String> arrCauTraLoi, arrGoiY;
    private GridView gdvCauTraLoi, gdvGoiY;
    private ImageView imgCauHoi;
    private ArrayAdapter<String> adapterCauTraLoi, adapterGoiY;
    private TextView txtTienNguoiDung, txtManChoi;
    private ImageButton btnGoiY, btnDoiCauHoi;

    private String dapAn;
    private int indexTraLoi = 0;

    private void addControls() {
        model = new PlayGameModel(PlayGameActivity.this);

        gdvCauTraLoi = findViewById(R.id.gdvCauTraLoi);
        gdvGoiY = findViewById(R.id.gdvGoiY);
        imgCauHoi = findViewById(R.id.imgCauHoi);
        txtTienNguoiDung = findViewById(R.id.txtTienNguoiDung);
        txtManChoi = findViewById(R.id.txtManChoi);
        btnGoiY = findViewById(R.id.btnGoiY);
        btnDoiCauHoi = findViewById(R.id.btnDoiCauHoi);

        arrCauTraLoi = new ArrayList<>();
        arrGoiY = new ArrayList<>();

        taoManChoi();
    }

    private void addEvents() {
        gdvGoiY.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if (s.length() != 0 && indexTraLoi < arrCauTraLoi.size()) {
                    int n = arrCauTraLoi.size();
                    for (int i = 0; i < n; i++) {
                        if (arrCauTraLoi.get(i).length() == 0) {
                            indexTraLoi = i;
                            break;
                        }
                    }
                    arrGoiY.set(position, "");
                    arrCauTraLoi.set(indexTraLoi, s);
                    indexTraLoi++;
                    adapterCauTraLoi.notifyDataSetChanged();
                    adapterGoiY.notifyDataSetChanged();

                    checkWin();
                }
            }
        });

        gdvCauTraLoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if (s.length() != 0) {
                    indexTraLoi = position;
                    arrCauTraLoi.set(position, "");
                    int n = arrGoiY.size();
                    for (int i = 0; i < n; i++) {
                        if (arrGoiY.get(i).length() == 0) {
                            arrGoiY.set(i, s);
                            break;
                        }
                    }

                    adapterCauTraLoi.notifyDataSetChanged();
                    adapterGoiY.notifyDataSetChanged();
                }
            }
        });

        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Animation scaledown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        btnGoiY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGoiY.startAnimation(scaleup);
                btnGoiY.startAnimation(scaledown);
                chucNangGoiY();
            }
        });

        btnDoiCauHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDoiCauHoi.startAnimation(scaleup);
                btnDoiCauHoi.startAnimation(scaledown);
                chucNangDoiCauHoi();
            }
        });


    }

    public void taoManChoi() {
        indexTraLoi = 0;

        model.layThongTin();

        cauHoi = model.layCauHoi();
        dapAn = cauHoi.getDapAn();

        txtTienNguoiDung.setText(model.getNguoiDung().getTien() + "");
        txtManChoi.setText(cauHoi.getTenMan());

        arrCauTraLoi.clear();
        arrGoiY.clear();

        Glide.with(PlayGameActivity.this).load(cauHoi.getAnh()).into(imgCauHoi);

        Random rd = new Random();
        int n = dapAn.length();

        for (int i = 0; i < n; i++) {
            arrCauTraLoi.add("");
            String s = "" + (char) (65 + rd.nextInt(26));
            arrGoiY.add(s);
            s = "" + (char) (65 + rd.nextInt(26));
            arrGoiY.add(s);
        }

        for (int i = 0; i < n; i++) {
            String s = dapAn.charAt(i) + "";
            arrGoiY.set(i, s.toUpperCase());
        }

        int sizeTL = arrCauTraLoi.size();
        int size = arrGoiY.size();

        for (int i = 0; i < sizeTL * 2; i++) {
            String s = arrGoiY.get(i);
            int vt = rd.nextInt(size);
            arrGoiY.set(i, arrGoiY.get(vt));
            arrGoiY.set(vt, s);
        }

        hienThiCauTraLoi();
        hienThiGoiY();
    }

    private void hienThiCauTraLoi() {
        adapterCauTraLoi = new AdapterDapAn(PlayGameActivity.this, 0, arrCauTraLoi);
        gdvCauTraLoi.setAdapter(adapterCauTraLoi);

        int size = arrCauTraLoi.size();
        if (size > 32)
            size /= 4;
        else if (size > 16)
            size /= 3;
        else if (size > 8)
            size /= 2;
        gdvCauTraLoi.setNumColumns(size);
    }

    private void hienThiGoiY() {
        adapterGoiY = new AdapterDapAn(PlayGameActivity.this, 0, arrGoiY);
        gdvGoiY.setAdapter(adapterGoiY);

        int size = arrGoiY.size();
        if (size > 32)
            size /= 4;
        else if (size > 16)
            size /= 3;
        else if (size > 8)
            size /= 2;
        gdvGoiY.setNumColumns(size);
    }

    private void checkWin() {
        String s = "";
        for (String str : arrCauTraLoi) {
            s += str;
        }

        if (s.equalsIgnoreCase(dapAn)) {
            model.layThongTin();
            NguoiDung nguoiDung = model.getNguoiDung();
            nguoiDung.setTien(nguoiDung.getTien() + 10);
            nguoiDung.setSoCau(nguoiDung.getSoCau() + 1);
            model.luuThongTin();

            Intent intent = new Intent(PlayGameActivity.this, WinActivity.class);
            intent.putExtra("dapAn", cauHoi.getChiTietDapAn());
            startActivity(intent);
            taoManChoi();
        }
    }

    private void chucNangGoiY() {
        model.layThongTin();
        NguoiDung nguoiDung = model.getNguoiDung();

        if (nguoiDung.getTien() < 5) {
            Toast.makeText(this, "Hết tiền rồi má", Toast.LENGTH_SHORT).show();
            return;
        }

        int flag = 0;
        for(String st:arrCauTraLoi) {
            if(st.length() != 0) {
                flag++;
            }
        }
        if(flag == arrCauTraLoi.size()) {
            return;
        }
        Random rd = new Random();
        int index = rd.nextInt(dapAn.length());

        while (arrCauTraLoi.get(index).length() != 0) {
            index = rd.nextInt(dapAn.length());
        }

        String goiY = dapAn.charAt(index) + "";
        goiY = goiY.toUpperCase();

        arrCauTraLoi.set(index, goiY);

        for (int i = 0; i < arrGoiY.size(); i++) {
            if (goiY.equalsIgnoreCase(arrGoiY.get(i))) {
                arrGoiY.set(i, "");
                break;
            }
        }

        adapterCauTraLoi.notifyDataSetChanged();
        adapterGoiY.notifyDataSetChanged();

        nguoiDung.setTien(nguoiDung.getTien() - 5);
        model.luuThongTin();
        txtTienNguoiDung.setText(model.getNguoiDung().getTien() + "");

        checkWin();
    }

    private void chucNangDoiCauHoi() {
        model.layThongTin();
        NguoiDung nguoiDung = model.getNguoiDung();

        if (nguoiDung.getTien() < 10) {
            Toast.makeText(this, "Hết tiền rồi má", Toast.LENGTH_SHORT).show();
            return;
        }

        taoManChoi();

        nguoiDung.setTien(nguoiDung.getTien() - 10);
        model.luuThongTin();
        txtTienNguoiDung.setText(model.getNguoiDung().getTien() + "");
    }

}