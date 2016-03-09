package com.example.joo.scribblesonthebook.data.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordResponse;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordSuccess;
import com.example.joo.scribblesonthebook.data.SearchingBookResponse;
import com.example.joo.scribblesonthebook.data.SearchingBookSuccess;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dongja94 on 2016-02-05.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    OkHttpClient mClient;
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        File cachefile = new File(context.getExternalCacheDir(), "mycache");
        if (!cachefile.exists()) {
            cachefile.mkdirs();
        }
        Cache cache = new Cache(cachefile, MAX_CACHE_SIZE);
        builder.cache(cache);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        try {
            disableCertificateValidation(context, builder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mClient = builder.build();
    }

    static void disableCertificateValidation(Context context, OkHttpClient.Builder builder) throws IOException {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.site);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            sc.init(null, tmf.getTrustManagers(), null);
            builder.sslSocketFactory(sc.getSocketFactory());
            builder.hostnameVerifier(hv);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


    public void cancelAll() {
        mClient.dispatcher().cancelAll();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFailure(Request request, int code, Throwable cause);
    }

    private static final int MESSAGE_SUCCESS = 0;
    private static final int MESSAGE_FAILURE = 1;

    static class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CallbackObject object = (CallbackObject) msg.obj;
            Request request = object.request;
            OnResultListener listener = object.listener;
            switch (msg.what) {
                case MESSAGE_SUCCESS:
                    listener.onSuccess(request, object.result);
                    break;
                case MESSAGE_FAILURE:
                    listener.onFailure(request, -1, object.exception);
                    break;
            }
        }
    }

    Handler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class CallbackObject<T> {
        Request request;
        T result;
        IOException exception;
        OnResultListener<T> listener;
    }

    public void cancelAll(Object tag) {

    }


    public Request testSSL(Context context, final OnResultListener<String> listener) {
        Request request = new Request.Builder().url("https://192.168.210.51:8443/test.html").build();
        final CallbackObject<String> callbackObject = new CallbackObject<String>();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callbackObject.result = response.body().string();
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;

    }

    private static final String SCRIBBLE_LIST_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/%s/doodles?page=%s&rows=%s";

    public Request getScribbleList(Context context, String isbn, String page, String rows, final OnResultListener<ReferScribbleRecordSuccess> listener) throws UnsupportedEncodingException {
        String url = String.format(SCRIBBLE_LIST_URL_FORMAT, isbn, page, rows);

        final CallbackObject<ReferScribbleRecordSuccess> callbackObject = new CallbackObject<ReferScribbleRecordSuccess>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ReferScribbleRecordResponse rsrr = gson.fromJson(response.body().string(), ReferScribbleRecordResponse.class);
                callbackObject.result = rsrr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;
    }

    private static final String SEARCHING_RESULT_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books?keyward=%s&page=%s&rows=%s";

    public Request getSearchingResult(Context context, String keyword, String page,String rows, final OnResultListener<SearchingBookSuccess> listener) throws UnsupportedEncodingException {
        String url = String.format(SEARCHING_RESULT_URL_FORMAT, keyword, page, rows);

        final CallbackObject<SearchingBookSuccess> callbackObject = new CallbackObject<SearchingBookSuccess>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FAILURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                SearchingBookResponse sbr = gson.fromJson(response.body().string(), SearchingBookResponse.class);
                callbackObject.result = sbr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

}
