import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class MainClass {
    private File file = new File("resources/wp.txt");
    List<String> strings;
    //    Здесь задается слово для подсчета задания 1
    private String word = "the";
    //    Здесь задается количество букв для задания 2
    private int wordLength = 3;


    {
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        if (mainClass.strings != null) {
            System.out.println("-----Частота встречаемости слова-----");
            ArrayList<String> list = mainClass.getWords(mainClass.strings);
            HashMap<String, Integer> map = mainClass.getMap(list);
            System.out.println(mainClass.getFrequency(map, mainClass.word));
            System.out.println("-----Собрать слова в группы по количеству букв-----");
            HashMap<Integer, ArrayList<String>> lengthMap = mainClass.getGroups(list);
            System.out.println(lengthMap.get(mainClass.wordLength));
            System.out.println("-----Вывести топ 10 самых частых слов-----");
            ArrayList<Map.Entry<String, Integer>> entryArrayList = mainClass.getTreeMap(map);
            for (int i = 0; i < 10; i++) {
                System.out.println(entryArrayList.get(entryArrayList.size() - 1 - i));
            }
            System.out.println("-----Собрать слова в группы по количеству букв без артиклей-----");
            ArrayList<String> listWOArticles = mainClass.removeArticles(list);
            HashMap<Integer, ArrayList<String>> lengthMapWOarticles = mainClass.getGroups(listWOArticles);
            System.out.println(lengthMapWOarticles.get(mainClass.wordLength));
            System.out.println("-----Вывести частоту встречаемости букв в процентах-----");
            HashMap<Character, Double> percentMap = mainClass.getPercentMap(list);
            mainClass.printMap(percentMap);

        } else {
            System.out.println("Что-то пошло не так! List == null");
        }

    }

    private void printMap(HashMap<Character,Double> percentMap) {
        for (Map.Entry<Character,Double> entry: percentMap.entrySet()){
            System.out.println(entry.getKey() + " в тексте - " + entry.getValue() + "%");
        }
    }

    private HashMap<Character, Double> getPercentMap(ArrayList<String> list) {
        HashMap<Character, Double> percentMap = new HashMap<>();
        HashMap<Character, Integer> map = new HashMap<>();
        for (String s : list) {
            if (s.length() > 0) {
                for (int i = 0; i < s.length(); i++) {
                    Character c = s.charAt(i);
                    if (!map.containsKey(c)) {
                        map.put(c, 1);
                    } else {
                        map.put(c, map.get(c) + 1);
                    }
                }

            }
        }
        double totalSumOfLetters = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            totalSumOfLetters += entry.getValue();
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            percentMap.put(entry.getKey(), entry.getValue() / totalSumOfLetters * 100);
        }
        return percentMap;
    }

    private ArrayList<String> removeArticles(ArrayList<String> list) {
        ArrayList<String> newList = new ArrayList<>();
        ArrayList<String> articles = new ArrayList<String>();
        for (ArticlesAndMore s : ArticlesAndMore.values()) {
            articles.add(s.toString());
        }
        for (String s : list) {
            if (!articles.contains(s)) {
                newList.add(s);
            }
        }
        return newList;
    }


    ArrayList<Map.Entry<String, Integer>> getTreeMap(Map<String, Integer> map) {
        ArrayList<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            listOfEntries.add(entry);
        }
        Comparator<Map.Entry<String, Integer>> comparator = new MapComparator();
        listOfEntries.sort(comparator);
        return listOfEntries;
    }

    private HashMap<Integer, ArrayList<String>> getGroups(ArrayList<String> list) {
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        for (String s : list) {
            if (!map.containsKey(s.length())) {
                map.put(s.length(), new ArrayList<String>());
            }
            if (!map.get(s.length()).contains(s)) {
                map.get(s.length()).add(s);
            }
        }
        return map;
    }

    HashMap<String, Integer> getMap(List<String> list) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : list) {
            if (s.length() > 0) {
                if (!map.containsKey(s)) {
                    map.put(s, 1);
                } else {
                    map.put(s, map.get(s) + 1);
                }
            }
        }
        return map;
    }

    ArrayList<String> getWords(List<String> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s : list) {
            String[] array = s.split(" ");
            for (int i = 0; i < array.length; i++) {
                arrayList.add(array[i].trim().replaceAll("[^a-zA-Z]", "").toLowerCase());
            }
        }
        return arrayList;
    }

    private String getFrequency(HashMap<String, Integer> map, String word) {
        return "Слово \"" + word + "\" встречается в тексте " + map.get(word) + " раз.";
    }


}

class MapComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return Integer.compare(o1.getValue(), o2.getValue());
    }
}

//    Здесь задаются артикли
enum ArticlesAndMore {
    a, an, the, on, to, are, is, he, she, his, that, of, was, in, and, but, did
}
