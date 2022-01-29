package com.wcc17;

import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.direct.*;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by wcc17 on 3/2/17.
 */
public class VLCPanel extends JPanel {

    private DirectMediaPlayerComponent mediaPlayerComponent;
    private BufferedImage image;

    //TODO: these need to change based on the video being played
    public static final int width = 480;
    public static final int height = 480;

    public VLCPanel() {
        setBackground(Color.BLACK);
        setOpaque(true);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

        initializeVideoSurfacePanel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, null, 0, 0);
    }

    public void initializeVideoSurfacePanel() {
        image = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleImage(width, height);

        BufferFormatCallback bufferFormatCallback = new BufferFormatCallback() {
            @Override
            public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
                return new RV32BufferFormat(width, height);
            }
        };

        mediaPlayerComponent = new DirectMediaPlayerComponent(bufferFormatCallback) {
            @Override
            protected RenderCallback onGetRenderCallback() {
                return new VineRenderCallbackAdapter();
            }
        };

        mediaPlayerComponent.getMediaPlayer().setRepeat(true);
    }

    private class VineRenderCallbackAdapter extends RenderCallbackAdapter {

        private VineRenderCallbackAdapter() {
            super(new int[width * height]);
        }

        @Override
        protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
            // Simply copy buffer to the image and repaint
            image.setRGB(0, 0, width, height, rgbBuffer, 0, width);
            repaint();
        }
    }

    public DirectMediaPlayerComponent getMediaPlayerComponent() {
        return mediaPlayerComponent;
    }

    public void setMediaPlayerComponent(DirectMediaPlayerComponent mediaPlayerComponent) {
        this.mediaPlayerComponent = mediaPlayerComponent;
    }
}
