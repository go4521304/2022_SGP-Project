package kr.ac.tukorea.sgp2022.dragonflight.game;

import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.sgp2022.dragonflight.R;
import kr.ac.tukorea.sgp2022.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Metrics;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable
{
    private static float size;
    protected float dy;
    public Enemy(float x, float y, float speed) {
//        super(x, 0, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x, -size/2, size, size, R.mipmap.f_01_01);
        dy = speed;
    }

    public static void setSize(float enemySize)
    {
        Enemy.size = enemySize;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public RectF getBoundingRect()
    {
        return dstRect;
    }
}
