package com.crud.mapping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crud.mapping.model.Library;
import com.crud.mapping.model.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {

	

	@Query("SELECT s FROM Student s ORDER BY s.id ASC")
	List<Student> findAll();

}
