package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.BitmapPool;

public class Doodle extends Sprite
{
    private static final String TAG = Doodle.class.getSimpleName();
    private ArrayList<Bitmap> doodleImage;

    private float dx, dy;
    private float tx;

    private enum State
    {
        left, left_sit, right, right_sit, COUNT
    }

    public Doodle(float x, float y)
    {
        super(x, y, R.dimen.doodle_radius, R.mipmap.lik_left);

        //doodleImage.add(BitmapPool.get(R.mipmap.lik_left));
//        doodleImage.add(State.left.ordinal(), BitmapPool.get(R.mipmap.lik_left));
//        doodleImage.add(State.left_sit.ordinal(), BitmapPool.get(R.mipmap.lik_left_sit));
//        doodleImage.add(State.right.ordinal(), BitmapPool.get(R.mipmap.lik_right));
//        doodleImage.add(State.right_sit.ordinal(), BitmapPool.get(R.mipmap.lik_right_sit));
    }

    public void setDirection(float tx, float ty)
    {
        dx = Metrics.size(R.dimen.doodle_x_speed);
        if (tx < Metrics.width / 2)
        {
            dx = -dx;
        }
    }

    public void stopMove()
    {
        this.dx = 0;
    }

    @Override
    public void update()
    {
        float frameTime = MainGame.getInstance().frameTime;
        if (dx == 0)
            return;

        float dx = this.dx * frameTime;
        if ((dx > 0 && x + dx > Metrics.width) || (dx < 0 && x + dx < 0))
        {
            this.dx = 0;
        }
        else
        {
            x += dx;
        }

        dstRect.offset(dx, 0);

        // 방향에 따라 변경
        // bitmap = doodleImage[~~]
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
