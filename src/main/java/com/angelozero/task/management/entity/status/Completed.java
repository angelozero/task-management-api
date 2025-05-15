package com.angelozero.task.management.entity.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Completed implements StatusTask {

    private String info;

    @Override
    public StatusType getType() {
        return StatusType.COMPLETED;
    }

    @Override
    public String getName() {
        return StatusType.COMPLETED.getDescription();
    }

    @Override
    public int getCode() {
        return StatusType.COMPLETED.getCode();
    }
}
