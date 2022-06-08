package kr.ac.tukorea.sgp22.nerverendingjump.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.game.Doodle;
import kr.ac.tukorea.sgp22.nerverendingjump.game.MainGame;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (GameView.view != null)
        {
            GameView.view.pauseGame();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (GameView.view != null)
        {
            GameView.view.resumeGame();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        GameView.view = null;
        MainGame.clear();
    }
}