package com.salah.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TaskDTO {

    private Long id;
    private String name;
    private float price;
    private Boolean status;
    private Long doctorId;
}
