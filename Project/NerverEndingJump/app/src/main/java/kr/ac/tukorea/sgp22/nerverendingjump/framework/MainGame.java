package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import android.graphics.Canvas;

public class MainGame
{
    private static MainGame singleton;

    public static MainGame getInstance()
    {
        if (singleton == null)
        {
            singleton = new MainGame();
        }
        return singleton;
    }
    
    public void init()
    {
    }

    public void update(int elapsed)
    {
    }


    public void draw(Canvas canvas)
    {
    }
}
