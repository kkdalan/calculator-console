package calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Calculator {

	private static final String CMD_EXIT = "exit";

	public static void main(String[] args) throws Exception {
		Calculator.start();
	}

	/**
	 * start calculator console
	 * @throws Exception
	 */
	public static void start() throws Exception {
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
					validateFormula(formula);
					double answer = computeFormula(formula);
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
	 * validate formula pattern, brackets ... etc.
	 * @param formula
	 * @throws Exception
	 */
	private static void validateFormula(String formula) throws Exception {
		checkFormulaPattern(formula);
		checkFormulaBrackets(formula);
	}

	/**
	 * ============================================================
	 * Recursively compute formula
	 * 
	 * 1. Find brackets part in formula and compute value 
	 *    to replace the brackets part until no brackets found.
	 * 2. Find multiply and divide operation in formula 
	 *    and compute value to replace the multiply/divide part 
	 *    until no multiply/divide operation found.
	 * 3. The formula will contain plus and minus operation only, 
	 *    compute the plus/minus operation to find the answer.
	 * ============================================================
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

	/**
	 * throws exception as formula has brackets outside, ex: (2/3)
	 * @param formula
	 * @throws Exception
	 */
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
	 * simplify brackets and evaluate value inside
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
	 * check if formula contains '(' or ')'
	 * @param formula
	 * @return
	 */
	private static boolean hasBrackets(String formula) {
		return formula.contains("(") && formula.contains(")");
	}
	
	/**
	 * check if formula contains '*' or '/'
	 * @param formula
	 * @return
	 */
	private static boolean hasMultiplyOrDivide(String formula) {
		return formula.contains("*") || formula.contains("/");
	}

	/**
	 * check if brackets enclosed
	 * @param formula
	 * @throws Exception
	 */
	private static void checkFormulaBrackets(String formulaStr) throws Exception {
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
	
	/**
	 * simplify formula by evaluating multiply and divide operation part
	 * @param formula
	 * @return
	 */
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

	/**
	 * compute multiply and divide part value
	 * @param formula
	 * @return
	 */
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
	
	/**
	 * compute plus and minus operation part value
	 * @param formula
	 * @return
	 */
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
