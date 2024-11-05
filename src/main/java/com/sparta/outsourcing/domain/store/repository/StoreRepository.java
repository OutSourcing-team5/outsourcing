package com.sparta.outsourcing.domain.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Page<Store> findAllByInactiveFalse(Pageable pageable);
	List<Store> findAllByMemberAndInactiveFalse(Member member);
	int countAllByMemberAndInactiveFalse(Member storeOwner);
	Page<Store> findAllByStoreNameContainingAndInactiveFalse(String storeName, Pageable pageable);
}
