package com.angelozero.task.management.entity.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class InProgress implements StatusTask {

    private String info;

    @Override
    public StatusType getType() {
        return StatusType.IN_PROGRESS;
    }

    @Override
    public String getName() {
        return StatusType.IN_PROGRESS.getDescription();
    }

    @Override
    public int getCode() {
        return StatusType.IN_PROGRESS.getCode();
    }
}
