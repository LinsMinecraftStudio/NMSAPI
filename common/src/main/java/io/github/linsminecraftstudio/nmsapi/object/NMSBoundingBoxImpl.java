package io.github.linsminecraftstudio.nmsapi.object;

import org.bukkit.util.BoundingBox;

public class NMSBoundingBoxImpl extends BoundingBox {
    public NMSBoundingBoxImpl(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.resize(x1, y1, z1, x2, y2, z2);
    }

    public NMSBoundingBoxImpl(BoundingBox boundingBox) {
        this.copy(boundingBox);
    }

    public NMSBoundingBoxImpl inflate(double x, double y, double z) {
        double d = this.getMinX() - x;
        double e = this.getMinY() - y;
        double f = this.getMinZ() - z;
        double g = this.getMaxX() + x;
        double h = this.getMaxY() + y;
        double i = this.getMaxZ() + z;
        return new NMSBoundingBoxImpl(d, e, f, g, h, i);
    }
}
