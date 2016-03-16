package com.example.joo.scribblesonthebook.data.manager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.BookDetailResponse;
import com.example.joo.scribblesonthebook.data.BookDetailSuccess;
import com.example.joo.scribblesonthebook.data.BookListResponse;
import com.example.joo.scribblesonthebook.data.BookListSuccess;
import com.example.joo.scribblesonthebook.data.MonthlyReadingResponse;
import com.example.joo.scribblesonthebook.data.MonthlyReadingSuccess;
import com.example.joo.scribblesonthebook.data.RecommendationResponse;
import com.example.joo.scribblesonthebook.data.RecommendationSuccess;
import com.example.joo.scribblesonthebook.data.ReferPersonalResponse;
import com.example.joo.scribblesonthebook.data.ReferPersonalSuccess;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordResponse;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordSuccess;
import com.example.joo.scribblesonthebook.data.RewardRecordResponse;
import com.example.joo.scribblesonthebook.data.RewardRecordSuccess;
import com.example.joo.scribblesonthebook.data.SearchingBookResponse;
import com.example.joo.scribblesonthebook.data.SearchingBookSuccess;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;
import com.example.joo.scribblesonthebook.data.vo.Success;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
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
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        String url = String.format(SEARCHING_RESULT_URL_FORMAT, URLEncoder.encode(keyword, "utf-8"), page, rows);

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

    private static final String BOOKLIST_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/bookcases/me?tense=%s&pageNum=%s";

    public Request getBookList(Context context, String tense, String page, final OnResultListener<BookListSuccess> listener) throws UnsupportedEncodingException {
        String url = String.format(BOOKLIST_URL_FORMAT, tense, page);

        final CallbackObject<BookListSuccess> callbackObject = new CallbackObject<BookListSuccess>();

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
                BookListResponse br = gson.fromJson(response.body().string(), BookListResponse.class);
                callbackObject.result = br.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }


    private static final String MONTHLY_READING_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/bookmarks/me?year=%s&month=%s";

    public Request getMonthlyReading(Context context, String year, String month, final OnResultListener<MonthlyReadingSuccess> listener) throws UnsupportedEncodingException {
        String url = String.format(MONTHLY_READING_URL_FORMAT, year, month);

        final CallbackObject<MonthlyReadingSuccess> callbackObject = new CallbackObject<MonthlyReadingSuccess>();

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
                MonthlyReadingResponse mrr = gson.fromJson(response.body().string(), MonthlyReadingResponse.class);
                callbackObject.result = mrr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String RECOMMENDATION_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/recommendations/me?pageNum=%s";

    public Request getRecommendations(Context context, String page, final OnResultListener<RecommendationSuccess> listener) throws UnsupportedEncodingException {
        String url = String.format(RECOMMENDATION_URL_FORMAT, page);

        final CallbackObject<RecommendationSuccess> callbackObject = new CallbackObject<RecommendationSuccess>();

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
                RecommendationResponse rr = gson.fromJson(response.body().string(), RecommendationResponse.class);
                callbackObject.result = rr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String SIGNUP_USER_URL_FORMAT = "https://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/users";

    public Request signupUser(Context context, String email, String password1, String password2, String nick, File path, final OnResultListener<Success> listener) throws UnsupportedEncodingException {

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        RequestBody requestBody;
        if (path != null) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.addFormDataPart("local_email", email)
                    .addFormDataPart("local_pw", password1)
                    .addFormDataPart("local_re_pw", password2)
                    .addFormDataPart("nickname", nick)
                    .addFormDataPart("user_photo_url", path.getName(), RequestBody.create(MediaType.parse("image/jpeg"), path));
            requestBody = builder.build();
        } else {
            requestBody = new FormBody.Builder()
                .add("local_email", email)
                .add("local_pw", password1)
                .add("local_re_pw", password2)
                .add("nickname", nick)
                .build();
        }
        Request request = new Request.Builder().url(SIGNUP_USER_URL_FORMAT)
                .post(requestBody)
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String LOGIN_USER_URL_FORMAT = "https://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/users/login";

    public Request userLogin(Context context, String email, String password, final OnResultListener<Success> listener) throws UnsupportedEncodingException {

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        RequestBody requestBody = new FormBody.Builder()
                .add("local_email", email)
                .add("local_pw", password)
                .build();

        /*MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("local_email", email)
                .addFormDataPart("local_pw", password);
        if (path != null) {
            builder.addFormDataPart("profile_image", path.getName(), RequestBody.create(MediaType.parse("image/jpeg"), path));
        }
        RequestBody requestBody = builder.build();*/

        Request request = new Request.Builder().url(LOGIN_USER_URL_FORMAT)
                .post(requestBody)
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String CHANGE_INTERESTS_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/users/filter";

    public Request changeInterests (Context context, String email, String password, final OnResultListener<Success> listener) throws UnsupportedEncodingException {

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        RequestBody requestBody = new FormBody.Builder()
                .add("local_email", email)
                .add("local_pw", password)
                .build();

        Request request = new Request.Builder().url(CHANGE_INTERESTS_URL_FORMAT)
                .post(requestBody)
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request getInterests (Context context, final OnResultListener<Success> listener) throws UnsupportedEncodingException {

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        Request request = new Request.Builder().url(CHANGE_INTERESTS_URL_FORMAT)
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String MENU_INFO_URL_FORMAT = "https://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/users/me";

    public Request getMenuInfo(Context context, final OnResultListener<ReferPersonalSuccess> listener) throws UnsupportedEncodingException {

        final CallbackObject<ReferPersonalSuccess> callbackObject = new CallbackObject<ReferPersonalSuccess>();

        Request request = new Request.Builder().url(MENU_INFO_URL_FORMAT)
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
                ReferPersonalResponse rpr = gson.fromJson(response.body().string(), ReferPersonalResponse.class);
                callbackObject.result = rpr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    public Request changePassword(Context context, String password1, String password2, final OnResultListener<ReferScribbleRecordSuccess> listener) throws UnsupportedEncodingException {

        final CallbackObject<ReferScribbleRecordSuccess> callbackObject = new CallbackObject<ReferScribbleRecordSuccess>();

        RequestBody requestBody = new FormBody.Builder()
                .add("local_pw", password1)
                .add("local_re_pw", password2)
                .build();

        Request request = new Request.Builder().url(MENU_INFO_URL_FORMAT)
                .put(requestBody)
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

    private static final String BOOK_DETAIL_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/%s";

    public Request getBookDetail(Context context, String isbn, final OnResultListener<BookDetailSuccess> listener) throws UnsupportedEncodingException {
        String url = String.format(BOOK_DETAIL_URL_FORMAT, isbn);

        final CallbackObject<BookDetailSuccess> callbackObject = new CallbackObject<BookDetailSuccess>();

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
                BookDetailResponse bdr = gson.fromJson(response.body().string(), BookDetailResponse.class);
                callbackObject.result = bdr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String SEND_BOOKMARK_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/%s/bookmarks";

    public Request sendBookMark(Context context, String isbn, String startPage, String endPage, final OnResultListener<Success> listener) throws UnsupportedEncodingException {
        String url = String.format(SEND_BOOKMARK_URL_FORMAT, isbn);

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        RequestBody requestBody = new FormBody.Builder()
                .add("start_page", startPage)
                .add("finish_page", endPage)
                .build();

        Request request = new Request.Builder().url(url)
                .post(requestBody)
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String WRITE_SCRIBBLE_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/:isbn/doodles";

    public Request writeScribble(Context context, String isbn, String scribblePage, String scribbleContent, String scribblePhoto, String emotionId, final OnResultListener<Success> listener) throws UnsupportedEncodingException {
        String url = String.format(WRITE_SCRIBBLE_URL_FORMAT, isbn);

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        RequestBody requestBody = new FormBody.Builder()
                .add("doodle_page", scribblePage)
                .add("text_doodle", scribbleContent)
                .add("picture_doodle", scribblePhoto)
                .add("emotion_doodle_id", emotionId)
                .build();

        Request request = new Request.Builder().url(url)
                .post(requestBody)
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String MODIFY_SCRIBBLE_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/%s/doodles/%s";

    public Request modifyScribble(Context context, String isbn, String scribblePage, String scribbleContent, String scribblePhoto, String emotionId, String scribbleId, final OnResultListener<Success> listener) throws UnsupportedEncodingException {
        String url = String.format(MODIFY_SCRIBBLE_URL_FORMAT, isbn, scribbleId);

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        RequestBody requestBody = new FormBody.Builder()
                .add("doodle_page", scribblePage)
                .add("text_doodle", scribbleContent)
                .add("picture_doodle", scribblePhoto)
                .add("emotion_doodle_id", emotionId)
                .build();

        Request request = new Request.Builder().url(url)
                .put(requestBody)
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String DELETE_SCRIBBLE_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/%s/doodles/%s";

    public Request deleteScribble(Context context, String isbn, String scribbleId, final OnResultListener<Success> listener) throws UnsupportedEncodingException {
        String url = String.format(DELETE_SCRIBBLE_URL_FORMAT, isbn, scribbleId);

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

        Request request = new Request.Builder().url(url)
                .delete()
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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String LIKE_SCRIBBLE_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/%s/doodles/%s/heart";

    public Request setScribbleLike(Context context, String isbn, String scribbleId, final OnResultListener<Success> listener) throws UnsupportedEncodingException {
        String url = String.format(LIKE_SCRIBBLE_URL_FORMAT, isbn, scribbleId);

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String ADD_BOOK_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/books/%s/bookcases/%s";

    public Request addBook(Context context, String isbn, String tense, final OnResultListener<Success> listener) throws UnsupportedEncodingException {
        String url = String.format(ADD_BOOK_URL_FORMAT, isbn, tense);

        final CallbackObject<Success> callbackObject = new CallbackObject<Success>();

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
                SimpleRequest sr = gson.fromJson(response.body().string(), SimpleRequest.class);
                callbackObject.result = sr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    private static final String REWARD_RECORD_URL_FORMAT = "http://ec2-52-79-99-227.ap-northeast-2.compute.amazonaws.com/rewards/me";

    public Request getRewardRecord(Context context, String isbn, String tense, final OnResultListener<RewardRecordSuccess> listener) throws UnsupportedEncodingException {

        final CallbackObject<RewardRecordSuccess> callbackObject = new CallbackObject<RewardRecordSuccess>();

        Request request = new Request.Builder().url(REWARD_RECORD_URL_FORMAT)
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
                RewardRecordResponse rrr = gson.fromJson(response.body().string(), RewardRecordResponse.class);
                callbackObject.result = rrr.success;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

}
