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
    private static final int BALL_COUNT = 10;
    private long previousTimeMillis;
    private int framesPerSecond;
    private Paint fpsPaint = new Paint();
    private ArrayList<Ball> balls = new ArrayList<>();
    private Fighter fighter;

    public static GameView view;

    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    private void initView()
    {
        view = this;

        Random random = new Random();
        for (int i = 0; i < BALL_COUNT; ++i)
        {
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            Ball ball = new Ball(dx, dy);
            balls.add(ball);
        }

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(70);

        fighter = new Fighter();

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimenanos)
    {
        long now = currentTimenanos;
        int elpased = (int) (now - previousTimeMillis);
        if (elpased != 0)
        {
            framesPerSecond = 1_000_000_000 / elpased;
            previousTimeMillis = now;
            update();
            invalidate();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update()
    {
        balls.forEach(Ball::update);
        //fighter.update();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        for (Ball ball : balls)
        {
            ball.draw(canvas);
        }
        fighter.draw(canvas);
        canvas.drawText("FPS: " + framesPerSecond, 100, 100, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();

        switch(action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setPosition(x,y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}