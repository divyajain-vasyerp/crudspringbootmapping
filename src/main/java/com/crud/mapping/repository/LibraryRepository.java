package com.crud.mapping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crud.mapping.model.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>{
	
	
	@Query("SELECT l FROM Library l ORDER BY l.id ASC")
	List<Library> findAll();

}
