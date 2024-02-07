package com.crud.mapping.controller;

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

import com.crud.mapping.dto.StudentDTO;
import com.crud.mapping.service.student.StudentService;

@RequestMapping("/api")
@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/getAllStudents")
	public ResponseEntity<List<StudentDTO>> get() {
		return ResponseEntity.ok(studentService.getAllStudents());
	}

	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		try {
			StudentDTO studentDto = studentService.getStudentById(id);
			System.out.println("Id is found " + id);
			return ResponseEntity.ok(studentDto);
		} catch (Exception e) {
			System.out.println("Id " + id + " is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is Not Found");
		}
	}

	@PostMapping("/addStudent")
	public ResponseEntity<String> add(@RequestBody StudentDTO studentDto) {
		try {
			studentService.save(studentDto);
			return ResponseEntity.ok("data is added " + HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.toString() + "Yor data is not added");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Yor data is not added");
		}
	}

	@PutMapping("/updateStudent/{id}")
	public ResponseEntity<String> update(@PathVariable long id, @RequestBody StudentDTO studentDto) {
		try {
			studentService.update(id, studentDto);
			return ResponseEntity.ok("Your data is updated" + " " + HttpStatus.UPGRADE_REQUIRED);
		} catch (Exception e) {
			System.out.println(e.toString() + "Yor data is not Upgraded");
			return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body("Yor data is not Updated");
		}
	}

	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
		try {
			studentService.deleteStudentById(id);
			return ResponseEntity.ok("Id is Sucessfully Deleted " + id);
		} catch (Exception e) {
			System.out.println("Id is Not Deleted " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Is is not Deleted " + id);
		}
	}

	@PostMapping("/{studentId}/assignBook/{bookId}")
	public ResponseEntity<String> assignBookToStudent(@PathVariable Long studentId, @PathVariable Long bookId) {
		try {
			studentService.assignBookToStudent(studentId, bookId);
			return ResponseEntity.ok("Book assigned to student successfully.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Student already has a book assigned");
		}
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
		try {
			return ResponseEntity.ok("File Uploaded Sucessfully "+studentService.uploadFile(file));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
	}
	
	@PostMapping("/uploadTextFile")
	public ResponseEntity<String> uploadTextFile(@RequestParam MultipartFile file) {
		try {
			return ResponseEntity.ok("File Uploaded Sucessfully "+studentService.uploadTextFile(file));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
		try {
			ResponseEntity<Object> files = studentService.downloadFile(fileName);
//			studentService.uploadFile(file);
			return files;
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("file Not Found");
		}
	}
	

}
