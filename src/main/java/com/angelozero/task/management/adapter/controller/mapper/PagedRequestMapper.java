package com.angelozero.task.management.adapter.controller.mapper;

import com.angelozero.task.management.adapter.controller.rest.response.PagedResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagedRequestMapper<T> {

    public PagedResponse<T> toPagedResponse(T content, Page page) {
        if (content == null || page == null) {
            return null;
        }
        return new PagedResponse<>(
                content,
                null,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.hasNext(),
                page.hasPrevious());
    }

    public PagedResponse<T> toPagedResponse(List<T> contents, Page page) {
        if (contents == null || contents.isEmpty() || page == null) {
            return null;
        }
        return new PagedResponse<>(
                null,
                contents,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.hasNext(),
                page.hasPrevious());
    }

}
