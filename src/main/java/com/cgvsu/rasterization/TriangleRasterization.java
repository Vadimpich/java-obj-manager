package com.cgvsu.rasterization;

import com.cgvsu.math.VectorNf;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector2f;
import com.cgvsu.render_engine.ZBuffer;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TriangleRasterization {

    private static final Comparator<Vector3f> COMPARATOR = (a, b) -> {
        int cmp = Double.compare(a.getY(), b.getY());
        if (cmp != 0) {
            return cmp;
        } else return Double.compare(a.getX(), b.getX());
    };

    private static Vector2f p = new Vector2f(0, 0);

    public static void drawTriangle(
            final PixelWriter pw,
            final Vector3f v1,
            final Vector3f v2,
            final Vector3f v3,
            final Color color,
            List<Vector2f> texture,
            ZBuffer zbuffer,
            PixelReader pixelReader
    ) {
        final var verts = new Vector3f[]{v1, v2, v3};
        Arrays.sort(verts, COMPARATOR);
        final int x1 = (int) verts[0].getX();
        final int x2 = (int) verts[1].getX();
        final int x3 = (int) verts[2].getX();
        final int y1 = (int) verts[0].getY();
        final int y2 = (int) verts[1].getY();
        final int y3 = (int) verts[2].getY();

        final double area = Math.abs(((VectorNf) v1.subtraction(v2)).cross((VectorNf) v1.subtraction(v3)));
        drawTopTriangle(pw, x1, y1, x2, y2, x3, y3, v1, color, v2, v3, area, texture, zbuffer, pixelReader);
        drawBottomTriangle(pw, x1, y1, x2, y2, x3, y3, v1, color, v2, v3, area, texture, zbuffer, pixelReader);
    }

    private static void drawTopTriangle(
            final PixelWriter pw,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Vector3f v1, Color color,
            final Vector3f v2,
            final Vector3f v3,
            final double area, List<Vector2f> texture, ZBuffer zbuffer, PixelReader pixelReader
    ) {

        Color color1 = null;
        Color color2 = null;
        Color color3 = null;

        if (!texture.isEmpty()) {
            color1 = pixelReader.getColor((int) (texture.get(0).getX() * 4095), (int) (texture.get(0).getY() * 4095));
            color2 = pixelReader.getColor((int) (texture.get(1).getX() * 4095), (int) (texture.get(1).getY() * 4095));
            color3 = pixelReader.getColor((int) (texture.get(2).getX() * 4095), (int) (texture.get(2).getY() * 4095));
        }
        final int x2x1 = x2 - x1;
        final int x3x1 = x3 - x1;
        final int y2y1 = y2 - y1;
        final int y3y1 = y3 - y1;

        for (int y = y1; y < y2; y++) {
            int l = x2x1 * (y - y1) / y2y1 + x1;
            int r = x3x1 * (y - y1) / y3y1 + x1;
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            for (int x = l; x <= r + 1; x++) {
                Color currentColor = null;
                if (!texture.isEmpty()) {
                    p = new Vector2f(x, y);
                    final double w1 = Math.abs(((VectorNf) new Vector2f(v2.getX(), v2.getY()).subtraction(p)).cross((VectorNf) new Vector2f(v2.getX(), v2.getY()).subtraction(new Vector2f(v3.getX(), v3.getY())))) / area;
                    final double w2 = Math.abs(((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(p)).cross((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(new Vector2f(v3.getX(), v3.getY())))) / area;
                    final double w3 = Math.abs(((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(p)).cross((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(new Vector2f(v2.getX(), v2.getY())))) / area;
                    final double red = clamp(((w1 * color1.getRed())
                            + (w2 * color2.getRed())
                            + (w3 * color3.getRed())));
                    final double green = clamp((((w1 * color1.getGreen())
                            + (w2 * color2.getGreen())
                            + (w3 * color3.getGreen()))));
                    final double blue = clamp(((w1 * color1.getBlue())
                            + (w2 * color2.getBlue())
                            + (w3 * color3.getBlue())));
                    if (red == 1 || green == 1 || blue == 1 || red == 0 || green == 0 || blue == 0) {
                        color = color1;
                    } else {
                        color = new Color(red, green, blue, 1);
                    }
                }
                try {
                    if (zbuffer.bufferCheck(x, y, v1.getZ())) {
                        double k = 0.8;
                        pw.setColor(x, y, new Color(color.getRed() * (1 - k) + color.getRed() * k, color.getGreen() * (1 - k) + color.getGreen() * k, color.getBlue() * (1 - k) + color.getBlue() * k, 1));
                    }
                } catch (Exception ignored) {

                }
            }
        }
    }

    private static void drawBottomTriangle(
            final PixelWriter pw,
            final int x1, final int y1,
            final int x2, final int y2,
            final int x3, final int y3,
            final Vector3f v1, Color color,
            final Vector3f v2,
            final Vector3f v3,
            final double area,
            List<Vector2f> texture, ZBuffer zbuffer, PixelReader pixelReader
    ) {

        Color color1 = null;
        Color color2 = null;
        Color color3 = null;

        if (!texture.isEmpty()) {
            color1 = pixelReader.getColor((int) (texture.get(0).getX() * 4095), (int) (texture.get(0).getY() * 4095));
            color2 = pixelReader.getColor((int) (texture.get(1).getX() * 4095), (int) (texture.get(1).getY() * 4095));
            color3 = pixelReader.getColor((int) (texture.get(2).getX() * 4095), (int) (texture.get(2).getY() * 4095));
        }
        final int x3x2 = x3 - x2;
        final int x3x1 = x3 - x1;
        final int y3y2 = y3 - y2;
        final int y3y1 = y3 - y1;


        if (y3y2 == 0 || y3y1 == 0) return;
        for (int y = y2; y <= y3; y++) {
            int l = x3x2 * (y - y2) / y3y2 + x2;
            int r = x3x1 * (y - y1) / y3y1 + x1;
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }
            Color currentColor = null;
            for (int x = l; x <= r + 1; x++) {
                if (!texture.isEmpty()) {
                    p = new Vector2f(x, y);
                    final double w1 = Math.abs(((VectorNf) new Vector2f(v2.getX(), v2.getY()).subtraction(p)).cross((VectorNf) new Vector2f(v2.getX(), v2.getY()).subtraction(new Vector2f(v3.getX(), v3.getY())))) / area;
                    final double w2 = Math.abs(((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(p)).cross((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(new Vector2f(v3.getX(), v3.getY())))) / area;
                    final double w3 = Math.abs(((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(p)).cross((VectorNf) new Vector2f(v1.getX(), v1.getY()).subtraction(new Vector2f(v2.getX(), v2.getY())))) / area;

                    final double red = clamp(((w1 * color1.getRed())
                            + (w2 * color2.getRed())
                            + (w3 * color3.getRed())));
                    final double green = clamp(((w1 * color1.getGreen())
                            + (w2 * color2.getGreen())
                            + (w3 * color3.getGreen())));
                    final double blue = clamp(((w1 * color1.getBlue())
                            + (w2 * color2.getBlue())
                            + (w3 * color3.getBlue())));


                    if (red == 1 || green == 1 || blue == 1 || red == 0 || green == 0 || blue == 0) {
                        color = color1;
                    } else {
                        color = new Color(red, green, blue, 1);
                    }

                }

                try {
                    if (zbuffer.bufferCheck(x, y, v1.getZ())) {
                        double k = 0.8;
                        pw.setColor(x, y, new Color(color.getRed() * (1 - k) + color.getRed() * k, color.getGreen() * (1 - k) + color.getGreen() * k, color.getBlue() * (1 - k) + color.getBlue()  * k, 1));
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private static double clamp(double v) {
        if (v < 0.0) return 0;
        if (v > 1.0) return 1.0;
        return v;
    }
}