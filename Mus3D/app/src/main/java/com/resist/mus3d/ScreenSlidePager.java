package com.resist.mus3d;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resist.mus3d.slides.Slide;
import com.resist.mus3d.slides.Slide1;
import com.resist.mus3d.slides.Slide2;
import com.resist.mus3d.slides.Slide3;
import com.resist.mus3d.slides.Slide4;
import com.resist.mus3d.slides.Slide5;
import com.resist.mus3d.slides.Slide6;
import com.resist.mus3d.slides.Slide7;
import com.resist.mus3d.slides.Slide8;
import com.resist.mus3d.slides.Slide9;
import com.viewpagerindicator.CirclePageIndicator;

public class ScreenSlidePager extends FragmentActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private CirclePageIndicator circlePageIndicator;
    private Slide[] slides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        slides = new Slide[] {
			new Slide1(), new Slide2(), new Slide3(), new Slide4(), new Slide5(), new Slide6(), new Slide7(), new Slide8(), new Slide9()
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
            super.onBackPressed();
        } else {
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
            return slides.length;
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
					textView.setText(slides[position].getText());
                }
            });

            final Button startButton = (Button) itemView.findViewById(R.id.btn_Start);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(slides[position].getDrawable());

            container.addView(itemView);

            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferenceManager.getDefaultSharedPreferences(mContext).edit().putBoolean("skipTutorial", false).apply();
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
