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

public class GameView extends View implements Choreographer.FrameCallback
{
    private static final String TAG = GameView.class.getSimpleName();

    public static GameView view;
    private boolean initialized;
    private boolean running;

    private long prevTime;
    private int FPS;
    private Paint fpsPaint = new Paint();

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
            initialized = true;
            running = true;
        }
    }

    private void initView()
    {
        view = this;

        MainGame game = MainGame.getInstance();
        game.init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);

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
            MainGame.getInstance().update(elapsed);
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        MainGame.getInstance().draw(canvas);
        canvas.drawText("FPS " + FPS, 10, 100, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }
}
