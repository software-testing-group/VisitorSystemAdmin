package beike.visitorsystem.authority.service;

import beike.visitorsystem.config.DataConfig;
import beike.visitorsystem.config.RootConfig;
import beike.visitorsystem.scenery.mapper.AttractionMapper;
import beike.visitorsystem.scenery.model.Attraction;
import beike.visitorsystem.utils.RoutePlanning;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import beike.visitorsystem.authority.controller.AuthorityController;
import beike.visitorsystem.config.WebAppInitializer;
import beike.visitorsystem.config.WebConfig;
import beike.visitorsystem.tour.controller.TourController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(classes = {WebConfig.class, WebAppInitializer.class, DataConfig.class, RootConfig.class})
@Transactional 
public class AuthorityServiceImplTest {
	@Autowired
	private AttractionMapper attractionMapper;

	@Test
	public void test() throws Exception {
		BigInteger route_id=new BigInteger("62078653193601");
		List<Attraction> aL = attractionMapper.getAttractionsByRouteId(route_id);
		String polyline = "";
		RoutePlanning rp = new RoutePlanning();
		try {
			for(int i=0;i<aL.size()-1;i++) {
				String message = rp.getPolyline(aL.get(i).getLatitude().doubleValue(), aL.get(i).getLongitude().doubleValue(), aL.get(i + 1).getLatitude().doubleValue(), aL.get(i + 1).getLongitude().doubleValue());
				JSONObject joo = JSONObject.parseObject(message);
				JSONArray result = ((JSONArray)((JSONObject)joo.get("result")).get("routes"));
				JSONObject r = (JSONObject) result.get(0);
				polyline = polyline + r.get("polyline").toString();
				System.out.println(i+"->"+(i+1));
				System.out.println(polyline);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
