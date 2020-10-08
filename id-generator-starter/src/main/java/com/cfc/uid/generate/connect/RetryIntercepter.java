package com.cfc.uid.generate.connect;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author zhangliang
 * @date 2020/10/8
 */
public class RetryIntercepter implements Interceptor {

    //最大重试次数
    public int maxRetry;

    //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    private int retryNum = 0;

    public RetryIntercepter(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("retryNum=" + retryNum);
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            System.out.println("retryNum=" + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}
