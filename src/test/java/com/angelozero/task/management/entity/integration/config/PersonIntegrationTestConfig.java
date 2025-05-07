package com.angelozero.task.management.entity.integration.config;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.PersonEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.PersonRepository;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.TaskRepository;
import com.angelozero.task.management.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class PersonIntegrationTestConfig extends BaseIntegrationTestConfig {

    @Autowired
    protected PersonRepository personRepository;

    @Autowired
    protected TaskRepository taskRepository;

    public PersonEntity findPersonById(String id) {
        return personRepository.findById(id).orElse(null);
    }

    public PersonEntity savePerson(Person person) {
        var tasksEntityList = taskRepository.saveAll(person.taskList()
                .stream()
                .map(task -> new TaskEntity(null, task.description(), task.completed()))
                .toList());
        var tasks = tasksEntityList.stream().map(TaskEntity::id).toList();
        return personRepository.save(new PersonEntity(null, person.name(), person.email(), person.profileInfo(), tasks));
    }

    public void deletePersonData() {
        taskRepository.deleteAll();
        personRepository.deleteAll();
        log.info("[PersonIntegrationTestConfig] - Person data deleted with success");
    }
}
