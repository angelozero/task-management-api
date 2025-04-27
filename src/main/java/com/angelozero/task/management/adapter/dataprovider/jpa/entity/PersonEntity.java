package com.angelozero.task.management.adapter.dataprovider.jpa.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record PersonEntity(@Id String id, String name, String email, String profileInfo) {
}
