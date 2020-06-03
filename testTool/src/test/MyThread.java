package test;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-02 11:17
 **/
public class MyThread {
    public static class T1 extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println(String.format("当前执行的线程是：%s，优先级：%d",
                    Thread.currentThread().getName(),
                    Thread.currentThread().getPriority()));
        }
    }

    public static void main(String[] args) {
        //IntStream.range(1,5).forEach(System.out::println);

        /*IntStream.range(1, 10).forEach(i -> {
            Thread thread = new Thread(new T1());
            thread.setPriority(i);
            thread.start();
        });*/
        // fff(check(),"你好吗");
        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;
        boolean aa = !(a==2) && !(c==d);
        System.out.println(aa);
    }

    private static void fff(Void sth, String param){
        System.out.println(sth+param);
    }

    private static Void check(){
        System.out.println("做了检测");
        return null;
    }



}
