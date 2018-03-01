package ad.relaxation;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第一次启动引导页 ViewPrage:显示图片 圆点指示器  跳过按钮  立即开启按钮
 * 0.编写布局文件   初始化引导页面资源
 * 1.设置全屏显示getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
 * 2.构建适配器--装在页卡
 * 3.给ViewPrage设置适配器
 * 4.初始化圆点指示器
 * 5.实现页卡选中事件--动态显示：立即开启按钮与圆点指示器的动态切换
 * 6.给跳过，立即开启按钮设置事件
 */
public class Start_lead_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //初始化引导页面资源
    private static final int[] START_PAGER = {
            R.drawable.splarsh0, R.drawable.splarsh1,
            R.drawable.splarsh2, R.drawable.splarsh3,
    };


    //记录上一个圆点指示器
    private  int dor_position;

    //获取ViewPrage对象
    @Bind(R.id.start_lead_image)
    ViewPager mViewPager;

    //立即开启按钮
    @Bind(R.id.at_once_start)
    ImageView at_once_start;

    //圆点指示器  显示容器
    @Bind(R.id.start_page_indicator)
    LinearLayout dor_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示顶部信息  全屏显示引导页面
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_start_lead);
        ButterKnife.bind(this);
        //设置适配器
        mViewPager.setAdapter(new MyPagerAdapter());
        //实现页卡选中事件
        mViewPager.setOnPageChangeListener(this);

        Dotmarkerinit();  //初始化圆点指示器
    }

    /**
     * 初始化圆点指示器
     */
    private void Dotmarkerinit() {
        View view;  //声明远点指示器容器对象
        for (int i=0;i<START_PAGER.length;i++){
            view=new View(this);

            //获取LinearLayout布局对象   设置圆点大小
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(15,15);

            //除第一个圆点外都设置外边距
            if(i!=0){
                layoutParams.leftMargin=15;
            }

            //给LinearLayout布局管理器重新设置圆点大小/边距
            view.setLayoutParams(layoutParams);
            //设置每一个圆点选中/非选中状态样式
            view.setBackgroundResource(R.drawable.dor_marker_selected);
            view.setEnabled(i==0); //默认第一个选中
            //将动态创建的圆点指示器添加到指定显示容器中
            dor_marker.addView(view);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //判断是否显示立即开启
        at_once_start.setVisibility(position != START_PAGER.length - 1 ? View.GONE : View.VISIBLE);

        //获取当前选中的圆点
        View dor_true = dor_marker.getChildAt(position);
        dor_true.setEnabled(true);  //设置选中

        //获取上一个选中的圆点
        View dor_false= dor_marker.getChildAt(dor_position);
        dor_false.setEnabled(false);  //设置非选中状态
        // 记录当前选中的圆点
        dor_position=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 构建适配器
     */
    class MyPagerAdapter extends PagerAdapter {

        /**
         * 获取页卡数量
         *
         * @return
         */
        @Override
        public int getCount() {
            return START_PAGER.length;
        }

        /**
         * 装载一项页卡
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //创建ImageView用来显示每一项页卡的图片
            ImageView imageView = new ImageView(container.getContext());
            //设置页卡对应显示的图片
            imageView.setBackgroundResource(START_PAGER[position]);
            //将图片添加到要显示的容器中
            container.addView(imageView);
            return imageView;
        }

        /**
         * 判断当前object对象是否是一个View对象
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
         * 销毁页卡
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

    /**
     * 进入主页
     * @param view
     */
    @OnClick({R.id.skip, R.id.at_once_start})
    void OnClick(View view) {
        //跳转到启动页
        Intent intent=new Intent(this,MainActivity.class);
        switch (view.getId()) {
            case R.id.skip:
                startActivity(intent);
                finish();
                break;
            case R.id.at_once_start:
                startActivity(intent);
                finish();
                break;
        }
    }

}
