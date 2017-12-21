package readinglist;

public class MyLog {
	
	public static void log(Class<?> clazz, String message) {
		StringBuilder sb = new StringBuilder("-----------------:");
		sb.append(clazz.getSimpleName()).append("\n");
		sb.append(message);
		System.out.println(sb.toString());
	}

}
