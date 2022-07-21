package netty.eventloop;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2022/7/17 19:17
 * @Description:
 */
@Slf4j
public class EventLoopTest {
    /**
     * 事件循环对象
     *
     * EventLoop 本质是一个单线程执行器（同时维护了一个 Selector），里面有 run 方法处理 Channel 上源源不断的 io 事件。
     *
     * 它的继承关系比较复杂
     *
     * * 一条线是继承自 j.u.c.ScheduledExecutorService 因此包含了线程池中所有的方法
     * * 另一条线是继承自 netty 自己的 OrderedEventExecutor，
     *   * 提供了 boolean inEventLoop(Thread thread) 方法判断一个线程是否属于此 EventLoop
     *   * 提供了 parent 方法来看看自己属于哪个 EventLoopGroup
     *
     * 事件循环组
     *
     * EventLoopGroup 是一组 EventLoop，Channel 一般会调用 EventLoopGroup 的 register 方法来绑定其中一个 EventLoop，后续这个 Channel 上的 io 事件都由此 EventLoop 来处理（保证了 io 事件处理时的线程安全）
     *
     * * 继承自 netty 自己的 EventExecutorGroup
     *   * 实现了 Iterable 接口提供遍历 EventLoop 的能力
     *   * 另有 next 方法获取集合中下一个 EventLoop
     */

    @Test
    public void DefaultEventLoopGroup_Test() {
        // DefaultEventLoopGroup 只能处理 普通任务、定时任务
        DefaultEventLoopGroup group = new DefaultEventLoopGroup(2);
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
    }

    @Test
    public void NioEventLoopGroup_Test() throws InterruptedException {
        // NioEventLoopGroup 能处理IO事件、普通任务、定时任务
        // 1. 创建事件循环组 EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        // 2. 调用next() 获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        // 3. 执行普通任务
        Future<?> future = group.next().submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("执行普通任务");
        });
        log.info("主线程结束");
        // 由于 eventLoop 默认都是守护线程，所以主线程结束后就看不到结果了，这里调用sync阻塞住主线程等待 eventLoop 执行结束
        future.sync();


    }
}
