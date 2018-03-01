package ad.relaxation.baseinterface;

import ad.relaxation.model.IntentResult;

/**
 * Created by Administrator on 2017/9/28.
 */

public interface BaseView {

    void showProgress();

    void hideProgress();

    /**
     * 服务器返回的不是 200
     * <p>
     * 服务器返回  200 400 403 405 500
     *
     */
    void onViewFailure(IntentResult result);

    /**
     * 服务断开
     *
     * @param e
     */
    void onServerFailure(String e);
}
