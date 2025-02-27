package com.angelozero.task.management.adapter.controller;

import com.angelozero.task.management.adapter.controller.mapper.TaskRequestMapper;
import com.angelozero.task.management.adapter.controller.rest.request.TaskRequest;
import com.angelozero.task.management.adapter.controller.rest.response.TaskResponse;
import com.angelozero.task.management.entity.Task;
import com.angelozero.task.management.usecase.GetAllTasksUseCase;
import com.angelozero.task.management.usecase.SaveTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final GetAllTasksUseCase getAllTasksUseCase;
    private final SaveTaskUseCase saveTaskUseCase;
    private final TaskRequestMapper taskRequestMapper;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        var taskList = getAllTasksUseCase.execute();
        var taskResponse = taskRequestMapper.toTaskResponseList(taskList);
        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping
    public ResponseEntity<List<TaskResponse>> saveTask(@RequestBody TaskRequest taskRequest) {
        var task = taskRequestMapper.toTask(taskRequest);
        var taskList = saveTaskUseCase.execute(task);
        var taskResponse = taskRequestMapper.toTaskResponseList(taskList);
        return ResponseEntity.ok(taskResponse);
    }
}
