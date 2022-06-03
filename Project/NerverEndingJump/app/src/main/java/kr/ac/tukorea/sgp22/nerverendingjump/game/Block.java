package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Bitmap;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.BitmapPool;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class Block extends Sprite
{
    public enum Type
    {
        normal, fake, vertical, horizon, singleUse, COUNT
    }

    private static final ArrayList<Bitmap> blockImage = new ArrayList<>();
    private Type blockType;

    public Block(int type, float x, float y, float width, float height)
    {
        super(x, y, width, height, R.mipmap.block_green);

        if (blockImage.isEmpty())
        {
            blockImage.add(Type.normal.ordinal(), BitmapPool.get(R.mipmap.block_green));
            // Add remain blocks
        }
    }

    @Override
    public void update()
    {
        Doodle player = MainGame.getInstance().getDoodle();
        if (player.isFalling() && CollisionCheck(player, this))
        {
            player.jumping();
        }

    }
}
