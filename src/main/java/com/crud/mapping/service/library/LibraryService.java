package com.crud.mapping.service.library;

import java.util.List;

import com.crud.mapping.dto.LibraryDTO;
import com.crud.mapping.model.Library;

public interface LibraryService {
	List<LibraryDTO> getAllStudents();
	LibraryDTO getStudentById(Long id);
    Library deleteBookById(Long id);
    public void save(LibraryDTO libraryDTO);
	public void update(long id, LibraryDTO libraryDTO);
}
