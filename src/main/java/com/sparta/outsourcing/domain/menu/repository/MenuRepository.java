package com.sparta.outsourcing.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
