package com.lk.community.jsbridge;

import com.modularity.common.annotation.KeepNotProguard;

@KeepNotProguard
public class DefaultHandler implements BridgeHandler{

	@Override
	public void handler(String data, CallBackFunction function) {
		if(function != null){
			function.onCallBack("DefaultHandler response data");
		}
	}

}
