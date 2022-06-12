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
        normal(R.mipmap.block_green), vertical(R.mipmap.block_blue),
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

    private float[] bound = new float[2];
    private final float speed = 300;

    private int dir = -1;

    public Block()
    {
        super(0, 0, Metrics.size(R.dimen.block_width), Metrics.size(R.dimen.block_height), R.mipmap.block_green);

        game = MainGame.getInstance();
        player = game.getDoodle();
    }

    public static Block get(int type, float x, float y)
    {
        Block block = (Block) Recycle.get(Block.class);
        if (block == null)
        {
            block = new Block();
        }
        block.set(type, x, y);
        return block;
    }

    private void set(int type, float x, float y)
    {
        this.blockType = Type.values()[type];
        this.x = x;
        this.y = y;
        this.setDstRect(Metrics.size(R.dimen.block_width), Metrics.size(R.dimen.block_height));
        changeSprite(blockType.getImageID());

        if (type == Type.vertical.ordinal())
        {
            bound[0] = y - (float)(Metrics.size(R.dimen.block_width) * 1.5);
            bound[1] = y + (float)(Metrics.size(R.dimen.block_width) * 1.5);
        }
        else if (type == Type.horizon.ordinal())
        {
            bound[0] = x - (float)(Metrics.size(R.dimen.block_width) * 1.5);
            bound[1] = x + (float)(Metrics.size(R.dimen.block_width) * 1.5);
        }
    }

    @Override
    public void update()
    {
        if (player.isFalling() && CollisionHelper.CollisionCheck(player, this))
        {
            player.jumping();

            if (this.blockType == Type.singleUse)
            {
                game.remove(MainGame.Layer.block, this);
            }
        }

        float dx = 0, dy = 0;

        if (blockType == Type.vertical)
        {
            float t_dy = dir * speed * game.frameTime;
            dy += t_dy;
            bound[0] += game.getScrollVal();
            bound[1] += game.getScrollVal();
        }
        else if (blockType == Type.horizon)
        {
            float t_dx = dir * speed * game.frameTime;
            dx += t_dx;
        }

        x += dx;

        dy += game.getScrollVal();
        y += dy;

        dstRect.offset(dx, dy);

        if (blockType == Type.horizon && (x < 0 || x > Metrics.width || x < bound[0] || x> bound[1]))
        {
            dir *= -1;
        }

        // 삭제 할지 검사
        if (blockType == Type.vertical)
        {
            if (bound[0] > Metrics.height)
            {
                game.remove(MainGame.Layer.block, this);
            }
            else if (y < bound[0] || y > bound[1])
            {
                dir *= -1;
            }
        }

        else if (y > Metrics.height)
        {
            game.remove(MainGame.Layer.block, this);
        }
    }
}