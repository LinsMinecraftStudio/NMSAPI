package io.github.linsminecraftstudio.nmsapi.object;

public interface NMSAPIRandomSource {
    void setSeed(long seed);

    long nextLong();

    int nextInt();

    int nextInt(int bound);

    default int nextIntBetweenInclusive(int min, int max) {
        return this.nextInt(max - min + 1) + min;
    }

    float nextFloat();

    double nextDouble();

    double nextGaussian();

    boolean nextBoolean();

    default double triangle(double mode, double deviation) {
        return mode + deviation * (this.nextDouble() - this.nextDouble());
    }

    default void consumeCount(int count) {
        for (int i = 0; i < count; ++i) {
            this.nextInt();
        }
    }

    default int nextInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("bound - origin is non positive");
        } else {
            return min + this.nextInt(max - min);
        }
    }

    NMSAPIRandomSource fork();
}
