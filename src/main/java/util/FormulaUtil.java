package util;

public class FormulaUtil {

	public static int indexOfSymbols(String str, String... syms) {
		int finalIndex = -1;
		for (String sym : syms) {
			int symIndex = str.indexOf(sym);
			if (symIndex != -1) {
				finalIndex = (finalIndex != -1 ? Math.min(finalIndex, symIndex) : symIndex);
			}
		}
		return finalIndex;
	}

	public static int lastIndexOfSymbols(String str, String... syms) {
		int finalIndex = -1;
		for (String sym : syms) {
			int symIndex = str.lastIndexOf(sym);
			finalIndex = (symIndex == -1) ? finalIndex : Math.max(finalIndex, symIndex);
		}
		return finalIndex;
	}
}
