package kr.ac.tukorea.sgp22.nerverendingjump.framework;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Metrics
{
    public static int width;
    public static int height;

    public static float size(int dimenResID)
    {
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResID);
    }

    public static float floatValue(int dimenResID)
    {
        Resources res = GameView.view.getResources();
        TypedValue outValue = new TypedValue();
        res.getValue(dimenResID, outValue, true);
        float value = outValue.getFloat();
        return value;
    }
}
