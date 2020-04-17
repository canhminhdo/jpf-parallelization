package abp.main;

import java.util.*;

public class TestABP {
    public static void main(String[] args)
        throws InterruptedException
    {
        /*
        String packets[] = {
            "Smalltalk-80", "Self", "CLOS", "Simula 67", "C++",
            "Objective-C", "ABCL/1", "Java" };
        */
        // String packets[] = { "Simula 67", "Smalltalk-80", "Java" };
        // String packets[] = { "Simula 67", "Smalltalk-80" };
        String packets[] = { "Simula 67" };
        List<String> list1 = Arrays.asList(packets);
        List<String> list2 = new ArrayList<String>();
        ABP<String> abp = new ABP<String>();
        // abp.begin(list1,list2,2);
        abp.begin(list1,list2,1);
        /*
        System.out.println("Packets sent: " + list1);
        System.out.println("Packets rec: " + list2);
        */
        assert list1.equals(list2);
    }
}