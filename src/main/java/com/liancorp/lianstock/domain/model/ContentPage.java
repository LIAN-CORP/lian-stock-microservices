package com.liancorp.lianstock.domain.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentPage<T> {
    private Integer totalPage;
    private Long totalElements;
    private Integer pageNumber;
    private Integer pageSize;
    private boolean first;
    private boolean last;
    private List<T> content;
}
