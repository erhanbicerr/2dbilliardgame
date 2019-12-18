
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import javax.swing.JOptionPane;

import javax.swing.Timer;


/*
 * How are you today sir ?
 * Do you need anything?
 * 
 */

/**
 *
 * @author erhan
 */
public class Platform extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
    

   
    private double previousX, previousY;
    private double previous2X, previous2Y;
    private double previous3X, previous3Y;
    
    
    double Vx=2; double Vy=2;
    double Vx2=0; double Vy2=0;
    double Vx3=0; double Vy3=0;
    double V = 15;
    double Vf=.99;
    int diameter=40;
    float radius=20;
    int score1 = 0;
    int score2 = 0;
    int flag1,flag2 = 0;
    int wallc1,wallc2,wallc3,wallc4=0;
    boolean turn = true;
    
    
    

    public Platform(double previousX, double previousY, double previous2X, double previous2Y, double previous3X, double previous3Y, BufferedImage image, BufferedImage cue) {
        this.previousX = previousX;
        this.previousY = previousY;
        this.previous2X = previous2X;
        this.previous2Y = previous2Y;
        this.previous3X = previous3X;
        this.previous3Y = previous3Y;
        this.image = image;
        this.cue = cue;
    }

    
           
 
    Ellipse2D.Double ball = new Ellipse2D.Double(400,400,diameter,diameter);
    Ellipse2D.Double ball2 = new Ellipse2D.Double(300,300,diameter,diameter);
    Ellipse2D.Double ball3 = new Ellipse2D.Double(100,100,diameter,diameter);

    private BufferedImage image;
    private BufferedImage cue;
    
    
    Timer timer = new Timer(15, this);
    private int gecenSure = 0;
    
    
    
    
    
    
    public Platform(){
 
        addMouseListener(this);
        addKeyListener(this);
        
        try {
            image = ImageIO.read(new FileImageInputStream(new File("green.jpg")));
                 
        } catch (IOException ex) {
            Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cue = ImageIO.read(new FileImageInputStream(new File("cue.png")));
                 
        } catch (IOException ex) {
            Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
       
    }
    


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        gecenSure+=1;
        
        g.drawImage(image, 0, 0, getWidth(),getHeight(),this);
         
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fill(ball);
        g2d.setColor(Color.RED);
        g2d.fill(ball2);
        g2d.setColor(Color.YELLOW);
        g2d.fill(ball3);
        if(devam1(Vx, Vy, Vx2, Vy2, Vx3, Vy3)){
            timer.stop();
            turn=!turn;
        }
        if(turn){
        g2d.setColor(Color.YELLOW);
        String T = "PLAYER1'S TURN";
        
        g2d.drawString(T,330, 10);
        if(skorGuncelle(flag1, flag2, wallc1, wallc2, wallc3, wallc4, score1)){
         flag1=0;
         flag2=0;
         wallc1=0;
         wallc2=0;
         wallc3=0;
         wallc4=0;
         score1+=20;
        }
        
        }
        else{
        g2d.setColor(Color.YELLOW);
        String T = "PLAYER2'S TURN";
        
        g2d.drawString(T,330, 10);
         if(skorGuncelle(flag1, flag2, wallc1, wallc2, wallc3, wallc4, score2)){
         flag1=0;
         flag2=0;
         wallc1=0;
         wallc2=0;
         wallc3=0;
         wallc4=0;
         score2+=20;
        }
            
        }
        
        g2d.setColor(Color.RED);
        String info = "Score: "+score1;
        
        g2d.drawString(info,10, 10);
        
        g2d.setColor(Color.BLUE);
        String info2 = "Score: "+score2;
        
        g2d.drawString(info2,getWidth()-50, getHeight()-50);
        
        
        
        if(score1>=50){
            timer.stop();
            String message = "Birinci Oyuncu Kazandı\n"
                    +"Gecen sure: "+gecenSure/60.0+" sn"
                    +"Skor: "+score1;
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("scores.txt", true);
                
                String s = " Score: "+score1+" Gecen Sure: "+gecenSure/60.0+" sn";
                byte[] s_array = s.getBytes();
                
                fos.write(s_array);
                
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            finally{
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
        else if(score2>=50){
            timer.stop();
            String message = "İkinci Oyuncu Kazandı\n"
                    +"Gecen sure: "+gecenSure/60.0+" sn"
                    +"Skor: "+score2;
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("scores.txt", true);
                
                String s = " Score: "+score2+" Gecen Sure: "+gecenSure/60.0+" sn";
                byte[] s_array = s.getBytes();
                
                fos.write(s_array);
                
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            finally{
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
   
        
    }
public int getScore1() {
        return score1;
    }

public int getScore2() {
        return score2;
    }
    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public double getSpeed(double Vx, double Vy){
        return Vx*Vx + Vy*Vy;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        updateBallPosition();
    }
    
    public void updateBallPosition(){
        
        previousX = ball.x; previousY = ball.y;
        previous2X = ball2.x; previous2Y = ball2.y;
        previous3X = ball3.x; previous3Y = ball3.y;
        Vx *= 0.99;
        Vy *= 0.99;
        if(getSpeed(Vx, Vy)< 0.05){
             Vx = 0;
             Vy = 0;
         }
        Vx2 *= 0.99;
        Vy2 *= 0.99;
        if(getSpeed(Vx2, Vy2)< 0.05){
             Vx2 = 0;
             Vy2 = 0;
         }
        Vx3 *= 0.99;
        Vy3 *= 0.99;
        if(getSpeed(Vx3, Vy3)< 0.05){
             Vx3 = 0;
             Vy3 = 0;
         }
       
        ball.x+=Vx;
        ball.y+=Vy;
        ball2.x+=Vx2;
        ball2.y+=Vy2;
        ball3.x+=Vx3;
        ball3.y+=Vy3;
        
        

         
        if(ball3.x<0) Vx3=-Vx3;
        if(ball3.y<0) Vy3=-Vy3;
        if(ball2.x<0) Vx2=-Vx2;
        if(ball2.y<0) Vy2=-Vy2;
        if(ball.x<0){ Vx=-Vx; wallc1++;}
        if(ball.y<0) {Vy=-Vy;wallc2++;}
        
        if(ball3.x>getWidth()-diameter) Vx3=-Vx3;
        if(ball3.y>getHeight()-diameter) Vy3=-Vy3;
        if(ball2.x>getWidth()-diameter) Vx2=-Vx2;
        if(ball2.y>getHeight()-diameter) Vy2=-Vy2;
        if(ball.x>getWidth()-diameter) {Vx=-Vx;wallc3++;}
        if(ball.y>getHeight()-diameter){ Vy=-Vy;wallc3++;}
        
        

        
        if(kontrolEt(ball.x+radius, ball.y+radius, ball2.x+radius, ball2.y+radius, diameter)){
            ball.x=previousX;
            ball.y = previousY;
            ball2.x=previous2X;
            ball2.y = previous2Y;
            
            double dx = ball2.x - ball.x;
            double dy = ball2.y-ball.y;
            
            double dist = Math.sqrt(dx*dx+dy*dy);
            
            if(dist == 0){
                dx = 0;
                dy = 0;
            }else{
                dx /= dist;
                dy /= dist;
            }
            double scale = (dx*Vx+dy*Vy)-(dx*Vx2+dy*Vy2);
            Vx -= dx*scale;
            Vy -= dy*scale;
            Vx2 += dx*scale;
            Vy2 += dy*scale;
            repaint();
            flag1++;
            
        }
        
        if(kontrolEt(ball.x+radius, ball.y+radius, ball3.x+radius, ball3.y+radius, diameter)){
            ball.x=previousX;
            ball.y = previousY;
            ball3.x=previous3X;
            ball3.y = previous3Y;
            
            double dx = ball3.x - ball.x;
            double dy = ball3.y-ball.y;
            
            double dist = Math.sqrt(dx*dx+dy*dy);
            
            if(dist == 0){
                dx = 0;
                dy = 0;
            }else{
                dx /= dist;
                dy /= dist;
            }
            double scale = (dx*Vx+dy*Vy)-(dx*Vx3+dy*Vy3);
            Vx -= dx*scale;
            Vy -= dy*scale;
            Vx3 += dx*scale;
            Vy3 += dy*scale;
            repaint();  
            flag2++;
            
            
        }
         if(kontrolEt(ball2.x, ball2.y, ball3.x, ball3.y, diameter)){
            ball2.x=previous2X;
            ball2.y = previous2Y;
            ball3.x=previous3X;
            ball3.y = previous3Y;
            
            double dx = ball3.x - ball2.x;
            double dy = ball3.y-ball2.y;
            
            double dist = Math.sqrt(dx*dx+dy*dy);
            
            if(dist == 0){
                dx = 0;
                dy = 0;
            }else{
                dx /= dist;
                dy /= dist;
            }
            double scale = (dx*Vx2+dy*Vy2)-(dx*Vx3+dy*Vy3);
            Vx2 -= dx*scale;
            Vy2 -= dy*scale;
            Vx3 += dx*scale;
            Vy3 += dy*scale;
            repaint();   
            

        }

       repaint(); 
        
    }
    
    public boolean skorGuncelle(int f1,int f2, int f4, int f5, int f6,int f7,int s){
        if(f1>1 && f2>1  && (f4+f5+f6+f7)>3){
            return true;
        }
        return false;
    }
    
    @Override
    public void mouseClicked(MouseEvent e)  {
       
        
    }
    public boolean devam1(double x1, double y1, double x2, double y2, double x3,double y3){
        if(x2==0&&y2==0&&x3==0&&y3==0&&x1==0&&y1==0){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public boolean kontrolEt(double x1, double y1, double x2, double y2, float d){
        
        return Math.abs(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) <= Math.pow(d, 2);
        
    }
    
    public double kontrolEt2(double x1, double y1, double x2, double y2, float d){
        return Math.abs(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) - Math.pow(d, 2);
        
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        
           if(!timer.isRunning()){
   
        double mouseX=e.getX();
        double mouseY=e.getY();
        double x2 = ball.x+radius;
        double y2 = ball.y+radius;
        double dx = x2-mouseX;
        double dy = y2-mouseY;
        Vx = (V*dx/Math.sqrt(dx*dx+dy*dy));
        Vy = (V*dy/Math.sqrt(dx*dx+dy*dy));
        }
        
        }

        
    
        
    @Override
    public void mouseReleased(MouseEvent e) {
        timer.start();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
    
    @Override
    public void mouseDragged(MouseEvent arg0) {
    
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
       
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        int c = e.getKeyCode();
         
        if(c == KeyEvent.VK_M){
            FirstScene f = new FirstScene();
        }
        if(c == KeyEvent.VK_S){
            if(turn)score1+=20;
            else score2+=20;
        }
        if(c== KeyEvent.VK_V){
            V+=5;
        }
        if(c==KeyEvent.VK_F){
            if(turn)score1=50;
            else score2=50;
        }
         
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

   
    
    
}