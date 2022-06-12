package kr.ac.tukorea.sgp22.nerverendingjump.game;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.BitmapPool;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Recycle;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class Monster extends Sprite
{
    private static MainGame game = null;
    private static Doodle doodle = null;

    public enum Type
    {
        hole(0.002f), monster(0.003f), COUNT(2.f);

        private final float parcentage;
        Type(float value)
        {
            parcentage = value;
        }

        public float getPercentage()
        {
            return parcentage;
        }
    }

    private boolean attackable;
    private Type type;

    public Monster()
    {
        super(0, 0, Metrics.size(R.dimen.bullet_w), Metrics.size(R.dimen.bullet_h), R.mipmap.projectile);

        game = MainGame.getInstance();
        doodle = game.getDoodle();
    }

    public static Monster get(float x, float y, Type type)
    {
        Monster monster = (Monster) Recycle.get(Monster.class);
        if (monster == null)
        {
            monster = new Monster();
        }
        monster.set(x, y, type);
        return monster;
    }

    private void set(float x, float y, Type type)
    {
        this.x = x;
        this.y = y;
        this.type = type;

        switch (this.type)
        {
            case hole:
                this.bitmap = BitmapPool.get(R.mipmap.hole);
                this.setDstRect(Metrics.size(R.dimen.hole_w), Metrics.size(R.dimen.hole_h));
                attackable = false;
                break;

            case monster:
                this.bitmap = BitmapPool.get(R.mipmap.monster);
                this.setDstRect(Metrics.size(R.dimen.monster_w_1), Metrics.size(R.dimen.monster_h_1));
                attackable = true;
                break;

            default:
                attackable = false;
                break;
        }
    }

    @Override
    public void update()
    {
        float dy = 0;
        dy = game.getScrollVal();
        y += dy;
        dstRect.offset(0, dy);

        if (CollisionHelper.CollisionCheck(this, doodle))
        {
            GameView.view.endGame();
            GameView.view.changeScene(GameView.Scene.score);
            return;
        }

        else if (y > Metrics.height)
        {
            game.remove(MainGame.Layer.block, this);
        }
    }

    public boolean isAttackable() {return attackable;}

}
