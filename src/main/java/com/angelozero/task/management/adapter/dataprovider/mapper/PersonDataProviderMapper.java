package com.angelozero.task.management.adapter.dataprovider.mapper;

import com.angelozero.task.management.adapter.dataprovider.jpa.entity.PersonEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import com.angelozero.task.management.entity.Person;
import com.angelozero.task.management.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PersonDataProviderMapper {

    @Mapping(target = "taskIdsList", source = "taskIdsList")
    PersonEntity toPersonEntity(Person person, List<String> taskIdsList);

    default List<String> mapTaskListToTaskIdsList(List<Task> taskList) {
        return taskList.stream().map(Task::id).collect(Collectors.toList());
    }

    @Mapping(target = "taskList", source = "taskEntityList")
    Person toPerson(PersonEntity personEntity, List<TaskEntity> taskEntityList);

    default List<Task> mapTaskEntityListToTaskList(List<TaskEntity> taskEntityList) {
        return taskEntityList.stream()
                .map(taskEntity -> new Task(taskEntity.id(), taskEntity.description(), taskEntity.completed(), null))
                .collect(Collectors.toList());
    }
}
