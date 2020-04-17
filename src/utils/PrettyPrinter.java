package utils;

import java.util.ArrayList;

/**
 * This file is a utility for debug printing
 * 
 * @author OgataLab
 *
 */
public class PrettyPrinter {
	
	public static void printHeader() {
		System.out.println("<==============PrettyPinter================");
	}
	
	public static void printList(ArrayList<String> list) {
		PrettyPrinter.printHeader();
		System.out.println("<+> Number of element: " + list.size());
		for (int i = 0; i < list.size(); i ++) {
			System.out.println("[" + (i + 1) + "]" + list.get(i));
		}
		PrettyPrinter.printFooter();
	}
	
	public static void printListToString(ArrayList<String> list) {
		PrettyPrinter.printHeader();
		if (list.size() == 0) {
			return;
		}
		String str = list.get(0);
		for (int i = 1; i < list.size(); i ++) {
			str += " " + list.get(i).trim();
		}
		System.out.println(str);
		PrettyPrinter.printFooter();
	}
	
	public static void printFooter() {
		System.out.println("==============================>");
	}

}
