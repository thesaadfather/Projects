import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
public class Enemy extends Character
{ 
  //timers used in move command
  private int timer=0;
  private int timer2=0;
  private int round = TowerDefense.roundGame; //sets round so we can increase hp
  private int hp= 45 + (round * (round+10));
  private int maxHP =45 + (round * (round+10));
  
  boolean isDead=false;
  boolean hit= false;
  //stores what picture is gonna be shown
  String pic;
  String pic2;
  private BufferedImage img = null;
  private BufferedImage img2 = null;
  
  public Enemy(String type)
  {
    //coordinates for beginning of track
    x=560;
    y=560;
    //reads the type parameter and changes the pic variable accordingly
    readType(type);
    //draws img
    try
    {
      img = ImageIO.read(new File(pic));
      img2= ImageIO.read(new File(pic2));
    } catch (IOException e)
    {
      System.out.println("No Image");
    }
    
  }
  
  public void move()
  {
    //moves once every 20 times this method is called
    timer++;
    
    if (timer%20==0)
    {
      if(hp<1)
      {
        isDead=true;
      }
      timer2++;
      if(timer2>79)
      {
        isDead=true;
        hit=true;
      }
      
      if(timer2>75&&timer2<80)
      {
        y=y-20;
      }
      if(timer2>62&&timer2<76)
      {
        x=x+20;
      }
      if(timer2>48&&timer2<63)
      {
        y=y-20;
      }
      if(timer2>36&&timer2<49)
      {
        x=x-20;
      }
      if(timer2>26&&timer2<37)
      {
        y=y+20;
      }
        
      if(timer2>12&&timer2<27)
      {
        x=x-20;
      }
      
      if(timer2<13)
      {
        y=y-20;
      }
   
    }
    
  }
  
//paint method  
  public void paint(Graphics2D g)
  {
    g.setColor(Color.RED); 
    g.fillRect(x,y,(int)(((hp*30)/maxHP)),5); 
    
    //enemies become upgraded past round 7
    if(TowerDefense.roundGame<7)
    {
    g.drawImage(img, x, y, null);
    }
    else
    {
      g.drawImage(img2, x, y, null);
    }
      
  }
  
  public void attack()
  {
  }
  
  public void getRound(int round)
  {
   this.round = round;
  }
  
  public void upgrade()
  {
  }
  
  public boolean getDead()
  {
    return isDead;
  }
  
  public boolean getHit()
  {
    return hit;
  }
  
  public int getX()
  {
    return x;
  }
  
  public int getY()
  {
    return y;
  }
  
  public static void hurt(Enemy a, Friendly b)
  {
    a.hp = (a.hp) - b.attackPower; //minus hp according to attacking characters attack power
  }
  
  public int getAttack()
  {
    return attackPower;
  }
  
  //reads type parameter and changes pic variable accordingly
  private void readType(String a)
  {
    if (a=="Swordsman")
    {
      pic="ESwordsman.png";
      pic2="Sprites\\ESwordsmanU.png";
      attackPower=50;
    }     
    if (a=="Archer")
    {
      pic="EArcher.png";
      pic2="Sprites\\EArcherU.png";
      attackPower=30;
    }  
    if (a=="Mage")
    {
      pic="EMage.png";
      pic2="Sprites\\EMageU.png";
      attackPower=30;
    }
    if (a=="Lancer")
    {
      pic="ELancer.png";
      pic2="Sprites\\ELancerU.png";
      attackPower=30;
    }
    if (a=="AxeWielder")
    {
      pic="EAxeWielder.png";
      pic2="Sprites\\EAxeWielderU.png";
      attackPower=30;
    }
    
  }
  
  
  
}
