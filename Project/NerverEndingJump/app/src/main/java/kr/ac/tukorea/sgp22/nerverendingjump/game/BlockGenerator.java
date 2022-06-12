package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameObject;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;

public class BlockGenerator extends GameObject
{
    private static final String TAG = BlockGenerator.class.getSimpleName();
    private static MainGame game;

    private static final Random random = new Random();

    private int makeCount;

    public BlockGenerator()
    {
        game = MainGame.getInstance();

        random.setSeed(System.currentTimeMillis());

        makeCount = 0;

        Block block;
        for (int i = 0; i < 20; ++i)
        {
            float x = random.nextInt(Metrics.width);
            float y = random.nextInt(Metrics.height);

            y -= Metrics.height / 2;

            block = Block.get(Block.Type.normal.ordinal(), x, y);
            game.add(MainGame.Layer.block, block);
        }
    }

    public void update()
    {
        if (game.score > 0 && game.score < 10000)
        {
            makeCount = 30 - game.blockCount();
        } else if (game.score > 10000 && game.score < 20000)
        {
            makeCount = 28 - game.blockCount();
        } else if (game.score > 20000 && game.score < 50000)
        {
            makeCount = 26 - game.blockCount();
        }
        else if (game.score > 50000 && game.score < 100000)
        {
            makeCount = 24 - game.blockCount();
        }
        else if (game.score > 100000 && game.score < 500000)
        {
            makeCount = 22 - game.blockCount();
        }
        else
        {
            makeCount = 20 - game.blockCount();
        }

        Block block;

        for (int i = 0; i < makeCount; ++i)
        {
            float x = random.nextInt(Metrics.width);
            float y = random.nextInt(Metrics.height);

            y -= Metrics.height;

            block = Block.get(random.nextInt(Block.Type.COUNT.ordinal()), x, y);
            game.add(MainGame.Layer.block, block);
        }
    }

    @Override
    public void draw(Canvas canvas)
    {

    }
}
