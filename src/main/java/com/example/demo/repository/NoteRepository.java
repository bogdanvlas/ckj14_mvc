package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {
	List<Note> findByTitleContainingOrDescriptionContaining(String s1, String s2);

	@Query("SELECT n FROM Note n WHERE n.title LIKE :str OR n.description LIKE :str")
	List<Note> search(@Param("str") String str);
	
	Note findByIdAndUserUsername(int id, String username);
}
