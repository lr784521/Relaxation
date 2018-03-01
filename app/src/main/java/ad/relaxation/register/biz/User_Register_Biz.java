package ad.relaxation.register.biz;

import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public interface User_Register_Biz {
    void user_register(User user,user_Register_Listener listener);

    interface user_Register_Listener{
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
