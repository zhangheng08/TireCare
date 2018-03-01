package com.pi2star.tirecare.dao;

import com.pi2star.tirecare.entity.TireMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TireRepository extends CrudRepository<TireMessage, Long> {

    @Query(value = "select * from tire_msg where box_id = ?1 and timestamp = ?2 and place = ?3 limit 0, 1", nativeQuery = true)
    TireMessage findTireMessageByBoxIdAndTimestampAndPlace(int boxId, long timestamp, int place);


    @Query(value = "select * from tire_msg where box_id = ?1 and place = ?2 and timestamp < ?3 order by timestamp asc limit 0, 180", nativeQuery = true)
    ArrayList<TireMessage> findTireMessagePast3min(int boxId, int place, long timestamp);

    @Query(value = "select * from tire_msg order by timestamp desc limit 0, 100", nativeQuery = true)
    ArrayList<TireMessage> findTopTireMessage();


}
