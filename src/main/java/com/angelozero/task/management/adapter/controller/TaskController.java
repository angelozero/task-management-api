package com.angelozero.task.management.adapter.controller;

import com.angelozero.task.management.adapter.controller.mapper.TaskRequestMapper;
import com.angelozero.task.management.adapter.controller.rest.request.TaskRequest;
import com.angelozero.task.management.adapter.controller.rest.response.TaskResponse;
import com.angelozero.task.management.usecase.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    private final FindTaskByIdUseCase findTaskByIdUseCase;
    private final FindTasksUseCase findTasksUseCase;
    private final SaveTaskUseCase saveTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    private final TaskRequestMapper taskRequestMapper;

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findTasks(@PathVariable String id) {
        var task = findTaskByIdUseCase.execute(id);
        var taskResponse = taskRequestMapper.toTaskResponse(task);

        return Optional.ofNullable(taskResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findTasks() {
        var taskList = findTasksUseCase.execute();
        var taskResponseList = taskRequestMapper.toTaskResponseList(taskList);

        return Optional.ofNullable(taskResponseList)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<List<TaskResponse>> saveTask(@RequestBody TaskRequest taskRequest) {
        var task = taskRequestMapper.toTask(taskRequest);
        var taskList = saveTaskUseCase.execute(task);
        var taskResponse = taskRequestMapper.toTaskResponseList(taskList);

        return ResponseEntity.ok(taskResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTasks(@PathVariable String id, @RequestBody TaskRequest taskRequest) {
        var taskToUpdate = taskRequestMapper.toTask(taskRequest);
        var task = updateTaskUseCase.execute(id, taskToUpdate);
        var taskResponse = taskRequestMapper.toTaskResponse(task);

        return Optional.ofNullable(taskResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        deleteTaskUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}
