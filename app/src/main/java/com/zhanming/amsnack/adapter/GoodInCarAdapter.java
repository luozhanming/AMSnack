package com.zhanming.amsnack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhanming.amsnack.R;
import com.zhanming.amsnack.bean.CarGoodCount;
import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhanming on 2017/8/6.
 */

public class GoodInCarAdapter extends RecyclerView.Adapter<GoodInCarAdapter.VH> {


    private Context mContext;
    private List<CarGoodCount> counters;

    public GoodInCarAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCounters(List<CarGoodCount> counters) {
        this.counters = counters;
        notifyDataSetChanged();
    }

    public void clearDatas(){
        this.counters.clear();
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_good_in_car, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final CarGoodCount counter = counters.get(position);
        final Good good = counter.getGood();
        Picasso.with(mContext).load(good.getImgUrl()).into(holder.iv_img);
        holder.tv_name.setText(good.getName() + "");
        holder.tv_totalPrice.setText("¥" + counter.getCount() * good.getPrice());
        holder.tv_quantity.setText(counter.getCount() + "");
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(holder.tv_quantity.getText().toString()) + 1;
                holder.tv_totalPrice.setText("¥" + count * good.getPrice());
                holder.tv_quantity.setText(count + "");
                //CarGoodCounter更新数量
                ShoppingBussiness.updateCarGoodCounter(counter,count);
            }
        });
        holder.btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(holder.tv_quantity.getText().toString());
                if (count > 0) {
                    int newCount = count - 1;
                    holder.tv_quantity.setText(newCount + "");
                    holder.tv_totalPrice.setText("¥" + newCount * good.getPrice());
                    ShoppingBussiness.updateCarGoodCounter(counter,newCount);
                } else {
                    int newCount = 0;
                    holder.tv_quantity.setText(newCount + "");
                    holder.tv_totalPrice.setText("¥" + newCount * good.getPrice());
                    ShoppingBussiness.updateCarGoodCounter(counter,newCount);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (counters != null && counters.size() > 0) {
            return counters.size();
        } else {
            return 0;
        }
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goodInCar_img)
        ImageView iv_img;
        @BindView(R.id.tv_goodInCar_name)
        TextView tv_name;
        @BindView(R.id.tv_goodInCar_totalPrice)
        TextView tv_totalPrice;
        @BindView(R.id.tv_goodInCar_quantity)
        TextView tv_quantity;
        @BindView(R.id.btn_goodInCar_add)
        ImageButton btn_add;
        @BindView(R.id.btn_goodInCar_sub)
        ImageButton btn_sub;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
