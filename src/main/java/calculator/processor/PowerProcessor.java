package calculator.processor;

import calculator.helper.FormulaHelper;
import util.FormulaUtil;

public class PowerProcessor extends FormulaProcessor {

	@Override
	public String init(String formula) throws Exception {
		FormulaHelper.throwExceptionAsBrachetsOutside(formula);
		return super.init(formula);
	}

	@Override
	public boolean conditionFound(String formula) {
		return FormulaUtil.containsPower(formula);
	}

	@Override
	public String simplifyFormula(String formula) {
		return simplifyPowerPart(formula);
	}

	@Override
	public double processFormula(String formula) throws Exception {
		return getNextProcessor().computeFormula(formula);
	}

	@Override
	public FormulaProcessor getNextProcessor() {
		return FormulaProcessorFactory.getProcessor(FormulaProcessorFactory.TYPE_MULDIV);
	}
	
	/**
	 * simplify formula by evaluating power operation part
	 * 
	 * @param formula
	 * @return
	 */
	private String simplifyPowerPart(String formula) {
		String formulaStr = FormulaUtil.cleanSpace(formula);
		String subStr = FormulaHelper.findPowerPart(formulaStr);
		String newFormula = formulaStr;
		if (!subStr.equals("")) {
			double value = FormulaHelper.computePowerPartValue(subStr);
			newFormula = formulaStr.replace(subStr, String.valueOf(value));
		}
		return newFormula;
	}



}
