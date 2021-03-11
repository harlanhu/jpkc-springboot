package com.study.jpkc.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author Harlan
 * @Date 2021/3/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsEntity implements Serializable {
    private String stringValue;
    private Integer integerValue;
    private Boolean booleanValue;
    private Long longValue;
    private LocalDateTime localDateTimeValue;
    private Date dateValue;
    private Double doubleValue;
    private List<String> listValue;
}
