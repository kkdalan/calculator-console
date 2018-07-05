package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class Calculator {

	private static final String CMD_EXIT = "exit";

	public static void main(String[] args) throws IOException {
		test();
//		execute();
	}

	private static void test() {
		//		System.out.println(simplifyMultiplyAndDivide("1-11+22-10+3"));
		//		System.out.println(findMultiplyAndDivide("1-11+22*-10+3"));
		//	    System.out.println(computeMultiplyAndDivideValue("2/-2/-2"));
		//		System.out.println(computePlusAndMinusValue("1+2-3++2--1+-3-+1--5"));
		
//				System.out.println(computeFormulaValue("2/-4+1"));
//				System.out.println(computeFormulaValue("1+2/-4"));
//				System.out.println(computeFormulaValue("1+2/-4+1"));
//				System.out.println(computeFormulaValue("1-2/-4"));
//				System.out.println(computeFormulaValue("2/-4-1"));
//				System.out.println(computeFormulaValue("-2/-4"));

//				System.out.println(computeFormulaValue("-2/-4/-1"));
//				System.out.println(computeFormulaValue("-2/-4/+1"));
//				System.out.println(computeFormulaValue("-1/-2/-4"));
	}

	private static void execute() throws IOException {
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
	 */
	public static double computeFormula(String formula) {
		formula = cleanFormula(formula);
		if (hasBrackets(formula)) {
			String newFormula = simplifyBrackets(formula);
			return computeFormula(newFormula);
		} else {
//			return computeValue(formula);
			return computeFormulaValue(formula);
		}
	}
	
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
		Double value = computeValue(formulaStr);
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
	 * compute formula value without brackets
	 * @param formula
	 * @return
	 */
	public static double computeValue(String formula) {
		String formulaStr = formula.replace(" ", "").replace("(", "").replace(")", "");
		String[] numbers = formulaStr.split("[\\+\\-\\*\\/]");
		String[] symbols = formulaStr.replaceAll("[0-9.]", "").split("");

		//處理負號相乘相除
		boolean hasMinus = false;
		int j = 1;
		for (; j < numbers.length;) {
			if (numbers[j].equals("") && symbols[j].equals("-")) {
				if (symbols[j - 1].equals("/") || symbols[j - 1].equals("*")) {
					numbers = ArrayUtils.remove(numbers, j);
					symbols = ArrayUtils.remove(symbols, j);
					hasMinus = !hasMinus;
					j--;
				}
			}
			if (!Arrays.asList(numbers).contains("")) {
				j++;
				break;
			} else {
				j++;
			}
		}
		if(hasMinus) {
			numbers = ArrayUtils.add(numbers, 0 , "0");
			symbols = ArrayUtils.add(symbols, 0 , "-");
		}

		//先乘除
		for (int i = 1; i < numbers.length;) {
			String sym = symbols[i - 1];
			if (sym.equals("*")) {
				Double mply = Double.valueOf(numbers[i - 1]) * Double.valueOf(numbers[i]);
				numbers = ArrayUtils.remove(numbers, i - 1);
				numbers = ArrayUtils.remove(numbers, i - 1);
				symbols = ArrayUtils.remove(symbols, i - 1);
				numbers = ArrayUtils.add(numbers, i - 1, mply.toString());
			}
			
			if (sym.equals("/")) {
				Double divd = Double.valueOf(numbers[i - 1]) / Double.valueOf(numbers[i]);
				numbers = ArrayUtils.remove(numbers, i - 1);
				numbers = ArrayUtils.remove(numbers, i - 1);
				symbols = ArrayUtils.remove(symbols, i - 1);
				numbers = ArrayUtils.add(numbers, i - 1, divd.toString());
			}
			if (sym.equals("+")) {
				i++;
			}
			if (sym.equals("-")) {
				i++;
			}
			if (!Arrays.asList(symbols).contains("*") && !Arrays.asList(symbols).contains("/")) {
				break;
			}
		}

		//後加減
		if (numbers[0].equals("") && symbols[0].equals("-")) {
			numbers[0] = "0";
		}
		Double answer = Double.valueOf(numbers[0]);
		for (int i = 1; i < numbers.length; i++) {
			String sym = symbols[i - 1];
			if (sym.equals("+")) {
				if(numbers[i].equals("") && symbols[i].equals("-")) {
					answer -= Double.valueOf(numbers[i+1]);
					i++;
				}else {
					answer += Double.valueOf(numbers[i]);
				}
			}
			if (sym.equals("-")) {
				if(numbers[i].equals("") && symbols[i].equals("-")) {
					answer += Double.valueOf(numbers[i+1]);
					i++;
				}else {
					answer -= Double.valueOf(numbers[i]);
				}
			}
		}
		return answer;
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
	
	
	private static String simplifyMultiplyAndDivide(String formula) {
		String formulaStr = formula.replace(" ", "");
		System.out.println(formula);
		
		int mplyIndex = formulaStr.indexOf("*");
		int divdIndex = formulaStr.indexOf("/");
		int keyIndex = 0;
		if(mplyIndex != -1 && divdIndex != -1) {
			keyIndex = Math.min(mplyIndex, divdIndex);
		}else if(mplyIndex != -1 && divdIndex == -1) {
			keyIndex = mplyIndex;
		}else if(mplyIndex == -1 && divdIndex != -1) {
			keyIndex = divdIndex;
		}else {
			keyIndex = 0;
		}
		String leftStr = formulaStr.substring(0, keyIndex);
		System.out.println(leftStr);
		
		int addIndex = leftStr.lastIndexOf("+");
		int minusIndex = leftStr.lastIndexOf("-");
		int beginIndex = 0;
		if(addIndex != -1 && minusIndex != -1) {
			beginIndex = Math.max(addIndex, minusIndex);
		}else if(addIndex != -1 && minusIndex == -1) {
			beginIndex = addIndex;
		}else if(addIndex == -1 && minusIndex != -1) {
			beginIndex = minusIndex;
		}else {
			beginIndex = 0;
		}
		
		String rightStr = formulaStr.substring(keyIndex+1,formulaStr.length());
		System.out.println(rightStr);
		

		int mplyIndex2 = rightStr.lastIndexOf("*");
		int divdIndex2 = rightStr.lastIndexOf("/");
		int keyIndex2 = 0;
		if(mplyIndex2 != -1 && divdIndex2 != -1) {
			keyIndex2 = Math.min(mplyIndex2, divdIndex2);
		}else if(mplyIndex2 != -1 && divdIndex2 == -1) {
			keyIndex2 = mplyIndex2;
		}else if(mplyIndex2 == -1 && divdIndex2 != -1) {
			keyIndex2 = divdIndex2;
		}else {
			keyIndex2 = 0;
		}
		
		int endIndex = keyIndex + keyIndex2 + 1;
		String y =rightStr.substring(keyIndex2 + 1 ,keyIndex2 + 2);
		if(y.equals("-")) {
			rightStr = rightStr.substring(keyIndex2+2,rightStr.length());
			endIndex++;
		}else{
			rightStr = rightStr.substring(keyIndex2+1,rightStr.length());
		}
		System.out.println(rightStr);
		
		
		
		int addIndex2 = rightStr.indexOf("+");
		int minusIndex2 = rightStr.indexOf("-");
		if(addIndex2 != -1 && minusIndex2 != -1) {
			endIndex += Math.min(addIndex2, minusIndex2);
		}else if(addIndex2 != -1 && minusIndex2 == -1) {
			endIndex += addIndex2;
		}else if(addIndex2 == -1 && minusIndex2 != -1) {
			endIndex += minusIndex2;
		}else {
			endIndex += 1;
		}

		if(beginIndex > 0) {
			formulaStr = formulaStr.substring(beginIndex + 1, endIndex);
		}else {
			formulaStr = formulaStr.substring(beginIndex , endIndex + 1);
		}
		System.out.println(formulaStr);
		
		if (!formulaStr.equals("-1")) {
			Double value = computeMultiplyAndDivideValue(formulaStr);
			formula = formula.replace(formulaStr, value.toString());
		}
		System.out.println(formula);
		
		return formula;
	}

	private static double computeMultiplyAndDivideValue(String formula) {
		String formulaStr = formula.replace(" ", "");
		String[] numbers = formulaStr.split("[\\*\\/]");
		String[] symbols = formulaStr.replaceAll("[0-9.\\-]", "").split("");
		
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
