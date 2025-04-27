package com.angelozero.task.management.adapter.dataprovider.mapper;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.PersonEntity;
import com.angelozero.task.management.entity.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonDataProviderMapper {

    List<Person> toPersonList(List<PersonEntity> personEntityList);

    PersonEntity toPersonEntity(Person person);

    Person toPerson(PersonEntity personEntity);
}
