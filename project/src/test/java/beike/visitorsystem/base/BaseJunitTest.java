package beike.visitorsystem.base;

import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.infrastructure.service.InfrastructureServiceImplTest;
import beike.visitorsystem.security.SecurityConfig;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

// ʹ��Spring�е�junit
@RunWith(SpringJUnit4ClassRunner.class)
// ������WebApplication�������Ļ���
@WebAppConfiguration
// ʹ��WebConfig��SecurityConfig��������
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class})
// ��������ع�
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional

public class BaseJunitTest {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for beike.visitorsystem.infrastructure.service");
        //$JUnit-BEGIN$
        suite.addTestSuite(InfrastructureServiceImplTest.class);
        //$JUnit-END$
        return suite;
    }

}

