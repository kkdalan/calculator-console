package calculator.helper;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class FormulaHelperTest extends TestCase {

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
	 
}
