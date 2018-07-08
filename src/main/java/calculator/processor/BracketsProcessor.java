package calculator.processor;

import calculator.helper.FormulaHelper;
import util.FormulaUtil;

public class BracketsProcessor extends FormulaProcessor {

	public BracketsProcessor() {
		FormulaProcessor formulaProcessor = FormulaProcessorFactory.getInstance(FormulaProcessorFactory.TYPE_POWER);
		this.setNextProcessor(formulaProcessor);
	}
	
	@Override
	public boolean conditionFound(String formula) {
		return containsBracketsPair(formula);
	}

	@Override
	public String simplifyFormula(String formula) throws Exception {
		return simplifyBracketsPart(formula);
	}

	@Override
	public double processFormula(String formula) throws Exception {
		return getNextProcessor().computeFormula(formula);
	}

	private boolean containsBracketsPair(String formula) {
		return FormulaUtil.containsBracketsPair(formula);
	}

	/**
	 * simplify brackets and evaluate value inside
	 * 
	 * @param formula
	 * @return
	 * @throws Exception
	 */
	private String simplifyBracketsPart(String formula) throws Exception {
		String formulaStr = FormulaUtil.cleanSpace(formula);
		String bracketsStr = FormulaHelper.findBracketsPart(formulaStr);
		FormulaProcessor formulaCalculator = new MultiplyDivideProcessor();
		double value = formulaCalculator.computeFormula(bracketsStr.replace("(", "").replace(")", ""));
		String newFormula = formulaStr.replace(bracketsStr, String.valueOf(value));
		return newFormula;
	}

}
