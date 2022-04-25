package kr.ac.tukorea.sgp2022.dragonflight.game;

import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.sgp2022.dragonflight.R;
import kr.ac.tukorea.sgp2022.dragonflight.framework.AnimSprite;
import kr.ac.tukorea.sgp2022.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Metrics;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Sprite;

public class Enemy extends AnimSprite implements BoxCollidable
{
    private final int level;
    private static float size;
    private static float inset;
    protected float dy;
    protected RectF boundingRect = new RectF();

    static protected int[] BITMAP_IDS = {
            R.mipmap.enemy_01, R.mipmap.enemy_02, R.mipmap.enemy_03, R.mipmap.enemy_04, R.mipmap.enemy_05,
            R.mipmap.enemy_06, R.mipmap.enemy_07, R.mipmap.enemy_08, R.mipmap.enemy_09, R.mipmap.enemy_10,
            R.mipmap.enemy_11, R.mipmap.enemy_12, R.mipmap.enemy_13, R.mipmap.enemy_14, R.mipmap.enemy_15,
            R.mipmap.enemy_16, R.mipmap.enemy_17, R.mipmap.enemy_18, R.mipmap.enemy_19, R.mipmap.enemy_20,
    };

    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = BITMAP_IDS.length;


    public Enemy(int level, float x, float speed) {
//        super(x, 0, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x, -size/2, size, size, BITMAP_IDS[level - 1], 6, 0);
        this.level = level;
        dy = speed;
    }

    public static void setSize(float enemySize)
    {
        Enemy.size = enemySize;
        Enemy.inset = enemySize/16;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset, inset);
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public RectF getBoundingRect()
    {
        return boundingRect;
    }
}
