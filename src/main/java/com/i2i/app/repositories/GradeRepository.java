package com.i2i.app.repositories;

import com.i2i.app.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Grade findGardeByStandardAndSection(int standard, char section);
    List<Grade> getGradeByStandard(int standard);
    Grade getGradeByStandardAndSection(int standard, char section);
}
