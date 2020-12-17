package com.lk.community.jsbridge;

import com.modularity.common.annotation.KeepNotProguard;

@KeepNotProguard
public interface BridgeHandler {
	
	void handler(String data, CallBackFunction function);

}
