package com.ble.server.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ble_raw_data")
public class BleRawData {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="raw_data")
    private String rawData;
    
    @Column(name="fcm_token")
    private String fcmToken;
    
    @Column(name="is_notification_sent")
    private boolean isNotificationSent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public boolean isNotificationSent() {
		return isNotificationSent;
	}

	public void setNotificationSent(boolean isNotificationSent) {
		this.isNotificationSent = isNotificationSent;
	}
}
