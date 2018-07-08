package calculator.processor;

import util.FormulaUtil;

public abstract class FormulaProcessor {

	private FormulaProcessor nextProcessor = null;

	public FormulaProcessor getNextProcessor() {
		return nextProcessor;
	}

	public void setNextProcessor(FormulaProcessor nextProcessor) {
		this.nextProcessor = nextProcessor;
	}

	public double computeFormula(String formula) throws Exception {
		String formulaStr = init(formula);
		if (conditionFound(formulaStr)) {
			String newFormula = simplifyFormula(formulaStr);
			return computeFormula(newFormula);
		} else {
			return processFormula(formulaStr);
		}
	}

	public String init(String formula) throws Exception {
		return FormulaUtil.cleanSpace(formula);
	}

	public abstract boolean conditionFound(String formula) throws Exception;

	public abstract String simplifyFormula(String formula) throws Exception;

	public abstract double processFormula(String formula) throws Exception;

}
