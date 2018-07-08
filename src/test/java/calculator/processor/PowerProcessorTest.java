package calculator.processor;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class PowerProcessorTest extends TestCase {

	private static final double DELTA_ERROR = 0.001;

	@Test
	public void testComputeFormula() throws Exception {
		System.out.println("---- Test computeFormula() ----");
		Assert.assertEquals(8, printComputeFormula("2^3"), DELTA_ERROR);
		Assert.assertEquals(9, printComputeFormula("1+2^3"), DELTA_ERROR);
		Assert.assertEquals(9, printComputeFormula("2^3+1"), DELTA_ERROR);
		Assert.assertEquals(7, printComputeFormula("2^3-1"), DELTA_ERROR);
		Assert.assertEquals(-7, printComputeFormula("1-2^3"), DELTA_ERROR);
		Assert.assertEquals(-9, printComputeFormula("-1-2^3"), DELTA_ERROR);
		Assert.assertEquals(0.125, printComputeFormula("2^-3"), DELTA_ERROR);
		Assert.assertEquals(1.41421, printComputeFormula("2^0.5"), DELTA_ERROR);
		Assert.assertEquals(0.707106, printComputeFormula("2^-0.5"), DELTA_ERROR);
		Assert.assertEquals(16, printComputeFormula("2*2^3"), DELTA_ERROR);
		Assert.assertEquals(16, printComputeFormula("2^3*2"), DELTA_ERROR);
		Assert.assertEquals(0.25, printComputeFormula("2/2^3"), DELTA_ERROR);
		Assert.assertEquals(4, printComputeFormula("2^3/2"), DELTA_ERROR);
		Assert.assertEquals(-4, printComputeFormula("2^3/-2"), DELTA_ERROR);
		Assert.assertEquals(-0.25, printComputeFormula("-2/2^3"), DELTA_ERROR);
		Assert.assertEquals(18, printComputeFormula("10+2^3"), DELTA_ERROR);
		Assert.assertEquals(18, printComputeFormula("2^3+10"), DELTA_ERROR);
		Assert.assertEquals(-18, printComputeFormula("-10-2^3"), DELTA_ERROR);
		Assert.assertEquals(-2, printComputeFormula("2^3-10"), DELTA_ERROR);
	}

	@Test
	public void testSimplifyFormula() throws Exception {
		System.out.println("---- Test simplifyFormula() ----");
		Assert.assertEquals("8.0", printSimplifyFormula("2^3"));
		Assert.assertEquals("1+8.0", printSimplifyFormula("1+2^3"));
		Assert.assertEquals("8.0+1", printSimplifyFormula("2^3+1"));
		Assert.assertEquals("8.0-1", printSimplifyFormula("2^3-1"));
		Assert.assertEquals("1-8.0", printSimplifyFormula("1-2^3"));
		Assert.assertEquals("-1-8.0", printSimplifyFormula("-1-2^3"));
		Assert.assertEquals("0.125", printSimplifyFormula("2^-3"));
		Assert.assertEquals("3.0", printSimplifyFormula("9^0.5"));
		Assert.assertEquals("0.5", printSimplifyFormula("4^-0.5"));
		Assert.assertEquals("2*8.0", printSimplifyFormula("2*2^3"));
		Assert.assertEquals("8.0*2", printSimplifyFormula("2^3*2"));
		Assert.assertEquals("2/8.0", printSimplifyFormula("2/2^3"));
		Assert.assertEquals("8.0/2", printSimplifyFormula("2^3/2"));
		Assert.assertEquals("8.0/-2", printSimplifyFormula("2^3/-2"));
		Assert.assertEquals("-2/8.0", printSimplifyFormula("-2/2^3"));
		Assert.assertEquals("10+8.0", printSimplifyFormula("10+2^3"));
		Assert.assertEquals("8.0+10", printSimplifyFormula("2^3+10"));
		Assert.assertEquals("-10-8.0", printSimplifyFormula("-10-2^3"));
		Assert.assertEquals("8.0-10", printSimplifyFormula("2^3-10"));
	}

	private double printComputeFormula(String formula) throws Exception {
		FormulaProcessor formulaCalculator = new PowerProcessor();
		double answer = formulaCalculator.computeFormula(formula);
		System.out.println(">>> " + formula + " => " + answer);
		return answer;
	}

	private String printSimplifyFormula(String formula) throws Exception {
		FormulaProcessor formulaCalculator = new PowerProcessor();
		String newFormula = formulaCalculator.simplifyFormula(formula);
		System.out.println(">>> " + formula + " => " + newFormula);
		return newFormula;
	}

}
