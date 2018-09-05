package com.example.wangjingyun.componentbasesdk.recyview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sicheng.ydjw.commonsdk.recyview.recycleview.HeadTailRecycleView;


/**
 * 分割线
 * Created by wangjingyun on 2017/8/11.
 *
 */

public class LinnerItemDecoration extends RecyclerView.ItemDecoration{

    private Drawable mDrawable;

    public LinnerItemDecoration(Context context,int mDrawable){

        this.mDrawable=context.getResources().getDrawable(mDrawable);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        //多布局recycleview
        if(parent instanceof HeadTailRecycleView){


            HeadTailRecycleView headTailRecycleView=(HeadTailRecycleView)parent;


            int childCount=parent.getLayoutManager().getItemCount();

            Rect rect=new Rect();

            rect.left=parent.getPaddingLeft();

            rect.right=parent.getWidth()-parent.getPaddingRight();

            for(int i=headTailRecycleView.getHeadViewSize()+1;i<childCount-headTailRecycleView.getFooterViewSize();i++){

                View childAt = parent.getChildAt(i);

                if(childAt!=null){

                    rect.bottom=childAt.getTop();
                    rect.top=childAt.getTop()-mDrawable.getIntrinsicHeight();
                    mDrawable.setBounds(rect);
                    mDrawable.draw(c);
                }
            }
        }else{
            //单布局 recycyleview
            int childCount=parent.getLayoutManager().getItemCount();

            Rect rect=new Rect();

            rect.left=parent.getPaddingLeft();

            rect.right=parent.getWidth()-parent.getPaddingRight();

            for(int i=1;i<childCount;i++){

                View childAt = parent.getChildAt(i);
                if(childAt!=null){

                    rect.bottom=childAt.getTop();
                    rect.top=childAt.getTop()-mDrawable.getIntrinsicHeight();
                    mDrawable.setBounds(rect);
                    mDrawable.draw(c);
                }


            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);

        if(parent instanceof HeadTailRecycleView){

            HeadTailRecycleView headTailRecycleView=(HeadTailRecycleView)parent;

            int childCount=headTailRecycleView.getLayoutManager().getItemCount();

            if(position>headTailRecycleView.getHeadViewSize()&&position<childCount-headTailRecycleView.getFooterViewSize()){

                outRect.top=mDrawable.getIntrinsicHeight();

            }

        }else{

            if(position!=0){

                outRect.top=mDrawable.getIntrinsicHeight();

            }


        }


    }
}
