package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class ScoreUI
{
    private static final String TAG = ScoreUI.class.getSimpleName();
    public static ScoreUI singleton;

    private final SharedPreferences pref = GameView.view.getContext().getSharedPreferences("pref", Activity.MODE_PRIVATE);
    private final SharedPreferences.Editor editor = pref.edit();

    private Sprite ui, btn_done;
    private Background bg;

    private boolean isPressDone;

    private Paint paint = new Paint();

    private int[] scoreList = null;

    public static ScoreUI getInstance()
    {
        if (singleton == null)
        {
            singleton = new ScoreUI();
        }
        return singleton;
    }

    public static void clear()
    {
        singleton = null;
    }


    public void init()
    {
        scoreList = new int[5];

        for (int i = 0; i < 5; ++i)
        {
            scoreList[i] = pref.getInt("Place" + i, 0);
        }

        ui = new Sprite(Metrics.width/2, Metrics.height/2, R.mipmap.score_bg);
        btn_done = new Sprite(Metrics.width/2 + (Metrics.size(R.dimen.btn_play_w) / 2), Metrics.height  - Metrics.size(R.dimen.btn_play_y), Metrics.size(R.dimen.btn_play_w), Metrics.size(R.dimen.btn_play_h), R.mipmap.btn_done);
        isPressDone = false;

        bg = new Background(R.mipmap.background);

        paint.setColor(Color.BLACK);
        paint.setTextSize(140);
    }

    public void scoreUpdate(int score)
    {
        Log.d(TAG, "scoreUpdate: " + score);

        int index = -1;
        for (int i = 0; i < 5; ++i)
        {
            if (scoreList[i] < score)
            {
                index = i;
                break;
            }
        }

        if (index == -1)
            return;

        for (int i = 4; i > index; --i)
        {
            scoreList[i] = scoreList[i - 1];
        }
        scoreList[index] = score;

        for (int i = 0; i < 5; ++i)
        {
            editor.putInt("Place" + i, scoreList[i]).apply();
        }
    }

    public void update(int elapsed)
    {
    }

    public void draw(Canvas canvas)
    {
        bg.draw(canvas);
        ui.draw(canvas);

        btn_done.draw(canvas);

        canvas.drawText("1st ", 200, Metrics.height/2 - 380, paint);
        canvas.drawText("2nd ", 200, Metrics.height/2 - 200, paint);
        canvas.drawText("3rd ", 200, Metrics.height/2 - 20, paint);
        canvas.drawText("4th ", 200, Metrics.height/2 + 160, paint);
        canvas.drawText("5th ", 200, Metrics.height/2 + 340, paint);

        canvas.drawText(String.valueOf(scoreList[0]), 480, Metrics.height/2 - 380, paint);
        canvas.drawText(String.valueOf(scoreList[1]), 480, Metrics.height/2 - 200, paint);
        canvas.drawText(String.valueOf(scoreList[2]), 480, Metrics.height/2 - 20, paint);
        canvas.drawText(String.valueOf(scoreList[3]), 480, Metrics.height/2 + 160, paint);
        canvas.drawText(String.valueOf(scoreList[4]), 480, Metrics.height/2 + 340, paint);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if(CollisionHelper.PointCheck(btn_done, x, y))
                {
                    btn_done.changeSprite(R.mipmap.btn_done_on);
                    isPressDone = true;
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (isPressDone)
                {
                    btn_done.changeSprite(R.mipmap.btn_done);
                    isPressDone = false;
                    GameView.view.changeScene(GameView.Scene.lobby);
                }
                return true;
        }

        return false;
    }
}
