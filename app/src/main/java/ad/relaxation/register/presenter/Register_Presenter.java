package ad.relaxation.register.presenter;

import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;
import ad.relaxation.register.biz.User_Register_Biz;
import ad.relaxation.register.biz.bizimpl.User_Register_BizImpl;
import ad.relaxation.register.view.Register_View;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class Register_Presenter {

    //声明注册View对象
    private Register_View mRegister_view;
    //声明注册Biz对象
    private User_Register_Biz biz;

    public Register_Presenter(Register_View mRegister_view) {
        this.mRegister_view = mRegister_view;
        biz=new User_Register_BizImpl();
    }

    public void userRegister(User user){
        biz.user_register(user, new User_Register_Biz.user_Register_Listener() {
            @Override
            public void onResponse(IntentResult result) {
                if(result.code==0){
                    mRegister_view.onRegisterSuccess(result);
                }else {
                    mRegister_view.onViewFailure(result);
                }
            }

            @Override
            public void onFailure(String e) {
                    mRegister_view.onServerFailure(e);
            }
        });
    }
}
