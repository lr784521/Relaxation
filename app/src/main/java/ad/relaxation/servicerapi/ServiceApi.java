package ad.relaxation.servicerapi;


import ad.relaxation.model.IntentResult;
import ad.relaxation.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 使用 Retrofit 进行网络请求
 * <p>
 * <p>
 * GET 获取数据
 * PUT 修改数据
 * DELETE 删除数据
 * POST 提交表单数据
 */

public interface ServiceApi {

    public static final String BASE_URL="http://192.168.42.138:8080/life/api/";

    String LOGIN_URL="login/";

    String REGISTER_URL="register/";

    /**
     * 登陆
     * @param name
     * @param pwd
     * @return
     */
    @POST(LOGIN_URL)
    @FormUrlEncoded
    Call<IntentResult<User>> login(@Field("name") String name, @Field("pwd") String pwd);




    /**
     * 注册
     * @param name
     * @param pwd
     * @return
     */
    @POST(REGISTER_URL)
    @FormUrlEncoded
    Call<IntentResult<User>> userRegister(@Field("name")String name,@Field("pwd")String pwd);




}
