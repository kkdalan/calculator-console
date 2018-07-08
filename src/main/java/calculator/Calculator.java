package calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import calculator.helper.FormulaHelper;
import util.FormulaUtil;

public class Calculator {

	private static final String CMD_EXIT = "exit";

	public static void main(String[] args) throws Exception {
		Calculator.start();
	}

	/**
	 * start calculator console
	 * 
	 * @throws Exception
	 */
	public static void start() throws Exception {
		System.out.println("=== Java Calculator ===");
		System.out.println("Notes: available operators: { + , - , * , / , ( , ) }");
		System.out.println("Enter formula or ('exit') to exit.");
		System.out.println("ex: ( 1+2)* 3/4 ");

		System.out.print(">>> ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String formula = "";
		while (!(formula = br.readLine()).equalsIgnoreCase(CMD_EXIT)) {
			if (!formula.trim().equals("")) {
				try {
					checkFormula(formula);
					double answer = computeFormula(formula);
					System.out.println("  ans = " + String.valueOf(answer));
				} catch (Exception e) {
					System.err.println(e.getMessage());
					// e.printStackTrace();
				}
			}
			System.out.print(">>> ");
		}
		System.out.println("Bye!");
	}

	/**
	 * validate formula pattern, brackets ... etc.
	 * 
	 * @param formula
	 * @throws Exception
	 */
	protected static void checkFormula(String formula) throws Exception {
		FormulaHelper.checkFormulaPattern(formula);
		FormulaHelper.checkFormulaBrackets(formula);
	}

	/**
	 * ============================================================ Recursively
	 * compute formula
	 * 
	 * 1. Find brackets part in formula and compute value to replace the brackets
	 * part until no brackets found.
	 * 
	 * 2. Find multiply and divide operation in formula and compute value to replace
	 * the multiply/divide part until no multiply/divide operation found.
	 * 
	 * 3. The formula will contain plus and minus operation only, compute the
	 * plus/minus operation to find the answer.
	 * ============================================================
	 * 
	 * @param formula
	 * @return
	 * @throws Exception
	 */
	public static double computeFormula(String formula) throws Exception {
		formula = FormulaUtil.cleanSpace(formula);
		if (FormulaUtil.containsBracketsPair(formula)) {
			String newFormula = simplifyBracketsPart(formula);
			return computeFormula(newFormula);
		} else {
			return computeMultiplyDivide(formula);
		}
	}

	/**
	 * compute formula value without brackets
	 * 
	 * @param formula
	 * @return
	 * @throws Exception 
	 */
	public static double computeMultiplyDivide(String formula) throws Exception {
		FormulaHelper.throwExceptionAsBrachetsOutside(formula);
		
		formula = FormulaUtil.cleanSpace(formula);
		if (FormulaUtil.containsMultiplyOrDivide(formula)) {
			String newFormula = simplifyMultiplyDividePart(formula);
			return computeMultiplyDivide(newFormula);
		} else {
			return FormulaHelper.computePlusMinusPartValue(formula);
		}
	}

	/**
	 * simplify brackets and evaluate value inside
	 * 
	 * @param formula
	 * @return
	 * @throws Exception 
	 */
	protected static String simplifyBracketsPart(String formula) throws Exception {
		String formulaStr = FormulaUtil.cleanSpace(formula);
		String bracketsStr = FormulaHelper.findBracketsPart(formulaStr);
		double value = computeMultiplyDivide(bracketsStr.replace("(", "").replace(")", ""));
		String newFormula = formulaStr.replace(bracketsStr, String.valueOf(value));
		return newFormula;
	}
	
	/**
	 * simplify formula by evaluating multiply and divide operation part
	 * 
	 * @param formula
	 * @return
	 */
	protected static String simplifyMultiplyDividePart(String formula) {
		String formulaStr = FormulaUtil.cleanSpace(formula);
		String subStr = FormulaHelper.findMultiplyDivicePart(formulaStr);
		String newFormula = formulaStr;
		if (!subStr.equals("")) {
			double value = FormulaHelper.computeMultiplyDividePartValue(subStr);
			newFormula = formulaStr.replace(subStr, String.valueOf(value));
		}
		return newFormula;
	}

}
