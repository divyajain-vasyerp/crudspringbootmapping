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

import com.crud.mapping.dto.LibraryDTO;
import com.crud.mapping.service.library.LibraryService;

@RequestMapping("/api/")
@RestController
public class LibraryController {

	@Autowired
    private LibraryService libraryService;
	@GetMapping("/getBook")
	public ResponseEntity<List<LibraryDTO>> get() {
		return ResponseEntity.ok(libraryService.getAllStudents());
	}
	
	@GetMapping("/getBook/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		try {
			LibraryDTO libraryDTO = libraryService.getStudentById(id);
			System.out.println("Id is found " + id);
			return ResponseEntity.ok(libraryDTO);
		} catch (Exception e) {
			System.out.println("Id " + id + " is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is Not Found");
		}
	}

	@PostMapping("/addBook")
	public ResponseEntity<String> addBook(@RequestBody LibraryDTO libraryDTO) {
		try {
			libraryService.save(libraryDTO);
			return ResponseEntity.ok("Your date is added" + libraryDTO + " " + HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.toString() + "Yor data is not added");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Yor data is not added");
		}
	}

	@PutMapping("/updateBook/{id}")
	public ResponseEntity<String> updateLibrary(@PathVariable long id, @RequestBody LibraryDTO libraryDTO) {
		try {
			libraryService.update(id, libraryDTO);
			return ResponseEntity.ok("Your data is added" + " " + HttpStatus.UPGRADE_REQUIRED);
		} catch (Exception e) {
			System.out.println(e.toString() + "Yor data is not Upgraded");
			return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body("Yor data is not Updated");
		}
	}
	
	@DeleteMapping("/deleteBook/{id}")
	public ResponseEntity<String> deleteLibrary(@PathVariable Long id) {
		try {
			libraryService.deleteBookById(id);
			return ResponseEntity.ok("Id is Sucessfully Deleted " + id);
		} catch (Exception e) {
			System.out.println("Id is Not Deleted " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Is is not Deleted " + id );
		}
	}
}

