package calculator.processor;

public class FormulaProcessorFactory {

	public static final String TYPE_VARIABLE = "4";
	public static final String TYPE_BRACKETS = "3";
	public static final String TYPE_POWER = "2";
	public static final String TYPE_MULDIV = "1";

	public static FormulaProcessor getProcessor(String processorType) {
		FormulaProcessor formulaProcessor = null;
		switch (processorType) {
		case TYPE_VARIABLE:
			formulaProcessor = new VariableProcessor();
			break;
		case TYPE_BRACKETS:
			formulaProcessor = new BracketsProcessor();
			break;
		case TYPE_POWER:
			formulaProcessor = new PowerProcessor();
			break;
		case TYPE_MULDIV:
			formulaProcessor = new MultiplyDivideProcessor();
			break;
		default:
			formulaProcessor = getDefaultProcessor();
			break;
		}
		return formulaProcessor;
	}
	
	public static FormulaProcessor getDefaultProcessor() {
		return getProcessor(TYPE_VARIABLE);
	}
}
