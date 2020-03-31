import java.util.*;

class UniPathFastInvoke {
	static public String format(Class<String> thiz, String format, Object arg1) {
		return String.format(format, arg1);
	}

	static public Object put(HashMap thiz, Integer key, String value) {
		return thiz.put(key, value);
	}
}