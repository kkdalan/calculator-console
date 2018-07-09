package calculator;

import calculator.helper.FormulaHelper;
import calculator.processor.FormulaProcessor;
import calculator.processor.FormulaProcessorFactory;

/**
 * =============================================================================
 * Recursively compute formula
 * 
 * 1. Find brackets part in formula and compute value to replace the brackets
 * part until no brackets found.
 * 
 * 2. Find power operation in formula and compute value to replace
 * the power part until no power operation found.
 * 
 * 3. Find multiply and divide operation in formula and compute value to 
 * replace the multiply/divide part until no multiply/divide operation found.
 * 
 * 4. The formula will contain plus and minus operation only, compute the
 * plus/minus operation to find the answer.
 * =============================================================================
 */
public class Calculator {

	/**
	 * Recursively compute formula
	 * 
	 * @param formula
	 * @return
	 * @throws Exception
	 */
	public static double compute(String formula) throws Exception {
//		checkFormula(formula);
		FormulaProcessor formulaProcessor = FormulaProcessorFactory.getDefaultProcessor();
		return formulaProcessor.computeFormula(formula);
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

}
