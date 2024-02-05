package com.crud.mapping.service.library;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.mapping.dto.LibraryDTO;
import com.crud.mapping.model.Library;
import com.crud.mapping.repository.LibraryRepository;

@Service
public class LibraryServiceImpl implements LibraryService{

	
	@Autowired
    private LibraryRepository libraryRepository;

	
	@Override
	public List<LibraryDTO> getAllStudents() {
		List<LibraryDTO> list = new ArrayList<>();
		List<Library>librarylist = libraryRepository.findAll();
		for (Library library : librarylist) {
			LibraryDTO libraryDTO = new LibraryDTO();
			libraryDTO.setBookId(library.getBookId());
			libraryDTO.setBookName(library.getBookName());
			libraryDTO.setBookAuthor(library.getBookAuthor());
			list.add(libraryDTO);
		}
		return list;
	}

	@Override
	public LibraryDTO getStudentById(Long id) {
		Library library = libraryRepository.findById(id).get();
		LibraryDTO libraryDTO = new LibraryDTO();
		libraryDTO.setBookId(library.getBookId());
		libraryDTO.setBookName(library.getBookName());
		libraryDTO.setBookAuthor(library.getBookAuthor());
		return libraryDTO;
	}

	@Override
	public void update(long id, LibraryDTO libraryDTO) {
		Library libraryEdit = libraryRepository.findById(id).get();
		if (libraryDTO.getBookName() != null) {
			libraryEdit.setBookName(libraryDTO.getBookName());
		} 
		if (libraryDTO.getBookAuthor() != null) {
			libraryEdit.setBookAuthor(libraryDTO.getBookAuthor());
		}
		libraryRepository.save(libraryEdit);
	}


	@Override
	public void save(LibraryDTO libraryDTO) {
		if (libraryDTO.getBookId() == 0) {
			Library library = new Library();
			library.setBookName(libraryDTO.getBookName());
			library.setBookAuthor(libraryDTO.getBookAuthor());
			libraryRepository.save(library);
		} else {
			Library libraryEdit = libraryRepository.findById(libraryDTO.getBookId()).get();
			if (libraryDTO.getBookName() != null) {
				libraryEdit.setBookName(libraryDTO.getBookName());
			} else if (libraryDTO.getBookAuthor() != null) {
				libraryEdit.setBookAuthor(libraryDTO.getBookAuthor());
			}
			libraryRepository.save(libraryEdit);
		}
	}

	@Override
	public Library deleteBookById(Long id) {

		Library library = libraryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

		libraryRepository.deleteById(id);

		return library;
	}
}
