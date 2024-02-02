package com.crud.mapping.service.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.mapping.dto.LibraryDTO;
import com.crud.mapping.dto.StudentDTO;
import com.crud.mapping.model.Library;
import com.crud.mapping.model.Student;
import com.crud.mapping.repository.LibraryRepository;
import com.crud.mapping.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private LibraryRepository libraryRepository;

	@Override
	public List<StudentDTO> getAllStudents() {
		List<StudentDTO> list = new ArrayList<>();
		List<Student> studentlist = studentRepository.findAll();
		for (Student student : studentlist) {
			StudentDTO studentDto = new StudentDTO();
			studentDto.setId(student.getId());
			studentDto.setRollNo(student.getRollNo());
			studentDto.setName(student.getName());
			studentDto.setEmail(student.getEmail());
			studentDto.setNumber(student.getNumber());
			
			// Create LibraryDTO and set its properties
	        LibraryDTO libraryDTO = new LibraryDTO();
	        libraryDTO.setBookId(student.getBook().getBookId());
	        libraryDTO.setBookName(student.getBook().getBookName());
	        libraryDTO.setBookAuthor(student.getBook().getBookAuthor());
	        // Set the LibraryDTO in StudentDTO
	        studentDto.setLibrary(libraryDTO);
	        
	        // Add the StudentDTO to the list
			list.add(studentDto);
		}
		return list;
	}

	@Override
	public StudentDTO getStudentById(Long id) {
		Student student = studentRepository.findById(id).get();
		StudentDTO studentDto = new StudentDTO();
		studentDto.setId(student.getId());
		studentDto.setRollNo(student.getRollNo());
		studentDto.setName(student.getName());
		studentDto.setEmail(student.getEmail());
		studentDto.setNumber(student.getNumber());
		LibraryDTO libraryDTO = new LibraryDTO();
		libraryDTO.setBookId(student.getBook().getBookId());
		libraryDTO.setBookName(student.getBook().getBookName());
		libraryDTO.setBookAuthor(student.getBook().getBookAuthor());
		studentDto.setLibrary(libraryDTO);
		return studentDto;
	}

	@Override
	public void update(long id, StudentDTO studentDTO) {
		Optional<Student> optionalStudent = studentRepository.findById(id);

		if (optionalStudent.isPresent()) {
			Student studentEdit = optionalStudent.get();

			if (studentDTO.getId() != 0) {
				studentEdit.setId(studentDTO.getId());
			}

			if (studentDTO.getRollNo() != null) {
				studentEdit.setRollNo(studentDTO.getRollNo());
			}

			if (studentDTO.getName() != null) {
				studentEdit.setName(studentDTO.getName());
			}

			if (studentDTO.getEmail() != null) {
				studentEdit.setEmail(studentDTO.getEmail());
			}

			if (studentDTO.getNumber() != 0) {
				studentEdit.setNumber(studentDTO.getNumber());
			}

			Library library = new Library();

			studentEdit.setRollNo(studentDTO.getRollNo());
			studentEdit.setName(studentDTO.getName());
			studentEdit.setEmail(studentDTO.getEmail());
			studentEdit.setNumber(studentDTO.getNumber());
			library.setBookId(studentDTO.getLibrary().getBookId());
			library.setBookName(studentDTO.getLibrary().getBookName());
			library.setBookAuthor(studentDTO.getLibrary().getBookAuthor());

			// Save the library
			libraryRepository.save(library);
			studentEdit.setBook(library);
			studentRepository.save(studentEdit);
		}
	}

//	@Override
//	public void updated(long id, StudentDTO studentDTO) {
//		Student studentEdit = studentRepository.findById(id).get();
//		Library library = new Library();
//		if (studentDTO.getId() != null) {
//			studentEdit.setId(studentDTO.getId());
//		} else if (studentDTO.getRollNo() != null) {
//			studentEdit.setRollNo(studentDTO.getRollNo());
//		} else if (studentDTO.getName() != null) {
//			studentEdit.setName(studentDTO.getName());
//		} else if (studentDTO.getEmail() != null) {
//			studentEdit.setEmail(studentDTO.getEmail());
//		} else if (studentDTO.getNumber() != 0) {
//			studentEdit.setNumber(studentDTO.getNumber());
//		}
//		studentRepository.save(studentEdit);
//	}

	@Override
	public void save(StudentDTO studentDTO) {
		if (studentDTO.getId() == 0) {
			Student student = new Student();
			Library library = new Library();
			student.setRollNo(studentDTO.getRollNo());
			student.setName(studentDTO.getName());
			student.setEmail(studentDTO.getEmail());
			student.setNumber(studentDTO.getNumber());
			library.setBookId(studentDTO.getLibrary().getBookId());
			library.setBookName(studentDTO.getLibrary().getBookName());
			library.setBookAuthor(studentDTO.getLibrary().getBookAuthor());
			// Save the library first
			libraryRepository.save(library);
			// Set the library in the student
			student.setBook(library);
			// Save the student
			studentRepository.save(student);
		} else {
			Optional<Student> optionalStudent = studentRepository.findById(studentDTO.getId());
//			Student studentEdit = studentRepository.findById(studentDTO.getId()).get();
			if (optionalStudent.isPresent()) {
				Student studentEdit = optionalStudent.get();
				if (studentDTO.getRollNo() != null) {
					studentEdit.setRollNo(studentDTO.getRollNo());
				} else if (studentDTO.getName() != null) {
					studentEdit.setName(studentDTO.getName());
				} else if (studentDTO.getEmail() != null) {
					studentEdit.setEmail(studentDTO.getEmail());
				} else if (studentDTO.getNumber() != 0) {
					studentEdit.setNumber(studentDTO.getNumber());
				}
				// Update library information
				Library libraryEdit = studentEdit.getBook();
				if (libraryEdit != null && studentDTO.getLibrary() != null) {
					libraryEdit.setBookId(studentDTO.getLibrary().getBookId());
					libraryEdit.setBookName(studentDTO.getLibrary().getBookName());
					libraryEdit.setBookAuthor(studentDTO.getLibrary().getBookAuthor());
				}
				studentRepository.save(studentEdit);
			}
		}
	}

	@Override
	public Student deleteStudentById(Long id) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

		studentRepository.deleteById(id);

		// Convert the deleted student to DTO and return it
		return student;
	}

}
