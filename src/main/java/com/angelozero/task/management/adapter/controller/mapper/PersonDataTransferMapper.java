package com.angelozero.task.management.adapter.controller.mapper;

import com.angelozero.task.management.adapter.controller.datatransfer.PersonInput;
import com.angelozero.task.management.adapter.controller.datatransfer.PersonOutput;
import com.angelozero.task.management.adapter.controller.rest.request.TaskRequest;
import com.angelozero.task.management.adapter.controller.rest.response.TaskResponse;
import com.angelozero.task.management.entity.Person;
import com.angelozero.task.management.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonDataTransferMapper {

    @Mapping(target = "id", expression = "java(null)")
    Person toPerson(PersonInput personInput);

    PersonOutput toPersonOutput(Person person);

    List<PersonOutput> toPersonOutputList(List<Person> personList);

}
