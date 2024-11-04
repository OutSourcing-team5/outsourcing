package com.sparta.outsourcing.domain.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Page<Store> findAllAndIsDeletedFalse(Pageable pageable);

	@Query("SELECT COUNT(s) FROM Store s WHERE s.member = :member AND s.isDeleted = false")
	int countActiveStoresByMember(Member storeOwner);

	@Query("SELECT s FROM Store s WHERE s.storeName LIKE %:storeName% AND s.isDeleted = false")
	Page<Store> findAllByStoreNameContainingAndIsDeletedFalse(String storeName, Pageable pageable);

	@Query("SELECT s FROM Store s WHERE s.member = :member AND s.isDeleted = false")
	List<Store> findAllByActiveMember(Member member);
}
