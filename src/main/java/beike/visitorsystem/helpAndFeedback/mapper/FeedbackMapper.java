package beike.visitorsystem.helpAndFeedback.mapper;

import beike.visitorsystem.helpAndFeedback.model.Feedback;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface FeedbackMapper {

    boolean addFeedback(@Param("fb") Feedback feedback);
    boolean updateFeedback(@Param("fb") Feedback feedback);
    boolean deleteFeedbackById(@Param("id") BigInteger id);
    List<Feedback> selectFeedbacks(@Param("userId") BigInteger userId, @Param("content") String content, @Param("page") int page, @Param("number") int number);
    Feedback getFeedbackDetailById(@Param("id") BigInteger id);

    int getSumOfFeedbacks(@Param("userId") BigInteger userId, @Param("content") String content);
}
