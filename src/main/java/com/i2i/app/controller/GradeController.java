package com.i2i.app.controller;

import java.util.List;

import com.i2i.app.customexception.StudentException;
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

/**
 * This class manages operations related to grades including retrieval.
 * Provides REST endpoints for displaying all grades and fetching a specific grade by standard and section.
 */
@RestController
@RequestMapping("cms/api/v1/grades")
@Slf4j
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * <p>
     *     Retrieves a list of all grades along with associated students.
     * </p>
     *
     * @return ResponseEntity containing a list of all grades and HTTP status.
     */
    @GetMapping
    public ResponseEntity<?> getAllGrades() {
        log.info("Displaying all grades");
        try {
            List<GradeResponseDto> grades = gradeService.getAllGrades();
            log.info("Successfully retrieved {} grade details", grades.size());
            return ResponseEntity.status(HttpStatus.OK).body(grades);
        } catch (StudentException e) {
            log.error("Error displaying grades: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to fetch all grade details");
        }
    }

    /**
     * <p>
     *    Retrieves the details of a specific grade based on the provided standard and section.
     * </p>
     *
     * @param standard The grade standard.
     * @param section  The grade section.
     * @return ResponseEntity containing the details of the specified grade and HTTP status.
     */
    @GetMapping("/{standard}/{section}")
    public ResponseEntity<?> getGradeByStandardAndSection(
            @PathVariable("standard") int standard, @PathVariable("section") char section) {
        log.info("Displaying a grade details by standard {} and section {}", standard, section);
        try {
            GradeResponseDto grade = gradeService.getGradeByStandardAndSection(standard, section);
            log.info("Successfully displayed the grade detail : {}", grade);
            return ResponseEntity.status(HttpStatus.OK).body(grade);
        } catch (StudentException e) {
            log.error("Error displaying grade by standard and section: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to get grade by standard");
        }
    }
}
