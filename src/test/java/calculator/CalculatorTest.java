package calculator;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

	private static final double DELTA_ERROR = 0.0001;

	@Test
	public void testComputeFormula() throws Exception {

		System.out.println("---- Test computeFormula() ----");

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
		Assert.assertEquals(-48.0, printComputeFormula("-2 * ( 8 / ( 3 -8 / 3))"), DELTA_ERROR);
		Assert.assertEquals(16.0, printComputeFormula("-2 * ( 8 / ( 3 -8 / 3) / - 3)"), DELTA_ERROR);

		Assert.assertEquals(-14, printComputeFormula("1*(-1)+2*(-2)+3*(-3)"), DELTA_ERROR);
		Assert.assertEquals(-14, printComputeFormula("1*-1+2*-2+3*-3"), DELTA_ERROR);
		Assert.assertEquals(14, printComputeFormula("1*1+2*2+3*3"), DELTA_ERROR);
		
		Assert.assertEquals(Double.POSITIVE_INFINITY, printComputeFormula("2/0"), DELTA_ERROR);
		Assert.assertEquals(Double.NEGATIVE_INFINITY, printComputeFormula("-2/0"), DELTA_ERROR);
		Assert.assertEquals(Double.POSITIVE_INFINITY, printComputeFormula("2/(1-1)"), DELTA_ERROR);
		Assert.assertEquals(Double.NEGATIVE_INFINITY, printComputeFormula("-2/(1-1)"), DELTA_ERROR);

		Assert.assertEquals(Double.NaN, printComputeFormula("0/0"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printComputeFormula("(1-1)/0"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printComputeFormula("0/(1-1)"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printComputeFormula("1+0/(1-1)"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printComputeFormula("0/(1-1)-3"), DELTA_ERROR);
	}

	@Test
	public void testComputeFormulaValue() {
		System.out.println("---- Test computeFormulaValue() ----");
		Assert.assertEquals(-0.5, printComputeFormulaValue("-2/-4/-1"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeFormulaValue("-2/-4/+1"), DELTA_ERROR);
		Assert.assertEquals(-0.125, printComputeFormulaValue("-1/-2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.875, printComputeFormulaValue("1-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(0.875, printComputeFormulaValue("-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(0.875, printComputeFormulaValue("1-1/-2/-4"), DELTA_ERROR);
		Assert.assertEquals(0.5, printComputeFormulaValue("1+2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeFormulaValue("2/4+1"), DELTA_ERROR);
	}

	@Test
	public void testSimplifyMultiplyAndDivide() {
		System.out.println("---- Test simplifyMultiplyAndDivide() ----");
		Assert.assertEquals("0.5+1", printSimplifyMultiplyAndDivide("2/4+1"));
		Assert.assertEquals("0.5-1", printSimplifyMultiplyAndDivide("2/4-1"));
		Assert.assertEquals("1+0.5", printSimplifyMultiplyAndDivide("1+2/4"));
		Assert.assertEquals("1-0.5", printSimplifyMultiplyAndDivide("1-2/4"));
		Assert.assertEquals("1+-0.5+3", printSimplifyMultiplyAndDivide("1+2/-4+3"));
		Assert.assertEquals("1--0.5-3", printSimplifyMultiplyAndDivide("1-2/-4-3"));
	}

	@Test
	public void testThrowExceptionAsBrachetsOutside() {
		System.out.println("---- Test throwExceptionAsBrachetsOutside() ----");
		Assert.assertFalse(hasExceptionAsComputeFormulaValueWithBracketsOutside("8 / -4"));
		Assert.assertFalse(hasExceptionAsComputeFormulaValueWithBracketsOutside("-8 / -4"));
		Assert.assertTrue(hasExceptionAsComputeFormulaValueWithBracketsOutside("( 8 / 4)"));
		Assert.assertTrue(hasExceptionAsComputeFormulaValueWithBracketsOutside("( 8 / -4)"));
	}

	private boolean hasExceptionAsComputeFormulaValueWithBracketsOutside(String formula) {
		boolean hasExcpetion = false;
		try {
			Calculator.throwExceptionAsBrachetsOutside(formula);
			System.out.println(">>> " + formula + " => ok!");
		} catch (Exception e) {
			hasExcpetion = true;
			System.out.println(">>> " + formula + " => " + e.getMessage());
		}
		return hasExcpetion;
	}

	private double printComputeFormula(String formula) throws Exception {
		double answer = Calculator.computeFormula(formula);
		System.out.println(">>> " + formula + " = " + answer);
		return answer;
	}

	private double printComputeFormulaValue(String formula) {
		double answer = Calculator.computeFormulaValue(formula);
		System.out.println(">>> " + formula + " => " + answer);
		return answer;
	}

	private String printSimplifyMultiplyAndDivide(String formula) {
		String newFormula = Calculator.simplifyMultiplyAndDivide(formula);
		System.out.println(">>> " + formula + " => " + newFormula);
		return newFormula;
	}
}
