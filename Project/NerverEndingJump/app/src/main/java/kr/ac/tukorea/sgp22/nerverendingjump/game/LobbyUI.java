package kr.ac.tukorea.sgp22.nerverendingjump.game;


import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class LobbyUI
{
    private static final String TAG = LobbyUI.class.getSimpleName();
    public static LobbyUI singleton;
    public static LobbyUI getInstance()
    {
        if (singleton == null)
        {
            singleton = new LobbyUI();
        }
        return singleton;
    }
    public static void clear()
    {
        singleton = null;
    }

    private Sprite btn_play, btn_score;
    private Background bg;
    private boolean isPressPlay, isPressScore;

    public void init()
    {
        bg = new Background(R.mipmap.background);
        btn_play = new Sprite(Metrics.width/2, Metrics.height  - Metrics.size(R.dimen.btn_play_y), Metrics.size(R.dimen.btn_play_w), Metrics.size(R.dimen.btn_play_h), R.mipmap.btn_play);
        isPressPlay = false;

        btn_score = new Sprite(Metrics.width/2, Metrics.height  - (Metrics.size(R.dimen.btn_play_y) / 2), Metrics.size(R.dimen.btn_play_w), Metrics.size(R.dimen.btn_play_h), R.mipmap.btn_scores);
        isPressScore = false;
    }

    public void update(int elapsed)
    {
    }

    public void draw(Canvas canvas)
    {
        bg.draw(canvas);
        btn_play.draw(canvas);

        btn_score.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if(CollisionHelper.PointCheck(btn_play, x, y))
                {
                    btn_play.changeSprite(R.mipmap.btn_play_press);
                    isPressPlay = true;
                }

                else if (CollisionHelper.PointCheck(btn_score, x, y))
                {
                    btn_score.changeSprite(R.mipmap.btn_scores_on);
                    isPressScore = true;
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (isPressPlay)
                {
                    btn_play.changeSprite(R.mipmap.btn_play);
                    isPressPlay = false;
                    GameView.view.changeScene(GameView.Scene.ingame);
                }

                else if (isPressScore)
                {
                    btn_score.changeSprite(R.mipmap.btn_scores);
                    isPressScore = false;
                    GameView.view.changeScene(GameView.Scene.score);
                }
                return true;
        }

        return false;
    }
}
