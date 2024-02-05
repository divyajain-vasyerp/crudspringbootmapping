package com.crud.mapping.service.student;

import java.util.List;

import com.crud.mapping.dto.StudentDTO;
import com.crud.mapping.model.Student;

public interface StudentService {

	List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    Student deleteStudentById(Long id);
    public void save(StudentDTO studentDTO);
	public void update(long id, StudentDTO studentDTO);
	
    void assignBookToStudent(Long studentId, Long bookId);

}
