
package com.project.main;

/**
 *
 * @author Karol
 */
public class Paint {
   
    public static PaintStart paintStart;
    
    public static void main(String[] args){
            
        try {
            Thread.sleep(2000);
            paintStart = new PaintStart();
        } catch (InterruptedException ex) {
        }
    }
}
