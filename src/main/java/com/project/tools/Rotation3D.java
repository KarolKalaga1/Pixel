/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.tools;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author Robert
 */
public class Rotation3D extends JFrame {

    public Rotation3D(BufferedImage bi) throws HeadlessException {

        setLayout(new BorderLayout());
        setSize(500, 500);
        
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        add("Center", canvas);

        TransformGroup tg_content = getScene(bi);

        BranchGroup content = new BranchGroup();

        content.addChild(tg_content);
        content.compile();

        SimpleUniverse universe = new SimpleUniverse(canvas);
        //
        Transform3D move = lookTowardsOriginFrom(new Point3d(0, 0, -3));
        universe.getViewingPlatform().getViewPlatformTransform().setTransform(move);
        universe.addBranchGraph(content);

        setVisible(true);
    }

    public TransformGroup getScene(BufferedImage bi) {
        TransformGroup group = new TransformGroup();

        Appearance app = new Appearance();
        Texture tex = new TextureLoader(bi, this).getTexture();
        app.setTexture(tex);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        app.setTextureAttributes(texAttr);

        // Create textured cube and add it to the scene graph.
        Box textureCube = new Box(0.4f, 0.4f, 0.4f,
                Box.GENERATE_TEXTURE_COORDS, app);
        group.addChild(textureCube);

        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
                4000, 0, 0, 0, 0, 0);

        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
                group, yAxis, 0.0f, (float) Math.PI * 2.0f);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);
        rotator.setSchedulingBounds(bounds);
        group.addChild(rotator);

        return group;
    }

    public Transform3D lookTowardsOriginFrom(Point3d point) {
        Transform3D move = new Transform3D();
        Vector3d up = new Vector3d(point.x, point.y + 1, point.z);
        move.lookAt(point, new Point3d(1.0d, 0.0d, 0.0d), up);

        return move;
    }
}
