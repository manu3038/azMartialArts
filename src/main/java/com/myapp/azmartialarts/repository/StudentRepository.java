package com.myapp.azmartialarts.repository;

import com.myapp.azmartialarts.domain.Location;
import com.myapp.azmartialarts.domain.Student;
import com.myapp.azmartialarts.domain.Teacher;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	@Query(value="select * from Student s where s.location_id = ?1",nativeQuery = true)
	List<Student> getByLocation(Long locationSearch);

	@Query(value="select * from Student s where s.belt_level_id = ?1",nativeQuery = true)
	List<Student> getbyBelt(Long beltSearch);
	
	@Query(value = "select * from Student s where s.location_id = ?1 and s.belt_level_id = ?2", nativeQuery = true)
	List<Student> getbyLocationAndBelt(Long locationSearch,Long beltSearch);

	List<Student> findByTeacher(Teacher id);
}
