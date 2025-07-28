package com.eatfast.feedback.repository;

import com.eatfast.feedback.model.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {

	
	List<FeedbackEntity> findByMemberId(Long memberId);

	// 對應 "storeId" 欄位
	List<FeedbackEntity> findByStoreId(Long storeId);
}
