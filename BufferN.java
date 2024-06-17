public class BufferN {
    private final Runnable[] data;
    private int begin;
    private int end;

    public BufferN(int size) {
        data = new Runnable[size + 1 /* one extra space for the empty condition */];
        begin = 0;
        end = 0;
    }
    public synchronized void write(Runnable obj) throws InterruptedException {
        while (this.isFull()) {
            wait();
        }
        data[begin] = obj;
        begin = next(begin);
        notifyAll();
    }

    public synchronized Runnable read() throws InterruptedException {
        while (end == begin) {
            wait();
        }
        Runnable aux = data[end];
        end = next(end);
        notifyAll();
        return aux;
    }

    private boolean isEmpty() {
        return end == begin;
    }

    private boolean isFull() {
        return next(begin) == end;
    }

    private int next(int i) {
        return (i + 1) % data.length;
    }
}
