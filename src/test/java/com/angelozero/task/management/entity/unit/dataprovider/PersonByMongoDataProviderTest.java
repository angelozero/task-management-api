package com.angelozero.task.management.entity.unit.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.PersonByMongoDataProvider;
import com.angelozero.task.management.adapter.dataprovider.jpa.entity.PersonEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.entity.TaskEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.PersonRepository;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.TaskRepository;
import com.angelozero.task.management.adapter.dataprovider.mapper.PersonDataProviderMapper;
import com.angelozero.task.management.adapter.dataprovider.mapper.TaskDataProviderMapper;
import com.angelozero.task.management.entity.Person;
import com.angelozero.task.management.entity.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonByMongoDataProviderTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PersonDataProviderMapper personDataProviderMapper;

    @Mock
    private TaskDataProviderMapper taskDataProviderMapper;

    @InjectMocks
    private PersonByMongoDataProvider personByMongoDataProvider;

    @Test
    @DisplayName("Should save a Person with success")
    void shouldSavePersonWithSuccess() {
        var personEntityMock = getPeronEntityMock();
        var tasEntityListMock = getTaskEntityListMock();
        var personMock = getPersonMock();

        when(taskDataProviderMapper.toTaskEntityList(anyList())).thenReturn(tasEntityListMock);
        when(taskRepository.saveAll(anyList())).thenReturn(tasEntityListMock);
        when(personDataProviderMapper.toPersonEntity(any(), any())).thenReturn(personEntityMock);
        when(personRepository.save(any())).thenReturn(personEntityMock);

        assertDoesNotThrow(() -> personByMongoDataProvider.save(personMock));
    }

    @Test
    @DisplayName("Should save a Person without a list with success")
    void shouldSavePersonWithoutListWithSuccess() {
        var personEntityMock = getPeronEntityMock();
        var personMock = getPersonMock();

        when(taskDataProviderMapper.toTaskEntityList(anyList())).thenReturn(null);

        when(personDataProviderMapper.toPersonEntity(any(), any())).thenReturn(personEntityMock);
        when(personRepository.save(any())).thenReturn(personEntityMock);

        assertDoesNotThrow(() -> personByMongoDataProvider.save(personMock));

        verify(taskRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("Should save a Person with a empty list with success")
    void shouldSavePersonWithEmptyListListWithSuccess() {
        var personEntityMock = getPeronEntityMock();
        var personMock = getPersonMock();

        when(taskDataProviderMapper.toTaskEntityList(anyList())).thenReturn(Collections.emptyList());

        when(personDataProviderMapper.toPersonEntity(any(), any())).thenReturn(personEntityMock);
        when(personRepository.save(any())).thenReturn(personEntityMock);

        assertDoesNotThrow(() -> personByMongoDataProvider.save(personMock));

        verify(taskRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("Should find a Person by id with success")
    void shouldFindPersonByIdWithSuccess() {
        var personEntityMock = getPeronEntityMock();
        var tasEntityListMock = getTaskEntityListMock();
        var taskMock = getTaskMock();
        var personMock = new Person("1",
                "name",
                "email",
                "profile-info",
                List.of(taskMock));


        when(personRepository.findById(anyString())).thenReturn(Optional.of(personEntityMock));
        when(taskRepository.findByIdIn(anyList())).thenReturn(tasEntityListMock);
        when(personDataProviderMapper.toPerson(any(), any())).thenReturn(personMock);

        var response = personByMongoDataProvider.findById("id");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should not find a Person by id with success")
    void shouldNotFindPersonByIdWithSuccess() {
        when(personRepository.findById(anyString())).thenReturn(Optional.empty());
        when(personDataProviderMapper.toPerson(any(), any())).thenReturn(null);

        var response = personByMongoDataProvider.findById("id");

        assertNull(response);

        verify(taskRepository, never()).findByIdIn(any());
    }

    @Test
    @DisplayName("Should find a Person by email with success")
    void shouldFindPersonByEmailWithSuccess() {
        var personEntityMock = getPeronEntityMock();
        var tasEntityListMock = getTaskEntityListMock();
        var personMock = getPersonMock();


        when(personRepository.findByEmail(anyString())).thenReturn(personEntityMock);
        when(taskRepository.findByIdIn(anyList())).thenReturn(tasEntityListMock);
        when(personDataProviderMapper.toPerson(any(), any())).thenReturn(personMock);

        var response = personByMongoDataProvider.findByEmail("email");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should not find a Person by email with success")
    void shouldNotFindPersonByEmailWithSuccess() {
        when(personRepository.findByEmail(anyString())).thenReturn(null);
        when(personDataProviderMapper.toPerson(any(), any())).thenReturn(null);

        var response = personByMongoDataProvider.findByEmail("email");

        assertNull(response);

        verify(taskRepository, never()).findByIdIn(any());
    }

    @Test
    @DisplayName("Should update a Person with success")
    void shouldUpdatePersonWithSuccess() {
        var personEntityMock = getPeronEntityMock();
        var tasEntityListMock = getTaskEntityListMock();
        var personMock = getPersonMock();

        when(taskDataProviderMapper.toTaskEntityList(anyList())).thenReturn(tasEntityListMock);
        when(taskRepository.saveAll(any())).thenReturn(tasEntityListMock);
        when(personDataProviderMapper.toPersonEntity(any(), any())).thenReturn(personEntityMock);
        when(personRepository.save(any())).thenReturn(personEntityMock);
        when(taskRepository.findByIdIn(anyList())).thenReturn(tasEntityListMock);
        when(personDataProviderMapper.toPerson(any(), any())).thenReturn(personMock);

        var response = personByMongoDataProvider.update(personMock);

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should delete a Person with success")
    void shouldDeletePersonWithSuccess() {
        var personEntityMock = getPeronEntityMock();
        var personMock = getPersonMock();

        when(personDataProviderMapper.toPersonEntity(any(), any())).thenReturn(personEntityMock);
        doNothing().when(taskRepository).deleteAllById(any());
        doNothing().when(personRepository).delete(any());

        assertDoesNotThrow(() -> personByMongoDataProvider.delete(personMock));
    }

    private PersonEntity getPeronEntityMock() {
        return new PersonEntity("1",
                "name",
                "email",
                "profile-info",
                List.of("1"));
    }

    private Person getPersonMock() {
        return new Person("1",
                "name",
                "email",
                "profile-info",
                List.of(getTaskMock()));
    }

    private List<TaskEntity> getTaskEntityListMock() {
        return List.of(new TaskEntity("1", "description", true));
    }

    private Task getTaskMock() {
        return new Task("1", "description", true);
    }
}
