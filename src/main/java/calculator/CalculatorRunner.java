package calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CalculatorRunner {
	
	private static final String INPUT_FLAG = ">>> ";
	private static final String CMD_EXIT = "exit";

	public static void main(String[] args) throws Exception {
		start();
	}

	/**
	 * start calculator console
	 * 
	 * @throws Exception
	 */
	public static void start() throws Exception {
		printStartMessage();

		System.out.print(INPUT_FLAG);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String formula = "";
		while (!(formula = br.readLine()).equalsIgnoreCase(CMD_EXIT)) {
			if (!formula.trim().equals("")) {
				try {
					double answer = Calculator.compute(formula);
					System.out.println("  ans = " + String.valueOf(answer));
				} catch (Exception e) {
					System.err.println(e.getMessage());
//					 e.printStackTrace();
				}
			}
			System.out.print(INPUT_FLAG);
		}
		System.out.println("Bye!");
	}

	private static void printStartMessage() {
		System.out.println("=== Java Calculator ===");
		System.out.println("Notes: available operators: { + , - , * , / , ^, ( , ) }");
		System.out.println("Enter formula or ('exit') to exit.");
		System.out.println("ex: ( 1+2)*3/-4^0.5 ");
	}
}
