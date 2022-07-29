package me.cuiyijie.nongmo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2022/5/10 15:40
 */
public class CompletableUtils {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();

        List<Integer> resultList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            int taskCount = 10;
            CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

            for (int index = 0; index < taskCount; index++) {
                completionService.submit(new Task(index));
            }

            for (int index = 0; index < taskCount; index++) {
                Integer result = completionService.take().get();
                System.out.println("任务i==" + result + "完成!" + new Date());
                resultList.add(result);
            }
            System.out.println("result = " + resultList);
            System.out.println("总耗时=" + (System.currentTimeMillis() - start));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


    static class Task implements Callable<Integer> {

        private Integer index;

        public Task(Integer index) {
            this.index = index;
        }

        @Override
        public Integer call() throws Exception {
            if (index % 5 == 0) {
                Thread.sleep(5000);
            } else {
                Thread.sleep(1000);
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "任务index=" + index + ",执行完成！");
            return index;
        }
    }

}
