package util;

public class FormulaUtil {

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

	public static int lastIndexOfSymbols(String str, String... syms) {
		int lastIndex = -1;
		for (String sym : syms) {
			int symIndex = str.lastIndexOf(sym);
			lastIndex = (symIndex == -1) ? lastIndex : Math.max(lastIndex, symIndex);
		}
		return lastIndex;
	}
}
