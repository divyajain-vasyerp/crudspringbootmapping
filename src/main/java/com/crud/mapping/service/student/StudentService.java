package com.crud.mapping.service.student;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.crud.mapping.dto.StudentDTO;
import com.crud.mapping.model.Student;

public interface StudentService {

	List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    Student deleteStudentById(Long id);
    public void save(StudentDTO studentDTO);
	public void update(long id, StudentDTO studentDTO);
	
    void assignBookToStudent(Long studentId, Long bookId);
    
    public String uploadFile(MultipartFile file) throws IOException;
    public ResponseEntity<Object> downloadFile(String fileName);
    
    public String uploadTextFile(MultipartFile file) throws IOException ;

}
