package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameObject;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;

public class MonsterGenerator extends GameObject
{
    private static final String TAG = MonsterGenerator.class.getSimpleName();

    private static MainGame game;

    private static final Random random = new Random();

    public MonsterGenerator()
    {
        game = MainGame.getInstance();

        random.setSeed(System.currentTimeMillis());
    }

    @Override
    public void update()
    {
        Monster.Type type = (Monster.Type.values()[random.nextInt(Monster.Type.COUNT.ordinal())]);
        float percentage = random.nextFloat();

        if (percentage <= type.getPercentage())
        {
            float x = random.nextInt(Metrics.width);
            float y = random.nextInt(Metrics.height);

            y -= ((float)Metrics.height);

            Monster monster = Monster.get(x, y, type);
            game.add(MainGame.Layer.monster, monster);

            Log.d(TAG, "update: ");
        }
    }

    @Override
    public void draw(Canvas canvas)
    {

    }
}
