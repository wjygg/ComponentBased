package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.wangjingyun.componentbased.entity.CustomDate;
import com.example.wangjingyun.componentbased.utils.DateUtil;

/**
 * 自定义 日历控件
 * Created by Administrator on 2017/11/17.
 */

public class CalendarView extends View {


    private Paint paint,mTextPaint;
    private static final int row = 5;// 6行
    private static final int column = 7;//7列
    private Row rows[]=new Row[row];// 行数组，每个元素代表一行  

    private int mCellSpace;//单元格间距
    private float mDownX;
    private float mDownY;
    private Cell mClickCell;
    private int touchSlop;

    private Integer clickX;
    private Integer clickY;
    private OnCellClickListener mCellClickListener;
    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initDate();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#FF3300"));
        paint.setAntiAlias(true);
        paint.setDither(true);


        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(45);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
    }


    private void initDate() {

        CustomDate customDate = new CustomDate();
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        int today = DateUtil.getCurrentMonthDay(); //今天

        int currentMonthDays = DateUtil.getMonthDays(customDate.year, customDate.month);//当前月的天数
        int firstDayWeek = DateUtil.getWeekDayFromDate(customDate.year, customDate.month);//第一天星期几
        boolean isCurrentMonth = false;
        if (DateUtil.isCurrentMonth(customDate)) {
            isCurrentMonth = true;
        }

        int day = 0;
        for (int j = 0; j < row; j++) {
            rows[j] = new Row(j);
            for (int i = 0; i < column; i++) {
                int position = i + j * column;//单元格位置
                //这个月的
                if (position >= firstDayWeek
                        && position < firstDayWeek + currentMonthDays) {
                    day++;
                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                            customDate, day), State.CURRENT_MONTH_DAY, i, j);
                    //今天
                    if (isCurrentMonth && day == today) {
                        CustomDate date = CustomDate.modifiDayForObject(customDate, day);
                        rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                    }

                    if (isCurrentMonth && day > today) {//如果比这个月的今天要大，表示还没到
                        rows[j].cells[i] = new Cell(
                                CustomDate.modifiDayForObject(customDate, day),
                                State.UNREACH_DAY, i, j);
                    }
                    //点击的天数
                    if(clickX!=null&&clickY!=null){
                        //点
                        if(clickX==j&&clickY==i){
                            rows[clickX].cells[clickY] = new Cell(
                                    CustomDate.modifiDayForObject(customDate, day),
                                    State.CLICKTODAY, clickY, clickX);
                        }


                    }
                }
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
   //     super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (MeasureSpec.EXACTLY == widthMode) {

            //每个单元格间距
            mCellSpace = MeasureSpec.getSize(widthMeasureSpec) / column;

            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),mCellSpace*row);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < row; i++) {
            if (rows[i] != null) {
                rows[i].drawCells(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            mDownX = event.getX();
            mDownY = event.getY();
            break;
            case MotionEvent.ACTION_UP:
            float disX = event.getX() - mDownX;
            float disY = event.getY() - mDownY;
            if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
                int col = (int) (mDownX / mCellSpace);
                int row = (int) (mDownY / mCellSpace);
                measureClickCell(col, row);
            }
            break;
            default:
                break;
        }

        return true;
    }

    /**
     * 计算点击的单元格
     *
     * @paramcol
     * @paramrow
     */
    private void measureClickCell(int col, int newRow) {
        if (col >= column || newRow >= row)
            return;
        if (mClickCell != null) {
            rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
        }
        if (rows[newRow] != null) {
            mClickCell = new Cell(rows[newRow].cells[col].date,
                    rows[newRow].cells[col].state, rows[newRow].cells[col].i,
                    rows[newRow].cells[col].j);

            CustomDate date = rows[newRow].cells[col].date;
            date.week = col;
          //  mCellClickListener.clickDate(date);

            clickX=newRow;
            clickY=col;

          //刷新界面
            initDate();
            invalidate();
        }
    }

    /**
     *组元素
     *
     *@authorwuwenjie
     *
     */
    class Row

    {
        public int j;

        Row(int j) {
        this.j = j;
    }

        public Cell[] cells = new Cell[column];

       //绘制单元格
        public void drawCells(Canvas canvas) {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] != null) {
                cells[i].drawSelf(canvas);
            }
        }
    }
 }
    /**
     *单元格元素
     *
     *@authorwuwenjie
     *
     */
    class Cell

    {
        public CustomDate date;
        public State state;
        public int i;
        public int j;

        public Cell(CustomDate date, State state, int i, int j) {
        super();
        this.date = date;
        this.state = state;
        this.i = i;
        this.j = j;
    }

        public void drawSelf(Canvas canvas) {
        switch (state) {
            case TODAY:
            //今天
            paint.setColor(Color.RED);
            canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                    (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                    paint);
            mTextPaint.setColor(Color.WHITE);
            break;
            case CURRENT_MONTH_DAY:
            //今天以前日期
            mTextPaint.setColor(Color.BLACK);
            break;
            case UNREACH_DAY:
            //还未到的天
            mTextPaint.setColor(Color.GRAY);
            break;
            case CLICKTODAY:
                paint.setColor(Color.BLUE);
                canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                        (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                        paint);
                mTextPaint.setColor(Color.WHITE);
                break;
            default:
                break;
        }
//绘制文字
        String content = date.day + "";
        canvas.drawText(content,
                (float) ((i + 0.5) * mCellSpace - mTextPaint
                        .measureText(content) / 2), (float) ((j + 0.7)
                        * mCellSpace - mTextPaint
                        .measureText(content, 0, 1) / 2), mTextPaint);
     }
    }
    /**
     *
     *@authorwuwenjie单元格的状态当前月日期，过去的月的日期，下个月的日期
     */
    enum State {
        TODAY,CURRENT_MONTH_DAY,UNREACH_DAY,CLICKTODAY;
    }

     public interface OnCellClickListener {

             void clickDate(CustomDate date);// 回调点击的日期  

          }
}
