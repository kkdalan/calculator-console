package calculator.processor;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class MultiplyDivideProcessorTest extends TestCase {

	private static final double DELTA_ERROR = 0.0001;

	@Test
	public void testComputeMultiplyDivide() throws Exception {
		System.out.println("---- Test computeFormula() ----");
		Assert.assertEquals(-0.5, printComputeFormula("-2/-4/-1"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeFormula("-2/-4/+1"), DELTA_ERROR);
		Assert.assertEquals(-0.125, printComputeFormula("-1/-2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.875, printComputeFormula("1-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(0.875, printComputeFormula("-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(0.875, printComputeFormula("1-1/-2/-4"), DELTA_ERROR);
		Assert.assertEquals(0.5, printComputeFormula("1+2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeFormula("2/4+1"), DELTA_ERROR);
	}

	@Test
	public void testSimplifyMultiplyDividePart() throws Exception {
		System.out.println("---- Test simplifyFormula() ----");
		Assert.assertEquals("0.5+1", printSimplifyFormula("2/4+1"));
		Assert.assertEquals("0.5-1", printSimplifyFormula("2/4-1"));
		Assert.assertEquals("1+0.5", printSimplifyFormula("1+2/4"));
		Assert.assertEquals("1-0.5", printSimplifyFormula("1-2/4"));
		Assert.assertEquals("1+-0.5+3", printSimplifyFormula("1+2/-4+3"));
		Assert.assertEquals("1--0.5-3", printSimplifyFormula("1-2/-4-3"));
	}

	private double printComputeFormula(String formula) throws Exception {
		FormulaProcessor formulaCalculator = new MultiplyDivideProcessor();
		double answer = formulaCalculator.computeFormula(formula);
		System.out.println(">>> " + formula + " => " + answer);
		return answer;
	}

	private String printSimplifyFormula(String formula) throws Exception {
		FormulaProcessor formulaCalculator = new MultiplyDivideProcessor();
		String newFormula = formulaCalculator.simplifyFormula(formula);
		System.out.println(">>> " + formula + " => " + newFormula);
		return newFormula;
	}

}
