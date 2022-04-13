package kr.ac.tukorea.sgp2022.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.sgp2022.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.sgp2022.dragonflight.framework.GameObject;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Metrics;
import kr.ac.tukorea.sgp2022.dragonflight.R;

public class Bullet implements GameObject, BoxCollidable
{
    protected float x, y;
    protected final float length;
    protected final float dy;

    protected static Paint paint;
    protected static float laserWidth;
    protected RectF boundingRect = new RectF();

    public Bullet(float x, float y) {
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
}
