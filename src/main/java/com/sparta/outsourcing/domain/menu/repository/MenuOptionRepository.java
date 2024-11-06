package com.sparta.outsourcing.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.menu.entity.MenuOption;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
}
