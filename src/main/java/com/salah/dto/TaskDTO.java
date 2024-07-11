package com.salah.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TaskDTO {

    private Long id;
    private String name;
    private Date createdAt;
    private float price;
    private Boolean status;
    private Long doctorId;
}
