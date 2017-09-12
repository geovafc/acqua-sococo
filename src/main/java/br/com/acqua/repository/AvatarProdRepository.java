package br.com.acqua.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.AvatarProd;

public interface AvatarProdRepository extends JpaRepository<AvatarProd, Long>{

	AvatarProd findByAvatar(byte[] avatar);
}
