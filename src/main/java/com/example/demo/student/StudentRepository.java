package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;
import java.util.List;


@Repository
public interface StudentRepository
        extends PagingAndSortingRepository<Student, Long>,JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email );
    @Query("SELECT s FROM Student s WHERE s.name = ?1")
    List<Student> findStudentsByName(String name);

    List<Student> findStudentsByNameContaining(String namePart);

}
