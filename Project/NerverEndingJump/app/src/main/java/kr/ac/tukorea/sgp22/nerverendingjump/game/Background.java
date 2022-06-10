package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class Background extends Sprite
{
    private final int height;

    private float dy;

    public Background(int bitmapResId)
    {
        super(Metrics.width / 2, Metrics.height / 2, Metrics.width, Metrics.height, bitmapResId);
        this.height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setDstRect(Metrics.width, height);
    }

    @Override
    public void update()
    {
        if (MainGame.getInstance().getDoodle().getY() < (float)(Metrics.height / 2))
        {
            dy = (float)(Metrics.height) * MainGame.getInstance().frameTime * 0.5f;
            this.y += dy;

            MainGame.getInstance().score += dy;
        }
        else
        {
            dy = 0;
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        int cur = (int)y % height;
        if (cur > 0) cur -= height;

        while (cur < Metrics.height)
        {
            dstRect.set(0, cur, Metrics.width, cur + height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            cur += height;
        }
    }

    public float getScrollVal() { return dy; }
}
