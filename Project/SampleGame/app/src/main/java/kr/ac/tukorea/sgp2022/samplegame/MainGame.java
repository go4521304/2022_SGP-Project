package kr.ac.tukorea.sgp2022.samplegame;

public class MainGame
{
    public static MainGame getInstance()
    {
        if (singleton == null)
        {
            singleton = new MainGame();
        }

        return singleton;
    }

    private MainGame()
    {
        
    }

    private static MainGame singleton;


}
