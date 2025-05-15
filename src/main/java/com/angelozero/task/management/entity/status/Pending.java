package com.angelozero.task.management.entity.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Pending implements StatusTask {

    private String info;

    @Override
    public StatusType getType() {
        return StatusType.PENDING;
    }

    @Override
    public String getName() {
        return StatusType.PENDING.getDescription();
    }

    @Override
    public int getCode() {
        return StatusType.PENDING.getCode();
    }
}
