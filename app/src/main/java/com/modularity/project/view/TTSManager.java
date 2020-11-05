package com.modularity.project.view;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.modularity.common.utils.managers.manager.LogManager;

import java.util.Locale;

public class TTSManager {
    private TextToSpeech textToSpeech = null;//创建自带语音对象

    public TTSManager(Context context){
        initTTS(context);
    }
    private void initTTS(Context context) {
        //实例化自带语音对象
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    textToSpeech.setPitch(1.0f);//方法用来控制音调
                    textToSpeech.setSpeechRate(1.0f);//用来控制语速

                    //判断是否支持下面两种语言
                    int result1 = textToSpeech.setLanguage(Locale.US);
                    int result2 = textToSpeech.setLanguage(Locale.SIMPLIFIED_CHINESE);
                    boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
                    boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);

                    LogManager.iTag("tts", "US支持否：" + a + "\nzh-CN支持否：" + b);

                } else {
                    LogManager.iTag("tts", "数据丢失，或者不支持");
                }

            }
        });

    }

    public void startTTS(String data) {
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        if (textToSpeech!=null) {
            textToSpeech.setPitch(1.0f);
            // 设置语速
            textToSpeech.setSpeechRate(0.3f);
            textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);//输入中文，若不支持的设备则不会读出来
        }

    }

    public void stopTTS() {
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }
}
