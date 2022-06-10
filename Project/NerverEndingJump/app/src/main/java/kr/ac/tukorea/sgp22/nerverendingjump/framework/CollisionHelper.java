package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import android.graphics.RectF;

public class CollisionHelper
{
    public static boolean CollisionCheck(Sprite object1, Sprite object2)
    {
        RectF r1 = object1.getRect(), r2 = object2.getRect();

        if (r1.left > r2.right) return false;
        if (r1.top > r2.bottom) return false;
        if (r1.right < r2.left) return false;
        if (r1.bottom < r2.top) return false;

        return true;
    }

    public static boolean PointCheck(Sprite object1, int px, int py)
    {
        RectF r1 = object1.getRect();

        if (r1.left > px) return false;
        if (r1.top > py) return false;
        if (r1.right < px) return false;
        if (r1.bottom < py) return false;

        return true;
    }
}
