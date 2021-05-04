package com.study.jpkc.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/5/4 16:05
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ScoreExelDto implements Serializable {

    private String userId;

    private String username;

    private String userPhone;

    private String userEmail;

    private Double mark;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate submitTime;
}
