package com.project.workspace;

import com.project.enums.ImageProcessEnum;
import com.project.enums.RotationEnum;
import com.project.main.Paint;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.*;

/**
 *
 * @author Karol
 */
public class PaintMenu extends JMenuBar {

    JMenu file;
    //otwieranie, zapisywanie
    JMenuItem news;
    JMenuItem open;
    JMenuItem save;
    JMenuItem saveas;
    JMenuItem quit;

    JMenu edit;
    //cofnij, wróc
    JMenuItem undo;
    JMenuItem redo;

    JMenu view;
    //powiększenie, oddalenie, pełny ekran
    JMenu rotation;
    JMenuItem rotate90;
    JMenuItem rotate180;
    JMenuItem flipHorizontalyRotate90;
    JMenuItem flipVerticallyRotate90;
    JMenuItem horizontalRotation;
    JMenuItem verticalRotation;


    JMenu filters;
    //standardow filtry, konwolucyjne, balans kolorów
    JMenu standardFilters;
        JMenuItem sepia;
        JMenuItem gray;
        JMenuItem blackWhite;
        JMenuItem brightness;
    JMenu convolutionFilters;
        JMenuItem sharpen;
        JMenuItem sharpen_more;
        JMenuItem box;
        JMenuItem gaussian;
        JMenuItem laplace;

    JMenu tools;
        JMenuItem changeSize;

    JMenu help;
    JMenuItem helpPaint;

    private String fileName;

    public PaintMenu() {

        file = new JMenu("File");
        edit = new JMenu("Edit");
        view = new JMenu("View");
        tools = new JMenu("Tools");
        help = new JMenu("Help");

        news = new JMenuItem("New File");
        open = new JMenuItem("Open File");
        save = new JMenuItem("Save");
        saveas = new JMenuItem("Save as...");
        quit = new JMenuItem("Quit");

        ActionMenu actionMenu = new ActionMenu();
        quit.addActionListener(actionMenu);
        save.addActionListener(actionMenu);
        saveas.addActionListener(actionMenu);
        news.addActionListener(actionMenu);
        open.addActionListener(actionMenu);

        save.setEnabled(false);

        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");

        rotation = new JMenu("Rotation");

        horizontalRotation = new JMenuItem("Flip Horizontal");
        verticalRotation = new JMenuItem("Flip Vertical");
        rotate90 = new JMenuItem("Rotate 90°");
        rotate180 = new JMenuItem("Rotate 180°");
        flipHorizontalyRotate90 = new JMenuItem("Flip Horizontally and rotate 90°");
        flipVerticallyRotate90 = new JMenuItem("Flip Vertically and rotate 90°");

        filters = new JMenu("Filters");
        standardFilters = new JMenu("Standard filters");
        convolutionFilters = new JMenu("Convolution filters");

        sharpen = new JMenuItem("Sharpen");
        sharpen_more = new JMenuItem("Sharpen more");
        box = new JMenuItem("Box blur");
        gaussian = new JMenuItem("Gaussian");
        laplace = new JMenuItem("Laplace");

        sepia = new JMenuItem("Sepia");
        gray = new JMenuItem("Gray");
        blackWhite = new JMenuItem("BlackWhite");
        brightness = new JMenuItem("Brightness");
        
        changeSize = new JMenuItem("Change Size");

        file.add(news);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.addSeparator();
        file.add(quit);

        edit.add(undo);
        edit.add(redo);

        helpPaint = new JMenuItem("Help...");

        help.add(helpPaint);

        helpPaint.addActionListener(actionMenu);

        add(file);

        rotation.add(rotate90);
        rotation.add(rotate180);
        rotation.addSeparator();
        rotation.add(horizontalRotation);
        rotation.add(verticalRotation); 
        rotation.addSeparator();
        rotation.add(flipHorizontalyRotate90);
        rotation.add(flipVerticallyRotate90);
   
        tools.add(changeSize);
        changeSize.addActionListener(actionMenu);
        
        view.add(rotation);

        undo.addActionListener(actionMenu);
        redo.addActionListener(actionMenu);
        rotate90.addActionListener(actionMenu);
        rotate180.addActionListener(actionMenu);
        flipHorizontalyRotate90.addActionListener(actionMenu);
        flipVerticallyRotate90.addActionListener(actionMenu);
        verticalRotation.addActionListener(actionMenu);
        horizontalRotation.addActionListener(actionMenu);

        add(edit);
        add(view);
        add(view);
        add(filters);
        add(tools);
        add(help);

        convolutionFilters.add(sharpen);
        convolutionFilters.add(sharpen_more);
        convolutionFilters.add(box);
        convolutionFilters.add(gaussian);
        convolutionFilters.add(laplace);

        standardFilters.add(sepia);
        standardFilters.add(gray);
        standardFilters.add(blackWhite);
        standardFilters.add(brightness);

        filters.add(convolutionFilters);
        filters.add(standardFilters);

        sharpen.addActionListener(actionMenu);
        sharpen_more.addActionListener(actionMenu);
        box.addActionListener(actionMenu);
        gaussian.addActionListener(actionMenu);
        laplace.addActionListener(actionMenu);

        sepia.addActionListener(actionMenu);
        gray.addActionListener(actionMenu);
        blackWhite.addActionListener(actionMenu);
        brightness.addActionListener(actionMenu);
    }

    public class ActionMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == quit) {
                System.exit(0);
            }
            if (e.getSource() == news) {
                Paint.paintStart.NewDocument();
                save.setEnabled(false);
            }

            if (e.getSource() == save) {

                try {
                    BufferedImage image = Paint.paintStart.getBuffImage();
                    ImageIO.write(image, "BMP", new File(fileName));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Save file Exception " + ex.getMessage(), "Save exception", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            if (e.getSource() == saveas) {

                try {
                    fileName = Paint.paintStart.getSaveFileName();
                    if (fileName != null) {
                        BufferedImage image = Paint.paintStart.getBuffImage();
                        ImageIO.write(image, "BMP", new File(fileName));
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Save file Exception " + ex.getMessage(), "Save exception", JOptionPane.INFORMATION_MESSAGE);
                }

            }

            if (e.getSource() == open) {
                try {
                    File openFile = Paint.paintStart.getOpenFileName();
                    if (openFile != null) {
                        BufferedImage image = ImageIO.read(openFile);
                        if (image != null) {
                            int height = image.getHeight();
                            int width = image.getWidth();

                            Paint.paintStart.setSizeSurface(new Dimension(width, height));
                            Paint.paintStart.setBufferedImage(image);
                            System.out.print(openFile.getPath());
                            fileName=openFile.getPath();
                            save.setEnabled(true);
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Open file Exception " + ex.getMessage(), "Open exception", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if(e.getSource() ==horizontalRotation)
            {
                Paint.paintStart.rotationImage(RotationEnum.FLIPHORIZONTALLY);
            }
            if(e.getSource() ==verticalRotation)
            {
                Paint.paintStart.rotationImage(RotationEnum.FLIPVERTICALLY);
            }
            if (e.getSource() == rotate180) {
                Paint.paintStart.rotationImage(RotationEnum.BOTTOMRIGHT);
            }
            if (e.getSource() == rotate90) {
                Paint.paintStart.rotationImage(RotationEnum.RIGHTTOP);
            }
            if (e.getSource() == flipHorizontalyRotate90) {
                Paint.paintStart.rotationImage(RotationEnum.FLIPHORIZONATLLYROTATE90);
            }
              if (e.getSource() == flipVerticallyRotate90) {
                Paint.paintStart.rotationImage(RotationEnum.FLIPVERTICALLYROTATE90);
            }

            if (e.getSource() == undo) {
                Paint.paintStart.UndoOperation();
            }
            if (e.getSource() == redo) {
                Paint.paintStart.RedoOperation();
            }
            if (e.getSource() == helpPaint) {
                ImageIcon icon = new ImageIcon(this.getClass().getResource("/about.png"));
                JOptionPane.showMessageDialog(null, "\n"
                        + "Program created by Karol Kalaga.\nFor more information please write at kalagakarol@gmail.com", "About", JOptionPane.INFORMATION_MESSAGE, icon);
            }
            if (e.getSource() == sharpen) {
                Paint.paintStart.imageProcess(ImageProcessEnum.SHARPEN);
            }
            if (e.getSource() == sharpen_more) {
                Paint.paintStart.imageProcess(ImageProcessEnum.SHARPEN_MORE);
            }
            if (e.getSource() == box) {
                Paint.paintStart.imageProcess(ImageProcessEnum.BOX);
            }
            if (e.getSource() == gaussian) {
                Paint.paintStart.imageProcess(ImageProcessEnum.GAUSSIAN);
            }
            if (e.getSource() == laplace) {
                Paint.paintStart.imageProcess(ImageProcessEnum.LAPLACE);
            }
            if (e.getSource() == sepia) {
                Paint.paintStart.imageProcess(ImageProcessEnum.SEPIA);
            }
            if (e.getSource() == gray) {
                Paint.paintStart.imageProcess(ImageProcessEnum.GRAY);
            }
            if (e.getSource() == blackWhite) {
                Paint.paintStart.imageProcess(ImageProcessEnum.BLACKWHITE);
            }
            if (e.getSource() == brightness) {
                Paint.paintStart.imageProcess(ImageProcessEnum.BRIGHTNESS);
            }
            if(e.getSource() == changeSize){
                Paint.paintStart.changeImageSize();
            }
            
        }

    }

}
