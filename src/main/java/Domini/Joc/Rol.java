package Domini.Joc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Rol {
	CodeMaster, CodeBreaker;
	
	private static final List<Rol> VALUES = Collections.unmodifiableList(Arrays.asList(Rol.values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static Rol obteRolRandom()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
