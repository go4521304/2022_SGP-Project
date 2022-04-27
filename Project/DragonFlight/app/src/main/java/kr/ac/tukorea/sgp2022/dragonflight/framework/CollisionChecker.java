package kr.ac.tukorea.sgp2022.dragonflight.framework;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp2022.dragonflight.game.Bullet;
import kr.ac.tukorea.sgp2022.dragonflight.game.Enemy;
import kr.ac.tukorea.sgp2022.dragonflight.game.MainGame;

public class CollisionChecker implements GameObject
{
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update()
    {
        MainGame game = MainGame.getInstance();
        ArrayList<GameObject> enemies = game.objectsAt(MainGame.Layer.enemy);
        ArrayList<GameObject> bullets = game.objectsAt(MainGame.Layer.bullet);
        for (GameObject o1 : enemies)
        {
            if (!(o1 instanceof Enemy))
            {
                continue;
            }
            Enemy enemy = (Enemy) o1;
            for (GameObject o2 : bullets)
            {
                if (!(o2 instanceof Bullet))
                {
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet))
                {
                    Log.d(TAG, "Collision !!");
                    game.remove(enemy);
                    game.remove(bullet);
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas)
    {

    }
}
