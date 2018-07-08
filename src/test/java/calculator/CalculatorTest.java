package calculator;

import org.junit.Assert;
import org.junit.Test;

import calculator.helper.FormulaHelper;
import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

	private static final double DELTA_ERROR = 0.0001;

	@Test
	public void testComputeFormula() throws Exception {
		System.out.println("---- Test computeFormula() ----");
		Assert.assertEquals(1, printComputeFormula("1"), DELTA_ERROR);
		Assert.assertEquals(-1, printComputeFormula("-1"), DELTA_ERROR);
		Assert.assertEquals(0, printComputeFormula("0"), DELTA_ERROR);
		Assert.assertEquals(0.0, printComputeFormula("0.0"), DELTA_ERROR);
		Assert.assertEquals(2.0, printComputeFormula("2.0"), DELTA_ERROR);
		Assert.assertEquals(-2.0, printComputeFormula("-2.0"), DELTA_ERROR);
		
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
	public void testSimplifyBracketsPart() throws Exception {
		System.out.println("---- Test simplifyBracketsPart() ----");
		Assert.assertEquals("1.5+1", printSimplifyBracketsPart("(2/4+1)+1"));
		Assert.assertEquals("1+1.5", printSimplifyBracketsPart("1+(2/4+1)"));
		Assert.assertEquals("1.5*2", printSimplifyBracketsPart("(2/4+1)*2"));
		Assert.assertEquals("2*1.5", printSimplifyBracketsPart("2*(2/4+1)"));
		Assert.assertEquals("2/1.5", printSimplifyBracketsPart("2/(2/4+1)"));
		Assert.assertEquals("1.5/2", printSimplifyBracketsPart("(2/4+1)/2"));
		
		Assert.assertEquals("1.5-1", printSimplifyBracketsPart("(2/4+1)-1"));
		Assert.assertEquals("1-1.5", printSimplifyBracketsPart("1-(2/4+1)"));
		Assert.assertEquals("1.5*-2", printSimplifyBracketsPart("(2/4+1)*-2"));
		Assert.assertEquals("-2*1.5", printSimplifyBracketsPart("-2*(2/4+1)"));
		Assert.assertEquals("-2/1.5", printSimplifyBracketsPart("-2/(2/4+1)"));
		Assert.assertEquals("1.5/-2", printSimplifyBracketsPart("(2/4+1)/-2"));
		
		Assert.assertEquals("1.5+(1-3)", printSimplifyBracketsPart("(2/4+1)+(1-3)"));
		Assert.assertEquals("-2.0+(2/4+1)", printSimplifyBracketsPart("(1-3)+(2/4+1)"));
		Assert.assertEquals("1.5*(1-3)", printSimplifyBracketsPart("(2/4+1)*(1-3)"));
		Assert.assertEquals("-2.0*(2/4+1)", printSimplifyBracketsPart("(1-3)*(2/4+1)"));
		Assert.assertEquals("-2.0/(2/4+1)", printSimplifyBracketsPart("(1-3)/(2/4+1)"));
		Assert.assertEquals("1.5/(1-3)", printSimplifyBracketsPart("(2/4+1)/(1-3)"));
		
		Assert.assertEquals("1.5+(1-3)*(1-2*3)", printSimplifyBracketsPart("(2/4+1)+(1-3)*(1-2*3)"));
		Assert.assertEquals("-5.0*(1-3)+(2/4+1)", printSimplifyBracketsPart("(1-2*3)*(1-3)+(2/4+1)"));
		Assert.assertEquals("1.5*(1-3)+(1-2*3)", printSimplifyBracketsPart("(2/4+1)*(1-3)+(1-2*3)"));
		Assert.assertEquals("-5.0+(1-3)*(2/4+1)", printSimplifyBracketsPart("(1-2*3)+(1-3)*(2/4+1)"));
		Assert.assertEquals("-2.0/(2/4+1)-(1-2*3)", printSimplifyBracketsPart("(1-3)/(2/4+1)-(1-2*3)"));
		Assert.assertEquals("-5.0-(2/4+1)/(1-3)", printSimplifyBracketsPart("(1-2*3)-(2/4+1)/(1-3)"));
	}

	
	@Test
	public void testComputeMultiplyDivide() throws Exception {
		System.out.println("---- Test ComputeMultiplyDivide() ----");
		Assert.assertEquals(-0.5, printComputeMultiplyDivide("-2/-4/-1"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeMultiplyDivide("-2/-4/+1"), DELTA_ERROR);
		Assert.assertEquals(-0.125, printComputeMultiplyDivide("-1/-2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.875, printComputeMultiplyDivide("1-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(0.875, printComputeMultiplyDivide("-1/-2/-4+1"), DELTA_ERROR);
		Assert.assertEquals(0.875, printComputeMultiplyDivide("1-1/-2/-4"), DELTA_ERROR);
		Assert.assertEquals(0.5, printComputeMultiplyDivide("1+2/-4"), DELTA_ERROR);
		Assert.assertEquals(1.5, printComputeMultiplyDivide("2/4+1"), DELTA_ERROR);
	}

	@Test
	public void testSimplifyMultiplyDividePart() {
		System.out.println("---- Test simplifyMultiplyAndDividePart() ----");
		Assert.assertEquals("0.5+1", printSimplifyMultiplyDividePart("2/4+1"));
		Assert.assertEquals("0.5-1", printSimplifyMultiplyDividePart("2/4-1"));
		Assert.assertEquals("1+0.5", printSimplifyMultiplyDividePart("1+2/4"));
		Assert.assertEquals("1-0.5", printSimplifyMultiplyDividePart("1-2/4"));
		Assert.assertEquals("1+-0.5+3", printSimplifyMultiplyDividePart("1+2/-4+3"));
		Assert.assertEquals("1--0.5-3", printSimplifyMultiplyDividePart("1-2/-4-3"));
	}

	@Test
	public void testThrowExceptionAsBrachetsOutside() {
		System.out.println("---- Test throwExceptionAsBrachetsOutside() ----");
		Assert.assertFalse(throwExceptionAsBracketsOutside("8 / -4"));
		Assert.assertFalse(throwExceptionAsBracketsOutside("-8 / -4"));
		Assert.assertTrue(throwExceptionAsBracketsOutside("( 8 / 4)"));
		Assert.assertTrue(throwExceptionAsBracketsOutside("( 8 / -4)"));
		
		Assert.assertFalse(throwExceptionAsBracketsOutside("1+( 8 / -4)"));
		Assert.assertFalse(throwExceptionAsBracketsOutside("( 8 / -4)+1"));
		Assert.assertTrue(throwExceptionAsBracketsOutside("( 8 / 4)+(1-2)"));
		Assert.assertTrue(throwExceptionAsBracketsOutside("(1-2)+( 8 / -4)"));
	}

	private boolean throwExceptionAsBracketsOutside(String formula) {
		boolean hasExcpetion = false;
		try {
			FormulaHelper.throwExceptionAsBrachetsOutside(formula);
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

	private double printComputeMultiplyDivide(String formula) throws Exception {
		double answer = Calculator.computeMultiplyDivide(formula);
		System.out.println(">>> " + formula + " => " + answer);
		return answer;
	}

	private String printSimplifyMultiplyDividePart(String formula) {
		String newFormula = Calculator.simplifyMultiplyDividePart(formula);
		System.out.println(">>> " + formula + " => " + newFormula);
		return newFormula;
	}
	
	private String printSimplifyBracketsPart(String formula) throws Exception {
		String newFormula = Calculator.simplifyBracketsPart(formula);
		System.out.println(">>> " + formula + " => " + newFormula);
		return newFormula;
	}
}
