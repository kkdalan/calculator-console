package calculator.processor;

import calculator.helper.FormulaHelper;
import util.FormulaUtil;

public class MultiplyDivideProcessor extends FormulaProcessor {

	@Override
	public String init(String formula) throws Exception {
		FormulaHelper.throwExceptionAsBrachetsOutside(formula);
		return super.init(formula);
	}

	@Override
	public boolean conditionFound(String formula) {
		return FormulaUtil.containsMultiplyOrDivide(formula);
	}

	@Override
	public String simplifyFormula(String formula) {
		return simplifyMultiplyDividePart(formula);
	}

	@Override
	public double processFormula(String formula) {
		return FormulaHelper.computePlusMinusPartValue(formula);
	}

	/**
	 * simplify formula by evaluating multiply and divide operation part
	 * 
	 * @param formula
	 * @return
	 */
	private String simplifyMultiplyDividePart(String formula) {
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
