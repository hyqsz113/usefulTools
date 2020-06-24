package data;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-22 12:03
 **/
public class myQueue {

    private int[] array;
    private int front;
    private int rear;

    public myQueue(int capacity) {
        this.array = new int[capacity];
    }

    /**
     * 入队
     *
     * @param element
     * @throws Exception
     */
    public void enQuene(int element) throws Exception {
        if ((rear + 1) % array.length == front) {
            throw new Exception("队列已满");
        }
        array[rear] = element;
        rear = (rear + 1) % array.length;
    }

    /**
     * 出队
     *
     * @return
     * @throws Exception
     */
    public int deQueue() throws Exception {
        if (rear == front) {
            throw new Exception("队列已空！");
        }
        int deQueueElement = array[front];
        front = (front + 1) % array.length;
        return deQueueElement;
    }

    /**
     * 输出队列
     */
    public void output(){
        for (int i = front; i!= rear; i= (i+1)% array.length) {
            System.out.println(array[i]);
        }
    }

    public static void main(String[] args) throws Exception {
        myQueue myQueue = new myQueue(6);
        myQueue.enQuene(3);
        myQueue.enQuene(5);
        myQueue.enQuene(6);
        myQueue.enQuene(8);
        myQueue.enQuene(1);
        myQueue.enQuene(1);
        myQueue.enQuene(1);
        myQueue.enQuene(1);
        myQueue.deQueue();
        myQueue.deQueue();
        myQueue.deQueue();
        myQueue.enQuene(2);
        myQueue.enQuene(4);
        myQueue.enQuene(9);
        myQueue.output();

    }
}
