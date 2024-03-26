package ratelimiter;

import java.time.Duration;

public class TokenBucket {
    private final long capacity;
    private final RefillStrategy refillStrategy;
    private long size;
    public TokenBucket(long capacity, long initialCapacity, RefillStrategy refillStrategy) {
        this.capacity = capacity;
        this.refillStrategy = refillStrategy;
        this.size = initialCapacity;
    }
    public long getCapacity() {
        return capacity;
    }

    public long getNumberOfTokens() {
        refill(refillStrategy.refill());
        return size;
    }

    public Duration getDurationUntilNextRefill() {
        return refillStrategy.getDurationToNextRefill();
    }

    public boolean tryConsume() {
        return tryConsume(1);
    }

    public boolean tryConsume(long numberOfTokens) {
        refill(refillStrategy.refill());

        if (numberOfTokens <= size) {
            size -= numberOfTokens;
            return true;
        }

        return false;
    }

    public void consume() {
        while (true) {
            if (tryConsume())  {
                break;
            }
        }
    }

    public void consume(long numberOfTokens) {
        while (true) {
            if (tryConsume(numberOfTokens))  {
                break;
            }
        }
    }

    public void refill(long numberOfTokens) {
        long newTokens = Math.min(capacity, Math.max(0, numberOfTokens));
        size = 
    }
}
