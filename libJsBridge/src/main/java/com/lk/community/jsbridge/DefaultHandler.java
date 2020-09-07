package com.lk.community.jsbridge;

public class DefaultHandler implements BridgeHandler{

	@Override
	public void handler(String data, CallBackFunction function) {
		if(function != null){
			function.onCallBack("DefaultHandler response data");
		}
	}

}
