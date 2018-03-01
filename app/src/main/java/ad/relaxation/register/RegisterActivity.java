package ad.relaxation.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ad.relaxation.R;
import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;
import ad.relaxation.register.presenter.Register_Presenter;
import ad.relaxation.register.view.Register_View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements Register_View {

    private static final String TAG = "RegisterActivity";
    //声明注册控制层
    private Register_Presenter mRegister_presenter;

    @Bind({R.id.user_register_name,R.id.user_register_pwd})
    List<EditText> user_register_name_and_pwd;
    private String name;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mRegister_presenter=new Register_Presenter(this);
    }

    @OnClick({R.id.back_login,R.id.user_register})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back_login:
                RegisterActivity.this.finish();
                break;
            case R.id.user_register:
                name = user_register_name_and_pwd.get(0).getText().toString();
                pwd = user_register_name_and_pwd.get(1).getText().toString();
                if(name.isEmpty() || pwd.isEmpty()){
                    Toast.makeText(this, "请输入用户名和密码!", Toast.LENGTH_SHORT).show();
                }else {
                    User user=new User(name, pwd);
                    mRegister_presenter.userRegister(user);
                }
                break;
        }
    }

    //声明本地广播Action地址
    public static final String LOCAL_REGISTER_CAST = "SAVE_USER_REGISTER_INFO";
    @Override
    public void onRegisterSuccess(IntentResult result) {
        Toast.makeText(this, result.msg, Toast.LENGTH_SHORT).show();
        //保存账号密码
        SharedPreferences userinfo = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = userinfo.edit();
        edit.putString("user_name",name);
        edit.putString("user_pwd",pwd);
        edit.commit();

        //发送本地广播通知用户页面更新数据
        Intent local=new Intent(LOCAL_REGISTER_CAST);
        Bundle bundle=new Bundle();
        bundle.putSerializable("register_userinfo",result);
        local.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(local);
        RegisterActivity.this.finish();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onViewFailure(IntentResult result) {
        Toast.makeText(this, result.msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServerFailure(String e) {
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }

}
