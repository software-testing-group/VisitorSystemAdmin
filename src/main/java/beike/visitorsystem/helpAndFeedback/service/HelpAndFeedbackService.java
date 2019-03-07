package beike.visitorsystem.helpAndFeedback.service;

import beike.visitorsystem.helpAndFeedback.model.Feedback;
import beike.visitorsystem.helpAndFeedback.model.Help;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public interface HelpAndFeedbackService {
    boolean addHelp(Help help);
    boolean updateHelp(Help help);
    boolean deleteHelpById(BigInteger id);
    List<Help> getHelps(int page,int number);
    Help getHelpDetailById(BigInteger id);

    int getSumOfHelps();


    boolean addFeedback(Feedback feedback);
    boolean updateFeedback(Feedback feedback);
    boolean deleteFeedbackById(BigInteger id);
    List<Feedback> selectFeedbacks(BigInteger userId,String content,int page,int number);
    Feedback getFeedbackDetailById(BigInteger id);

    int getSumOfFeedbacks(BigInteger userId,String content);
}
