package kr.ac.tukorea.sgp2022.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp2022.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.sgp2022.dragonflight.framework.GameObject;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Metrics;
import kr.ac.tukorea.sgp2022.dragonflight.R;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Recyclable;
import kr.ac.tukorea.sgp2022.dragonflight.framework.RecycleBin;

public class Bullet implements GameObject, BoxCollidable, Recyclable
{
    private static final String TAG = Bullet.class.getSimpleName();
    protected float x, y;
    protected final float length;
    protected final float dy;

    protected static Paint paint;
    protected static float laserWidth;
    private float power;
    protected RectF boundingRect = new RectF();

    public static Bullet get(float x, float y, float power)
    {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if (bullet != null)
        {
            //Bullet bullet = recycleBin.remove(0);
            bullet.set(x, y, power);
            return bullet;
        }
        return new Bullet(x, y, power);
    }

    private void set(float x, float y, float power)
    {
        {
            this.x = x;
            this.y = y;
            this.power = power;
        }
    }

    private Bullet(float x, float y, float power)
    {
        this.x = x;
        this.y = y;
        this.length = Metrics.size(R.dimen.laser_length);
        this.dy = -Metrics.size(R.dimen.laser_speed);
        this.power = power;
        if (paint == null)
        {
            paint = new Paint();
            paint.setColor(Color.RED);
            laserWidth = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(laserWidth);
        }
        Log.d(TAG, "Created: " + this);
    }

    @Override
    public void update()
    {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;

        float hw = laserWidth / 2;
        boundingRect.set(x - hw, y, x + hw, y - length);

        if (y < 0)
        {
            MainGame.getInstance().remove(this);
            //recycleBin.add(this);
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawLine(x, y, x, y - length, paint);
    }

    @Override
    public RectF getBoundingRect()
    {
        return boundingRect;
    }

    @Override
    public void finish()
    {

    }

    public float getPower()
    {
        return power;
    }
}
