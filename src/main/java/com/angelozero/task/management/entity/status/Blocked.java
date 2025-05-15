package com.angelozero.task.management.entity.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Blocked implements StatusTask {

    private String info;

    @Override
    public StatusType getType() {
        return StatusType.BLOCKED;
    }

    @Override
    public String getName() {
        return StatusType.BLOCKED.getDescription();
    }

    @Override
    public int getCode() {
        return StatusType.BLOCKED.getCode();
    }
}
