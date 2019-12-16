import java.util.*;
import java.util.concurrent.Callable;

public class Top100Thread implements Runnable {
    private String string;
    Map<String, Integer> map;

    public Top100Thread(String string, Map<String, Integer> map) {
        this.string = string;
        this.map = map;
    }

    @Override
    public void run() {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] array = string.split(" ");
        for (int i = 0; i < array.length; i++) {
            arrayList.add(array[i].trim().replaceAll("[^a-zA-Z]", "").toLowerCase());
        }
        for (String s : arrayList) {
            if (s.length() > 0) {
                if (!map.containsKey(s)) {
                    map.put(s, 1);
                } else {
                    map.put(s, map.get(s) + 1);
                }
            }
        }
    }
}


