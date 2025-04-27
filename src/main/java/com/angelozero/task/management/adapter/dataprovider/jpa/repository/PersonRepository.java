package com.angelozero.task.management.adapter.dataprovider.jpa.repository;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<PersonEntity, String> {

    Page<PersonEntity> findByName(String name, Pageable pageable);

    PersonEntity findByEmail(String email);
}
