package ad.relaxation.register.biz.bizimpl;

import ad.relaxation.baseinterface.BaseBiz;
import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;
import ad.relaxation.register.biz.User_Register_Biz;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class User_Register_BizImpl extends BaseBiz implements User_Register_Biz {
    @Override
    public void user_register(User user, final user_Register_Listener listener) {
        serviceApi.userRegister(user.getName(), user.getPassword()).enqueue(new Callback<IntentResult<User>>() {
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
