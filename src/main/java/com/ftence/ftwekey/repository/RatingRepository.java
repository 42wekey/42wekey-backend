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
    Double starRatingAvg(@Param("subject") Long subject);


    /***
     * 별점 선택지 각각의 개수
     */

    @Query(value = "SELECT COUNT(Rating.star_rating) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.star_rating=:rated AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    Integer starRatingCount(@Param("subject") Long subject, @Param("rated") int rated);




//    /***
//     * 별점 개수가 제일 많은 선택지의 퍼센테이지
//     *
//     */
//    @Query(value = "SELECT COUNT(*) / (SELECT COUNT(*) FROM Comment WHERE Comment.subject_id = 1) * 100 AS percentage " +
//            "FROM Comment, Rating " +
//            "WHERE Comment.subject_id=:subject AND Rating.star_rating=:rated AND Comment.rating_id = Rating.id"
//            , nativeQuery = true)
//    Double starRatingPercentage(@Param("subject") Long subject, @Param("rated") int rated);
//


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
     * @param value  a_week , two_week , three_week , a_month , three_month
     * @return
     */
    @Query(value = "SELECT COUNT(Rating.time_taken) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.time_taken=:value AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer timeTakenAWeekCount(@Param("subject") Long subject, @Param("value") String value);



    /***
     * Time Taken 각 결과에 해당하는 퍼센테이지 구하기
     * @param subject
     * @param value    찾고자 하는 문자열 ex) a_week
     * @return
     */
    @Query(value = "SELECT COUNT(*) / (SELECT COUNT(*) FROM Comment WHERE Comment.subject_id=:subject) * 100 AS percentage " +
            "FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.time_taken=:value AND Comment.rating_id = Rating.id"
            , nativeQuery = true)
    Double timeTakenPercentage(@Param("subject") Long subject, @Param("value") String value);




    /***
     * 학습량 선택지 개수
     * @param subject
     * @param value         low , middle , high
     * @return
     */

    @Query(value = "SELECT COUNT(Rating.amount_study) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.amount_study=:value AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer amountStudyLowCount(@Param("subject") Long subject, @Param("value") String value);


    /***
     * amount study 공부량 각 결과에 해당하는 퍼센테이지 구하기
     * @param subject
     * @param value    찾고자 하는 문자열 low , middle , high
     * @return
     */
    @Query(value = "SELECT COUNT(*) / (SELECT COUNT(*) FROM Comment WHERE Comment.subject_id=:subject) * 100 AS percentage " +
            "FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.amount_study=:value AND Comment.rating_id = Rating.id"
            , nativeQuery = true)
    Double amountStudyPercentage(@Param("subject") Long subject, @Param("value") String value);


    /***
     * 보너스 여부 개수
     * @param subject
     * @param value         no , little , complete
     * @return
     */
    @Query(value = "SELECT COUNT(Rating.bonus) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.bonus=:value AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer bonusNoCount(@Param("subject") Long subject, @Param("value") String value);


    /***
     * bonus 공부량 각 결과에 해당하는 퍼센테이지 구하기
     * @param subject
     * @param value    찾고자 하는 문자열 low , middle , high
     * @return
     */
    @Query(value = "SELECT COUNT(*) / (SELECT COUNT(*) FROM Comment WHERE Comment.subject_id=:subject) * 100 AS percentage " +
            "FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.bonus=:value AND Comment.rating_id = Rating.id"
            , nativeQuery = true)
    Double bonusPercentage(@Param("subject") Long subject, @Param("value") String value);



    /***
     * 체감 난이도 개수
     * @param subject
     * @param value         easy , normal ,hard
     * @return
     */

    @Query(value = "SELECT COUNT(Rating.difficulty) FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.difficulty=:value AND Comment.rating_id=Rating.id"
            , nativeQuery = true)
    public Integer difficultyEasyCount(@Param("subject") Long subject, @Param("value") String value);


    /***
     * bonus 공부량 각 결과에 해당하는 퍼센테이지 구하기
     * @param subject
     * @param value    찾고자 하는 문자열 easy , normal ,hard
     * @return
     */
    @Query(value = "SELECT COUNT(*) / (SELECT COUNT(*) FROM Comment WHERE Comment.subject_id=:subject) * 100 AS percentage " +
            "FROM Comment, Rating " +
            "WHERE Comment.subject_id=:subject AND Rating.difficulty=:value AND Comment.rating_id = Rating.id"
            , nativeQuery = true)
    Double  difficultyPercentage(@Param("subject") Long subject, @Param("value") String value);


}

