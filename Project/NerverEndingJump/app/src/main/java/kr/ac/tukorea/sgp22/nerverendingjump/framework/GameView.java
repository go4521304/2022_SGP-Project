package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.tukorea.sgp22.nerverendingjump.game.LobbyUI;
import kr.ac.tukorea.sgp22.nerverendingjump.game.MainGame;

public class GameView extends View implements Choreographer.FrameCallback
{
    private static final String TAG = GameView.class.getSimpleName();

    public static GameView view;
    private static MainGame game;
    private static LobbyUI lobby;

    private boolean initialized;
    private boolean running;

    private long prevTime;
    private int FPS;
    private Paint fpsPaint = new Paint();
    private Paint objcnt = new Paint();

    public enum Scene {lobby, ingame, pause, score};
    private Scene scene;

    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.width = w;
        Metrics.height = h;

        if (!initialized)
        {
            initView();
            initialized = true;
            running = true;
        }
    }

    private void initView()
    {
        view = this;

        scene = Scene.lobby;

        // lobby
        lobby = LobbyUI.getInstance();
        lobby.init();

        // game
        game = MainGame.getInstance();
        game.init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(80);

        objcnt.setColor(Color.BLACK);
        objcnt.setTextSize(80);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long curTime)
    {
        if (!running)
        {
            Log.d(TAG, "Running is false on doFrame()");
            return;
        }

        long now = curTime;
        int elapsed = (int)(now - prevTime);
        if (elapsed != 0)
        {
            FPS = 1_000_000_000 / elapsed;
            prevTime = now;

            switch (scene)
            {
                case lobby:
                {
                    lobby.update(elapsed);
                    break;
                }

                case ingame:
                {
                    game.update(elapsed);
                    break;
                }

                case pause:
                {
                    break;
                }

                case score:
                {
                    break;
                }

                default:
                    break;
            }

            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        switch (scene)
        {
            case lobby:
            {
                lobby.draw(canvas);
                break;
            }

            case ingame:
            {
                game.draw(canvas);
                break;
            }

            case pause:
            {
                break;
            }

            case score:
            {
                break;
            }

            default:
                break;
        }
        canvas.drawText(String.valueOf(FPS), 20, 80, fpsPaint);
        canvas.drawText(String.valueOf(game.blockCount()), Metrics.width/2, 80, objcnt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (scene)
        {
            case lobby:
            {
                return lobby.onTouchEvent(event);
            }

            case ingame:
            {
                return game.onTouchEvent(event);
            }

            case pause:
            {
                break;
            }

            case score:
            {
                break;
            }

            default:
                return false;
        }
        return false;
    }

    public void pauseGame()
    {
        running = false;

        game.getDoodle().unregisterListener();
    }

    public void resumeGame()
    {
        if (initialized && !running)
        {
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
            game.getDoodle().registerListener();
        }
    }

    public void changeScene(Scene newScene)
    {
        scene = newScene;
    }
}
