package jpf;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChoiceSelectorTest {
	static TraceStorer storer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		storer = new TraceStorer();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void calculateNextDeptTest1() {
		int currentDepth = Integer.MAX_VALUE;
		int storeDepth = Integer.MAX_VALUE;
		int bmcDepth = Integer.MAX_VALUE;
		assert storer.calculateNextDepth(currentDepth, storeDepth, bmcDepth) == currentDepth;
	}
	
	@Test
	public void calculateNextDeptTest2() {
		int currentDepth = 100;
		int storeDepth = Integer.MAX_VALUE;
		int bmcDepth = Integer.MAX_VALUE;
		assert storer.calculateNextDepth(currentDepth, storeDepth, bmcDepth) == storeDepth;
	}
	
	@Test
	public void calculateNextDeptTest3() {
		int currentDepth = 0;
		int storeDepth = 100;
		int bmcDepth = Integer.MAX_VALUE;
		assert storer.calculateNextDepth(currentDepth, storeDepth, bmcDepth) == 100;
	}
	
	@Test
	public void calculateNextDeptTest4() {
		int currentDepth = 100;
		int storeDepth = 100;
		int bmcDepth = Integer.MAX_VALUE;
		assert storer.calculateNextDepth(currentDepth, storeDepth, bmcDepth) == 200;
	}
	
	@Test
	public void calculateNextDeptTest5() {
		int currentDepth = 150;
		int storeDepth = 100;
		int bmcDepth = 200;
		assert storer.calculateNextDepth(currentDepth, storeDepth, bmcDepth) == bmcDepth;
	}
	
	@Test
	public void calculateNextDeptTest6() {
		int currentDepth = 150;
		int storeDepth = 100;
		int bmcDepth = 100;
		boolean flag = true;
		try {
			storer.calculateNextDepth(currentDepth, storeDepth, bmcDepth);
			flag = false;
		} catch (AssertionError error) {
			assert error.getMessage().equals("BMC depth must be equal to or greater than the current depth");
		}
		assert flag == true;
	}


}
