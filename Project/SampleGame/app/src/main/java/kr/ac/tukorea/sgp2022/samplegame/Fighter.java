package kr.ac.tukorea.sgp2022.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Fighter
{
    private static Bitmap bitmap;
    private static Rect srcRest = new Rect();
    private Rect dstRect = new Rect();

    public Fighter()
    {
        dstRect.set(0,0,200,200);
        if (bitmap == null)
        {
            Resources res = GameView.view.getResources();
            bitmap  = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            srcRest.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void update()
    {

    }

    public void draw(Canvas canvas)
    {
        canvas .drawBitmap(bitmap, srcRest, dstRect, null);
    }

    public void setPosition(int x, int y)
    {
        int radius = dstRect.width()/2;
        dstRect.set(x - radius, y - radius,
                x + radius, y + radius);
    }
}
