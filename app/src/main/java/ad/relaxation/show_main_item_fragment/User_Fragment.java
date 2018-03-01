package ad.relaxation.show_main_item_fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ad.relaxation.MainActivity;
import ad.relaxation.R;
import ad.relaxation.login.LoginActivity;
import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;
import ad.relaxation.register.RegisterActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * 用户
 */
public class User_Fragment extends BaseFragment {

    private static final String TAG = "User_Fragment";
    private View view;

    //显示用户信息
    @Bind({R.id.user_name, R.id.user_credits, R.id.user_day})
    List<TextView> userInfoViews;
    //声明功能菜单对象
    private Menu menu;

    @Bind(R.id.user_function_list)
    RecyclerView mRecyclerView;
    private SharedPreferences userSP;
    private int widthPixels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //判断view是否等于空 解决预加载问题 防止数据重复加载
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user, container, false);
            ButterKnife.bind(this, view);

            //初始化功能菜单
            menu = new MenuBuilder(mContext);
            //获取activity对象  来加载Menu菜单文件
            MainActivity activity = (MainActivity) mContext;
            //加载Menu菜单文件
            activity.getMenuInflater().inflate(R.menu.user_function_menu, menu);

            //给RecyclerView控件设置布局管理器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            //设置适配器
            mRecyclerView.setAdapter(new MyRecyclerViewAdapter());

            //创建意图过滤器
            IntentFilter filter = new IntentFilter();
            filter.addAction(LoginActivity.LOCAL_LOGIN_CAST);  //接收更新用户信息广播
            filter.addAction(RegisterActivity.LOCAL_REGISTER_CAST);  //接收更新用户信息广播
            //注册本地广播
            LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, filter);
            //创建SharedPreferences用户第一次登陆后缓存用户信息
            userSP = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
            initUserInfo();  //初始化加载用户信息
            //获取屏幕宽度
            widthPixels = getResources().getDisplayMetrics().widthPixels;
        }
        return view;
    }

    private void initUserInfo() {
        userInfoViews.get(0).setText(userSP.getString("user_sp_name", "您还没有登陆!"));
        userInfoViews.get(1).setText(userSP.getString("user_sp_cmd", "0"));
        userInfoViews.get(2).setText(userSP.getString("user_sp_day", "0"));
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    /**
     * 构建适配器
     */
    class MyRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        /**
         * 加载显示样式的布局
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.user_function_item_activity, null));
        }

        /**
         * 绑定holder对象  刷新每一项数据
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setItem(menu.getItem(position));
            holder.refreshViewItem();
        }

        /**
         * 获取菜单总数
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return menu.size();
        }
    }

    /**
     * 构建HolderView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //获取要显示的控件对象
        @Bind(R.id.fun_name)
        TextView fun_name;
        @Bind(R.id.fun_img)
        ImageView fun_img;

        MenuItem item;  //声明菜单每一项对象

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);   //每一项的点击事件
        }

        //获取每一项数据
        public void setItem(MenuItem item) {
            this.item = item;
        }

        //刷新每一项数据
        void refreshViewItem() {
            fun_name.setText(item.getTitle());
            fun_img.setImageDrawable(item.getIcon());
        }

        @Override
        public void onClick(View view) {
            switch (getAdapterPosition()) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    loadUser_fun_Seeting();
                    break;
            }
        }
    }
    //设置选项点击事件
    private void loadUser_fun_Seeting() {
        //转换设置布局文件
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_setting_menu, null);
        //创建PopupWindow对象
        PopupWindow window=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置常用参数
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));  //设置背景
        window.setFocusable(true); //设置允许获得焦点
        window.setSplitTouchEnabled(true);  //点击其他区域消失
        window.setAnimationStyle(android.R.style.Animation_InputMethod);  //设置出现动画，平滑

        //显示菜单
        window.showAtLocation(view, Gravity.BOTTOM,0,0);
    }
    /**
     * 登陆页面跳转
     */
    @OnClick(R.id.user_login_register)
    void OnClick() {
        startActivity(new Intent(mContext, LoginActivity.class));
    }

    /**
     * 注册接收更新用户信息广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case LoginActivity.LOCAL_LOGIN_CAST:
                    //获取返回结果   更新用户显示信息
                    IntentResult<User> user = (IntentResult<User>) intent.getSerializableExtra("user");
                    String name = user.data.getName();
                    String cmd = String.valueOf(user.cmd);
                    String day = String.valueOf(user.data.getUserid());
                    userInfoViews.get(0).setText(name);
                    userInfoViews.get(1).setText(cmd);
                    userInfoViews.get(2).setText(day);
                    //缓存用户信息
                    SharedPreferences.Editor edit = userSP.edit();
                    edit.putString("user_sp_name", name);
                    edit.putString("user_sp_cmd", cmd);
                    edit.putString("user_sp_day", day);
                    edit.commit();
                    break;
                case RegisterActivity.LOCAL_REGISTER_CAST:
                    //获取返回结果   更新用户显示信息
                    IntentResult<User> regUser = (IntentResult<User>) intent.getSerializableExtra("register_userinfo");
                    String uname = regUser.data.getName();
                    String ucmd = String.valueOf(regUser.cmd);
                    String uday = String.valueOf(regUser.data.getUserid());
                    userInfoViews.get(0).setText(uname);
                    userInfoViews.get(1).setText(ucmd);
                    userInfoViews.get(2).setText(uday);
                    //缓存用户信息
                    SharedPreferences.Editor uedit = userSP.edit();
                    uedit.putString("user_sp_name", uname);
                    uedit.putString("user_sp_cmd", ucmd);
                    uedit.putString("user_sp_day", uday);
                    uedit.commit();
                    break;
            }
        }
    };

}


