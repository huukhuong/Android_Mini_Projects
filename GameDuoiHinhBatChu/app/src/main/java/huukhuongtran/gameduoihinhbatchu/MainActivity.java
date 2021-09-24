package huukhuongtran.gameduoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import huukhuongtran.gameduoihinhbatchu.API.DATA;
import huukhuongtran.gameduoihinhbatchu.API.GetCauHoi;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Animation scaledown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        btnPlay = findViewById(R.id.btnPlay);

        new GetCauHoi(MainActivity.this).execute();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.startAnimation(scaleup);
                btnPlay.startAnimation(scaledown);

                CountDownTimer count = new CountDownTimer(200, 10) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        playGame(v);
                    }
                }.start();
            }
        });
    }

    public void playGame(View view) {
        if (DATA.getData().arrCauHoi.size() > 0) {
            startActivity(new Intent(MainActivity.this, PlayGameActivity.class));
        }
    }

}