package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    /***
     * 별점 평균 구하기
     * @param subject
     * @return
     */

    @Query(value = "SELECT AVG(star_rating) FROM Rating, Comment WHERE Comment.rating_id=Rating.id AND Comment.subject_id=:subject", nativeQuery = true)
    public Double starRatingAvg(@Param("subject") Long subject);


    /***
     * 총 평가자 수 합
     * @param subject
     * @return
     */

    @Query(value = "SELECT COUNT(star_rating) FROM Rating, Comment " +
            "WHERE Comment.rating_id=Rating.id AND Comment.subject_id=:subject", nativeQuery = true)
    public int countRating(@Param("subject") Long subject);


    /***
     * Time taken 각각의 선택지 개수
     * @param subject
     * @return
     */

    @Query(value = "SELECT COUNT(Rating.time_taken) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.time_taken = 'a_week' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer timeTakenAWeekCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.time_taken) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.time_taken = 'two_week' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer timeTakenTwoWeekCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.time_taken) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.time_taken = 'three_week' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer timeTakenThreeWeekCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.time_taken) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.time_taken = 'a_month' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer timeTakenAMonthCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.time_taken) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.time_taken = 'three_month' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer timeTakenThreeMonthCount(@Param("subject") Long subject);


    /***
     * 학습량 선택지 개수
     *
     */

    @Query(value = "SELECT COUNT(Rating.amount_study) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.amount_study = 'low' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer amountStudyLowCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.amount_study) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.amount_study = 'middle' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer amountStudyMiddleCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.amount_study) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.amount_study = 'high' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer amountStudyHighCount(@Param("subject") Long subject);

    /***
     * 보너스 여부 개수
     *
     */

    @Query(value = "SELECT COUNT(Rating.bonus) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.bonus = 'no' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer bonusNoCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.bonus) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.bonus = 'little' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer bonusLittleCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.bonus) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.bonus = 'complete' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer bonusCompleteCount(@Param("subject") Long subject);


    /***
     * 체감 난이도 개수
     */

    @Query(value = "SELECT COUNT(Rating.difficulty) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.difficulty = 'easy' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer difficultyEasyCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.difficulty) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.difficulty = 'normal' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer difficultyNormalCount(@Param("subject") Long subject);

    @Query(value = "SELECT COUNT(Rating.difficulty) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.difficulty = 'hard' AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer difficultyHardCount(@Param("subject") Long subject);

}

