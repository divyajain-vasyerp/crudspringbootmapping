package com.crud.mapping.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crud.mapping.dto.ResponseDTO;
import com.crud.mapping.dto.StudentDTO;
import com.crud.mapping.exception.NoDataFoundException;
import com.crud.mapping.exception.StudentNotFoundException;
import com.crud.mapping.model.Student;
import com.crud.mapping.service.student.StudentService;

@RequestMapping("/api")
@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

//	@GetMapping("/getAllStudents")
//	public ResponseEntity<List<StudentDTO>> get() {
//		return ResponseEntity.ok(studentService.getAllStudents());
//	}

	@GetMapping("/getAllStudents")
	public ResponseEntity<List<StudentDTO>> getAllStudents() {
		List<StudentDTO> studentList = studentService.getAllStudents();
		return ResponseEntity.ok(studentList);
	}

	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		ResponseDTO response;
		StudentDTO studentDto = studentService.getStudentById(id);
		System.out.println("Id is found " + id);
		response = new ResponseDTO(200, "Ok", studentDto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/addStudent")
	public ResponseEntity<?> add(@RequestBody StudentDTO studentDto) {
		ResponseDTO response;
		StudentDTO studentDTO2;
		studentDTO2 = studentService.save(studentDto);
		response = new ResponseDTO(200, "Ok", studentDTO2);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateStudent/{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody StudentDTO studentDto) {
		ResponseDTO response;
		StudentDTO studentDTO2 = studentService.update(id, studentDto);
		response = new ResponseDTO(200, "Ok", studentDTO2);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		ResponseDTO response;
		Student student = studentService.deleteStudentById(id);
		response = new ResponseDTO(200, "Id is Sucessfully Deleted ", student);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{studentId}/assignBook/{bookId}")
	public ResponseEntity<?> assignBookToStudent(@PathVariable Long studentId, @PathVariable Long bookId) {
		ResponseDTO response;
		Student student = studentService.assignBookToStudent(studentId, bookId);
		response = new ResponseDTO(200, "Book is Assigned ", student);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException{
		ResponseDTO response = null;
			String student;
			try {
				student = studentService.uploadFile(file);
				response = new ResponseDTO(200, "ok", student);
//				return ResponseEntity.ok(response);
			} catch (IOException e) {
				throw new IOException(e.getMessage());
			}
			return ResponseEntity.ok(response);
	}

	@PostMapping("/uploadTextFile")
	public ResponseEntity<?> uploadTextFile(@RequestParam MultipartFile file) throws IOException {
		ResponseDTO response = null;
			String student;
			try {
				student = studentService.uploadFile(file);
				response = new ResponseDTO(200, "Book is Assigned ", student);
			} catch (IOException e) {
				throw new IOException(e.getMessage());
			}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName) throws IOException {
		try {
			ResponseEntity<Object> files = studentService.downloadFile(fileName);
//			studentService.uploadFile(file);
			return files;
		} catch (Exception e) {
			throw new IOException(e.getMessage());
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("file Not Found");
		}
	}

}
