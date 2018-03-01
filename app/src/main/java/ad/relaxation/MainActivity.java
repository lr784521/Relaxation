package ad.relaxation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ad.relaxation.show_main_item_fragment.Classify_Fragment;
import ad.relaxation.show_main_item_fragment.Collect_Fragment;
import ad.relaxation.show_main_item_fragment.Home_Fragment;
import ad.relaxation.show_main_item_fragment.User_Fragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页
 * 0.绑定ButterKnife
 * 1.创建组件TabLayout+ViewPager   并获取组件对象
 * 2.在Menu文件夹中创建选项卡菜单   并声明Menu对象
 * 3.构建适配器
 * 4初始化Menu对象
 * 5.设置适配器
 * 6.绑定组件
 * 7.设置选项卡标题和图标
 *
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    //获取TabLayout选项卡
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    //获取页面显示载体
    @Bind(R.id.vp_mainshow_Fremage)
    ViewPager mViewPager;

    //侧滑菜单
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    //声明菜单选项卡
    public Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        //构建适配器
        MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager());

        //初始化Menu  必须放在设置适配器之前 不然找不到menu文件夹中的数据
        menu=new MenuBuilder(this);
        getMenuInflater().inflate(R.menu.main_top_menu,menu); //转换menu布局文件

        //设置ViewPager预加载的问题
        mViewPager.setOffscreenPageLimit(4);
        //设置适配器
        mViewPager.setAdapter(adapter);

        //绑定选项卡和ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        //获取选项卡数量
        int count = mTabLayout.getTabCount();
        for (int i=0;i<count;i++){
            //获取选项卡每一项
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i);
            MenuItem item = menu.getItem(i);//获取菜单选项卡的每一项
            tabAt.setIcon(item.getIcon());  //设置选项卡图标
            tabAt.setText(item.getTitle());  //设置选项卡标题

        }
        //侧滑菜单点击事件
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //int id = item.getItemId();
        Toast.makeText(this, ""+item.getGroupId()+item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * 构建适配器
     */
    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * 获取每一项选项卡
         *
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position) {
                case 0:
                    fragment=new Home_Fragment();
                    break;
                case 1:
                    fragment=new Classify_Fragment();
                    break;
                case 2:
                    fragment=new Collect_Fragment();
                    break;
                case 3:
                    fragment=new User_Fragment();
                    break;
            }
            return fragment;
        }

        /**
         * 获取选项卡数量
         *
         * @return
         */
        @Override
        public int getCount() {
            return menu.size();
        }
    }



}
