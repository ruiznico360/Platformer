package com.tempbusiness.platformer.game.graphics.rendering;

import com.tempbusiness.platformer.game.graphics.Graphic;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphicsLayerer {
    private ArrayList[] layers;

    public GraphicsLayerer(int layerCount) {
        this.layers = new ArrayList[layerCount];
        for (int i = 0; i < layerCount; i++) {
            layers[i] = new ArrayList<Graphic>();
        }
    }

    public void add(Graphic g, int layer) {
        layers[layer].add(g);
    }

    public void add(Graphic g) {
        add(g, 0);
    }
//    public void remove(Graphic g) {
//        graphics.remove(g);
//    }
    public int totalLayers() {
        return layers.length;
    }
    public int layerSize(int layer) {
        return layers[layer].size();
    }
    public List<Graphic> getLayer(int layer) {
        return layers[layer];
    }
    public int overallSize() {
        int total = 0;
        for (ArrayList e : layers) {
            total += e.size();
        }
        return total;
    }

    public Graphic get(int i, int layer) {
        return (Graphic) layers[layer].get(i);
    }
    public void clear() {
        for (ArrayList e : layers) {
            e.clear();;
        }
    }
}
