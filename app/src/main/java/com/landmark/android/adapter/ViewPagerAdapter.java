package com.landmark.android.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.landmark.android.AppUtils;
import com.landmark.android.BellmanFord;
import com.landmark.android.CurrencyType;
import com.landmark.android.R;
import com.landmark.android.network.response.ProductResModel;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    List<ProductResModel.ProductsBean> productRes;
    LayoutInflater inflater;
    CurrencyType currencyType;
    double[] arr;

    public ViewPagerAdapter(Context context, List<ProductResModel.ProductsBean> productRes) {
        this.context = context;
        this.productRes = productRes;
        currencyType = CurrencyType.INR ;
    }

    @Override
    public int getCount() {
        return productRes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ConstraintLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ProductResModel.ProductsBean products = productRes.get(position);

        // Declare Variables
        TextView productNameTv;
        TextView productPriceTv;
        ImageView productIv;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.product_item, container, false);

        // Locate the TextViews in viewpager_item.xml
        productNameTv = (TextView) itemView.findViewById(R.id.productName);
        productPriceTv = (TextView) itemView.findViewById(R.id.productPrice);
        productIv = (ImageView) itemView.findViewById(R.id.productIv);

        productNameTv.setText(products.getName());
        BellmanFord  bellmanFord = new BellmanFord(CurrencyType.valueOf(products.getCurrency()).ordinal()+1, AppUtils.getMultiples());
        arr = bellmanFord.getConversion();
        switch (currencyType){
            case INR:
                if (products.getCurrency().equalsIgnoreCase(CurrencyType.INR.name())) {
                    productPriceTv.setText("₹ " + AppUtils.convertDoubleToInt(Double.parseDouble(products.getPrice())));
                } else {
                    double productPrice = Double.parseDouble(products.getPrice()) * arr[currencyType.ordinal()+1];
                    productPriceTv.setText("₹ " + AppUtils.convertDoubleToInt(productPrice));
                }
                break;
            case AED:
                if (products.getCurrency().equalsIgnoreCase(CurrencyType.AED.name())) {
                    productPriceTv.setText("AED " + AppUtils.convertDoubleToInt(Double.parseDouble(products.getPrice())));
                } else {
                    double productPrice = Double.parseDouble(products.getPrice()) * arr[currencyType.ordinal()+1];
                    productPriceTv.setText("AED " + AppUtils.convertDoubleToInt(productPrice));
                }

                break;
            case SAR:
                if (products.getCurrency().equalsIgnoreCase(CurrencyType.SAR.name())) {
                    productPriceTv.setText("SAR " + AppUtils.convertDoubleToInt(Double.parseDouble(products.getPrice())));
                } else {
                    double productPrice = Double.parseDouble(products.getPrice()) * arr[currencyType.ordinal()+1];
                    productPriceTv.setText("SAR " + AppUtils.convertDoubleToInt(productPrice));
                }
                break;
        }
        Glide.with(context).load(products.getUrl()).into(productIv);
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((ConstraintLayout) object);

    }

    public void setCurrencyType(CurrencyType currencyType){
       this.currencyType = currencyType;
    }


}