import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import config.AppConfig;

public class HashMapTest {
	
	HashMap<Integer, HashMap<String, Integer>> seqSet = new HashMap<Integer, HashMap<String, Integer>>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		AppConfig.reset();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public void printSeqSet() {
		for (Map.Entry mapElement : seqSet.entrySet()) { 
			Integer depth = (Integer)mapElement.getKey(); 
            HashMap<String, Integer> seqDepthSet = (HashMap<String,Integer>)mapElement.getValue();
            System.out.println("Depth=" + depth + " #Seq=" + seqDepthSet.size());
		}
	}

	@Test
	public void testUpdateHashMap() {
		int depth1 = 100;
		if (!seqSet.containsKey(depth1)) {
			seqSet.put(depth1, new HashMap<String, Integer>());
		}
		HashMap<String, Integer> seqDepthSet = seqSet.get(depth1);
		seqDepthSet.put("Canh", 1);
		seqDepthSet.put("Thanh", 2);
		seqDepthSet.put("Vinh", 3);
		
		System.out.println(seqDepthSet);
		System.out.println(seqSet.get(depth1));
		
		seqDepthSet.remove("Canh");
		
		System.out.println(seqDepthSet);
		System.out.println(seqSet.get(depth1));
		
		seqSet.get(depth1).remove("Vinh");
		
		System.out.println(seqDepthSet);
		System.out.println(seqSet.get(depth1));
		
		System.out.println(seqSet);
		this.printSeqSet();
	}
}
