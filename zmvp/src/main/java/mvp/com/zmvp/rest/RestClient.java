package mvp.com.zmvp.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvp.com.zmvp.rest.api.ApiServiceUser;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created by Administrator
 * date 2015/6/9
 * Description: 请求数据客户端
 */
public class RestClient {

    private static class Holder {
        static final RestClient instance = new RestClient();
    }

    public static RestClient g() {
        return Holder.instance;
    }

    private Retrofit mRetrofit;

    private Gson mGson;

    private ApiServiceUser mApiServiceUser;


    public synchronized Gson getGson() {
        if (mGson == null) {
            mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        }
        return mGson;
    }



    private RestClient() {
        loadAdapter();
    }

//    public class SessionRequestInterceptor implements RequestInterceptor {
//        @Override
//        public void intercept(RequestFacade request) {
//            request.addHeader("User-Agent",
//                    new StringBuilder("MobileApp-Android:")
//                            .append(android.os.Build.MODEL)
//                            .append('_')
//                            .append(android.os.Build.VERSION.RELEASE)
//                            .toString());
//
//            String authKey = EntityUserInfo.g().getAuthKey();
//            if (!TextUtils.isEmpty(authKey)) {
//                request.addHeader("Authorization", "App " + authKey);
//            }
//            request.addHeader("Content-type", "application/json;charset=UTF-8");
//        }
//    }

    private void loadAdapter() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 读取超时
        builder.readTimeout(30_1000, TimeUnit.MILLISECONDS);
        // 写入超时
        builder.writeTimeout(30_1000, TimeUnit.MILLISECONDS);

//        //Header Authorization
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request.Builder newBuilder = chain.request().newBuilder();
//                newBuilder.addHeader("Authorization", new StringBuilder()
//                        .append(UserInfoEntity.getInstance().getTokenType())
//                        .append(" ")// 必须有空格
//                        .append(UserInfoEntity.getInstance().getToken())
//                        .toString());
//                return chain.proceed(newBuilder.build());
//            }
//        });

        /**
         * 日记拦截
         */
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);


        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_ADDRESS)
                .addConverterFactory(new NConverter())
                .client(builder.build())

                .build();

    }


    //格式化此类型时间 /Date(-62135596800000)/
    private static class DateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
            Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
            Matcher matcher = pattern.matcher(json.getAsJsonPrimitive().getAsString());
            String result = matcher.replaceAll("$2");
            return new Date(Long.valueOf(result));
        }
    }


    private ApiServiceUser getApiServiceUser() {
        if (null == mApiServiceUser) {
            mApiServiceUser = mRetrofit.create(ApiServiceUser.class);
        }
        return mApiServiceUser;
    }

    public static ApiServiceUser getApiUser() {
        return g().getApiServiceUser();
    }


    public class NConverter extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = getGson().getAdapter(TypeToken.get(type));
            return new NGsonResponseBodyConverter<>(getGson(), adapter);
        }
    }

    class NGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        NGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }


}
