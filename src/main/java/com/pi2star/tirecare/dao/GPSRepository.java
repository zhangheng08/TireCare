package com.pi2star.tirecare.dao;

import com.pi2star.tirecare.entity.GPSMessage;
import com.pi2star.tirecare.entity.TireMessage;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public interface GPSRepository extends CrudRepository<GPSMessage, Long> {

    @Query(value = "select gps_msg.* from gps_msg where gps_msg.timestamp = ?1 group by gps_msg.box_id order by gps_msg.box_id asc", nativeQuery=true)
    public ArrayList<GPSMessage> findByTimestamp(long timestamp);

    @Query(value = "select gps_msg.* from gps_msg group by gps_msg.box_id order by gps_msg.box_id asc", nativeQuery=true)
    public ArrayList<GPSMessage> findAllByGroup();

    @Query(value = "select count(*) as count from (select distinct box_id from gps_msg) as tmp;", nativeQuery=true)
    public int countDistinctByBoxId();

    @Query(value = "select * from gps_msg where box_id = ?1 and timestamp = ?2", nativeQuery = true)
    public GPSMessage findByBoxIdAndTimestamp(int boxId, long timestamp);

}
