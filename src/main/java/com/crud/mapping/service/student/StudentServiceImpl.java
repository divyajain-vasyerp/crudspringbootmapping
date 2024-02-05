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

			Library book = student.getBook();
			if (book != null) {
				LibraryDTO libraryDTO = new LibraryDTO();
				libraryDTO.setBookId(student.getBook().getBookId());
				libraryDTO.setBookName(student.getBook().getBookName());
				libraryDTO.setBookAuthor(student.getBook().getBookAuthor());
				studentDto.setLibrary(libraryDTO);
			}

			list.add(studentDto);
		}
		return list;
	}

	@Override
	public StudentDTO getStudentById(Long id) {
		Student student = studentRepository.findById(id).get();
		Library book = student.getBook();

		StudentDTO studentDto = new StudentDTO();
		LibraryDTO libraryDTO = new LibraryDTO();
		studentDto.setId(student.getId());
		studentDto.setRollNo(student.getRollNo());
		studentDto.setName(student.getName());
		studentDto.setEmail(student.getEmail());
		studentDto.setNumber(student.getNumber());
		if (book != null) {
			libraryDTO.setBookId(student.getBook().getBookId());
			libraryDTO.setBookName(student.getBook().getBookName());
			libraryDTO.setBookAuthor(student.getBook().getBookAuthor());
			studentDto.setLibrary(libraryDTO);
		}
		return studentDto;
	}

	@Override
	public void update(long id, StudentDTO studentDTO) {
		Optional<Student> optionalStudent = studentRepository.findById(id);

		if (optionalStudent.isPresent()) {
			Student studentEdit = optionalStudent.get();

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

			if (studentDTO.getLibrary() != null) {
				Library library = new Library();
				library.setBookId(studentDTO.getLibrary().getBookId());
				library.setBookName(studentDTO.getLibrary().getBookName());
				library.setBookAuthor(studentDTO.getLibrary().getBookAuthor());
				studentEdit.setBook(library);
			}
			studentRepository.save(studentEdit);
		}
	}

	@Override
	public void save(StudentDTO studentDTO) {
		if (studentDTO.getId() == 0) {
			Student student = new Student();
			Library library = null; // Allow null library for new student
			if (studentDTO.getLibrary() != null) {
				library = new Library();
				library.setBookId(studentDTO.getLibrary().getBookId());
				library.setBookName(studentDTO.getLibrary().getBookName());
				library.setBookAuthor(studentDTO.getLibrary().getBookAuthor());
				libraryRepository.save(library);
			}
			student.setBook(library);
			student.setRollNo(studentDTO.getRollNo());
			student.setName(studentDTO.getName());
			student.setEmail(studentDTO.getEmail());
			student.setNumber(studentDTO.getNumber());
			studentRepository.save(student);
		} else {
			Optional<Student> optionalStudent = studentRepository.findById(studentDTO.getId());
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

		return student;
	}

	@Override
	public void assignBookToStudent(Long studentId, Long bookId) {

		Optional<Student> studentOptional = studentRepository.findById(studentId);
		Optional<Library> bookOptional = libraryRepository.findById(bookId);

		if (studentOptional.isPresent()) {

			if (bookOptional.isPresent()) {
				Student student = studentOptional.get();
				Library library = bookOptional.get();

				if (student.getBook() != null) {
					throw new IllegalArgumentException("Student already has a book assigned");
				}
				student.setBook(library);
				studentRepository.save(student);
			} else {
				throw new IllegalArgumentException("Book not found");
			}
		} else {
			throw new IllegalArgumentException("Student not found");
		}

	}

}
