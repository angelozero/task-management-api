package com.angelozero.task.management.entity.status;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewStatusTask extends CustomStatusTask {

    private String reviewerName;

}
