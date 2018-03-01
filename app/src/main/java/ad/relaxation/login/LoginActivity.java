package ad.relaxation.login;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

import ad.relaxation.R;
import ad.relaxation.login.presenter.User_persenter;
import ad.relaxation.login.view.Login_View;
import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;
import ad.relaxation.register.RegisterActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements Login_View {

    @Bind({R.id.user_login_name, R.id.user_login_pwd})
    List<EditText> user_name_or_pwd;

    @Bind(R.id.video_view)
    VideoView mVideoView;
    //声明管理层对象
    private User_persenter mUser_persenter;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //启用返回箭头
        //设置全屏显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initVideo();  //初始化数据
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("登录");
        mDialog.setMessage("请稍候。。。");
        mUser_persenter = new User_persenter(this);
        IntentFilter filter=new IntentFilter();
        filter.addAction(RegisterActivity.LOCAL_REGISTER_CAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }
    /**
     * 接收广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case RegisterActivity.LOCAL_REGISTER_CAST:
                    LoginActivity.this.finish();
                    break;
            }
        }
    };

    private void initVideo() {
        //设置视频路径
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //开启播放
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        //初始化用户名/密码
        SharedPreferences userinfo = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        user_name_or_pwd.get(0).setText(userinfo.getString("user_name", null));
        user_name_or_pwd.get(1).setText(userinfo.getString("user_pwd", null));
    }

    @Override
    protected void onResume() {
        //重新加载页面
        initVideo();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止视频播放
        mVideoView.stopPlayback();
    }

    @OnClick({R.id.user_login, R.id.new_user_register})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.user_login:
                String name = user_name_or_pwd.get(0).getText().toString().trim();
                String pwd = user_name_or_pwd.get(1).getText().toString().trim();
                if (name.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(this, "请输入账号/密码!", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(name, pwd);
                    mUser_persenter.login(user);
                }
                break;
            case R.id.new_user_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }

    }

    private static final String TAG = "LoginActivity";
    //声明本地广播Action地址
    public static final String LOCAL_LOGIN_CAST = "SAVE_USER_LOGIN_INFO";

    @Override
    public void onLoginSuccess(final IntentResult result) {
        Toast.makeText(this, result.msg, Toast.LENGTH_SHORT).show();
        //保存用户名/密码
        SharedPreferences userinfo = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = userinfo.edit();
        edit.putString("user_name", user_name_or_pwd.get(0).getText().toString());
        edit.putString("user_pwd", user_name_or_pwd.get(1).getText().toString());
        edit.commit();

        //登陆成功后更新User_Fragment的UI用户信息
        Intent local = new Intent(LOCAL_LOGIN_CAST);
        Bundle bundle = new Bundle();
        //存储result返回的用户数据  在User_Fragment中更新UI
        bundle.putSerializable("user", result);
        local.putExtras(bundle);
        //发送本地广播 更新主页面UI
        LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(local);

        //登陆成功返回用户页面
        LoginActivity.this.finish();
    }

    @Override
    public void showProgress() {
        mDialog.show();
    }

    @Override
    public void hideProgress() {
        mDialog.hide();
    }

    @Override
    public void onViewFailure(IntentResult result) {
        Toast.makeText(this, result.msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServerFailure(String e) {
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }

/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当点击返回键时销毁当前activity
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            LoginActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
