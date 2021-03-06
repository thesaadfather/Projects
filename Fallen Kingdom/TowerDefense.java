//main class

/*GAME CONTROL NOTES
 * Right click on sidebar to select which friendly you want (only 2 rn)
 * Left click anywhere on the field and the friendly will spawn there
 * Mage (First pic) costs 30 gold
 * Archer (second pic) costs 30 gold
 * AxeWielder (third pic) costs 100 gold
 * Knight (4th pic) coss 70 gold
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
public class TowerDefense extends JPanel
{
  //initializes an instance of some of the classes
  Map map= new Map();
  EnemySpawn q= new EnemySpawn();
  Castle castle= new Castle();
  TitleScreen title = new TitleScreen();
  
  //if this variable is true, you see the title screen
  boolean titleScreen = true;
  boolean instructions=false;
  
  //variables that dictate whether you can place units
  public static boolean characterMage = false;
  public static boolean characterArcher = false;
  public static boolean characterSwordsman = false;
  public static boolean characterAxeWielder = false;
  public static boolean characterLance = false;
  boolean buttonDown = false;
  
  //initializes starting gold, amount of enemies and round
  static public int gold = 100;
  private int enemyCounter = 20;
  public static int roundGame =0;
  
  //variable that allows the game to stop between rounds
  public static int fakeRound = 0;
  
  //amount of enemies spawned so far in the round
  static int amountSpawned;
  
  //images that are used in unit selection screen
  private BufferedImage img = null;
  private BufferedImage img2 = null;
  private BufferedImage img3 = null;
  private BufferedImage img4 = null;
  private BufferedImage img6= null;
  private BufferedImage img7= null;
  private BufferedImage img8= null;
  private BufferedImage img9= null;
  private BufferedImage img10= null;
  private BufferedImage img11= null;
  
  //creates a list of enemies and friendlies
  static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  static ArrayList<Friendly> friendlies = new ArrayList<Friendly>();
  static boolean upgrade= false;
  
  //timer used to spawn in enemies periodically
  int timer =0;
  
  //timer used to attack enemies periodically
  int attackTimer=0;
  
  //determines if a round is currently active
  boolean round=true;
  
  //single enemy used to simplify code later on
  Enemy enemy = new Enemy("Swordsman");
  
  //contructor 
  public TowerDefense()
  {
    //reads images for unit selection screen
    try
    {
      img2 = ImageIO.read(new File("Images\\MageProfile.png"));
      img3 = ImageIO.read(new File("Images\\ArcherProfile.png"));
      img4 = ImageIO.read(new File("Images\\SoldierProfile.png"));
      img6 = ImageIO.read(new File("Images\\FighterProfile.png"));
      img = ImageIO.read(new File("Images\\WarriorProfile.png"));
      img7 = ImageIO.read(new File("Images\\NoUpgrade.png"));
      img8 = ImageIO.read(new File("Images\\Upgrade.png"));
      img9 = ImageIO.read(new File("Images\\NoFight.png"));
      img10 = ImageIO.read(new File("Images\\Fight.png"));
      img11= ImageIO.read(new File("Images\\instructionscreen.png"));
    } catch (IOException e)
    {
      System.out.println("No Image");
    }
    
    
//handles mouse functionality     
    addMouseListener(new MouseAdapter() 
                       {
      public void mouseClicked(MouseEvent e) 
      {
        if (e.getButton() == MouseEvent.NOBUTTON) 
        {
          //textArea.setText("No button clicked...");
        } 
        else if (e.getButton() == MouseEvent.BUTTON1) 
        {
          if (titleScreen){//Check to see if TitleScreen or Game should be active
            if(instructions)
            {
              titleScreen=false;
            }
            
            if (e.getX() > 327 && e.getX() < 480 && e.getY() < 365 && e.getY() > 332)
            {
                 //Change co-ordinates of words PLAY
              instructions = true; //Change to false to load actual game if you change to true anywhere titlescreen will load again
            }
            
          }
        } 
        else if (e.getButton() == MouseEvent.BUTTON2) 
        {
          //middle click
        } 
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
          //Right click
        }
        //Uncomment these to find x and y co-ordinates
        System.out.println("Number of click: " + e.getClickCount());
        System.out.println("Click position (X, Y):  " + e.getX() + ", " + e.getY());
      }
      public void mousePressed(MouseEvent e)
      {
        buttonDown = true;
        q.mousePressed(e);
      }
      public void mouseReleased(MouseEvent e) 
      {
        buttonDown = false;
        q.mouseReleased(e);
      }
      
    }
    );
  }
  
  //method that spawns in enemies while a round is active
  public void addE()
  {
    //if you ahve spawned the limit for enemies this round, increase limit and add end of round gold
    if (amountSpawned == enemyCounter&&enemies.isEmpty())
    {
      amountSpawned = 0; //resets variable for use in next round
      enemyCounter += 10;
      gold += enemyCounter/2;
    }
    
    //pauses enemy spawning if characters are being placed, and while the round is over
    if (titleScreen == false && characterMage == false && characterArcher == false&&characterLance == false && characterAxeWielder == false&&characterSwordsman == false&& fakeRound%2!=0 ){
      
      if(round)
      {
        //timer used to place enemies at certain times
        timer++;
        
        //every 50 times this spawn method is called, one enemy spawns 
        if (timer%50==0)
        { 
          //randomn value dictates what type of enemy will spawn
          double value= Math.random();
          
          //5 different if statements for each type of unit, all equally likely to occur
          if(value<0.2)
          {
            //if the amount of enemies spawned has reached the limit for the round, end the round
            if (amountSpawned == enemyCounter)
            {
              //this adds 1 to the fake round variable.
              //enemies only spawn while this variable is even
              fakeRound+=1; 
              sendRound();
            }
            //else spawn an enemy
            else
            {
              enemies.add(new Enemy("Archer"));
              //adds one to the spawn count
              amountSpawned += 1;
            }
          }
          
          else if(value<0.4)
          {
            if (amountSpawned == enemyCounter)
            {
              fakeRound+=1;
              sendRound();
            }
            else
            {
              enemies.add(new Enemy("Swordsman"));
              amountSpawned += 1;
            }
          }
          
          else if(value<0.6)
          {
            if (amountSpawned == enemyCounter)
            {
              fakeRound+=1;
              sendRound();
            }
            else
            {
              enemies.add(new Enemy("Lancer"));
              amountSpawned += 1;
            }
          }
          
          else if(value<0.8)
          {
            if (amountSpawned == enemyCounter)
            {
              fakeRound+=1;
              sendRound();
            }
            else
            {
              enemies.add(new Enemy("AxeWielder"));
              amountSpawned += 1;
            }
          }
          
          else if(value<1)
          {
            if (amountSpawned == enemyCounter)
            {
              fakeRound+=1; 
              sendRound();
            }
            else
            {
              enemies.add(new Enemy("Mage"));
              amountSpawned += 1;
            }
          }
        }
      }
    }
  }
  
  
  //variable that checks each enemy unit too see if they have enough health to be alive
  //if not, they are removed from the array and user recieves 1 gold
  public void checkDead()
  {
    if(titleScreen == false && characterMage == false && characterArcher == false&&characterLance == false && characterAxeWielder == false&&characterSwordsman == false){
      for (int x=0; x<enemies.size(); x++)
      {
        if(enemies.get(x).getDead())
        {
          enemies.remove(x);
          gold += 1;
        }
      }
      
    }
  }
  
  //checks if the enemies are at the castle, and if they are the castle takes damage
  public void checkHit()
  {
    if (titleScreen == false && characterMage == false && characterArcher == false&&characterLance == false && characterAxeWielder == false&&characterSwordsman == false){
      for (int x=0; x<enemies.size(); x++)
      {
        if(enemies.get(x).getHit())
        {
          castle.hit(enemies.get(x).getAttack());
        }
      }
    }
  }
  
  //paint method
  public void paint(Graphics g)
  { 
    //paints things
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    
    //while titlescreen is true it paints the titlescreen
    if (titleScreen)
    {
      if(!instructions)
      {
      title.paint(g2d); //Paint title screen
      }
      else
      {
        g2d.drawImage(img11, 0,0,null);
      }
    }
    else
    { 
      
      //paints map, round and gold
      map.paint(g2d);
      
      //locks mage until round 5
      if(TowerDefense.roundGame<5&&Castle.health>0)
      {
        g.setColor(Color.BLACK);
        g.fillRect(645,147,150,69);
        g.setColor(Color.RED);
        g.setFont(new Font("Calibri", Font.PLAIN, 16));
        g.drawString("LOCKED UNTIL", 650, 175);
        g.drawString("ROUND 5", 650, 190);
      }
      
      g2d.setFont(new Font("Calibri", Font.PLAIN, 36)); 
      g2d.setColor(Color.RED);
      g2d.drawString("Round:", 645, 50);
      g2d.setColor(Color.GREEN);
      g2d.drawString(String.valueOf(roundGame), 755, 50);
      g2d.setColor(Color.RED);
      g2d.drawString("Gold:", 645, 100);
      g2d.setColor(Color.YELLOW);
      g2d.drawString(String.valueOf(gold), 735, 100);
      
      //paints all enemies
      for (int x=0; x<enemies.size(); x++)
      {
        enemies.get(x).paint(g2d);
      }
      
      
      //paints all friendlies
      for (int x=0; x<friendlies.size(); x++)
      {
        friendlies.get(x).paint(g2d);
      }
      
      //paints Enemyspawn
      q.paint(g2d);
      
      //mage unit seletion screen
      if (characterMage)
      { 
        g2d.drawImage(img2, 200,200,null);
      }
      
      //archer unit selection screen
      if (characterArcher)
      {
        g2d.drawImage(img3, 200,200,null);
      }
      
      //swordsman unit selection screen
      if (characterSwordsman)
      { 
        g2d.drawImage(img, 200,200,null);
      }
      //lancer unit selection screen
      if (characterLance)
      { 
        g2d.drawImage(img4, 200,200,null);
      }
      
      //Axe selection screen
      if (characterAxeWielder)
      { 
        g2d.drawImage(img6, 200,200,null);
      }
      
      //paints upgrade button
      if(upgrade)
      { 
        //picture if there isnt enough gold
        if(gold<100)
        {
          g2d.drawImage(img7,639,532,null);
        }
        else
        {
          g2d.drawImage(img8,639,532,null);
        } 
        
      }
      else
      {
        g2d.drawImage(img7,639,532,null);
      }
      
      if(fakeRound%2!=0)
      {
        g2d.drawImage(img9,720,532,null);
      }
      else
      {
        if(enemies.isEmpty())
        {
        g2d.drawImage(img10,720,532,null);
        }
        else
        {
          g2d.drawImage(img9,720,532,null);
        }
      }
      //paints castle healthbar
      castle.paint(g2d);
    }
    
    
    
  }
  
  //move method
  //only while user is not placing units
  public void move()
  {
    
    if (titleScreen == false && characterMage == false && characterArcher == false&&characterLance == false && characterAxeWielder == false&&characterSwordsman == false){
      //moves all enemies
      for (int x=0; x<enemies.size(); x++)
      {
        enemies.get(x).move();
      }
    }
  }
  
  //friendly atack methods
  public void attack()
  {
    if(titleScreen == false&& characterMage == false && characterArcher == false&&characterLance == false && characterAxeWielder == false&&characterSwordsman == false)
    {
      attackTimer++;
      int unitCounter=0;
      for(int y=0; y<friendlies.size(); y++)
      {
        for (int x=0; x<enemies.size(); x++)
        {
          if(Friendly.attack(enemies.get(x),friendlies.get(y)))
          {
            //the attack timer modular determines how often each unit attacks
            if(friendlies.get(y).pic=="Swordsman.png" &&attackTimer%50==0)
            {
              //attack the earliest spawned enemy within its range
              Enemy.hurt(enemies.get(x), friendlies.get(y));
              
              //enables attack animation
              friendlies.get(y).attackA=true;
              
              //ends for loop
              break;
            }
            if(friendlies.get(y).pic=="Lancer.png"&&attackTimer%75==0)
            {
              
              Enemy.hurt(enemies.get(x), friendlies.get(y));
              unitCounter++;
              friendlies.get(y).attackA=true;
              //allows it to attack 2 units at once
              if(unitCounter==2)
              {
                
                break;
              }
            }
            if(friendlies.get(y).pic=="Mage.png"&&attackTimer%75==0)
            {
              friendlies.get(y).attackA=true;
              Enemy.hurt(enemies.get(x), friendlies.get(y));
            }
            if(friendlies.get(y).pic=="AxeWielder.png" &&attackTimer%100==0)
            {
              Enemy.hurt(enemies.get(x), friendlies.get(y));
              friendlies.get(y).attackA=true;
              break;
            }
            if(friendlies.get(y).pic=="Archer.png" &&attackTimer%75==0)
            {
              Enemy.hurt(enemies.get(x), friendlies.get(y));
              friendlies.get(y).attackA=true;
              break;
            }
          }
        }
      }
    }  
  }
  
  public void sendGold()
  { 
//Sends gold amount to EnemySpawn class so it can check if we have enough gold to spawn a Friendly
    q.getGold(gold);
  }
  
  //Sends round to enemy to enemy HP can be increased
  public void sendRound()
  { 
    enemy.getRound(roundGame); 
  }
  
  public void addF()
  {
  }
  
  public void spawnF()
  {
    if (titleScreen == false && characterMage == false && characterArcher == false&&characterLance == false && characterAxeWielder == false&&characterSwordsman == false){
      if(q.getS())
      {
        friendlies.add(new Friendly(q.getType(),q.getA(),q.getB()));
        q.setS();
        
        if (q.getType() == "Mage"){
          //Minus 15 gold if knight is spawned
          gold -= 50; 
        }else if(q.getType() == "Archer"){
          gold -= 30;  
        }else if(q.getType() == "AxeWielder"){
          gold -= 60;  
        }else if(q.getType() == "Swordsman"){
          gold -= 60;  
        }
        else if(q.getType() == "Lancer"){
          gold -= 60;  
        }
        q.getAB(); //Sets co-ordinates back to click position
      }
    }
  }
  
  //method that starts and stops rounds
  public void round()
  {
    if (titleScreen == false && characterMage == false && characterArcher == false&&characterLance == false && characterAxeWielder == false&&characterSwordsman == false){
      if(q.getRound())
      {
        
        if(round)
        {
          round=false;
        }
        else 
          if(!round)
        {
          round = true;
        }
      }
      q.setRound();
    }
  }
  
  public static void main(String[] args)throws InterruptedException
  {
    JFrame frame = new JFrame("TowerDefense");
    TowerDefense ie = new TowerDefense();
    frame.add(ie);
    frame.setSize(818, 645);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ie.addF();
    while (true) 
    {
      ie.attack();
      ie.move();
      ie.checkHit();
      ie.checkDead();
      ie.addE();
      ie.repaint(); 
      ie.spawnF();
      ie.round();
      ie.sendGold();
      Thread.sleep(10); 
      
      
      
    }
  }
}