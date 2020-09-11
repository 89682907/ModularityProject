package com.modularity.common.expand.banner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.modularity.common.expand.banner.holder.IBannerHolderCreator;
import com.modularity.common.expand.banner.holder.BannerHolder;
import com.modularity.common.expand.banner.listener.OnItemClickListener;

import java.util.List;

public class PageAdapter<T> extends RecyclerView.Adapter<BannerHolder> {
    protected List<T>              mDates;
    private   IBannerHolderCreator mCreator;
    private   PageAdapterHelper    mHelper;
    private   boolean             canLoop;
    private   OnItemClickListener onItemClickListener;

    public PageAdapter(IBannerHolderCreator creator, List<T> datas, boolean canLoop) {
        this.mCreator = creator;
        this.mDates = datas;
        this.canLoop = canLoop;
        mHelper = new PageAdapterHelper();
    }


    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mCreator.getLayoutId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        mHelper.onCreateViewHolder(parent, itemView);
        return mCreator.createHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BannerHolder holder, int position) {
        mHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        int realPosition = position % mDates.size();
        holder.updateBanner(mDates.get(realPosition));

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnPageClickListener(realPosition));
        }
    }

    @Override
    public int getItemCount() {
        //根据模式决定长度
        if (mDates.size() == 0) return 0;
        return canLoop ? 3 * mDates.size() : mDates.size();
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public int getRealItemCount() {
        return mDates != null ? mDates.size() : 0;
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class OnPageClickListener implements View.OnClickListener {
        private int position;

        public OnPageClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(position);
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
