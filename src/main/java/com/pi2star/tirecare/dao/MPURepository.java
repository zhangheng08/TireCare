package com.pi2star.tirecare.dao;

import com.pi2star.tirecare.entity.MPUMessage;
import org.springframework.data.repository.CrudRepository;

public interface MPURepository extends CrudRepository<MPUMessage, Long> {



}
