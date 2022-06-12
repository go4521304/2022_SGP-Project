package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameObject;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Recycle;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class Bullet extends Sprite
{
    private static final String TAG = Bullet.class.getSimpleName();

    private static MainGame game = null;

    private static final float speed = 3000;

    public Bullet()
    {
        super(0, 0, Metrics.size(R.dimen.bullet_w), Metrics.size(R.dimen.bullet_h), R.mipmap.projectile);

        game = MainGame.getInstance();
    }

    public static Bullet get(float x, float y)
    {
        Bullet bullet = (Bullet) Recycle.get(Bullet.class);
        if (bullet == null)
        {
            bullet = new Bullet();
        }
        bullet.set(x, y);
        return bullet;
    }

    private void set(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.setDstRect(Metrics.size(R.dimen.bullet_w), Metrics.size(R.dimen.bullet_h));
    }

    @Override
    public void update()
    {
        float frametime = game.frameTime;

        float dy = (speed * -1) * frametime;
        dy += game.getScrollVal();

        this.y += dy;
        dstRect.offset(0, dy);

        ArrayList<GameObject> arr = game.getGameObject(MainGame.Layer.monster);

        for (GameObject i : arr)
        {
            Monster tmp = (Monster) i;
            if (tmp.isAttackable() && CollisionHelper.CollisionCheck(tmp, this))
            {
                game.remove(MainGame.Layer.monster, i);
                game.remove(MainGame.Layer.bullet, this);
                return;
            }
        }

        if (y < 0)
        {
            game.remove(MainGame.Layer.bullet, this);
        }
    }
}
