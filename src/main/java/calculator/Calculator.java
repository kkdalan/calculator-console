package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class Calculator {

	private static final String CMD_EXIT = "exit";

	public static void main(String[] args) throws Exception {
		Calculator.start();
	}

	private static void start() throws Exception {
		System.out.println("=== Java Calculator ===");
		System.out.println("Notes: available operators: { + , - , * , / , ( , ) }");
		System.out.println("Enter formula or ('exit') to exit.");
		System.out.println("ex: ( 1+2)* 3/4 ");

		System.out.print(">>> ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String formula = null;
		while (!(formula = br.readLine()).equalsIgnoreCase(CMD_EXIT)) {
			if(!formula.trim().equals("")) {
				try {
					Calculator.checkFormulaPattern(formula);
					Calculator.checkBrackets(formula);
					double answer = Calculator.computeFormula(formula);
					System.out.println("  ans = " + String.valueOf(answer));
				} catch (Exception e) {
					System.err.println(e.getMessage());
//					e.printStackTrace();
				}
			}
			System.out.print(">>> ");
		}
		System.out.println("Bye!");
	}

	/**
	 * recursively compute formula value
	 * @param formula
	 * @return
	 * @throws Exception 
	 */
	public static double computeFormula(String formula) throws Exception {
		formula = cleanFormula(formula);
		if (hasBrackets(formula)) {
			String newFormula = simplifyBrackets(formula);
			return computeFormula(newFormula);
		} else {
//			return computeValue(formula);
			throwExceptionAsBrachetsOutside(formula);
			return computeFormulaValue(formula);
		}
	}

	protected static void throwExceptionAsBrachetsOutside(String formula) throws Exception {
		if (formula.substring(0, 1).equals("(")
				&& formula.substring(formula.length() - 1, formula.length()).equals(")")) {
			throw new Exception("Formula with ( and ) is not allowed!");
		}
	}
	
	/**
	 * compute formula value without brackets
	 * @param formula
	 * @return
	 */
	public static double computeFormulaValue(String formula) {
		formula = cleanFormula(formula);
		if (hasMultiplyOrDivide(formula)) {
			String newFormula = simplifyMultiplyAndDivide(formula);
			return computeFormulaValue(newFormula);
		} else {
			return computePlusAndMinusValue(formula);
		}
	}

	/**
	 * clean formula string
	 * @param formula
	 * @return
	 */
	private static String cleanFormula(String formula) {
		formula = formula.replace(" ", "");
		return formula;
	}

	/**
	 * simplify brackets and evaluate
	 * @param formula
	 * @return
	 */
	private static String simplifyBrackets(String formula) {
		String formulaStr = formula.replace(" ", "");
		formulaStr = formulaStr.substring(0, formulaStr.indexOf(")")+1);
		formulaStr = formulaStr.substring(formulaStr.lastIndexOf("("));
		Double value = computeFormulaValue(formulaStr.replace("(", "").replace(")", ""));
		formula = formula.replace(formulaStr, value.toString());
		return formula;
	}

	/**
	 * check if brackets exists in formula
	 * @param formula
	 * @return
	 */
	private static boolean hasBrackets(String formula) {
		return formula.contains("(") && formula.contains(")");
	}
	
	private static boolean hasMultiplyOrDivide(String formula) {
		return formula.contains("*") || formula.contains("/");
	}

	/**
	 * check if brackets enclosed
	 * @param formula
	 * @throws Exception
	 */
	private static void checkBrackets(String formulaStr) throws Exception {
		formulaStr = cleanFormula(formulaStr).replaceAll("[0-9.\\+\\-\\*\\/]", "");
		while (formulaStr.contains("()")) {
			formulaStr = formulaStr.replace("()", "");
		}
		if (formulaStr.contains("(") || formulaStr.contains(")")) {
			throw new Exception("Brackets pair not correct!");
		}
	}

	/**
	 * check if characters exist
	 * @param formulaStr
	 * @throws Exception
	 */
	private static void checkFormulaPattern(String formulaStr) throws Exception {
		formulaStr = cleanFormula(formulaStr);
		if (!formulaStr.matches("^[0-9(\\-][0-9\\+\\-\\*\\/()]*[0-9)]$")) {
			throw new Exception("Formula not correct!");
		}
	}
	
	
	protected static String simplifyMultiplyAndDivide(String formula) {
//		System.out.println("formula = " + formula);
		
		String formulaStr = formula.replace(" ", "");
		formulaStr = formulaStr.replace("*-", "m").replace("/-", "d");
//		System.out.println("====> " + formulaStr);
		
		int mplyIndex = formulaStr.indexOf("*");
		int divdIndex = formulaStr.indexOf("/");
		int mIndex = formulaStr.indexOf("m");
		int dIndex = formulaStr.indexOf("d");
		int keyIndex = 0;
		keyIndex = (mplyIndex == -1) ? keyIndex : (keyIndex > 0 ? Math.min(keyIndex, mplyIndex) : mplyIndex);
		keyIndex = (divdIndex == -1) ? keyIndex : (keyIndex > 0 ? Math.min(keyIndex, divdIndex) : divdIndex);
		keyIndex = (mIndex == -1) ? keyIndex : (keyIndex > 0 ? Math.min(keyIndex, mIndex) : mIndex);
		keyIndex = (dIndex == -1) ? keyIndex : (keyIndex > 0 ? Math.min(keyIndex, dIndex) : dIndex);
		String leftStr = formulaStr.substring(0, keyIndex);
//		System.out.println("left string = " + leftStr);
		
		int addIndex = leftStr.lastIndexOf("+");
		int minusIndex = leftStr.lastIndexOf("-");
		int beginIndex = 0;
		beginIndex = (addIndex == -1) ? beginIndex : addIndex;
		beginIndex = (minusIndex == -1) ? beginIndex : Math.max(beginIndex, minusIndex);
		String rightStr = formulaStr.substring(keyIndex+1,formulaStr.length());
//		System.out.println("right string = " + rightStr);
		
		int addIndex2 = rightStr.indexOf("+");
		int minusIndex2 = rightStr.indexOf("-");
		int endIndex = formulaStr.length();
		endIndex = (addIndex2 == -1) ? endIndex : Math.min(endIndex, keyIndex + 1 + addIndex2);
		endIndex = (minusIndex2 == -1) ? endIndex : Math.min(endIndex, keyIndex + 1 + minusIndex2);
		String subStr = formulaStr.substring(beginIndex , endIndex);
		subStr = subStr.substring(0,1).equals("+")? subStr.substring(1):subStr;
		subStr = subStr.substring(0,1).equals("-")? subStr.substring(1):subStr;
//		System.out.println("sub string = " + subStr);
		
		subStr = subStr.replace("m", "*-").replace("d", "/-");
//		System.out.println("====> "+subStr);
		
		if (!subStr.equals("")) {
			Double value = computeMultiplyAndDivideValue(subStr);
			formulaStr = formulaStr.replace("m", "*-").replace("d", "/-");
			formula = formulaStr.replace(subStr, value.toString());
		}
//		System.out.println("simplified = "+formula);
		
		return formula;
	}

	private static double computeMultiplyAndDivideValue(String formula) {
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
//		System.out.println(answer);
		return answer;
	}
	
	private static double computePlusAndMinusValue(String formula) {
		String formulaStr = formula.replace(" ", "")
								   .replace("++", "+")
								   .replace("+-", "-")
								   .replace("-+", "-")
								   .replace("--", "+");

		String[] numbers = formulaStr.split("[\\+\\-]");
		String[] symbols = formulaStr.replaceAll("[0-9.]", "").split("");
		
		if(numbers[0].equals("")) {
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
//		System.out.println(answer);
		return answer;
	}
	
}
