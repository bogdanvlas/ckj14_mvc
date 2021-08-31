package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ConfirmationToken;

@Repository
public interface TokenRepository extends CrudRepository<ConfirmationToken, Integer>{
	ConfirmationToken findByValue(String value);
}
