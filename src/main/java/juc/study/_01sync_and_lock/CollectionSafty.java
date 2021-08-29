package juc.study._01sync_and_lock;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本类解决三个问题：
 *      1, 分别用 CopyOnWriteArraySet / CopyOnWriteArrayList / ConcurrentHashMap
 *      2, Collections.synchronizedList
 */
public class CollectionSafty {
    public static void main(String[] args) {
        //创建ArrayList集合, 这个线程不安全
        //List<String> list = new ArrayList<>();

        /**
         * Solution 1:
         *      Vector解决
         */
        //List<String> list = new Vector<>();

        /**
         * Solution 2:
         *      Collections.synchronizedList
         */
        //List<String> list = Collections.synchronizedList(new ArrayList<>());

        /**
         * Solution 3:
         *      CopyOnWriteArrayList解决
         */
//        List<String> list = new CopyOnWriteArrayList<>();
//        for (int i = 0; i <30; i++) {
//            new Thread(()->{
//                //向集合添加内容
//                list.add(UUID.randomUUID().toString().substring(0,8));
//                //从集合获取内容
//                System.out.println(list);
//            },String.valueOf(i)).start();
//        }

        //演示Hashset
//        Set<String> set = new HashSet<>();

//        Set<String> set = new CopyOnWriteArraySet<>();
//        for (int i = 0; i <30; i++) {
//            new Thread(()->{
//                //向集合添加内容
//                set.add(UUID.randomUUID().toString().substring(0,8));
//                //从集合获取内容
//                System.out.println(set);
//            },String.valueOf(i)).start();
//        }

        //演示HashMap
//        Map<String,String> map = new HashMap<>();

        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i <30; i++) {
            String key = String.valueOf(i);
            new Thread(()->{
                //向集合添加内容
                map.put(key,UUID.randomUUID().toString().substring(0,8));
                //从集合获取内容
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
