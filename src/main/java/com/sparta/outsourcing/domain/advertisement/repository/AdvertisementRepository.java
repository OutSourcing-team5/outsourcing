package com.sparta.outsourcing.domain.advertisement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.outsourcing.domain.advertisement.entity.Advertisement;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
	boolean existsByMemberAndStore(Member member, Store store);
	Optional<Advertisement> findByMemberAndStore(Member member, Store store);

	@Query("SELECT a.store FROM Advertisement a WHERE a.store.storeName LIKE %:storeName% AND a.store.inactive = false")
	List<Store> findStoresByStoreNameContainingAndInactiveFalse(String storeName);

	@Query("SELECT a.store FROM Advertisement a WHERE a.store.inactive = false")
	List<Store> findAllByInactiveFalse();
}
