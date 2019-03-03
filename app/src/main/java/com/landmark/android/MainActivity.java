package com.landmark.android;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.landmark.android.adapter.ViewPagerAdapter;
import com.landmark.android.allinterfaces.GetProductDetailsPresenterImpl;
import com.landmark.android.allinterfaces.IGetProductDetailsPresenter;
import com.landmark.android.allinterfaces.IGetProductDetailsView;
import com.landmark.android.network.response.ProductResModel;
public class MainActivity extends AppCompatActivity implements IGetProductDetailsView {

    public final String TAG = MainActivity.class.getSimpleName();

    ViewPagerAdapter adapter;
    ViewPager viewPager;
    TextView title;
    private ImageView[] ivArrayDotsPager;
    private LinearLayout llPagerDots;
    RadioGroup currencyType;

    IGetProductDetailsPresenter iGetProductDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        title = findViewById(R.id.title);
        llPagerDots = (LinearLayout) findViewById(R.id.pager_dots);
        currencyType = findViewById(R.id.currencyType);

        iGetProductDetailsPresenter = new GetProductDetailsPresenterImpl(this);


        if (AppUtils.isNetworkAvailable(MainActivity.this)) {
            iGetProductDetailsPresenter.onGetProductDetails();
        } else {
            Toast.makeText(this, getString(com.landmark.android.R.string.check_connection), Toast.LENGTH_SHORT).show();
        }


        //Change the currency type
        currencyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.inr:
                        if (adapter != null) {
                            adapter.setCurrencyType(CurrencyType.INR);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.aed:
                        if (adapter != null) {
                            adapter.setCurrencyType(CurrencyType.AED);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.sar:
                        if (adapter != null) {
                            adapter.setCurrencyType(CurrencyType.SAR);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected_item);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected_item);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //Add the dots
    private void setupPagerIndidcatorDots(int size) {
        ivArrayDotsPager = new ImageView[size];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected_item);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
            llPagerDots.bringToFront();
        }
    }

    //on api success
    @Override
    public void onGetProductDetailsSuccess(ProductResModel productResModel) {
        title.setText(productResModel.getTitle());

        adapter = new ViewPagerAdapter(MainActivity.this, productResModel.getProducts());
        viewPager.setAdapter(adapter);
        adapter.setCurrencyType(CurrencyType.INR);
        adapter.notifyDataSetChanged();

        //to set the number of dots
        setupPagerIndidcatorDots(productResModel.getProducts().size());
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_item);
    }

    //on api fail
    @Override
    public void onGetProductDetailsError() {
        Toast.makeText(MainActivity.this, getString(com.landmark.android.R.string.something_wrong), Toast.LENGTH_SHORT).show();
    }
}
