package com.tryking.EasyList.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tryking.EasyList.R;
import com.tryking.EasyList._bean.TodayEventData;
import com.tryking.EasyList.global.Constants;
import com.tryking.EasyList.utils.AppInfoUtil;
import com.tryking.EasyList.utils.AppUtils;
import com.tryking.EasyList.utils.CommonUtils;
import com.tryking.EasyList.utils.SPUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tryking on 2016/5/16.
 */
public class TodayEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TodayEventData> mData;
    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;
    private Context mCtx;
    private boolean isNeedAnim = true;
    private boolean isColorFull;

    public TodayEventAdapter(WeakReference<Context> context, List<TodayEventData> data, boolean needAnim) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext.get());
        isNeedAnim = needAnim;
    }

    public TodayEventAdapter(WeakReference<Context> context, Context ctx, List<TodayEventData> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext.get());
        mCtx = ctx;
        if ((boolean) SPUtils.get(ctx, Constants.Setting.SP_SET_COLOR_FULL, true)) {
            isColorFull = true;
        } else {
            isColorFull = false;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TodayEventData.TYPE_NO_EVENT) {
            view = mInflater.inflate(R.layout.item_today_event_no_event, parent, false);
            return new NoEventViewHolder(view);
        } else {
            view = mInflater.inflate(R.layout.item_today_event_have_event, parent, false);
            return new HaveEventViewHolder(view);
        }
    }

    public interface onNoEventItemClickListener {
        void onNoEventItemClick(int position, String hint);
    }

    private onNoEventItemClickListener mNoEventListener;

    public void setOnNoEventItemClickListener(onNoEventItemClickListener listener) {
        mNoEventListener = listener;
    }

    public interface onHaveEventItemClickListener {
        void onHaveEventItemClick(int position, String hint);
    }

    private onHaveEventItemClickListener mHaveEventListener;

    public void setOnHaveEventItemClickListener(onHaveEventItemClickListener listener) {
        mHaveEventListener = listener;
    }

    public interface onHaveEventItemLongClickListener {
        void onHaveEventItemLongClick(int position, String startTime, String endTime);
    }

    private onHaveEventItemLongClickListener mHaveEventItemLongClickListener;

    public void setOnHaveEventItemLongClickListener(onHaveEventItemLongClickListener listener) {
        mHaveEventItemLongClickListener = listener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (isNeedAnim) {
            runEnterAnimation(holder.itemView, position);
        }
        if (holder instanceof HaveEventViewHolder) {
            if (isColorFull) {
                switch (mData.get(position).getDataType()) {
                    case TodayEventData.TYPE_WORK:
                        ((HaveEventViewHolder) holder).llParent.setBackgroundResource(R.drawable.pressed_work);
                        break;
                    case TodayEventData.TYPE_AMUSE:
                        ((HaveEventViewHolder) holder).llParent.setBackgroundResource(R.drawable.pressed_amuse);
                        break;
                    case TodayEventData.TYPE_LIFE:
                        ((HaveEventViewHolder) holder).llParent.setBackgroundResource(R.drawable.pressed_life);
                        break;
                    case TodayEventData.TYPE_STUDY:
                        ((HaveEventViewHolder) holder).llParent.setBackgroundResource(R.drawable.pressed_study);
                        break;
                    default:
                        break;
                }
                ((HaveEventViewHolder) holder).startTime.setTextColor(mCtx.getResources().getColor(R.color.white));
                ((HaveEventViewHolder) holder).endTime.setTextColor(mCtx.getResources().getColor(R.color.white));
                ((HaveEventViewHolder) holder).specificEvent.setTextColor(mCtx.getResources().getColor(R.color.white));
            } else {
                //主背景设置成白色
                ((HaveEventViewHolder) holder).llParent.setBackgroundColor(mCtx.getResources().getColor(R.color.white));
                switch (mData.get(position).getDataType()) {
                    case TodayEventData.TYPE_WORK:
                        ((HaveEventViewHolder) holder).durationTime.setBackgroundResource(R.drawable.pressed_work);
                        break;
                    case TodayEventData.TYPE_AMUSE:
                        ((HaveEventViewHolder) holder).durationTime.setBackgroundResource(R.drawable.pressed_amuse);
                        break;
                    case TodayEventData.TYPE_LIFE:
                        ((HaveEventViewHolder) holder).durationTime.setBackgroundResource(R.drawable.pressed_life);
                        break;
                    case TodayEventData.TYPE_STUDY:
                        ((HaveEventViewHolder) holder).durationTime.setBackgroundResource(R.drawable.pressed_study);
                        break;
                    default:
                        break;
                }
                ((HaveEventViewHolder) holder).startTime.setTextColor(mCtx.getResources().getColor(R.color.black));
                ((HaveEventViewHolder) holder).endTime.setTextColor(mCtx.getResources().getColor(R.color.black));
                ((HaveEventViewHolder) holder).specificEvent.setTextColor(mCtx.getResources().getColor(R.color.black));
            }
            String durationTime = CommonUtils.durationTime(mData.get(position).getStartTime(), mData.get(position).getEndTime());
            int durationMinutes = CommonUtils.durationMinutes(mData.get(position).getStartTime(), mData.get(position).getEndTime());
            ((HaveEventViewHolder) holder).startTime.setText(CommonUtils.addSignToStr(mData.get(position).getStartTime()));
            ((HaveEventViewHolder) holder).endTime.setText(CommonUtils.addSignToStr(mData.get(position).getEndTime()));
            ((HaveEventViewHolder) holder).specificEvent.setText(mData.get(position).getSpecificEvent());
            ((HaveEventViewHolder) holder).durationTime.setText(durationTime);
            ((HaveEventViewHolder) holder).llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHaveEventListener != null) {
                        mHaveEventListener.onHaveEventItemClick(position, mData.get(position).getSpecificEvent());
                    }
                }
            });
            ViewGroup.LayoutParams layoutParams = ((HaveEventViewHolder) holder).llParent.getLayoutParams();
            if (durationMinutes <= 60) {
                layoutParams.height = 170;
            } else if (durationMinutes <= 360) {
                layoutParams.height = 170 + (durationMinutes - 60) * 250 / 300;
            } else {
                layoutParams.height = 420;
            }

            ((HaveEventViewHolder) holder).llParent.setLayoutParams(layoutParams);
            ((HaveEventViewHolder) holder).llParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mHaveEventItemLongClickListener != null) {
                        mHaveEventItemLongClickListener.onHaveEventItemLongClick(position, mData.get(position).getStartTime(), mData.get(position).getEndTime());
                    }
                    return true;
                }
            });
        } else if (holder instanceof NoEventViewHolder) {
            ((NoEventViewHolder) holder).hint.setText(CommonUtils.addSignToStr(mData.get(position).getStartTime()) + "\t\t-\t\t" + CommonUtils.addSignToStr(mData.get(position).getEndTime()) + "\t\t\t\t" + mData.get(position).getSpecificEvent());
            ((NoEventViewHolder) holder).hint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNoEventListener != null) {
                        mNoEventListener.onNoEventItemClick(position, mData.get(position).getSpecificEvent());
                    }
                }
            });
        }
    }

    private void runEnterAnimation(View view, int position) {
        view.setTranslationY(AppUtils.getScreenHeight(mCtx));
        view.animate()
                .translationY(0)
                .setStartDelay(10 * position)
                .setInterpolator(new DecelerateInterpolator(3.0f))
                .setDuration(400)
                .start();
    }


    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getDataType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class HaveEventViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_parent)
        LinearLayout llParent;
        @Bind(R.id.start_time)
        TextView startTime;
        @Bind(R.id.end_time)
        TextView endTime;
        @Bind(R.id.specific_event)
        TextView specificEvent;
        @Bind(R.id.tv_duration)
        TextView durationTime;

        public HaveEventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NoEventViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.hint)
        TextView hint;

        public NoEventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void refresh(List<TodayEventData> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void refreshAndChangeColorMode(List<TodayEventData> data) {
        isColorFull = !isColorFull;
        mData = data;
        notifyDataSetChanged();
    }

}
