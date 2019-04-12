/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlescolar.view.components;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author hugoluna
 */
public class ImageProfile extends JPanel {

    private URL url = getClass().getResource("user.png");
    Image image = new ImageIcon(url).getImage();

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0,0, 100, 100, this);
        setOpaque(false);
        super.paint(g);
    }
    
}
