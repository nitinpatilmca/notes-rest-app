package org.gotprint.persistence;

import org.gotprint.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<Users, String>{
	
	@Query("SELECT u FROM Users u WHERE u.username = ?1 AND u.password = ?2")
	public Users loginValidation(String username,String password);
	
	@Query("SELECT u FROM Users u WHERE u.email = ?1")
	public Users findUserByEmailId(String email);

}
