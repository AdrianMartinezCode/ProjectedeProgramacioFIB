package Domini.Aplicacio.Drivers;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestPartida.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
			System.out.println(failure.getTrace());
		}
		if (result.wasSuccessful()) {
			System.out.println("El test ha anat be");
		} else {
			System.out.println("El test ha anat malament");
		}
	}
}
