package ad.relaxation.login.biz.bizimpl;

import ad.relaxation.baseinterface.BaseBiz;
import ad.relaxation.login.biz.User_login_Biz;
import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class User_Login_Biz_Impl extends BaseBiz implements User_login_Biz {

    @Override
    public void userLogin(User user, final OnLoginListener listener) {
        serviceApi.login(user.getName(),user.getPassword()).enqueue(new Callback<IntentResult<User>>() {
            @Override
            public void onResponse(Call<IntentResult<User>> call, Response<IntentResult<User>> response) {
                if (response.isSuccessful()) {
                    listener.onResponse(response.body());
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<IntentResult<User>> call, Throwable throwable) {
                listener.onFailure(throwable.getMessage());
            }
        });
    }
}
