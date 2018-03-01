package ad.relaxation.baseinterface;

import ad.relaxation.servicerapi.ServiceApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/10/15 0015.
 * 所有网络请求服务基类
 */

public class BaseBiz {

    protected   ServiceApi serviceApi;

    public BaseBiz() {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServiceApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        //创建网络请求服务
        serviceApi = retrofit.create(ServiceApi.class);
    }
}
