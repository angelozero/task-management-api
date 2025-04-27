package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.PersonEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.PersonRepository;
import com.angelozero.task.management.adapter.dataprovider.mapper.PersonDataProviderMapper;
import com.angelozero.task.management.entity.Person;
import com.angelozero.task.management.usecase.gateway.PersonGateway;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonByMongoDataProvider implements PersonGateway {

    private final PersonRepository personRepository;
    private final PersonDataProviderMapper personDataProviderMapper;

    @Override
    public Person findById(String id) {
        var personEntity = personRepository.findById(id).orElse(null);

        return personDataProviderMapper.toPerson(personEntity);
    }

    @Override
    public Person findByEmail(String email) {
        var personEnt = personRepository.findByEmail(email);

        return personDataProviderMapper.toPerson(personEnt);
    }

    @Override
    public Page<Person> findAll(int page, int size, String name, String email) {
        Page<PersonEntity> personEntities;
        var pageable = StringUtils.isBlank(name) ? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(email));

        if (name != null) {
            personEntities = personRepository.findByName(name, pageable);

        } else {
            personEntities = personRepository.findAll(pageable);
        }

        var persons = personDataProviderMapper.toPersonList(personEntities.getContent());

        return new PageImpl<>(persons, pageable, personEntities.getTotalElements());
    }

    @Override
    public void save(Person person) {
        var personEntity = personDataProviderMapper.toPersonEntity(person);

        personRepository.save(personEntity);
    }

    @Override
    public Person update(Person person) {
        var personEntity = personDataProviderMapper.toPersonEntity(person);
        var personEntityUpdated = personRepository.save(personEntity);

        return personDataProviderMapper.toPerson(personEntityUpdated);
    }

    @Override
    public void delete(Person person) {
        var personEntity = personDataProviderMapper.toPersonEntity(person);

        personRepository.delete(personEntity);
    }
}
