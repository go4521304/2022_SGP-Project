package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameObject;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Recycle;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class Block extends Sprite
{
    private static final String TAG = Block.class.getSimpleName();

    public enum Type
    {
        normal(R.mipmap.block_green), fake(R.mipmap.block_green), vertical(R.mipmap.block_blue),
        horizon(R.mipmap.block_bluegray), singleUse(R.mipmap.block_white), COUNT(0);

        private final int image;
        Type(int value)
        {
            image = value;
        }

        public int getImageID()
        {
            return image;
        }
    }

    private static MainGame game = null;
    private static Doodle player = null;

    private Type blockType;

    public Block(int type, float x, float y, float width, float height)
    {
        super(x, y, width, height, R.mipmap.block_green);

        if (game == null)
            game = MainGame.getInstance();
        if (player == null)
            player = game.getDoodle();

        this.blockType = Type.values()[type];
        changeSprite(blockType.getImageID());

    }

    public static Block get(int type, float x, float y, float width, float height)
    {
        Block block = (Block) Recycle.get(Block.class);
        if (block != null)
        {
            block.set(type, x, y, width, height);
            return block;
        }
        return new Block(type, x, y, width, height);
    }

    private void set(int type, float x, float y, float width, float height)
    {
        this.blockType = Type.values()[type];
        this.x = x;
        this.y = y;
        this.setDstRect(width, height);
        changeSprite(blockType.getImageID());
    }

    @Override
    public void update()
    {
        if (player.isFalling() && CollisionHelper.CollisionCheck(player, this))
        {
            player.jumping();
        }

        y += game.getScrollVal();
        dstRect.offset(0, game.getScrollVal());

        if (y > Metrics.height)
        {
            game.remove(MainGame.Layer.block, this);
        }
    }
}