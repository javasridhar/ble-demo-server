package com.ble.server.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ble.server.demo.model.BleRawData;
import com.ble.server.demo.repository.BleRawDataRepository;

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
    	String urlStr = "https://fcm.googleapis.com/fcm/send";
    	String key = "";
    	
    	String to = bleRawData.getFcmToken();
//    	NotificationData data = new NotificationData(to, bleRawData);
    	String bleRawDataStr = "{"
    			+ "rawData : " + bleRawData.getRawData() + ","
    			+ "fcmToken : " + bleRawData.getFcmToken()
    			+ "}";
    	
    	URL url;
    	try {
    		url = new URL (urlStr);
    		HttpURLConnection con = (HttpURLConnection)url.openConnection();
    		con.setRequestMethod("POST");
    		con.setRequestProperty("Content-Type", "application/json");
    		con.setRequestProperty("Accept", "application/json");
    		con.setRequestProperty("Authorization", "key=" + key);
    		con.setDoOutput(true);
    		String jsonInputString = "{" + "to : " + to + ", data : " + bleRawDataStr;
    		try(OutputStream os = con.getOutputStream()) {
    		    byte[] input = jsonInputString.getBytes("utf-8");
    		    os.write(input, 0, input.length);			
    		}
    		try(BufferedReader br = new BufferedReader(
    				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
    				    StringBuilder response = new StringBuilder();
    				    String responseLine = null;
    				    while ((responseLine = br.readLine()) != null) {
    				        response.append(responseLine.trim());
    				    }
    				    System.out.println(response.toString());
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
//    	HttpRequest request = HttpRequest.newBuilder()
//    			  .uri(URI.create(url))
//    			  .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(data)))
//    			  .header("Authorization", "key=" + key)
//    			  .header("Content-Type", "application/json")
////    			    Base64.getEncoder().encodeToString(("baeldung:123456").getBytes()))
//    			  .build();
//    	
//    	HttpClient client = HttpClient.newHttpClient();
//    	CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//    	try {
//			HttpResponse<String> response = futureResponse.get();
//			System.out.println("Response=> " + response.statusCode() + response.body());
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
    	
    }
}
