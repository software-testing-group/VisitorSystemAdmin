package beike.visitorsystem.helpAndFeedback.controller;

import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.security.SecurityConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class HelpAndFeedbackControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        // ָ��WebApplicationContext������Ӹ������Ļ�ȡ��Ӧ�Ŀ��������õ���Ӧ��MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getHelps() throws Exception{
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/helpAndFeedback/getHelps")
                .param("page","1")
                .accept(MediaType.ALL_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();                 //�õ����ش���
        String content = mvcResult.getResponse().getContentAsString();       //�õ����ؽ��

        Assert.assertEquals(200,status);              //���ԣ��жϷ��ش����Ƿ���ȷ
    }

    @Test
    public void getHelpDetailById() throws Exception{
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/helpAndFeedback/getHelpDetailById")
                .param("id","1")
                .accept(MediaType.ALL_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();                 //�õ����ش���
        String content = mvcResult.getResponse().getContentAsString();       //�õ����ؽ��

        Assert.assertEquals(200,status);              //���ԣ��жϷ��ش����Ƿ���ȷ
    }

    @Test
    public void getSumOfHelps() throws Exception{
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/helpAndFeedback/getSumOfHelps")
                .accept(MediaType.ALL_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();                 //�õ����ش���
        String content = mvcResult.getResponse().getContentAsString();       //�õ����ؽ��

        Assert.assertEquals(200,status);              //���ԣ��жϷ��ش����Ƿ���ȷ
    }
}