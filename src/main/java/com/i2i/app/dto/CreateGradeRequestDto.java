package com.i2i.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGradeRequestDto {
    private int standard;
    private char section;
}
