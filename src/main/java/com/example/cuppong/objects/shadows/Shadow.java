package com.example.cuppong.objects.shadows;

import com.example.cuppong.CupPongMain;
import com.example.cuppong.objects.Sprite;
import com.example.cuppong.util.GV;
import com.example.cuppong.util.Vector2F;
import com.example.cuppong.util.Vector3F;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shadow {

    private int _width;
    private int _height;
    private Vector2F _pos;
    private Image _image;
    private Sprite _parent;

    private float xBase;
    private float yBase;

    private final double WINDOWH = (double)GV.getInstance().height();

    public Shadow(Sprite parent) {
        _pos = new Vector2F(0, 0);
        _parent = parent;
        _width=parent.getWidth();
        _height=parent.getHeight();
        loadImage("images/shadow.png");
        xBase = _parent.getPos().getX();
        yBase = _parent.getPos().getY();
    }

    public Shadow(Sprite parent, float xBase, float yBase) {
        _pos = new Vector2F(0, 0);
        _parent = parent;
        _width=parent.getWidth();
        _height=parent.getHeight();
        loadImage("images/shadow.png");
        this.xBase = xBase;
        this.yBase = yBase;
    }

    public Vector2F getPos() {
        return _pos;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    private void loadImage(String filename) {
        _image = new Image(CupPongMain.class.getResourceAsStream(filename));
    }

    public void update() {
        _width = newWidth();
        //System.out.println(_width);
        _height = newHeight();
        _pos.set(newPos());

        System.out.println("W: " + _width + ", H: " + _height + ", POS: " + _pos.toString());
    }

    public void render(GraphicsContext context){
        if (_pos.getY() + _height < GV.getInstance().height()) {
            context.drawImage(_image, (int)_pos.getX(), (int)_pos.getY(), _width,_height);
        }
    }

    public int newWidth() { //this is good for now
        double dw = (double)_parent.get_default_width();
        float parY = _parent.getPos().getY();
        return (int)(parY/WINDOWH * dw);
    }

    public int newHeight() {
        double h = _parent.get_default_height();
        double h2 = _parent.getHeight();
        return (int)((h/h2 * h/2));
    }

    private Vector2F newPos() {
        double margin = ((double)_parent.getWidth() - (double)_parent.get_default_width()) / 2d;
        double calcY = (yBase + (WINDOWH - _parent.getPos().getY()))+5;
        return new Vector2F((float)(xBase + margin), (float)calcY);
    }
}
