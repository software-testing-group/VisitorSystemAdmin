package beike.visitorsystem.base;

import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.infrastructure.controller.InfrastructureControllerTest;
import beike.visitorsystem.security.SecurityConfig;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class})
@TransactionConfiguration(defaultRollback = true)
@Transactional

@WithMockUser(username="admin",password="admin123",roles={"manage_role","manage_person"})

public class BaseJunitTest {
    InfrastructureControllerTest infrastructureControllerTest;



}
