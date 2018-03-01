package ad.relaxation.login.view;

import ad.relaxation.baseinterface.BaseView;
import ad.relaxation.model.IntentResult;

/**
 * Created by Administrator on 2017/10/17 0017.
 * 登陆接口
 */

public interface Login_View extends BaseView{

    /**
     * 登陆成功
     * @param result
     */
    void onLoginSuccess(IntentResult result);
}
