package kr.ac.tukorea.sgp2022.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class GameView extends View implements Choreographer.FrameCallback
{
    private static final String TAG = GameView.class.getSimpleName();

    private long previousTimeMillis;
    private int framesPerSecond;
    private Paint fpsPaint = new Paint();


    public static GameView view;
    private boolean iniitialized;

    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        //initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.width = w;
        Metrics.height = h;

        if (!iniitialized)
        {
            initView();
            iniitialized = true;
        }
    }

    private void initView()
    {
        view = this;

        MainGame game = MainGame.getInstance();
        game.init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(70);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimenanos)
    {
        long now = currentTimenanos;
        int elapsed = (int) (now - previousTimeMillis);
        if (elapsed != 0)
        {
            framesPerSecond = 1_000_000_000 / elapsed;
            previousTimeMillis = now;
            MainGame.getInstance().update(elapsed);
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        MainGame.getInstance().draw(canvas);

        canvas.drawText("FPS: " + framesPerSecond, 100, 100, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return MainGame.getInstance().onTouchEvent(event);
    }
}