package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class Calculator {

	private static final String CMD_EXIT = "exit";

	public static void main(String[] args) throws IOException {
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
			return computeValue(formula);
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
	private static void checkBrackets(String formula) throws Exception {
		String formulaStr = formula.replace(" ", "").replaceAll("[0-9.\\+\\-\\*\\/]", "");
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
		if (!formulaStr.matches("^[0-9(\\-][0-9\\+\\-\\*\\/()]*[0-9)]$")) {
			throw new Exception("Formula not correct!");
		}
	}

}
