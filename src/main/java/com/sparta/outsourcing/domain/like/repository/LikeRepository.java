package com.sparta.outsourcing.domain.like.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.outsourcing.domain.like.entity.Like;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByMemberAndStore(Member member, Store store);

	@Query("SELECT l.store FROM Like l WHERE l.member = :member AND l.store.inactive = false ORDER BY l.store.modifiedAt DESC")
	List<Store> findStoresByMemberAndInactiveFalseOrderByModifiedAtDesc(@Param("member") Member member);

	@Query("SELECT l.store FROM Like l WHERE l.member = :member AND l.store.inactive = false AND l.store.storeName LIKE %:storeName% ORDER BY l.store.modifiedAt DESC")
	List<Store> findStoresByMemberAndInactiveFalseAndStoreNameContainingOrderByModifiedAtDesc(@Param("member") Member member, @Param("storeName") String storeName);

	List<Like> findAllByMemberAndStore(Member member, Store store);
}
