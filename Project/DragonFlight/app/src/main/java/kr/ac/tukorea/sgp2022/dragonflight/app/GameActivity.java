package kr.ac.tukorea.sgp2022.dragonflight.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.sgp2022.dragonflight.framework.GameView;
import kr.ac.tukorea.sgp2022.dragonflight.game.MainGame;

public class GameActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, null));
    }

    @Override
    protected void onPause()
    {
        if (GameView.view != null)
        {
            GameView.view.pauseGame();
        }
        super.onPause();
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
        GameView.view = null;
        MainGame.clear();
        super.onDestroy();
    }
}