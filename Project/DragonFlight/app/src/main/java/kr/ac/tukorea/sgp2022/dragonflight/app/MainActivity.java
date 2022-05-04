package kr.ac.tukorea.sgp2022.dragonflight.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import kr.ac.tukorea.sgp2022.dragonflight.R;
import kr.ac.tukorea.sgp2022.dragonflight.framework.GameView;
import kr.ac.tukorea.sgp2022.dragonflight.game.MainGame;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnStart(View view)
    {
//        Log.d("tag", "onBtnStart()");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}