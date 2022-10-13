package com.ble.server.demo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ble.server.demo.model.BleRawData;
import com.ble.server.demo.model.NotificationData;
import com.ble.server.demo.repository.BleRawDataRepository;
import com.google.gson.Gson;

@Service
public class BleRawDataService {
	
	@Autowired
	private BleRawDataRepository bleRawDataRepository;
	
	// CREATE 
    public BleRawData createOrInsertBleRawData(BleRawData bleRawData) {
        BleRawData bleRawDataSaved = bleRawDataRepository.save(bleRawData);
        updateBleRawData(bleRawData.getId());
        return bleRawDataSaved;
    }

    // READ ALL
    public List<BleRawData> getAllBleRawData() {
        return bleRawDataRepository.findAll();
    }
    
    // READ BY ID
    private BleRawData getBleRawDataById(Long id) {
    	return bleRawDataRepository.findById(id).get();
    }
    
    // DELETE
    public void deleteBleRawData(Long id) {
    	bleRawDataRepository.deleteById(id);
    }
    
    // UPDATE
    private void updateBleRawData(Long id) {
            BleRawData bleRawData = getBleRawDataById(id);
            bleRawData.setNotificationSent(true);
            bleRawDataRepository.save(bleRawData);       
            sendNotificationToDevice(bleRawData);
    }
    
    private void sendNotificationToDevice(BleRawData bleRawData) {
    	String url = "https://fcm.googleapis.com/fcm/send";
    	String key = "";
    	
    	String to = bleRawData.getFcmToken();
    	NotificationData data = new NotificationData(to, bleRawData);
    	
    	HttpRequest request = HttpRequest.newBuilder()
    			  .uri(URI.create(url))
    			  .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(data)))
    			  .header("Authorization", "key=" + key)
    			  .header("Content-Type", "application/json")
//    			    Base64.getEncoder().encodeToString(("baeldung:123456").getBytes()))
    			  .build();
    	
    	HttpClient client = HttpClient.newHttpClient();
    	CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    	try {
			HttpResponse<String> response = futureResponse.get();
			System.out.println("Response=> " + response.statusCode() + response.body());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    	
    }
}
