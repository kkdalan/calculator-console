package calculator;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

	private static final double DELTA_ERROR = 0.0001;

	@Test
	public void testCompute() throws Exception {
		System.out.println("---- Test compute() ----");
		Assert.assertEquals(1, printCompute("1"), DELTA_ERROR);
		Assert.assertEquals(-1, printCompute("-1"), DELTA_ERROR);
		Assert.assertEquals(0, printCompute("0"), DELTA_ERROR);
		Assert.assertEquals(0.0, printCompute("0.0"), DELTA_ERROR);
		Assert.assertEquals(2.0, printCompute("2.0"), DELTA_ERROR);
		Assert.assertEquals(-2.0, printCompute("-2.0"), DELTA_ERROR);

		Assert.assertEquals(3.0, printCompute("1+4/2"), DELTA_ERROR);
		Assert.assertEquals(-1.0, printCompute("1-4/2"), DELTA_ERROR);
		Assert.assertEquals(3.0, printCompute("4/2+1"), DELTA_ERROR);
		Assert.assertEquals(1.0, printCompute("4/2-1"), DELTA_ERROR);

		Assert.assertEquals(1.0, printCompute("2/2"), DELTA_ERROR);
		Assert.assertEquals(0.5, printCompute("2/2/2"), DELTA_ERROR);
		Assert.assertEquals(-1.0, printCompute("2/-2"), DELTA_ERROR);
		Assert.assertEquals(0.5, printCompute("2/-2/-2"), DELTA_ERROR);
		Assert.assertEquals(0.25, printCompute("2/-2/2/-2"), DELTA_ERROR);

		Assert.assertEquals(4, printCompute("2*2"), DELTA_ERROR);
		Assert.assertEquals(-4, printCompute("2*-2"), DELTA_ERROR);
		Assert.assertEquals(8, printCompute("2*2*2"), DELTA_ERROR);
		Assert.assertEquals(16, printCompute("2*-2*2*-2"), DELTA_ERROR);
		Assert.assertEquals(-2, printCompute("2*2/-2"), DELTA_ERROR);
		Assert.assertEquals(1.33333, printCompute("2*-2/-3"), DELTA_ERROR);
		Assert.assertEquals(1.33333, printCompute("2*(-2)/(-3)"), DELTA_ERROR);
		Assert.assertEquals(-1.33333, printCompute("-2*(-2)/(-3)"), DELTA_ERROR);
		Assert.assertEquals(-3.0, printCompute("-2/(-2)*(-3)"), DELTA_ERROR);

		Assert.assertEquals(4, printCompute("(1+2+3-5)*4"), DELTA_ERROR);
		Assert.assertEquals(0.25, printCompute("(1+2 +3-5)/4"), DELTA_ERROR);
		Assert.assertEquals(-0.5, printCompute("( 1+2+3-5)/4*(-2)"), DELTA_ERROR);
		Assert.assertEquals(-0.125, printCompute("(1+2+3-5)/4/(-2)"), DELTA_ERROR);

		Assert.assertEquals(0.25, printCompute("(1 +2  +3 -5) / 4"), DELTA_ERROR);
		Assert.assertEquals(24.0, printCompute("8 / ( 3 - 8 / 3)"), DELTA_ERROR);
		Assert.assertEquals(-48.0, printCompute("-2 * ( 8 / ( 3 -8 / 3))"), DELTA_ERROR);
		Assert.assertEquals(16.0, printCompute("-2 * ( 8 / ( 3 -8 / 3) / - 3)"), DELTA_ERROR);

		Assert.assertEquals(-14, printCompute("1*(-1)+2*(-2)+3*(-3)"), DELTA_ERROR);
		Assert.assertEquals(-14, printCompute("1*-1+2*-2+3*-3"), DELTA_ERROR);
		Assert.assertEquals(14, printCompute("1*1+2*2+3*3"), DELTA_ERROR);

		Assert.assertEquals(Double.POSITIVE_INFINITY, printCompute("2/0"), DELTA_ERROR);
		Assert.assertEquals(Double.NEGATIVE_INFINITY, printCompute("-2/0"), DELTA_ERROR);
		Assert.assertEquals(Double.POSITIVE_INFINITY, printCompute("2/(1-1)"), DELTA_ERROR);
		Assert.assertEquals(Double.NEGATIVE_INFINITY, printCompute("-2/(1-1)"), DELTA_ERROR);

		Assert.assertEquals(Double.NaN, printCompute("0/0"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printCompute("(1-1)/0"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printCompute("0/(1-1)"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printCompute("1+0/(1-1)"), DELTA_ERROR);
		Assert.assertEquals(Double.NaN, printCompute("0/(1-1)-3"), DELTA_ERROR);

		Assert.assertEquals(8, printCompute("2^3"), DELTA_ERROR);
		Assert.assertEquals(9, printCompute("1+2^3"), DELTA_ERROR);
		Assert.assertEquals(9, printCompute("2^3+1"), DELTA_ERROR);
		Assert.assertEquals(7, printCompute("2^3-1"), DELTA_ERROR);
		Assert.assertEquals(-7, printCompute("1-2^3"), DELTA_ERROR);
		Assert.assertEquals(-9, printCompute("-1-2^3"), DELTA_ERROR);
		Assert.assertEquals(0.125, printCompute("2^-3"), DELTA_ERROR);
		Assert.assertEquals(1.41421, printCompute("2^0.5"), DELTA_ERROR);
		Assert.assertEquals(0.707106, printCompute("2^-0.5"), DELTA_ERROR);
		Assert.assertEquals(16, printCompute("2*2^3"), DELTA_ERROR);
		Assert.assertEquals(16, printCompute("2^3*2"), DELTA_ERROR);
		Assert.assertEquals(0.25, printCompute("2/2^3"), DELTA_ERROR);
		Assert.assertEquals(4, printCompute("2^3/2"), DELTA_ERROR);
		Assert.assertEquals(-4, printCompute("2^3/-2"), DELTA_ERROR);
		Assert.assertEquals(-0.25, printCompute("-2/2^3"), DELTA_ERROR);
		Assert.assertEquals(18, printCompute("10+2^3"), DELTA_ERROR);
		Assert.assertEquals(18, printCompute("2^3+10"), DELTA_ERROR);
		Assert.assertEquals(-18, printCompute("-10-2^3"), DELTA_ERROR);
		Assert.assertEquals(-2, printCompute("2^3-10"), DELTA_ERROR);
	}

	private double printCompute(String formula) throws Exception {
		double answer = Calculator.compute(formula);
		System.out.println(">>> " + formula + " = " + answer);
		return answer;
	}

}
