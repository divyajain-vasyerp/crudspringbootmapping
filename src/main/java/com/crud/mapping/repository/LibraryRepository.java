package com.crud.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.mapping.model.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>{

}
