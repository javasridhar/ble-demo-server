package com.ble.server.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ble.server.demo.model.BleRawData;
import com.ble.server.demo.service.BleRawDataService;

@RestController
@RequestMapping("/api")
public class BleRawDataController {
	
	@Autowired
	BleRawDataService bleRawDataService;
	
	@RequestMapping(value="/postBleRawData", method=RequestMethod.POST)
    public BleRawData createOrInsertBleRawData(@RequestBody BleRawData bleRawData) {
        return bleRawDataService.createOrInsertBleRawData(bleRawData);
    }

}
