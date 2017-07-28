package br.com.acqua.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
