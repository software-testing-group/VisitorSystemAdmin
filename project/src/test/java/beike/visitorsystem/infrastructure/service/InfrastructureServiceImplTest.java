package beike.visitorsystem.infrastructure.service;

import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.infrastructure.mapper.InfrastructureMapper;
import beike.visitorsystem.infrastructure.model.Infrastructure;
import beike.visitorsystem.infrastructure.model.InfrastructureType;
import beike.visitorsystem.security.SecurityConfig;
import beike.visitorsystem.utils.GenerateID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

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
public class InfrastructureServiceImplTest {

    @Autowired
    private InfrastructureServiceImpl infrastructureService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addInfrastructure() {
        Infrastructure infrastructure = new Infrastructure();
        //1
        infrastructure.setTypeId(null);
        infrastructure.setName("芙蓉公社超市");
        infrastructure.setLongitude(new BigDecimal(118.0983));
        infrastructure.setLatitude(new BigDecimal(24.4389));
        assertEquals(false,infrastructureService.addInfrastructure(infrastructure));
        //2
        infrastructure.setTypeId(new BigInteger("2"));
        infrastructure.setName(null);
        assertEquals(false,infrastructureService.addInfrastructure(infrastructure));
        //3
        infrastructure.setName("");
        assertEquals(false,infrastructureService.addInfrastructure(infrastructure));
        //4
        infrastructure.setName("芙蓉公社超市");
        infrastructure.setLongitude(new BigDecimal(181.0003));
        assertEquals(false,infrastructureService.addInfrastructure(infrastructure));
        //5
        infrastructure.setLongitude(null);
        infrastructure.setLatitude(null);
        assertEquals(true,infrastructureService.addInfrastructure(infrastructure));
        //6
        infrastructure.setLongitude(new BigDecimal(118.0983));
        assertEquals(true,infrastructureService.addInfrastructure(infrastructure));
        //7
        infrastructure.setLatitude(new BigDecimal(24.4389));
        assertEquals(true,infrastructureService.addInfrastructure(infrastructure));
        //8
        infrastructure.setLatitude(new BigDecimal(90.0003));
        assertEquals(false,infrastructureService.addInfrastructure(infrastructure));
    }

    @Test
    public void updateInfrastructure() {
        Infrastructure infrastructure = new Infrastructure();
        //1
        infrastructure.setId(new BigInteger("70097391144640"));
        infrastructure.setTypeId(new BigInteger("2"));
        infrastructure.setName(null);
        infrastructure.setLongitude(new BigDecimal(118.0983));
        infrastructure.setLatitude(new BigDecimal(24.4389));
        assertEquals(false,infrastructureService.updateInfrastructure(infrastructure));
        //2
        infrastructure.setName("");
        assertEquals(false,infrastructureService.updateInfrastructure(infrastructure));
        //3
        infrastructure.setName("芙蓉公社超市");
        infrastructure.setLongitude(new BigDecimal(181.0003));
        assertEquals(false,infrastructureService.updateInfrastructure(infrastructure));
        //4
        infrastructure.setLongitude(null);
        infrastructure.setLatitude(null);
        assertEquals(true,infrastructureService.updateInfrastructure(infrastructure));
        //5
        infrastructure.setLongitude(new BigDecimal(118.0983));
        assertEquals(true,infrastructureService.updateInfrastructure(infrastructure));
        //6
        infrastructure.setLatitude(new BigDecimal(24.4389));
        assertEquals(true,infrastructureService.updateInfrastructure(infrastructure));
        //7
        infrastructure.setLatitude(new BigDecimal(90.0003));
        assertEquals(false,infrastructureService.updateInfrastructure(infrastructure));
    }

    @Test
    public void deleteInfrastructureById() {
        assertEquals(true,infrastructureService.deleteInfrastructureById(new BigInteger("70103631615040")));
        assertEquals(false,infrastructureService.deleteInfrastructureById(new BigInteger("7010363161504")));
    }

    @Test
    public void addInfrastructureType() {
        InfrastructureType infrastructureType = new InfrastructureType();
        //1
        infrastructureType.setName(null);
        JSONObject json = new JSONObject();
        List<Infrastructure> infrastructureList = json.parseArray("[]",Infrastructure.class);
        assertEquals(false,infrastructureService.addInfrastructureType(infrastructureType,infrastructureList));
        //2
        infrastructureType.setName("超市-1");
        assertEquals(true,infrastructureService.addInfrastructureType(infrastructureType,infrastructureList));
        //3
        Infrastructure infrastructure = new Infrastructure();
        infrastructure.setName("芙蓉公社超市-1");
        infrastructure.setLongitude(new BigDecimal(120.0983));
        infrastructure.setLatitude(new BigDecimal(24.4389));
        infrastructureList.add(infrastructure);
        assertEquals(true,infrastructureService.addInfrastructureType(infrastructureType,infrastructureList));
        //4
        infrastructureList.clear();
        infrastructure.setLongitude(new BigDecimal(121.0983));
        infrastructure.setLatitude(new BigDecimal(25.4389));
        infrastructureList.add(infrastructure);
        Infrastructure infrastructure1 = new Infrastructure();
        infrastructure1.setName("芙蓉公社超市-2");
        infrastructure1.setLongitude(new BigDecimal(119.0984));
        infrastructure1.setLatitude(new BigDecimal(24.4380));
        infrastructureList.add(infrastructure1);
        assertEquals(true,infrastructureService.addInfrastructureType(infrastructureType,infrastructureList));
    }

    @Test
    public void deleteInfrastructureType() {
        assertEquals(true,infrastructureService.deleteInfrastructureType(new BigInteger("70103890424640")));
        assertEquals(false,infrastructureService.deleteInfrastructureType(new BigInteger("70103890424641")));
    }
}