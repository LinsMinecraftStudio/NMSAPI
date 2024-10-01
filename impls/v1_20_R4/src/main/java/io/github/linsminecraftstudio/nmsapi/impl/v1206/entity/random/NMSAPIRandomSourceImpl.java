package io.github.linsminecraftstudio.nmsapi.impl.v1206.entity.random;

import io.github.linsminecraftstudio.nmsapi.object.NMSAPIRandomSource;
import net.minecraft.util.RandomSource;

public class NMSAPIRandomSourceImpl implements NMSAPIRandomSource {
    private final RandomSource randomSource;

    public NMSAPIRandomSourceImpl(RandomSource randomSource) {
        this.randomSource = randomSource;
    }

    @Override
    public void setSeed(long seed) {
        this.randomSource.setSeed(seed);
    }

    @Override
    public long nextLong() {
        return this.randomSource.nextLong();
    }

    @Override
    public int nextInt() {
        return this.randomSource.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return this.randomSource.nextInt(bound);
    }

    @Override
    public float nextFloat() {
        return this.randomSource.nextFloat();
    }

    @Override
    public double nextDouble() {
        return this.randomSource.nextDouble();
    }

    @Override
    public double nextGaussian() {
        return this.randomSource.nextGaussian();
    }

    @Override
    public boolean nextBoolean() {
        return this.randomSource.nextBoolean();
    }

    @Override
    public NMSAPIRandomSource fork() {
        return new NMSAPIRandomSourceImpl(this.randomSource.fork());
    }
}
