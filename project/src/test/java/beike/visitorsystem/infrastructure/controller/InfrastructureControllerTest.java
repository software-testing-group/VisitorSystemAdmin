package beike.visitorsystem.infrastructure.controller;

import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.security.SecurityConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
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

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional

public class InfrastructureControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)��ָ��WebApplicationContext������Ӹ������Ļ�ȡ��Ӧ�Ŀ��������õ���Ӧ��MockMvc��
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//����ʹ������
    }

    @Test
    public void deleteInfrastructureType() throws Exception{
        /**
         * 1��mockMvc.performִ��һ������
         * 2��MockMvcRequestBuilders.get("XXX")����һ������
         * 3��ResultActions.param�������ֵ
         * 4��ResultActions.accept(MediaType.TEXT_HTML_VALUE))���÷�������
         * 5��ResultActions.andExpect���ִ����ɺ�Ķ��ԡ�
         * 6��ResultActions.andDo���һ���������������ʾҪ�Խ������ʲô����
         *   ����˴�ʹ��MockMvcResultHandlers.print()���������Ӧ�����Ϣ��
         * 5��ResultActions.andReturn��ʾִ����ɺ󷵻���Ӧ�Ľ����
         */

        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/infrastructure/deleteInfrastructureType")
                .param("id","66871337330880")
                .accept(MediaType.ALL_VALUE))
                // .andExpect(MockMvcResultMatchers.status().isOk())             //��ͬ��Assert.assertEquals(200,status);
                // .andExpect(MockMvcResultMatchers.content().string("true"))    //��ͬ��Assert.assertEquals("hello lvgang",content);
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();                 //�õ����ش���
        String content = mvcResult.getResponse().getContentAsString();    //�õ����ؽ��

        Assert.assertEquals(200,status);                        //���ԣ��жϷ��ش����Ƿ���ȷ
        Assert.assertEquals("true",content);            //���ԣ��жϷ��ص�ֵ�Ƿ���ȷ
    }


    @Test
    public void addInfrastructureType() throws Exception{
        JSONObject json = new JSONObject();

        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.post("/infrastructure/addInfrastructureType")
                .param("id","66871337330880")
                .accept(MediaType.ALL_VALUE))
                // .andExpect(MockMvcResultMatchers.status().isOk())             //��ͬ��Assert.assertEquals(200,status);
                // .andExpect(MockMvcResultMatchers.content().string("true"))    //��ͬ�� Assert.assertEquals("hello lvgang",content);
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();                 //�õ����ش���
        String content = mvcResult.getResponse().getContentAsString();    //�õ����ؽ��

        Assert.assertEquals(200,status);                        //���ԣ��жϷ��ش����Ƿ���ȷ
        Assert.assertEquals("true",content);            //���ԣ��жϷ��ص�ֵ�Ƿ���ȷ
    }
}