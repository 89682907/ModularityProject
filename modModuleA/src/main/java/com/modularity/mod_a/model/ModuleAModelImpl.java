package com.modularity.mod_a.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.modularity.mod_a.bean.ModuleARequestBean;
import com.modularity.mod_a.bean.ModuleAResponseBean;
import com.modularity.mod_a.IModuleAStatics;
import com.modularity.mod_a.bean.TestResBean;
import com.modularity.perfectionRetrofit.PerfectionCallBack;
import com.modularity.perfectionRetrofit.PerfectionRetrofit;
import com.modularity.perfectionRetrofit.exception.PerfectionThrowable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jishen on 2017/12/25.
 */

public class ModuleAModelImpl implements IModuleAModel {


    private PerfectionRetrofit    retrofit;
    private Context               mContext;
    private IModuleAModelListener mListener;

    public ModuleAModelImpl(Context context, IModuleAModelListener mListener) {
        this.mContext = context;
        this.mListener = mListener;
    }

    @Override
    public void request() {
        test();
    }

    private void test() {
//        TestReqBean reqBean = new TestReqBean();
//        reqBean.setName("jishen001");
//        reqBean.setPassword("jishen001");
        Map<String, String> parmasMap = new HashMap<>();
        parmasMap.put("name", "jishen001");
        parmasMap.put("password", "jishen001");
        Map<String, String> header = new HashMap<>();
        header.put("appType", "android");
        retrofit = new PerfectionRetrofit.Builder()
                .baseUrl("http://entropy-qa.op.laikang.com/")
                .addHeader(header)
                .build();
        retrofit.requestGet("test/login", parmasMap, new PerfectionCallBack<TestResBean>() {
            @Override
            public void onStart() {
                if (mListener != null) {
                    mListener.showLoad();
                }
            }

            @Override
            public void onComplete() {
                if (mListener != null) {
                    mListener.dismissLoad();
                }
            }

            @Override
            public void onSuccess(TestResBean data) {
                if (data != null) {
                    Log.i("jishen", data.getData());
                }
            }

            @Override
            public void onError(PerfectionThrowable e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void post() {
        ModuleARequestBean bean = new ModuleARequestBean();
        bean.setUnits("metric");
        bean.setCnt(7);
        bean.setMode("json");
        bean.setAPPID("15646a06818f61f7b8d7823ca833e1ce");
        bean.setQ("");

        Map<String, String> header = new HashMap<>();
        header.put("appType", "android");
        retrofit = new PerfectionRetrofit.Builder()
                .baseUrl(IModuleAStatics.MODULE_A_BASE_URL)
                .addHeader(header)
                .build();
        retrofit.requestPost(IModuleAStatics.MODULE_A_TEST_URL, bean, new PerfectionCallBack<ModuleAResponseBean>() {
            @Override
            public void onStart() {
                if (mListener != null) {
                    mListener.showLoad();
                }
            }

            @Override
            public void onComplete() {
                if (mListener != null) {
                    mListener.dismissLoad();
                }
            }

            @Override
            public void onSuccess(ModuleAResponseBean data) {
                if (mListener != null) {
                    if (data != null && data.isSuccess()) {
                        mListener.onRequest(true, data);
                    } else {
                        mListener.onRequest(false, null);
                    }
                }
            }

            @Override
            public void onError(PerfectionThrowable e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void destroy() {
        mListener = null;
        if (retrofit != null) {
            retrofit.cancelRequest();
        }
    }
}
