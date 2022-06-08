package kr.ac.tukorea.sgp22.nerverendingjump.game;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.BitmapPool;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Recycle;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class Block extends Sprite
{
    public enum Type
    {
        normal(R.mipmap.block_green), fake(R.mipmap.block_green), vertical(R.mipmap.block_green),
        horizon(R.mipmap.block_green), singleUse(R.mipmap.block_green), COUNT(0);

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

    private static final MainGame game = MainGame.getInstance();

    private Type blockType;

    public Block(int type, float x, float y, float width, float height)
    {
        super(x, y, width, height, R.mipmap.block_green);

        this.blockType = Type.values()[type];
        this.bitmap = BitmapPool.get(blockType.getImageID());

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
        this.bitmap = BitmapPool.get(blockType.getImageID());
    }

    @Override
    public void update()
    {
        Doodle player = game.getDoodle();
        if (player.isFalling() && CollisionCheck(player, this))
        {
            player.jumping();
        }

        y += game.getScrollVal();
        dstRect.offset(0, game.getScrollVal());
    }
}
