package com.hyq.test;

import com.utils.template.RetryTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-03 12:19
 **/
@RunWith(SpringRunner.class)
@Slf4j
public class UtilsTest {

    @Test
    public void retryDemo() throws InterruptedException {

        Object ans = new RetryTemplate() {
            @Override
            protected Object doBiz() throws Exception {
                int temp = (int) (Math.random() * 10);
                System.out.println("随机数" + temp);
                if (temp > 3) {
                    throw new Exception("generate value bigger then 3! need retry");
                }
                return temp;
            }
        }.setRetryTime(10).setSleepTime(5 * 1000).execute();
        System.out.println("重试抽象类" + ans);
    }

    @Test
    public void retryThreadDemo() throws InterruptedException {
        Thread a = new Thread(() -> {
            log.info("a线程开始");
            Object ans = null;
            try {
                ans = new RetryTemplate() {
                    @Override
                    protected Object doBiz() throws Exception {
                        int temp = (int) (Math.random() * 10);
                        System.out.println("随机数" + temp);
                        if (temp > 3) {
                            throw new Exception("generate value bigger then 3! need retry");
                        }
                        return temp;
                    }
                }.setRetryTime(10).setSleepTime(5 * 1000).execute();
            } catch (InterruptedException e) {
                log.info("线程：{}", Thread.currentThread().getName() + "抛出异常");
                e.printStackTrace();
            }

            System.out.println("重试结束" + ans);
        }, "a");

        Thread b = new Thread(() -> {
            log.info("b线程开始");
            Object ans = null;
            try {
                ans = new RetryTemplate() {
                    @Override
                    protected Object doBiz() throws Exception {
                        int temp = (int) (Math.random() * 10);
                        System.out.println("随机数" + temp);
                        if (temp > 3) {
                            throw new Exception("generate value bigger then 3! need retry");
                        }
                        return temp;
                    }
                }.setRetryTime(10).setSleepTime(5 * 1000).execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("重试结束" + ans);
        }, "b");


        log.info("线程准备启动");
        a.start();
        Thread.sleep(1000);
        b.start();


        System.out.println(a.getName() + ":" + a.getState()); // 输出？
        System.out.println(b.getName() + ":" + b.getState()); // 输出？
    }


    private Integer getInt() {
        int i = (int) (Math.random() * 10);
        System.out.println("随机数为：" + i);
        return i;
    }

    @Test
    public void retryThreadTimes() throws InterruptedException {

        int checkNum = 5;

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("a线程开始");
                Integer num = getInt();
                // 小于 ， 不满足
                if (num <= checkNum) {
                    System.out.println("不满足，重新获取");
                    // 重试3次
                    for (int i = 0; i < 3; i++) {
                        try {
                            System.out.println("等待" + (i + 1) + "秒");
                            Thread.sleep((i + 1) * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 再次执行
                        num = getInt();
                        if (num > checkNum) {
                            System.out.println("满足条件，数字为" + num);
                            return;
                        }
                    }
                    System.out.println("执行了3次");
                }
                if (num >= checkNum) {

                    // 大于，满足
                    System.out.println("满足条件，数字为：" + num);
                } else {
                    System.out.println("不满足条件，数字为：" + num);
                }

            }
        });

        a.start();
        System.out.println("a线程start");
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(10 * 1000);
    }

    @Test
    public void TestConcurrentCollection() {
        Map ass = new HashMap();
        Map b = new Hashtable();
        List ss = new CopyOnWriteArrayList();
        CountDownLatch countDownLatch = new CountDownLatch(11);
        Set aa = new TreeSet<>();

    }

    @Test
    public void testFibonacci() {
        int n = 2;
        int f = f(n);
        System.out.println(f);
    }

    private int f(int n) {
        int loop = 1;

        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int result = 0;
        int pre = 1;
        int next = 2;

        for (int i = 3; i < n + 1; i++) {
            result = pre + next;
            pre = next;
            next = result;
            System.out.println("循环次数：" + loop + "，结果：" + result);
            loop++;
        }
        System.out.println("最后：" + result);
        return result;
    }

    @Test
    public void testTraverse() {
        /*int[][] aa = {
                {2, 0, 0, 0},
                {3, 4, 0, 0},
                {6, 5, 7, 0},
                {4, 1, 8, 3}
        };
        int[] ints = aa[3];
        for (int anInt : ints) {
            System.out.println(anInt);
        }*/

        int a = 0;
        a += 1;
        System.out.println(a);

        Random random = new Random();
        for (int i1 = 0; i1 < 10; i1++) {
            int i = random.nextInt(5);
            System.out.println(i);
        }
    }
}
