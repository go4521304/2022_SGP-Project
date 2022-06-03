package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import android.graphics.Canvas;
import android.graphics.RectF;

public abstract class GameObject extends CollisionHelper
{
    public abstract void update();
    public abstract void draw(Canvas canvas);

    public abstract RectF getRect();
}
