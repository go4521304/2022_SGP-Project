package kr.ac.tukorea.sgp2022.imageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] resId = new int[]{
            R.mipmap.card_1,
            R.mipmap.card_2,
            R.mipmap.card_3,
            R.mipmap.card_4,
            R.mipmap.card_5,
    };

    private int pageNumber;
    private TextView pageTextView;
    private ImageView contentImageView;
    private ImageButton btnPrev, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        pageTextView = findViewById(R.id.pageTextView);
        contentImageView = findViewById(R.id.contentImageView);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        SetPage(1);
    }

    public void OnBtnPrev(View view)
    {
        Log.d(TAG, "Prev Pressed");

        SetPage(pageNumber - 1);
    }

    public void OnBtnNext(View view)
    {
        Log.d(TAG, "Next Pressed");

        SetPage(pageNumber + 1);
    }

    private void SetPage(int page)
    {
        if (page <= 1)
        {
            btnPrev.setEnabled(false);
            btnNext.setEnabled(true);
        } else if (page >= 5)
        {
            btnPrev.setEnabled(true);
            btnNext.setEnabled(false);
        } else
        {
            btnPrev.setEnabled(true);
            btnNext.setEnabled(true);
        }

        pageNumber = page;
        String text = page + " / " + 5;
        pageTextView.setText(text);

        contentImageView.setImageResource(resId[pageNumber - 1]);
    }
}