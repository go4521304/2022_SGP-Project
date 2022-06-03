package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Sprite extends GameObject
{
    protected Bitmap bitmap;
    protected float x, y, radius;
    protected RectF dstRect = new RectF();

    public Sprite(float x, float y, int radiusDimenResId, int bitmapResId) {
        this.x = x;
        this.y = y;
        this.radius = Metrics.size(radiusDimenResId);
        dstRect.set(x - radius, y - radius, x + radius, y + radius);
        bitmap = BitmapPool.get(bitmapResId);
    }

    public Sprite(float x, float y, float w, float h, int bitmapResId) {
        this.x = x;
        this.y = y;
        this.radius = w / 2;
        dstRect.set(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
        bitmap = BitmapPool.get(bitmapResId);
    }

    @Override
    public void update()
    {
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public RectF getRect()
    {
        return dstRect;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setDstRectWithRadius()
    {
        dstRect.set(x - radius, y - radius, x + radius, y + radius);
    }

    public void setDstRect(float width, float height)
    {
        dstRect.set(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
    }
}
