package com.angelozero.task.management.entity.unit.dataprovider;

import com.angelozero.task.management.adapter.dataprovider.TasksByMongoDataProvider;
import com.angelozero.task.management.adapter.dataprovider.jpa.document.TaskEntity;
import com.angelozero.task.management.adapter.dataprovider.jpa.repository.mongo.TaskRepository;
import com.angelozero.task.management.adapter.dataprovider.mapper.TaskDataProviderMapper;
import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.entity.status.Completed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TasksByMongoDataProviderTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskDataProviderMapper taskDataProviderMapper;

    @InjectMocks
    private TasksByMongoDataProvider tasksByMongoDataProvider;

    @Test
    @DisplayName("Should find a task by id with success")
    void shouldFindTaskByIdWithSuccess() {
        var taskEntityMock = getTaskEntityMock();
        var taskMock = getTaskMock();

        when(taskRepository.findById(anyString())).thenReturn(Optional.of(taskEntityMock));
        when(taskDataProviderMapper.toTask(any())).thenReturn(taskMock);

        var response = tasksByMongoDataProvider.findById("id");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should find a task list by ids with success")
    void shouldFindTaskListByIdsWithSuccess() {
        var taskEntityListMock = getTaskEntityListMock();
        var taskListMock = getTaskListMock();

        when(taskRepository.findByIdIn(anyList())).thenReturn(taskEntityListMock);
        when(taskDataProviderMapper.toTaskList(anyList())).thenReturn(taskListMock);

        var response = tasksByMongoDataProvider.findByIds(List.of("id"));

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should find a pageable completed tasks with success")
    void shouldFindPageableCompletedTasksWithSuccess() {
        var taskListEntityMock = getTaskEntityListMock();
        var pageMock = new PageImpl<>(taskListEntityMock);
        var taskListMock = getTaskListMock();

        when(taskRepository.findByCompleted(any(), any())).thenReturn(pageMock);
        when(taskDataProviderMapper.toTaskList(any())).thenReturn(taskListMock);

        var response = tasksByMongoDataProvider.findAll(0, 1, "sorted field", true);

        assertNotNull(response);
        verify(taskRepository, never()).findAll();
    }

    @Test
    @DisplayName("Should find a pageable tasks with success")
    void shouldFindPageableTasksWithSuccess() {
        var taskListEntityMock = getTaskEntityListMock();
        var pageMock = new PageImpl<>(taskListEntityMock);
        var taskListMock = getTaskListMock();

        when(taskRepository.findAll(any(PageRequest.class))).thenReturn(pageMock);
        when(taskDataProviderMapper.toTaskList(any())).thenReturn(taskListMock);

        var response = tasksByMongoDataProvider.findAll(0, 1, "sorted field", null);

        assertNotNull(response);
        verify(taskRepository, never()).findByCompleted(any(), any());
    }

    @Test
    @DisplayName("Should save a task with success")
    void shouldSaveTaskWithSuccess() {
        var taskMock = getTaskMock();
        var taskEntityMock = getTaskEntityMock();

        when(taskDataProviderMapper.toTaskEntity(any())).thenReturn(taskEntityMock);
        when(taskRepository.save(any())).thenReturn(taskEntityMock);

        assertDoesNotThrow(() -> tasksByMongoDataProvider.save(taskMock));
    }

    @Test
    @DisplayName("Should save all tasks with success")
    void shouldSaveAllTasksWithSuccess() {
        var taskMock = getTaskMock();
        var taskEntityListMock = getTaskEntityListMock();

        when(taskDataProviderMapper.toTaskEntityList(any())).thenReturn(taskEntityListMock);
        when(taskRepository.saveAll(any())).thenReturn(taskEntityListMock);

        assertDoesNotThrow(() -> tasksByMongoDataProvider.saveAll(List.of(taskMock)));
    }

    @Test
    @DisplayName("Should update a task with success")
    void shouldUpdateTaskWithSuccess() {
        var taskMock = getTaskMock();
        var taskEntityMock = getTaskEntityMock();

        when(taskDataProviderMapper.toTaskEntity(any())).thenReturn(taskEntityMock);
        when(taskRepository.save(any())).thenReturn(taskEntityMock);
        when(taskDataProviderMapper.toTask(any())).thenReturn(taskMock);

        assertDoesNotThrow(() -> tasksByMongoDataProvider.update(taskMock));
    }

    @Test
    @DisplayName("Should delete a task with success")
    void shouldDeleteTaskWithSuccess() {
        var taskMock = getTaskMock();
        var taskEntityMock = getTaskEntityMock();

        when(taskDataProviderMapper.toTaskEntity(any())).thenReturn(taskEntityMock);
        doNothing().when(taskRepository).delete(any());

        assertDoesNotThrow(() -> tasksByMongoDataProvider.delete(taskMock));
    }

    private List<TaskEntity> getTaskEntityListMock() {
        return List.of(new TaskEntity("1", "description", true, "statusTYpe", 0));
    }

    private TaskEntity getTaskEntityMock() {
        return new TaskEntity("1", "description", true, "statusTYpe", 0);
    }

    private List<Task> getTaskListMock() {
        return List.of(new Task("1", "description", true, new Completed()));
    }

    private Task getTaskMock() {
        return new Task("1", "description", true, new Completed());
    }
}
