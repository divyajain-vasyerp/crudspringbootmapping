package com.crud.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.mapping.model.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {

}
