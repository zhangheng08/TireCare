package com.pi2star.tirecare.dao;

import com.pi2star.tirecare.entity.MPUMessage;
import com.pi2star.tirecare.entity.TireMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MPURepository extends CrudRepository<MPUMessage, Long> {

    //@Query(value = "select * from mpu_msg order by timestamp desc limit 0, 1", nativeQuery = true)
    @Query(value = "select * from mpu_msg inner join (select _id from mpu_msg order by mpu_msg.timestamp desc limit 0, 20) as tab2 using (_id);", nativeQuery = true)
    ArrayList<MPUMessage> findTopMpuMessage();

    @Query(value = "select * from mpu_msg where utc_time_year = ?1 order by timestamp desc", nativeQuery = true)
    ArrayList<MPUMessage> findAllByUtc_time_year(String utc_year);


}
