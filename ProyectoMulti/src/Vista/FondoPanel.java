/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Michael 
 */

/* este codigo tiene la finalidad de establecer un panel con la imagen de fondo especificada por la ruta 
--importante: se modifico en el MDI el codigo directamente del panel de fondo para hacerlo una instancia de 
esta calse FondoPanel

*/
public class FondoPanel extends JPanel{
    private Image image;

    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        image =new ImageIcon(getClass().getResource("/IMG/fondo.jpeg")).getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);  
        setOpaque(false);
        super.paint(g); 
    }
    
}
