package com.resist.mus3d;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;


/**
 * Created by Thomas on 26-5-2015.
 */
public class ScreenSlidePager extends FragmentActivity {
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private CirclePageIndicator circlePageIndicator;
    private int[] tutorialImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        tutorialImages = new int[]{R.drawable.tutorial1, R.drawable.tutorial2,
                R.drawable.tutorial3, R.drawable.tutorial4, R.drawable.tutorial5,
                R.drawable.tutorial6, R.drawable.tutorial7, R.drawable.tutorial8,
                R.drawable.tutorial9
        };

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new CustomPagerAdapter(this);
        mPager.setAdapter(mPagerAdapter);


        //Bind the title indicator to the adapter
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(mPager);


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return tutorialImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
            final TextView textView = (TextView) itemView.findViewById(R.id.tutorialTextView);
            textView.setText(R.string.tutorial1);
            circlePageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {

                    Log.e("Current Postion", "" + position);
                    switch (position) {
                        case 0:
                            textView.setText(R.string.tutorial1);
                            break;
                        case 1:
                            textView.setText(R.string.tutorial2);
                            break;
                        case 2:
                            textView.setText(R.string.tutorial3);
                            break;
                        case 3:
                            textView.setText(R.string.tutorial4);
                            break;
                        case 4:
                            textView.setText(R.string.tutorial5);
                            break;
                        case 5:
                            textView.setText(R.string.tutorial6);
                            break;
                        case 6:
                            textView.setText(R.string.tutorial7);
                            break;
                        case 7:
                            textView.setText(R.string.tutorial8);
                            break;
                        case 8:
                            textView.setText(R.string.tutorial9);
                            break;
                    }

                }
            });


            Button startButton = (Button) itemView.findViewById(R.id.btn_Start);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(tutorialImages[position]);

            container.addView(itemView);


            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Splash.startTutorial = false;
                    Intent intent = new Intent(v.getContext(), Search.class);
                    v.getContext().startActivity(intent);
                    finish();


                }
            });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
