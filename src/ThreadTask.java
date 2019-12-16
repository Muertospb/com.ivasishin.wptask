import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTask {





    public static void main(String[] args) {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());

        //Используем готовый лист из старого задания
        MainClass mainClass = new MainClass();
        List<String> strings = mainClass.strings;

        //Запускаем три потока заполнять потокобезопасный Map
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        for (String s: strings){
            fixedPool.execute(new Top100Thread(s, map));
        }
        fixedPool.shutdown();

        //Ожидаем выполнения всех задач
        while (!fixedPool.isTerminated()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Используем метод getTreeMap из старой задачи и выводим первые сто слов
        ArrayList<Map.Entry<String, Integer>> entryArrayList = mainClass.getTreeMap(map);
        for (int i = 0; i < 100; i++) {
            System.out.println(entryArrayList.get(entryArrayList.size() - 1 - i));
        }
    }
}

