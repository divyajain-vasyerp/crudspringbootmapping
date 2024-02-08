package com.crud.mapping.service.student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.crud.mapping.dto.LibraryDTO;
import com.crud.mapping.dto.StudentDTO;
import com.crud.mapping.exception.BookIsAlreadyAssignedException;
import com.crud.mapping.exception.StudentNotFoundException;
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
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student Not Found: " + id));
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
	public StudentDTO update(long id, StudentDTO studentDTO) {
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
		return studentDTO;
	}

	@Override
	public StudentDTO save(StudentDTO studentDTO) {
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
				Student studentEdit = optionalStudent
						.orElseThrow(() -> new NullPointerException("Any Feilds is null values"));
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
		return studentDTO;
	}

	@Override
	public Student deleteStudentById(Long id) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

		studentRepository.deleteById(id);

		return student;
	}

	@Override
	public Student assignBookToStudent(Long studentId, Long bookId) {

		Optional<Student> studentOptional = studentRepository.findById(studentId);
		Optional<Library> bookOptional = libraryRepository.findById(bookId);
		Student student1;
		if (studentOptional.isPresent()) {

			if (bookOptional.isPresent()) {
				Student student = studentOptional.get();
				Library library = bookOptional.get();

				if (student.getBook() != null) {
					throw new BookIsAlreadyAssignedException("another student already book assigned ");
				}
				student.setBook(library);
				student1 = studentRepository.save(student);
			} else {
				throw new BookIsAlreadyAssignedException("Book not found");
			}
		} else {
			throw new StudentNotFoundException("Student not found: " + studentId);
		}
		;
		return student1;
	}

	public static final String Upload_File = "D:\\JAVA\\DemoApiCrudMapping\\uploadFiles";

	@Override
	public String uploadFile(MultipartFile file) throws IOException{
		String uriString = null;
		try {
			File uploadDir = new File(Upload_File);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			String fileName = file.getOriginalFilename();
			Path path = Paths.get(Upload_File).resolve(fileName);
			Files.write(path, file.getBytes(), StandardOpenOption.CREATE);
			uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/download/").path(fileName)
					.toUriString();
		} catch (Exception e) {
			throw new IOException("File Fields is Empty ");
		}
		return uriString;
	}

	@Override
	public String uploadTextFile(MultipartFile file) throws IOException {
		String fileName;
		try {
			File directory = new File(Upload_File);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (file.getContentType().equals("text/plain")) {
				fileName = "index.txt";
				Path filePath = Paths.get(Upload_File).resolve(fileName);
				if (!filePath.toFile().exists()) {
					filePath.toFile().createNewFile();
				}
				Files.write(filePath, file.getBytes(), StandardOpenOption.APPEND);
			} else {
				fileName = file.getOriginalFilename();
				Path filePath = Paths.get(Upload_File).resolve(fileName);
				Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
			}

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/download/")
					.path(fileName).toUriString();

			System.out.println(file.getContentType());

			return ("File uploaded successfully. Download URL: " + fileDownloadUri);
		} catch (IOException ex) {
			throw new IOException("File Fields is Empty : ");
		}
	}

	@Override
	public ResponseEntity<Object> downloadFile(String fileName) throws IOException {

		ResponseEntity<Object> response = null;
		try {
			Path filepath = Paths.get(Upload_File).resolve(fileName).normalize();
			File file = filepath.toFile();
			if (file.exists()) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", fileName);
				response = ResponseEntity.ok().headers(headers).contentLength(file.length())
						.body(new FileSystemResource(file));
			} else {
				response = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			throw new IOException("Could not upload the file: ");
		}
		return response;
	}
}
