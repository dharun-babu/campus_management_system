package com.i2i.app.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.i2i.app.dto.GradeResponseDto;
import com.i2i.app.service.GradeService;

@RestController
@RequestMapping("cms/api/1.0.0/grades")
@Slf4j
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * <p>Display all grades along with students.</p>
     *
     * @return ResponseEntity with list of all grades and their students
     */
    @GetMapping
    public ResponseEntity<List<GradeResponseDto>> getAllGrades() {
        log.info("Displaying all grades");
        try {
            List<GradeResponseDto> grades = gradeService.getAllGrades();
            log.info("Successfully retrieved {} grade details", grades.size());
            return ResponseEntity.status(HttpStatus.OK).body(grades);
        } catch (Exception e) {
            log.error("Error displaying grades: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * <p>Display a particular grade by given standard and section.</p>
     *
     * @param standard the grade standard
     * @param section the grade section
     * @return ResponseEntity with the grade details
     */
    @GetMapping("/{standard}/{section}")
    public ResponseEntity<GradeResponseDto> getGradeByStandardAndSection(
            @PathVariable("standard") int standard, @PathVariable("section") char section) {
        log.info("Displaying a grade details by standard {} and section {}", standard, section );
        try {
            GradeResponseDto grade = gradeService.getGradeByStandardAndSection(standard, section);
            log.info("Successfully displayed the grade detail : {}", grade);
            return ResponseEntity.status(HttpStatus.OK).body(grade);
        } catch (Exception e) {
            log.error("Error displaying grade by standard and section: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
