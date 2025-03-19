package com.angelozero.task.management.adapter.controller.mapper;

import com.angelozero.task.management.adapter.controller.rest.request.TaskRequest;
import com.angelozero.task.management.adapter.controller.rest.response.TaskResponse;
import com.angelozero.task.management.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskRequestMapper {

    @Mapping(target = "id", expression = "java(null)")
    Task toTask(TaskRequest taskRequest);

    TaskResponse toTaskResponse(Task task);

    List<TaskResponse> toTaskResponseList(List<Task> taskList);

}
