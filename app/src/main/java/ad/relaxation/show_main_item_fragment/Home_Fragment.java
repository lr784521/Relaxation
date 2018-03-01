package ad.relaxation.show_main_item_fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import ad.relaxation.MainActivity;
import ad.relaxation.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * 首页
 */
public class Home_Fragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    //定义轮播图片
    private static final int[] IMG_RUNNABLE= {
        R.drawable.history_banner,R.drawable.main_banner,
        R.drawable.simple_health,R.drawable.time_head,R.drawable.simple_story
    };




    //顶部页卡
    @Bind(R.id.main_top_img)
    ViewPager pager;
    //圆点指示器
    @Bind(R.id.ll_dor)
    LinearLayout lldor;
    private int pretion;        //记录上一个点
    private View view;
    private Handler handler=new Handler();
    private int index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this,view);
            //设置适配器
            pager.setAdapter(new MyPageAdlder());
            pager.addOnPageChangeListener(this);


            //圆点指示器
            initdot();
            //图片轮播
            looper();
        }
        return view;

    }


    private void initdot() {

        View view;  //声明圆点容器对象
        for (int i = 0; i < IMG_RUNNABLE.length; i++) {
            view = new View(mContext);
            //获取LinearLayout布局对象  设置圆点大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            //除第一个圆点外都设置外边距
            if (i != 0) {
                params.leftMargin = 15;
            }
            //给LinearLayout布局管理器重新设置圆点大小/边距
            view.setLayoutParams(params);
            //设置每一个点的背景
            view.setBackgroundResource(R.drawable.shape_select);
            view.setEnabled(i == 0);  //设置默认第一个选中
            //将动态创建的圆点指示器添加到指定显示容器中
            lldor.addView(view);
        }
    }

    //图片轮播
    private void looper() {
        handler.postDelayed(runnable,10000);  //每三秒执行一次
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            pager.setCurrentItem(pager.getCurrentItem()+1);   //当前图片+1
            handler.postDelayed(runnable,10000);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % IMG_RUNNABLE.length;   //用当前下标和数据源长度取余
        //获取当前选中的圆点
        View view = lldor.getChildAt(newPosition);
        view.setEnabled(true);
        //获取上一个圆点
        View childAt = lldor.getChildAt(pretion);
        childAt.setEnabled(false);
        pretion = newPosition;  //将当前选中的点赋给上一个点变量
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //解决Fragment加载的问题    做侧滑菜单
    /**
     * fragment 显示状态改变
     *
     * @param isVisible 不可见 -> 可见
     */
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            Toast.makeText(mContext, "" + index, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * fragment 第一次显示
     */
    @Override
    protected void onFragmentFirstVisible() {

    }

    /**
     * 构造适配器
     */
    class MyPageAdlder extends PagerAdapter {

        /**
         * 获取数据源数量
         *
         * @return
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;   //无限大 XX
        }

        /**
         * 判断当前Object对象是否是一个View对象
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 装载每一项页卡的数据源
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int newPosition = position % IMG_RUNNABLE.length;   //用当前下标和数据源长度取余
            //创建ImageView对象    (创建容器将要显示的view对象)
            ImageView view = new ImageView(container.getContext());
            view.setBackgroundResource(IMG_RUNNABLE[newPosition]);  //设置背景图

            //将view添加到容器对象中
            container.addView(view);
            return view;
        }

        /**
         * 销毁一个页卡对象
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
