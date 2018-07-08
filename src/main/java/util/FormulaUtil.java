package util;

public class FormulaUtil {

	/**
	 * find the index of the first position among multiple symbols 
	 * from the string
	 * 
	 * @param str
	 * @param syms
	 * @return
	 */
	public static int indexOfSymbols(String str, String... syms) {
		int firstIndex = -1;
		for (String sym : syms) {
			int symIndex = str.indexOf(sym);
			if (symIndex != -1) {
				firstIndex = (firstIndex != -1 ? Math.min(firstIndex, symIndex) : symIndex);
			}
		}
		return firstIndex;
	}

	/**
	 * find the index of the last position among multiple symbols 
	 * from the string
	 * 
	 * @param str
	 * @param syms
	 * @return
	 */
	public static int lastIndexOfSymbols(String str, String... syms) {
		int lastIndex = -1;
		for (String sym : syms) {
			int symIndex = str.lastIndexOf(sym);
			lastIndex = (symIndex == -1) ? lastIndex : Math.max(lastIndex, symIndex);
		}
		return lastIndex;
	}

	/**
	 * replace " " to "" in the string
	 * 
	 * @param formula
	 * @return
	 */
	public static String cleanSpace(String formula) {
		formula = formula.replace(" ", "");
		return formula;
	}

	/**
	 * check if formula contains '(' or ')'
	 * 
	 * @param formula
	 * @return
	 */
	public static boolean containsBracketsPair(String formula) {
		return formula.contains("(") && formula.contains(")");
	}
	
	/**
	 * check if formula contains '*' or '/'
	 * @param formula
	 * @return
	 */
	public static boolean containsMultiplyOrDivide(String formula) {
		return formula.contains("*") || formula.contains("/");
	}
}
