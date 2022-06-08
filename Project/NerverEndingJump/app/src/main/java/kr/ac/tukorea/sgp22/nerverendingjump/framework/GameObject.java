package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import android.graphics.Canvas;

public abstract class GameObject extends CollisionHelper
{
    public abstract void update();
    public abstract void draw(Canvas canvas);
}
