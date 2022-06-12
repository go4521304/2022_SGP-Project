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
import kr.ac.tukorea.sgp22.nerverendingjump.game.PauseUI;
import kr.ac.tukorea.sgp22.nerverendingjump.game.ScoreUI;

public class GameView extends View implements Choreographer.FrameCallback
{
    private static final String TAG = GameView.class.getSimpleName();

    public static GameView view;
    private static MainGame game;
    private static LobbyUI lobby;
    private static ScoreUI scoreBoard;
    private static PauseUI pause;

    private boolean initialized;
    private boolean running;

    private long prevTime;
    private int FPS;
    private Paint fpsPaint = new Paint();

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

        // scorebaord
        scoreBoard = ScoreUI.getInstance();
        scoreBoard.init();

        // pause
        pause = PauseUI.getInstance();
        pause.init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(80);

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
                    pause.update(elapsed);
                    break;
                }

                case score:
                {
                    scoreBoard.update(elapsed);
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
                pause.draw(canvas);
                break;
            }

            case score:
            {
                scoreBoard.draw(canvas);
                break;
            }

            default:
                break;
        }
        canvas.drawText(String.valueOf(FPS), 20, 80, fpsPaint);
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
                return pause.onTouchEvent(event);
            }

            case score:
            {
                return scoreBoard.onTouchEvent(event);
            }

            default:
                return false;
        }
    }

    public void pauseGame()
    {
        running = false;

        if (scene == Scene.ingame)
        {
            game.getDoodle().unregisterListener();
            changeScene(Scene.pause);
        }
    }

    public void resumeGame()
    {
        if (initialized && !running &&  scene == Scene.ingame)
        {
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
            game.getDoodle().registerListener();
        }
    }

    public void endGame()
    {
        scoreBoard.scoreUpdate((int) game.score);
        game.init();
    }

    public void changeScene(Scene newScene)
    {
        scene = newScene;
    }
}
