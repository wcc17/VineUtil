package com.wcc17;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import javax.swing.*;
import java.util.List;

public class Main {

    static Window window;

    public static void main(String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window = new Window();
            }
        });

//        List<Vine> vines = VineService.parseVineList();
//        VineService.downloadVines(vines);
    }
}
