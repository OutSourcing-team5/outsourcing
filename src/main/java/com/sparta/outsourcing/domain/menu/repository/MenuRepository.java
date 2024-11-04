package com.sparta.outsourcing.domain.menu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findAllByStore(Store store);

	@Query("SELECT m FROM Menu m WHERE m.store = :store AND m.isDeleted = false")
	List<Menu> findAllByActiveStore(Store store);

	@Query("SELECT m FROM Menu m WHERE m.store = :store AND m.isDeleted = false")
	Page<Menu> findAllByActiveStore(Store store, Pageable menuPageable);
}
