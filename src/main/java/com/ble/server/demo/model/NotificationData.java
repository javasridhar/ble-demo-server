package com.ble.server.demo.model;

public class NotificationData {
	
	private String to;
	private BleRawData bleRawData;
	
	public NotificationData(String to, BleRawData bleRawData) {
		this.to = to;
		this.bleRawData = bleRawData;
	}
	
	@Override
	public String toString() {
		return "NotificationData [to=" + to + ", bleRawData=" + bleRawData + "]";
	}
}
