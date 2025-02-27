package com.angelozero.task.management.adapter.controller.rest.request;

public record TaskRequest(String id,
                          String description,
                          Boolean completed) {
}
