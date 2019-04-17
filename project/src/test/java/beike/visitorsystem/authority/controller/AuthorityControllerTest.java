package beike.visitorsystem.authority.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import beike.visitorsystem.config.WebAppInitializer;
import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.security.SecurityConfig;
import beike.visitorsystem.security.SecurityWebInitializer;

@RunWith(SpringJUnit4ClassRunner.class)    
@WebAppConfiguration 
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class})
@TransactionConfiguration(defaultRollback = true) 
@Transactional
@WithMockUser(username="admin",password="wangdaye123",roles={"manage_role","manage_person"})
public class AuthorityControllerTest {
	
	@Autowired
	private AuthorityController userController;
	
	@Test
	public void test() throws Exception {
		
		System.out.println("hello");

	}

}
