
package com.modularity.perfection.exception;

import android.net.ParseException;

import com.squareup.moshi.JsonDataException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.cert.CertPathValidatorException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;


public class PerfectionException {

    private static final int UNAUTHORIZED          = 401;
    private static final int FORBIDDEN             = 403;
    private static final int NOT_FOUND             = 404;
    private static final int REQUEST_TIMEOUT       = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY           = 502;
    private static final int SERVICE_UNAVAILABLE   = 503;
    private static final int GATEWAY_TIMEOUT       = 504;
    private static final int ACCESS_DENIED         = 302;
    private static final int HANDEL_ERROR          = 417;

    public static PerfectionThrowable handleException(Exception e) {

        PerfectionThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new PerfectionThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex.setMessage("未授权的请求");
                    break;
                case FORBIDDEN:
                    ex.setMessage("禁止访问");
                    break;
                case NOT_FOUND:
                    ex.setMessage("服务器地址未找到");
                    break;
                case REQUEST_TIMEOUT:
                    ex.setMessage("请求超时");
                    break;
                case GATEWAY_TIMEOUT:
                    ex.setMessage("网关响应超时");
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.setMessage("服务器出错");
                    break;
                case BAD_GATEWAY:
                    ex.setMessage("无效的请求");
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.setMessage("服务器不可用");
                    break;
                case ACCESS_DENIED:
                    ex.setMessage("网络错误");
                    break;
                case HANDEL_ERROR:
                    ex.setMessage("接口处理失败");
                    break;
                default:
                    ex.setMessage(e.getMessage());
                    break;
            }
            ex.setCode(httpException.code());
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new PerfectionThrowable(resultException, resultException.code);
            ex.setMessage(resultException.getMessage());
            return ex;
        } else if (e instanceof JSONException
                || e instanceof ParseException
//                || e instanceof com.alibaba.fastjson.JSONException
                || e instanceof JsonDataException
        ) {
            ex = new PerfectionThrowable(e, ERROR.PARSE_ERROR);
            ex.setMessage("解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new PerfectionThrowable(e, ERROR.NETWORK_ERROR);
            ex.setMessage("连接失败");
            return ex;
        } else if (e instanceof SSLHandshakeException) {
            ex = new PerfectionThrowable(e, ERROR.SSL_ERROR);
            ex.setMessage("证书验证失败");
            return ex;
        } else if (e instanceof CertPathValidatorException) {
            ex = new PerfectionThrowable(e, ERROR.SSL_NOT_FOUND);
            ex.setMessage("证书路径没找到");
            return ex;
        } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
            ex = new PerfectionThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage("连接超时");
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new PerfectionThrowable(e, ERROR.FORMAT_ERROR);
            ex.setMessage("类型转换出错");
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new PerfectionThrowable(e, ERROR.NULL);
            ex.setMessage("数据有空");
            return ex;
        } else if (e instanceof FormatException) {
            FormatException resultException = (FormatException) e;
            ex = new PerfectionThrowable(resultException, resultException.code);
            ex.setMessage(resultException.message);
            return ex;
        } else {
            ex = new PerfectionThrowable(e, ERROR.UNKNOWN);
            String message = e.getLocalizedMessage();
            if (message.startsWith("Unable to resolve host")) {
                ex.setMessage("没有可用网络");
            } else {
                ex.setMessage(e.getLocalizedMessage());
            }
            return ex;
        }
    }


    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN       = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR   = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR    = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 证书未找到
         */
        public static final int SSL_NOT_FOUND = 1007;

        /**
         * 出现空值
         */
        public static final int NULL = -100;

        /**
         * 格式错误
         */
        public static final int FORMAT_ERROR = 1008;
    }

}

