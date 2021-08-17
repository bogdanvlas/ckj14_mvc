package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	// SELECT * FROM users WHERE users.name = name
	User findByUsername(String name);

	// SELECT * FROM users WHERE users.name = name
	List<User> findAllByUsername(String name);

	// SELECT * FROM users WHERE users.age = age ORDER BY age LIMIT 2
	List<User> findTop2ByAge(int age);

	// SELECT * FROM users WHERE users.name LIKE '%'+str+'%'
	List<User> findByUsernameContaining(String str);

	// SELECT * FROM users WHERE users.age > age
	List<User> findByAgeGreaterThan(int age);

	// SELECT * FROM users WHERE users.name LIKE '%'+str+'%' AND users.age > age
	List<User> findByUsernameContainingAndAgeGreaterThan(String str, int age);

	@Query("SELECT u FROM User u WHERE u.age > :param AND u.username LIKE :str")
	List<User> search(@Param("str") String str, @Param("param") int param);

}
