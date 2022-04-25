package kr.ac.tukorea.sgp2022.dragonflight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.sgp2022.dragonflight.game.MainGame;

public class AnimSprite extends Sprite
{
    protected final int imageWidth;
    protected final int imageHeight;
    private final int frameCount;
    private final float framePerSecond;
    private Rect srcRect = new Rect();
    //private float time;
    private long createOn;

    public AnimSprite(float x, float y, float w, float h, int bitmapResId, float framePerSecond, int frameCount)
    {
        super(x, y, w, h, bitmapResId);
        imageHeight = bitmap.getHeight();
        if (frameCount == 0)
        {
            imageWidth = imageHeight;
            frameCount = bitmap.getWidth() / imageHeight;
        }
        else
        {
            imageWidth = bitmap.getWidth() / frameCount;
        }

        this.frameCount = frameCount;
        this.framePerSecond = framePerSecond;
        srcRect.set(0, 0, imageHeight, imageWidth);

        createOn = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas)
    {
        long now = System.currentTimeMillis();
        float time = (now - createOn) / 1000.0f;
        int frameIndex = Math.round(time * framePerSecond) % frameCount;
        srcRect.set(frameIndex * imageWidth, 0, (frameIndex + 1) * imageWidth, imageHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
