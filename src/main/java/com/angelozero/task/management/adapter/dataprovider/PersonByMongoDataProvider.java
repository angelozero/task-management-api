package com.angelozero.task.management.adapter.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.jpa.document.PersonEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.document.TaskEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.mongo.PersonRepository;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.mongo.TaskRepository;
import com.angelozero.task.management.adapter.dataprovider.mapper.PersonDataProviderMapper;
import com.angelozero.task.management.adapter.dataprovider.mapper.TaskDataProviderMapper;
import com.angelozero.task.management.entity.Person;
import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.gateway.PersonGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonByMongoDataProvider implements PersonGateway {

    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;
    private final PersonDataProviderMapper personDataProviderMapper;
    private final TaskDataProviderMapper taskDataProviderMapper;

    @Override
    public void save(Person person) {
        List<String> taskEntityIdsList = null;
        var taskEntityList = taskDataProviderMapper.toTaskEntityList(person.taskList());

        if (taskEntityList != null && !taskEntityList.isEmpty()) {
            var taskEntitySavedList = taskRepository.saveAll(taskEntityList);
            taskEntityIdsList = taskEntitySavedList.stream().map(TaskEntity::id).toList();
        }

        var personEntity = personDataProviderMapper.toPersonEntity(person, taskEntityIdsList);
        personRepository.save(personEntity);
    }

    @Override
    public Person findById(String id) {
        var personEntity = personRepository.findById(id).orElse(null);
        var taskEntityList = getTaskEntityList(personEntity);

        return personDataProviderMapper.toPerson(personEntity, taskEntityList);
    }

    @Override
    public Person findByEmail(String email) {
        var personEntity = personRepository.findByEmail(email);
        var taskEntityList = getTaskEntityList(personEntity);

        return personDataProviderMapper.toPerson(personEntity, taskEntityList);
    }


    @Override
    public Person update(Person person) {
        List<String> taskEntityIdsList = null;
        var taskEntityList = taskDataProviderMapper.toTaskEntityList(person.taskList());

        if (taskEntityList != null && !taskEntityList.isEmpty()) {
            var taskEntitySavedList = taskRepository.saveAll(taskEntityList);
            taskEntityIdsList = taskEntitySavedList.stream().map(TaskEntity::id).toList();
        }

        var personEntity = personDataProviderMapper.toPersonEntity(person, taskEntityIdsList);
        var personEntityUpdated = personRepository.save(personEntity);
        var taskEntities = getTaskEntityList(personEntityUpdated);

        return personDataProviderMapper.toPerson(personEntityUpdated, taskEntities);
    }

    @Override
    public void delete(Person person) {
        var taskIdList = person.taskList() != null ? person.taskList().stream().map(Task::id).toList() : null;
        var personEntity = personDataProviderMapper.toPersonEntity(person, taskIdList);
        taskRepository.deleteAllById(personEntity.taskIdsList());
        personRepository.delete(personEntity);
    }

    private List<TaskEntity> getTaskEntityList(PersonEntity personEntity) {
        return personEntity != null ? taskRepository.findByIdIn(personEntity.taskIdsList()) : null;
    }
}
