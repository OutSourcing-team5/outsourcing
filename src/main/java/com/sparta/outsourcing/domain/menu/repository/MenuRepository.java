package com.sparta.outsourcing.domain.menu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Page<Menu> findAllByStoreAndInactiveFalse(Store store, Pageable menuPageable);
	List<Menu> findAllByStoreAndInactiveFalse(Store store);
	Page<Menu> findAllByStoreAndCategoryAndInactiveFalse(Store store, String category, Pageable menuPageable);
}
