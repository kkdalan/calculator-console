package calculator.processor;

public class FormulaProcessorFactory {

	public static final String TYPE_BRACKETS = "0";
	public static final String TYPE_POWER = "1";
	public static final String TYPE_MULDIV = "2";

	public static FormulaProcessor getDefaultInstance() {
		return getInstance(TYPE_BRACKETS);
	}
	
	public static FormulaProcessor getInstance(String processorType) {
		FormulaProcessor formulaProcessor = null;
		switch (processorType) {
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
			formulaProcessor = new BracketsProcessor();
			break;
		}
		return formulaProcessor;
	}
}
