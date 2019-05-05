package beike.visitorsystem.helpAndFeedback.service;

import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.helpAndFeedback.model.Help;
import beike.visitorsystem.security.SecurityConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static org.junit.Assert.*;

// 使用Spring中的junit
@RunWith(SpringJUnit4ClassRunner.class)
// 声明是WebApplication的上下文环境
@WebAppConfiguration
// 使用WebConfig和SecurityConfig的上下文
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class})
// 进行事务回滚
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class HelpAndFeedbackServiceImplTest {

    @Autowired
    private HelpAndFeedbackServiceImpl helpAndFeedbackService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addHelp() {
        //1
        assertEquals(false,helpAndFeedbackService.addHelp(null));
        //2
        Help help = new Help();
        help.setQuestion(null);
        help.setAnswer("Answer");
        assertEquals(false,helpAndFeedbackService.addHelp(help));
        //3
        help.setQuestion("Question");
        help.setAnswer(null);
        assertEquals(false,helpAndFeedbackService.addHelp(help));
        //4
        help.setQuestion("");
        help.setAnswer("Answer");
        assertEquals(false,helpAndFeedbackService.addHelp(help));
        //5
        help.setQuestion("Question");
        help.setAnswer("");
        assertEquals(false,helpAndFeedbackService.addHelp(help));
        //6
        help.setQuestion("Question");
        help.setAnswer("Answer");
        assertEquals(true,helpAndFeedbackService.addHelp(help));
    }

    @Test
    public void updateHelp() {
        //1
        assertEquals(false,helpAndFeedbackService.updateHelp(null));
        //2
        Help help = new Help();
        help.setId(null);
        help.setQuestion("Question-1");
        help.setAnswer("Answer-1");
        assertEquals(false,helpAndFeedbackService.updateHelp(help));
        //3
        help.setId(new BigInteger("70113609472032"));
        help.setQuestion(null);
        help.setAnswer("Answer-1");
        assertEquals(false,helpAndFeedbackService.updateHelp(help));
        //4
        help.setQuestion("Question-1");
        help.setAnswer(null);
        assertEquals(false,helpAndFeedbackService.updateHelp(help));
        //5
        help.setQuestion("");
        help.setAnswer("Answer-1");
        assertEquals(false,helpAndFeedbackService.updateHelp(help));
        //6
        help.setQuestion("Question-1");
        help.setAnswer("");
        assertEquals(false,helpAndFeedbackService.updateHelp(help));
        //7
        help.setQuestion("Question-1");
        help.setAnswer("Answer-1");
        assertEquals(true,helpAndFeedbackService.updateHelp(help));
    }

    @Test
    public void deleteHelpById() {
        assertEquals(true,helpAndFeedbackService.deleteHelpById(new BigInteger("70113609472032")));
        assertEquals(false,helpAndFeedbackService.deleteHelpById(new BigInteger("70113609472031")));
    }
}