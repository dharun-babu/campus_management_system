package com.i2i.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CreateGradeRequestDto {

    @Min(value = 1)
    @Max(value = 12)
    private int standard;

    @Min(value = 1)
    @Max(value = 12)
    public int getStandard() {
        return standard;
    }

    public void setStandard(@Min(value = 1) @Max(value = 12) int standard) {
        this.standard = standard;
    }
}
