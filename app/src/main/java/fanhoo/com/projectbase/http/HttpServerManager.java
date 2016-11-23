package fanhoo.com.projectbase.http;

import java.io.IOException;

import fanhoo.com.projectbase.base.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月22日  11:29
 * 用途 接口调用基础封装,用到了Retrofit,具体使用方法可参照官方文档.
 */

public class HttpServerManager {
    private static HttpServerManager instance;
    private OkHttpClient client;
    private ServerApi serverApi;


    public static HttpServerManager getInstance() {
        if (instance == null)
            instance = new HttpServerManager();
        return instance;
    }

    private HttpServerManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = "";  //这里自行获取token,一般在登录成功后获取到token进行保存
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("tooken", token)  //header由具体项目决定.这里只是写了个示例.
                        .build();
                return chain.proceed(request);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP_SERVER_BASE_URL)
                .build();
        serverApi = retrofit.create(ServerApi.class);
    }

    public ServerApi getServerApi() {
        return serverApi;
    }

    /**
     * 获取保存的token,具体实现由项目决定.
     *
     * @author 胡焕
     * @date 2016/11/23 11:44
     */
    private String getSavedToken() {
        return "";
    }
}
