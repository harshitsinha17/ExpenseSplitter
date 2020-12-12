package com.controller.test;

import java.util.List;

import com.controller.ControllerService;
import com.participant.Users;
import com.payment.PaymentDetails;

@RunWith(MockitoJunitRunner.class)
public class ControllerTest {
	
	@InjectMocks
	private ControllerService testController;
	
	private static final String EVENT_TYPE = "Rent";
	private static final float AMOUNT = 1000f;
	private static final String PAID_BY = "Ram";
	private static final String SPLIT_BY_EQUAL = "equal";
	private static final String SPLIT_BY_PERCENT = "60";
	private PaymentDetails entity;
	private List<Users> con;
	
	
	@BeforeMethod
	public void initTest() {
		entity = createEntity(EVENT_TYPE,AMOUNT,PAID_BY);
	}
	
	@Test
	public void testCalculateWhenSplitEqual() {
		entity.setSplit_by(SPLIT_BY_EQUAL);
		con = testController.getOwedMoney(entity);
		AssertJUnit.assertNotNull(con);
		AssertJUnit.assertEquals(con.get(0).getAmount(), -0.75 * AMOUNT);
		AssertJUnit.assertEquals(con.get(1).getAmount(), 0.25 * AMOUNT);
		AssertJUnit.assertEquals(con.get(2).getAmount(), 0.25 * AMOUNT);
		AssertJUnit.assertEquals(con.get(3).getAmount(), 0.25 * AMOUNT);
		
	}
	
	@Test
	public void testCalculateWhenSplitPercent() {
		entity.setSplit_by(SPLIT_BY_PERCENT);
		con = testController.getOwedMoney(entity);
		AssertJUnit.assertNotNull(con);
		AssertJUnit.assertEquals(con.get(0).getAmount(), -0.60 * AMOUNT);
		AssertJUnit.assertEquals(con.get(1).getAmount(), 0.20 * AMOUNT);
		AssertJUnit.assertEquals(con.get(2).getAmount(), 0.20 * AMOUNT);
		AssertJUnit.assertEquals(con.get(3).getAmount(), 0.20 * AMOUNT);
		
	}
	
	
	private PaymentDetails createEntity(final String name, final float amount, final String paid_by, final String split_by ) {
		PaymentDetails details = new PaymentDetails();
		details.setName(name);
		details.setAmount(amount);
		details.setPaid_by(paid_by);
		return details;
	}

}
