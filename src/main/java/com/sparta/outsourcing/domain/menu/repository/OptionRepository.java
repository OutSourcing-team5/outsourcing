package com.sparta.outsourcing.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.menu.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
