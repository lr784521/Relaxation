package ad.relaxation.login.biz;


import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public interface User_login_Biz {

    void userLogin(User user,OnLoginListener listener);
    interface OnLoginListener {

        /**
         * 服务器响应
         *
         * @param result
         */
        void onResponse(IntentResult result);

        /**
         * 服务器未响应
         *
         * @param e
         */
        void onFailure(String e);

    }
}
