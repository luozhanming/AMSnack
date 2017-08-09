package com.zhanming.amsnack.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhanming.amsnack.R;
import com.zhanming.amsnack.bean.AppUser;
import com.zhanming.amsnack.bean.Good;
import com.zhanming.amsnack.bussiness.ShoppingBussiness;
import com.zhanming.amsnack.picasso.RoundTransform;
import com.zhanming.amsnack.ui.GoodComfrimDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhanming on 2017/8/4.
 */

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.VH> {

    private Context mContext;
    private List<Good> goods;

    public GoodAdapter(Context ctx) {
        this.mContext = ctx;
    }

    public void setGoods(List<Good> data) {
        this.goods = data;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_good, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final Good good = goods.get(position);
        holder.tv_name.setText("\u3000\u3000" + good.getName());
        holder.tv_description.setText(good.getDescription());
        holder.tv_price.setText("¥" + good.getPrice() + "");
        holder.tv_sellCount.setText("共售" + good.getSellCount() + "件");
        holder.tv_store.setText("还剩" + good.getStore() + "件");
        holder.iv_addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodComfrimDialog dialog = new GoodComfrimDialog(mContext, good);
                dialog.show();
            }
        });
        Picasso.with(mContext).load(good.getImgUrl()).transform(new RoundTransform()).into(holder.iv_img);
        //设置喜欢CheckBox
        setLoveCheckBox(good, holder);
        //        setGoodLoverCount(good, holder);
        final Action1 emptyAction = new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        };
        holder.cb_love.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ShoppingBussiness.addLoveGoods(good).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(emptyAction);
                } else {
                    ShoppingBussiness.cancelLoveGoods(good)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()
                            ).subscribe(emptyAction);
                }
            }
        });

    }

    private void setGoodLoverCount(Good good, final VH holder) {
        ShoppingBussiness.queryLoveGoodUser(good)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AppUser>>() {
                    @Override
                    public void call(List<AppUser> appUsers) {
                        holder.tv_goodLike.setText("共" + appUsers.size() + "人喜欢");
                        holder.tv_goodLike.setTag(appUsers.size() + "");
                    }
                });
    }

    private void setLoveCheckBox(final Good good, final VH holder) {
        ShoppingBussiness.queryLoveGoodUser(good)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<AppUser>, Observable<AppUser>>() {
                    @Override
                    public Observable<AppUser> call(List<AppUser> appUsers) {
                        holder.tv_goodLike.setText("共"+appUsers.size()+"人喜欢");
                        return Observable.from(appUsers);
                    }
                })
                .observeOn(Schedulers.newThread())
                .filter(new Func1<AppUser, Boolean>() {
                    @Override
                    public Boolean call(AppUser appUser) {
                        return appUser.getObjectId().equals(BmobUser.getCurrentUser(AppUser.class).getObjectId());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AppUser>() {
                    @Override
                    public void call(AppUser appUser) {
                        holder.cb_love.setChecked(true);
                    }
                });


//        ShoppingBussiness.queryLoveGoods()
//                .map(new Func1<List<Good>, List<String>>() {
//                    @Override
//                    public List<String> call(List<Good> goods) {
//                        List<String> objectIds = new ArrayList<>();
//                        for (Good good : goods) {
//                            objectIds.add(good.getObjectId());
//                        }
//                        return objectIds;
//                    }
//                })
//                .flatMap(new Func1<List<String>, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(List<String> strings) {
//                        return Observable.from(strings);
//                    }
//                }).filter(new Func1<String, Boolean>() {
//            @Override
//            public Boolean call(String s) {
//                return s.equals(good.getObjectId());
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        holder.cb_love.setChecked(true);
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        if (goods != null && goods.size() > 0) {
            return goods.size();
        } else {
            return 0;
        }
    }

    public class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_good_img)
        ImageView iv_img;
        @BindView(R.id.tv_good_name)
        TextView tv_name;
        @BindView(R.id.tv_good_description)
        TextView tv_description;
        @BindView(R.id.tv_good_store)
        TextView tv_store;
        @BindView(R.id.cb_good_love)
        CheckBox cb_love;
        @BindView(R.id.iv_good_addCar)
        ImageView iv_addCar;
        @BindView(R.id.tv_good_price)
        TextView tv_price;
        @BindView(R.id.tv_good_sellCount)
        TextView tv_sellCount;
        @BindView(R.id.tv_good_like)
        TextView tv_goodLike;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
