package ad.relaxation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * 启动页
 */
public class StartInfoActivity extends AppCompatActivity {

    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_info);
        //判断用户是否第一次登陆
        initUserLoginIsTrue();
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(StartInfoActivity.this,MainActivity.class));
            handler.removeCallbacks(this);
            //销毁启动页
            StartInfoActivity.this.finish();
        }
    };

    private void initUserLoginIsTrue() {
        SharedPreferences user_login_count = getSharedPreferences("user_login_oneisTrue", Context.MODE_PRIVATE);
        boolean login_istrue = user_login_count.getBoolean("login_istrue",true);
        if(login_istrue){
            //若是第一次登陆则进入引导页  并存储登陆次数
            SharedPreferences.Editor edit = user_login_count.edit();
            edit.putBoolean("login_istrue",false);
            edit.commit();
            startActivity(new Intent(this,Start_lead_Activity.class));  //启动引导页
            //销毁主页
            StartInfoActivity.this.finish();
        }else {
            //发送消息3秒后跳转主页面
            handler.postDelayed(runnable,3000);
        }
    }

}
