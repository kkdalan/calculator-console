package calculator.processor;

import java.util.HashMap;
import java.util.Map;

import calculator.helper.FormulaHelper;
import util.FormulaUtil;

public class VariableProcessor extends FormulaProcessor {

	@Override
	public String init(String formula) throws Exception {
		return super.init(formula);
	}

	@Override
	public boolean conditionFound(String formula) throws Exception {
		return FormulaUtil.containsVariable(formula);
	}

	@Override
	public String simplifyFormula(String formula) {
		return simplifyVariablePart(formula);
	}

	@Override
	public double processFormula(String formula) throws Exception {
		return getNextProcessor().computeFormula(formula);
	}
	
	@Override
	public FormulaProcessor getNextProcessor() {
		return FormulaProcessorFactory.getProcessor(FormulaProcessorFactory.TYPE_BRACKETS);
	}

	/**
	 * simplify formula by evaluating power operation part
	 * 
	 * @param formula
	 * @return
	 */
	private String simplifyVariablePart(String formula) {
		String formulaStr = FormulaUtil.cleanSpace(formula);
		String definition = FormulaHelper.findVariableDefinitionPart(formulaStr);
		Map<String, Double> variableMap = resolveVariableDefinition(definition);
		formulaStr = formulaStr.replace("where", "").replace(definition, "");
		String newFormula = evaluateFormulaByVariables(formulaStr, variableMap);
		return newFormula;
	}

	private String evaluateFormulaByVariables(String formulaStr, Map<String, Double> variableMap) {
		if (variableMap != null) {
			for(Map.Entry<String, Double> entry : variableMap.entrySet()) {
				String variable = entry.getKey();
				double value = entry.getValue();
				formulaStr = formulaStr.replace(variable, String.valueOf(value));
			}
		}
		return formulaStr;
	}
	
	private Map<String, Double> resolveVariableDefinition(String definition){
		Map<String, Double> variableMap = new HashMap<String, Double>();
		String variables[] = definition.replace("where", "").split(",");
		for(String token : variables) {
			String symbol = token.split("=")[0];
			String value = token.split("=")[1];
			variableMap.put(symbol, Double.valueOf(value));
		}
		return variableMap;
	}

}
