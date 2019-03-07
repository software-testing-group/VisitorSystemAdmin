package beike.visitorsystem.helpAndFeedback.controller;


import beike.visitorsystem.helpAndFeedback.model.Feedback;
import beike.visitorsystem.helpAndFeedback.model.Help;
import beike.visitorsystem.helpAndFeedback.service.HelpAndFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/helpAndFeedback")
public class HelpAndFeedbackController {

    @Autowired
    HelpAndFeedbackService helpAndFeedbackService;

    @RequestMapping(value = "/addHelp",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean addHelp(Help help)
    {
        return helpAndFeedbackService.addHelp(help);
    }

    @RequestMapping(value = "/updateHelp",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean updateHelp(Help help)
    {
        return helpAndFeedbackService.updateHelp(help);
    }

    @RequestMapping(value = "/deleteHelp",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean deleteHelpById(BigInteger id)
    {
        return helpAndFeedbackService.deleteHelpById(id);
    }

    @RequestMapping(value = "/getHelps",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Help> getHelps(int page)
    {
        return helpAndFeedbackService.getHelps(page,10);
    }

    @RequestMapping(value = "/getHelpDetailById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Help getHelpDetailById(BigInteger id)
    {
        return helpAndFeedbackService.getHelpDetailById(id);
    }

    @RequestMapping(value = "/getSumOfHelps",produces = "application/json;charset=utf-8")
    @ResponseBody
    public int getSumOfHelps()
    {
        return helpAndFeedbackService.getSumOfHelps();
    }




    @RequestMapping(value = "/addFeedback",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean addFeedback(Feedback feedback)
    {
        return helpAndFeedbackService.addFeedback(feedback);
    }

    @RequestMapping(value = "/updateFeedback",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean updateFeedback(Feedback feedback)
    {
        return helpAndFeedbackService.updateFeedback(feedback);
    }

    @RequestMapping(value = "/deleteFeedback",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean deleteFeedbackById(BigInteger id)
    {
        return helpAndFeedbackService.deleteFeedbackById(id);
    }

    @RequestMapping(value = "/getFeedbacks",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Feedback> getFeedbacks(BigInteger userId,String content,int page)
    {
        return helpAndFeedbackService.selectFeedbacks(userId,content,page,10);
    }

    @RequestMapping(value = "/getFeedbackDetailById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Feedback getFeedbackDetailById(BigInteger id)
    {
        return helpAndFeedbackService.getFeedbackDetailById(id);
    }

    @RequestMapping(value = "/getSumOfFeedbacks",produces = "application/json;charset=utf-8")
    @ResponseBody
    public int getSumOfFeedbacks(BigInteger userId,String content)
    {
        return helpAndFeedbackService.getSumOfFeedbacks(userId, content);
    }
}
