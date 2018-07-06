package calculator;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

	private static final double DELTA_ERROR = 0.0001;

	@Test
	public void testCalculator() {

		System.out.println("---- Test Calculator() ----");

		Assert.assertEquals(3.0, printComputeFormula("1+4/2"), DELTA_ERROR);
		Assert.assertEquals(-1.0, printComputeFormula("1-4/2"), DELTA_ERROR);
		Assert.assertEquals(3.0, printComputeFormula("4/2+1"), DELTA_ERROR);
		Assert.assertEquals(1.0, printComputeFormula("4/2-1"), DELTA_ERROR);

		Assert.assertEquals(1.0, printComputeFormula("2/2"), DELTA_ERROR);
		Assert.assertEquals(0.5, printComputeFormula("2/2/2"), DELTA_ERROR);
		Assert.assertEquals(-1.0, printComputeFormula("2/-2"), DELTA_ERROR);
		Assert.assertEquals(0.5, printComputeFormula("2/-2/-2"), DELTA_ERROR);
		Assert.assertEquals(0.25, printComputeFormula("2/-2/2/-2"), DELTA_ERROR);

		Assert.assertEquals(4, printComputeFormula("2*2"), DELTA_ERROR);
		Assert.assertEquals(-4, printComputeFormula("2*-2"), DELTA_ERROR);
		Assert.assertEquals(8, printComputeFormula("2*2*2"), DELTA_ERROR);
		Assert.assertEquals(16, printComputeFormula("2*-2*2*-2"), DELTA_ERROR);
		Assert.assertEquals(-2, printComputeFormula("2*2/-2"), DELTA_ERROR);
		Assert.assertEquals(1.33333, printComputeFormula("2*-2/-3"), DELTA_ERROR);
		Assert.assertEquals(1.33333, printComputeFormula("2*(-2)/(-3)"), DELTA_ERROR);
		Assert.assertEquals(-1.33333, printComputeFormula("-2*(-2)/(-3)"), DELTA_ERROR);
		Assert.assertEquals(-3.0, printComputeFormula("-2/(-2)*(-3)"), DELTA_ERROR);

		Assert.assertEquals(4, printComputeFormula("(1+2+3-5)*4"), DELTA_ERROR);
		Assert.assertEquals(0.25, printComputeFormula("(1+2 +3-5)/4"), DELTA_ERROR);
		Assert.assertEquals(-0.5, printComputeFormula("( 1+2+3-5)/4*(-2)"), DELTA_ERROR);
		Assert.assertEquals(-0.125, printComputeFormula("(1+2+3-5)/4/(-2)"), DELTA_ERROR);

		Assert.assertEquals(0.25, printComputeFormula("(1 +2  +3 -5) / 4"), DELTA_ERROR);
		Assert.assertEquals(24.0, printComputeFormula("8 / ( 3 - 8 / 3)"), DELTA_ERROR);
		Assert.assertEquals(-48.0, printComputeFormula("-2 * ( 8 / ( 3 -8 / 3)"), DELTA_ERROR);
		Assert.assertEquals(16.0, printComputeFormula("-2 * ( 8 / ( 3 -8 / 3) / - 3"), DELTA_ERROR);
	}

	@Test
	public void testComputeFormulaValue() {
		System.out.println("---- Test computeFormulaValue() ----");
		
		Assert.assertEquals(-0.5, printComputeFormulaValue("-2/-4/-1"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeFormulaValue("-2/-4/+1"), DELTA_ERROR);
		Assert.assertEquals(-0.125, printComputeFormulaValue("-1/-2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.875, printComputeFormulaValue("1-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(1.125, printComputeFormulaValue("-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(0.875, printComputeFormulaValue("1-1/-2/-4"), DELTA_ERROR);
		
		Assert.assertEquals(0.5, printComputeFormulaValue("1+2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeFormulaValue("2/4+1"), DELTA_ERROR);
	}

	private double printComputeFormula(String formula) {
		double answer = Calculator.computeFormula(formula);
		System.out.println(">>> " + formula + " = " + answer);
		return answer;
	}
	
	private double printComputeFormulaValue(String formula) {
		double answer = Calculator.computeFormulaValue(formula);
		System.out.println(">>> " + formula + " => " + answer);
		return answer;
	}
}
