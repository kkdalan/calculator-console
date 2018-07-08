package calculator;

import calculator.helper.FormulaHelper;
import util.FormulaUtil;

public class Calculator {

	/**
	 * ==================================================================== 
	 * Recursively compute formula
	 * 
	 * 1. Find brackets part in formula and compute value to replace 
	 * the brackets part until no brackets found.
	 * 
	 * 2. Find multiply and divide operation in formula and compute 
	 * value to replace the multiply/divide part until no multiply/divide 
	 * operation found.
	 * 
	 * 3. The formula will contain plus and minus operation only, compute 
	 * the plus/minus operation to find the answer.
	 * ====================================================================
	 * 
	 * @param formula
	 * @return
	 * @throws Exception
	 */
	public static double compute(String formula) throws Exception {
//		checkFormula(formula);
		return computeFormula(formula);
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
	 * compute formula value with brackets
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
			return computePower(formula);
		}
	}
	
	/**
	 * compute power part first (without brackets)
	 * 
	 * @param formula
	 * @return
	 * @throws Exception 
	 */
	public static double computePower(String formula) throws Exception {
		FormulaHelper.throwExceptionAsBrachetsOutside(formula);
		
		formula = FormulaUtil.cleanSpace(formula);
		if (FormulaUtil.containsPower(formula)) {
			String newFormula = simplifyPowerPart(formula);
			return computePower(newFormula);
		} else {
			return computeMultiplyDivide(formula);
		}
	}
	
	/**
	 * compute multiply/divide part first (without brackets)
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
			return computePlusMinus(formula);
		}
	}

	public static double computePlusMinus(String formula) {
		return FormulaHelper.computePlusMinusPartValue(formula);
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
	 * simplify formula by evaluating power operation part
	 * 
	 * @param formula
	 * @return
	 */
	protected static String simplifyPowerPart(String formula) {
		String formulaStr = FormulaUtil.cleanSpace(formula);
		String subStr = FormulaHelper.findPowerPart(formulaStr);
		String newFormula = formulaStr;
		if (!subStr.equals("")) {
			double value = FormulaHelper.computePowerPartValue(subStr);
			newFormula = formulaStr.replace(subStr, String.valueOf(value));
		}
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
