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
import org.springframework.web.bind.annotation.RestController;

import com.crud.mapping.dto.StudentDTO;
import com.crud.mapping.service.student.StudentService;

@RequestMapping("/api")
@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/")
	public ResponseEntity<List<StudentDTO>> get() {
		return ResponseEntity.ok(studentService.getAllStudents());
	}
	
	@GetMapping("/{id}")
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

	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody StudentDTO studentDto) {
		try {
			studentService.save(studentDto);
			return ResponseEntity.ok("data is added " + HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.toString() + "Yor data is not added");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Yor data is not added");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable long id, @RequestBody StudentDTO studentDto) {
		try {
			studentService.update(id, studentDto);
			return ResponseEntity.ok("Your data is updated" + " " + HttpStatus.UPGRADE_REQUIRED);
		} catch (Exception e) {
			System.out.println(e.toString() + "Yor data is not Upgraded");
			return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body("Yor data is not Updated");
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
		try {
			studentService.deleteStudentById(id);
			return ResponseEntity.ok("Id is Sucessfully Deleted " + id);
		} catch (Exception e) {
			System.out.println("Id is Not Deleted " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Is is not Deleted " + id );
		}
	}

}
