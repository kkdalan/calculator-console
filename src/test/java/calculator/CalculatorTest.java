package calculator;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {

	private static final double DELTA_ERROR = 0.0001;

	@Test
	public void testCalculator() {
		
		System.out.println("---- Calculator Test ----");
		
		Assert.assertEquals(3.0, printEquation("1+4/2"), DELTA_ERROR);
		Assert.assertEquals(-1.0, printEquation("1-4/2"), DELTA_ERROR);
		Assert.assertEquals(3.0, printEquation("4/2+1"), DELTA_ERROR);
		Assert.assertEquals(1.0, printEquation("4/2-1"), DELTA_ERROR);

		Assert.assertEquals(1.0, printEquation("2/2"), DELTA_ERROR);
		Assert.assertEquals(0.5, printEquation("2/2/2"), DELTA_ERROR);
		Assert.assertEquals(-1.0, printEquation("2/-2"), DELTA_ERROR);
		Assert.assertEquals(0.5, printEquation("2/-2/-2"), DELTA_ERROR);
		Assert.assertEquals(0.25, printEquation("2/-2/2/-2"), DELTA_ERROR);

		Assert.assertEquals(4, printEquation("2*2"), DELTA_ERROR);
		Assert.assertEquals(-4, printEquation("2*-2"), DELTA_ERROR);
		Assert.assertEquals(8, printEquation("2*2*2"), DELTA_ERROR);
		Assert.assertEquals(16, printEquation("2*-2*2*-2"), DELTA_ERROR);
		Assert.assertEquals(-2, printEquation("2*2/-2"), DELTA_ERROR);
		Assert.assertEquals(1.33333, printEquation("2*-2/-3"), DELTA_ERROR);
		Assert.assertEquals(1.33333, printEquation("2*(-2)/(-3)"), DELTA_ERROR);
		Assert.assertEquals(-1.33333, printEquation("-2*(-2)/(-3)"), DELTA_ERROR);
		Assert.assertEquals(-3.0, printEquation("-2/(-2)*(-3)"), DELTA_ERROR);

		Assert.assertEquals(4, printEquation("(1+2+3-5)*4"), DELTA_ERROR);
		Assert.assertEquals(0.25, printEquation("(1+2 +3-5)/4"), DELTA_ERROR);
		Assert.assertEquals(-0.5, printEquation("( 1+2+3-5)/4*(-2)"), DELTA_ERROR);
		Assert.assertEquals(-0.125, printEquation("(1+2+3-5)/4/(-2)"), DELTA_ERROR);

		Assert.assertEquals(0.25, printEquation("(1 +2  +3 -5) / 4"), DELTA_ERROR);
		Assert.assertEquals(24.0, printEquation("8 / ( 3 - 8 / 3)"), DELTA_ERROR);
		Assert.assertEquals(-48.0, printEquation("-2 * ( 8 / ( 3 -8 / 3)"), DELTA_ERROR);
		Assert.assertEquals(16.0, printEquation("-2 * ( 8 / ( 3 -8 / 3) / - 3"), DELTA_ERROR);
	}

	private double printEquation(String formula) {
		double answer = Calculator.computeFormula(formula);
		System.out.println(">>> " + formula + " = " + answer);
		return answer;
	}
}
