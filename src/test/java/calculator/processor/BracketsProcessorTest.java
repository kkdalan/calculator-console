package calculator.processor;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class BracketsProcessorTest extends TestCase {

	private static final double DELTA_ERROR = 0.0001;

	@Test
	public void testComputeFormula() throws Exception {
		System.out.println("---- Test computeFormula() ----");
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
	public void testSimplifyMultiplyDividePart() throws Exception {
		System.out.println("---- Test simplifyFormula() ----");
		Assert.assertEquals("2*-2.0/(-3)", printSimplifyFormula("2*(-2)/(-3)"));
		Assert.assertEquals("-2*-2.0/(-3)", printSimplifyFormula("-2*(-2)/(-3)"));
		Assert.assertEquals("-2/-2.0*(-3)", printSimplifyFormula("-2/(-2)*(-3)"));
		Assert.assertEquals("1.0*4", printSimplifyFormula("(1+2+3-5)*4"));
		Assert.assertEquals("1.0/4", printSimplifyFormula("(1+2 +3-5)/4"));
		Assert.assertEquals("1.0/4*(-2)", printSimplifyFormula("( 1+2+3-5)/4*(-2)"));
		Assert.assertEquals("1.0/4/(-2)", printSimplifyFormula("(1+2+3-5)/4/(-2)"));
		Assert.assertEquals("1.0/4", printSimplifyFormula("(1 +2  +3 -5) / 4"));
		Assert.assertEquals("8/1.0", printSimplifyFormula("8 / ( 3 - 6 / 3)"));
		Assert.assertEquals("-2*(8/1.0)", printSimplifyFormula("-2 * ( 8 / ( 3 -6 / 3))"));
		Assert.assertEquals("-2*(8/1.0/-3)", printSimplifyFormula("-2 * ( 8 / ( 3 -6 / 3) / - 3)"));
		Assert.assertEquals("1*-1.0+2*(-2)+3*(-3)", printSimplifyFormula("1*(-1)+2*(-2)+3*(-3)"));
	}

	private double printComputeFormula(String formula) throws Exception {
		FormulaProcessor formulaCalculator = new BracketsProcessor();
		double answer = formulaCalculator.computeFormula(formula);
		System.out.println(">>> " + formula + " => " + answer);
		return answer;
	}

	private String printSimplifyFormula(String formula) throws Exception {
		FormulaProcessor formulaCalculator = new BracketsProcessor();
		String newFormula = formulaCalculator.simplifyFormula(formula);
		System.out.println(">>> " + formula + " => " + newFormula);
		return newFormula;
	}
}
