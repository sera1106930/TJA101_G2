package com.eatfast.announcement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // 記得要引入 Param
import org.springframework.stereotype.Repository;

import com.eatfast.announcement.model.AnnouncementEntity;
import com.eatfast.common.enums.AnnouncementStatus;

@Repository

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {

	
	List<AnnouncementEntity> findByStatus(AnnouncementStatus status);
	List<AnnouncementEntity> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime now1, LocalDateTime now2);
	List<AnnouncementEntity> findByStore_StoreId(Long storeId);
	List<AnnouncementEntity> findByEmployee_EmployeeId(Long employeeId);

	// ====================================================================
	//                      萬用 search 方法 
	// ====================================================================

	/**
	
	 * 對於每一個查詢條件，我們都先檢查傳入的參數是不是 null。
	 * 如果是 null，就等於讓這個條件永遠為 True，忽略這個查詢條件。
	 * 如果不是 null，比對欄位的內容。
	 * @param title 查詢的標題 (可為 null)
	 * @param status 查詢的狀態 (可為 null)
	 * @param startTime 查詢的開始時間 (可為 null)
	 * @param endTime 查詢的結束時間 (可為 null)
	 * @return 符合條件的公告列表
	 */
	@Query("SELECT a FROM AnnouncementEntity a WHERE " +
			"(:title IS NULL OR a.title LIKE %:title%) AND " +
			"(:status IS NULL OR a.status = :status) AND " +
			"(:startTime IS NULL OR a.startTime >= :startTime) AND " +
			"(:endTime IS NULL OR a.endTime <= :endTime)")
	List<AnnouncementEntity> search(
			@Param("title") String title,
			@Param("status") AnnouncementStatus status,
			@Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime
	);


	
	@Query("SELECT a FROM AnnouncementEntity a " +
			"WHERE a.status = :status " +
			"AND a.startTime <= CURRENT_TIMESTAMP " +
			"AND a.endTime >= CURRENT_TIMESTAMP")
	List<AnnouncementEntity> findCurrentlyActiveAnnouncements(@Param("status") AnnouncementStatus status);
}
