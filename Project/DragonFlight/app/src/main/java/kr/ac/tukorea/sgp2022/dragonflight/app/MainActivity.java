package kr.ac.tukorea.sgp2022.dragonflight.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import kr.ac.tukorea.sgp2022.dragonflight.R;
import kr.ac.tukorea.sgp2022.dragonflight.framework.GameView;
import kr.ac.tukorea.sgp2022.dragonflight.game.MainGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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