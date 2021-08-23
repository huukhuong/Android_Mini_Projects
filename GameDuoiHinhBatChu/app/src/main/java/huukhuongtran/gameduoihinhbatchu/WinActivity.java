package huukhuongtran.gameduoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class WinActivity extends AppCompatActivity {

    ImageButton btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Intent intent = getIntent();
        String dapAn = intent.getStringExtra("dapAn").toUpperCase();

        TextView txtDapAn = findViewById(R.id.txtDapAn);
        txtDapAn.setText(dapAn);

        TextView txtChucMung = findViewById(R.id.txtChucMung);
        ArrayList<String> str = new ArrayList<>();
        str.add("Siêu cấp vippro");
        str.add("Siêu hạng");
        str.add("Đẳng cấp mãi mãi");
        str.add("Vua giải đố");
        str.add("Đỉnh cao");
        Random rd = new Random();
        txtChucMung.setText(str.get(rd.nextInt(5)));

        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Animation scaledown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnContinue.startAnimation(scaledown);
                btnContinue.startAnimation(scaleup);
                CountDownTimer count = new CountDownTimer(200, 10) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        choiTiep(v);
                    }
                }.start();
            }
        });

    }

    public void choiTiep(View view) {
        finish();
    }

}