package calculator.helper;

import util.FormulaUtil;

public class FormulaHelper {

	public static String findBracketsPart(String formula) {
		String subStr = FormulaUtil.cleanSpace(formula);
		subStr = subStr.substring(0, subStr.indexOf(")")+1);
		subStr = subStr.substring(subStr.lastIndexOf("("));
		subStr.replace("(", "").replace(")", "");
		return subStr;
	}
	
	public static String findMultiplyDivicePart(String formula) {
		// System.out.println("formula = " + formula);
		String formulaStr = FormulaUtil.cleanSpace(formula);
		formulaStr = formula.replace("*-", "m").replace("/-", "d");
		// System.out.println("====> " + formulaStr);

		int keyIndex = 0;
		if (FormulaUtil.indexOfSymbols(formulaStr, "*", "/", "m", "d") != -1) {
			keyIndex = FormulaUtil.indexOfSymbols(formulaStr, "*", "/", "m", "d");
		}
		String leftStr = formulaStr.substring(0, keyIndex);
		// System.out.println("left string = " + leftStr);

		int beginIndex = 0;
		if (FormulaUtil.lastIndexOfSymbols(leftStr, "+", "-") != -1) {
			beginIndex = FormulaUtil.lastIndexOfSymbols(leftStr, "+", "-");
		}
		String rightStr = formulaStr.substring(keyIndex + 1, formulaStr.length());
		// System.out.println("right string = " + rightStr);

		int endIndex = formulaStr.length();
		if (FormulaUtil.indexOfSymbols(rightStr, "+", "-") != -1) {
			endIndex = keyIndex + 1 + FormulaUtil.indexOfSymbols(rightStr, "+", "-");
		}
		String subStr = formulaStr.substring(beginIndex, endIndex);
		subStr = subStr.substring(0, 1).equals("+") ? subStr.substring(1) : subStr;
		subStr = subStr.substring(0, 1).equals("-") ? subStr.substring(1) : subStr;
		// System.out.println("sub string = " + subStr);

		subStr = subStr.replace("m", "*-").replace("d", "/-");
		// System.out.println("====> "+subStr);
		return subStr;
	}

	/**
	 * compute multiply and divide part value
	 * 
	 * @param formula
	 * @return
	 */
	public static double computeMultiplyAndDivideValue(String formula) {
		String formulaStr = formula.replace(" ", "");
		String[] numbers = formulaStr.split("[\\*\\/]");
		String[] symbols = formulaStr.replaceAll("[0-9.\\-+]", "").split("");

		double answer = Double.valueOf(numbers[0]);
		for (int i = 1; i < numbers.length; i++) {

			String sym = symbols[i - 1];
			if (sym.equals("*")) {
				answer *= Double.valueOf(numbers[i]);
			}
			if (sym.equals("/")) {
				answer /= Double.valueOf(numbers[i]);
			}
		}
		// System.out.println(answer);
		return answer;
	}

	/**
	 * compute plus and minus operation part value
	 * 
	 * @param formula
	 * @return
	 */
	public static double computePlusAndMinusValue(String formula) {
		String formulaStr = formula.replace(" ", "").replace("++", "+").replace("+-", "-").replace("-+", "-")
				.replace("--", "+");

		String[] numbers = formulaStr.split("[\\+\\-]");
		String[] symbols = formulaStr.replaceAll("[0-9.]", "").split("");

		if (numbers[0].equals("")) {
			numbers[0] = "0";
		}

		double answer = Double.valueOf(numbers[0]);
		for (int i = 1; i < numbers.length; i++) {
			String sym = symbols[i - 1];
			if (sym.equals("+")) {
				answer += Double.valueOf(numbers[i]);
			}
			if (sym.equals("-")) {
				answer -= Double.valueOf(numbers[i]);
			}
		}
		// System.out.println(answer);
		return answer;
	}

	/**
	 * check if characters exist
	 * 
	 * @param formulaStr
	 * @throws Exception
	 */
	public static void checkFormulaPattern(String formulaStr) throws Exception {
		formulaStr = FormulaUtil.cleanSpace(formulaStr);
		if (formulaStr.matches("^[0-9(\\-]+[0-9\\+\\-\\*\\/()]*[0-9)]+$") || formulaStr.matches("[0-9]+$")) {
			// formula string correct!
		} else {
			throw new Exception("formula string not correct!");
		}
	}

	/**
	 * check if brackets enclosed
	 * 
	 * @param formula
	 * @throws Exception
	 */
	public static void checkFormulaBrackets(String formulaStr) throws Exception {
		formulaStr = FormulaUtil.cleanSpace(formulaStr).replaceAll("[0-9.\\+\\-\\*\\/]", "");
		while (formulaStr.contains("()")) {
			formulaStr = formulaStr.replace("()", "");
		}
		if (formulaStr.contains("(") || formulaStr.contains(")")) {
			throw new Exception("brackets pair not complete!");
		}
	}

	/**
	 * throws exception as formula has brackets outside, ex: (2/3)
	 * 
	 * @param formula
	 * @throws Exception
	 */
	public static void throwExceptionAsBrachetsOutside(String formula) throws Exception {
		if (formula.substring(0, 1).equals("(")
				&& formula.substring(formula.length() - 1, formula.length()).equals(")")) {
			throw new Exception("formula with ( and ) is not allowed!");
		}
	}

}
