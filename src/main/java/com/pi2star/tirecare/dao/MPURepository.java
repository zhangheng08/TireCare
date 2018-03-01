package com.pi2star.tirecare.dao;

import com.pi2star.tirecare.entity.MPUMessage;
import com.pi2star.tirecare.entity.TireMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MPURepository extends CrudRepository<MPUMessage, Long> {

    @Query(value = "select * from mpu_msg order by timestamp desc limit 0, 100", nativeQuery = true)
    ArrayList<MPUMessage> findTopMpuMessage();

}
