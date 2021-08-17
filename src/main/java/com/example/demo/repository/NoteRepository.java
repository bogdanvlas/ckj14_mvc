package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {
	List<Note> findByTitle(String title);
	
	// поиск по подстроке, содержащейся в названии или описании

}
