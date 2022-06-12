package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class PauseUI
{
    private static final String TAG = PauseUI.class.getSimpleName();
    public static PauseUI singleton;
    public static PauseUI getInstance()
    {
        if (singleton == null)
        {
            singleton = new PauseUI();
        }
        return singleton;
    }
    public static void clear()
    {
        singleton = null;
    }

    private Sprite pause_img, btn_menu, btn_done;
    private Background bg, bg_back;
    private boolean isPressMenu, isPressDone;

    public void init()
    {
        bg = new Background(R.mipmap.background);
        bg_back = new Background(R.mipmap.pause_background);

        btn_menu = new Sprite(Metrics.width - (float)(Metrics.size(R.dimen.btn_play_w) * 0.7), Metrics.height  - Metrics.size(R.dimen.btn_play_y), Metrics.size(R.dimen.btn_play_w), Metrics.size(R.dimen.btn_play_h), R.mipmap.btn_menu);
        isPressMenu = false;

        btn_done = new Sprite(Metrics.width - (float)(Metrics.size(R.dimen.btn_play_w) * 0.7), Metrics.height  - (Metrics.size(R.dimen.btn_play_y) / 2), Metrics.size(R.dimen.btn_play_w), Metrics.size(R.dimen.btn_play_h), R.mipmap.btn_done);
        isPressDone = false;

        pause_img = new Sprite(Metrics.width/2, Metrics.height/4, R.mipmap.pause);
    }

    public void update(int elapsed)
    {
    }

    public void draw(Canvas canvas)
    {
        bg.draw(canvas);
        bg_back.draw(canvas);

        pause_img.draw(canvas);

        btn_menu.draw(canvas);
        btn_done.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if(CollisionHelper.PointCheck(btn_menu, x, y))
                {
                    btn_menu.changeSprite(R.mipmap.btn_menu_on);
                    isPressMenu = true;
                }

                else if (CollisionHelper.PointCheck(btn_done, x, y))
                {
                    btn_done.changeSprite(R.mipmap.btn_done_on);
                    isPressDone = true;
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (isPressMenu)
                {
                    btn_menu.changeSprite(R.mipmap.btn_menu);
                    isPressMenu = false;
                    MainGame.getInstance().init();
                    GameView.view.changeScene(GameView.Scene.lobby);
                }

                else if (isPressDone)
                {
                    btn_done.changeSprite(R.mipmap.btn_done);
                    isPressDone = false;
                    GameView.view.changeScene(GameView.Scene.ingame);
                }
                return true;
        }

        return false;
    }
}
