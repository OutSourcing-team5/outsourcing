package com.sparta.outsourcing.domain.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	int countAllByMemberAndIsDeletedFalse(Member storeOwner);
	Page<Store> findAllByStoreNameContaining(String storeName, Pageable pageable);

}
