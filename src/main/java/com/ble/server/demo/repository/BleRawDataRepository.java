package com.ble.server.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ble.server.demo.model.BleRawData;

@Repository
public interface BleRawDataRepository extends JpaRepository<BleRawData, Long> {
}
