package beike.visitorsystem.helpAndFeedback.service;

import beike.visitorsystem.helpAndFeedback.mapper.FeedbackMapper;
import beike.visitorsystem.helpAndFeedback.mapper.HelpMapper;
import beike.visitorsystem.helpAndFeedback.model.Feedback;
import beike.visitorsystem.helpAndFeedback.model.Help;
import beike.visitorsystem.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HelpAndFeedbackServiceImpl implements HelpAndFeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private HelpMapper helpMapper;
    @Autowired
    GenerateID generateID;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean addHelp(Help help)
    {
        if(help==null){
            return false;
        }
        if(help.getQuestion()==null||help.getAnswer()==null){
            return false;
        }
        if(help.getQuestion().equals("")||help.getAnswer().equals("")){
            return false;
        }
        help.setId(generateID.getID());
        help.setCreateTime(df.format(new Date()).toString());
        help.setUpdateTime(df.format(new Date()).toString());
        return helpMapper.addHelp(help);
    }

    public boolean updateHelp(Help help)
    {
        if(help==null||help.getId()==null){
            return false;
        }
        if(help.getQuestion()==null||help.getAnswer()==null){
            return false;
        }
        if(help.getQuestion().equals("")||help.getAnswer().equals("")){
            return false;
        }
        help.setUpdateTime(df.format(new Date()).toString());
        return helpMapper.updateHelp(help);
    }

    public boolean deleteHelpById(BigInteger id)
    {
        return  helpMapper.deleteHelpById(id);
    }

    public List<Help> getHelps(int page, int number)
    {
        List<Help> helps = helpMapper.getHelps((page - 1)*number, number);
        return  helps;
    }

    public Help getHelpDetailById(BigInteger id)
    {
        Help help = helpMapper.getHelpDetailById(id);
        return  help;
    }

    public int getSumOfHelps()
    {
        return helpMapper.getSumOfHelps();
    }



    public boolean addFeedback(Feedback feedback)
    {
        feedback.setId(generateID.getID());
        feedback.setCreateTime(df.format(new Date()).toString());
        feedback.setUpdateTime(df.format(new Date()).toString());
        return feedbackMapper.addFeedback(feedback);
    }

    public boolean updateFeedback(Feedback feedback)
    {
        feedback.setUpdateTime(df.format(new Date()).toString());
        return  feedbackMapper.updateFeedback(feedback);
    }

    public boolean deleteFeedbackById(BigInteger id)
    {
        return feedbackMapper.deleteFeedbackById(id);
    }

    public List<Feedback> selectFeedbacks(BigInteger userId,String content,int page,int number)
    {
        List<Feedback> feedbackList = feedbackMapper.selectFeedbacks(userId, content, (page - 1)*number, number);
        return feedbackList;
    }
    public Feedback getFeedbackDetailById(BigInteger id)
    {
        Feedback feedback = feedbackMapper.getFeedbackDetailById(id);
        return  feedback;
    }

    public int getSumOfFeedbacks(BigInteger userId,String content)
    {
        if(content=="")
        {
            content = null;
        }
        return feedbackMapper.getSumOfFeedbacks(userId, content);
    }
}
