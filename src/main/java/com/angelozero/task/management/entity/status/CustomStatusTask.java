package com.angelozero.task.management.entity.status;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A record cannot be non-sealed. The very nature of a record implies that it is final (implicitly).
 * Records are designed to be immutable and concise data classes, and making them non-sealed would
 * conflict with this design intention, as it would allow other classes to inherit from them and potentially
 * modify their behavior or state in ways not intended by the record's design
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public non-sealed class CustomStatusTask implements StatusTask {

    private String info;

    @Override
    public StatusType getType() {
        return StatusType.CUSTOM;
    }

    @Override
    public String getName() {
        return StatusType.CUSTOM.getDescription();
    }

    @Override
    public int getCode() {
        return StatusType.CUSTOM.getCode();
    }
}
