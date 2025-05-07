package com.angelozero.task.management.adapter.dataprovider.jpa.repository;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<PersonEntity, String> {

    PersonEntity findByEmail(String email);
}
