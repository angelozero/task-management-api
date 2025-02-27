package com.angelozero.task.management.adapter.dataprovider.jpa.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record TaskEntity(@Id String id,
                         String description,
                         Boolean completed) {
}
