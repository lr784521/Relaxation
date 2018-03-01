package ad.relaxation.login.presenter;


import ad.relaxation.login.biz.bizimpl.User_Login_Biz_Impl;
import ad.relaxation.login.biz.User_login_Biz;
import ad.relaxation.login.view.Login_View;
import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class User_persenter {
    /**
     * 抽象视图借口
     */
    private Login_View loginView;

    /**
     * 抽象BIZ接口
     */
    private User_login_Biz login_biz;

    public User_persenter(Login_View loginView) {
        this.loginView = loginView;
        login_biz = new User_Login_Biz_Impl();
    }

    public void login(User user) {
       // loginView.showProgress();
        login_biz.userLogin(user, new User_login_Biz.OnLoginListener() {
            @Override
            public void onResponse(IntentResult result) {
                //loginView.hideProgress();
                if (result.code == 0) {
                    loginView.onLoginSuccess(result);
                } else {
                    loginView.onViewFailure(result);
                }
            }

            @Override
            public void onFailure(String e) {
               // loginView.hideProgress();
                loginView.onServerFailure(e);
            }
        });
    }
}
