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
    protected RectF boundingRect = new RectF();

    //private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Bullet get(float x, float y)
    {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if (bullet != null)
        {
            //Bullet bullet = recycleBin.remove(0);
            bullet.set(x, y);
            return bullet;
        }
        return new Bullet(x, y);
    }

    private void set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    private Bullet(float x, float y) {
        this.x = x;
        this.y = y;
        this.length = Metrics.size(R.dimen.laser_length);
        this.dy = -Metrics.size(R.dimen.laser_speed);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            laserWidth = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(laserWidth);
        }
        Log.d(TAG, "Created: " + this);
    }

    @Override
    public void update() {
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
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x, y - length, paint);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingRect;
    }

    @Override
    public void finish()
    {

    }
}
