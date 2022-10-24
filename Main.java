import java.io.*;
import java.util.Scanner;
import java.util.Random;
public class Main {
static Stack UnOrderedCountries;
static Stack OrderedCountries;
static Stack PlayersAndScores;
static Stack Players;
static Stack Scores;
static Stack OrderedPlayers;
static Stack OrderedScores;
static int Score=0;
static int Step=1;
static boolean IsGameOver=false;
static Stack Letters = new Stack(26);

static CircularQueue QuestionAndScoreCalculation(CircularQueue QuestionQueue,String Guess,String Wheel,String SelectedCountry)
{
	int Counter=0;
	CircularQueue SelectedCountryChars = new CircularQueue(SelectedCountry.length());
	CircularQueue tempQueue = new CircularQueue(SelectedCountry.length());
	//Stacking selected word's chars.
	for (int index = 0; index <SelectedCountry.length() ; index++) {
		SelectedCountryChars.enqueue(SelectedCountry.charAt(index));
		
	}
	//Filling our stack which seen in the screen with guesses.
	for (int index = 0; index < SelectedCountry.length(); index++) {
		String Char = String.valueOf(SelectedCountryChars.dequeue());
		if(Guess.equalsIgnoreCase(Char))
		{
			Counter++;
			for (int index2 = 0; index2 < SelectedCountry.length(); index2++) {
				tempQueue.enqueue(QuestionQueue.dequeue());
			}
			for (int index2 = 0; index2 < index; index2++) {
				
				QuestionQueue.enqueue(tempQueue.dequeue());
				
			}
			tempQueue.dequeue();
			QuestionQueue.enqueue(Char);
			for (int index2 = 0; index2 < SelectedCountry.length()-index-1; index2++) {
				
				QuestionQueue.enqueue(tempQueue.dequeue());
				
			}



		}
		SelectedCountryChars.enqueue(Char);
	}
    boolean flag=false;
	//Controlling "Is Game Over?"
	for (int index = 0; index < SelectedCountry.length(); index++) {
		String Empty = String.valueOf(QuestionQueue.dequeue());
		QuestionQueue.enqueue(Empty);
		if(Empty.equals("-"))
		{
			flag=true;
		}
	}
	if(!flag)
	IsGameOver=true;
	//Calculating new score.
	if(Wheel.equals("10"))
	Score += Integer.parseInt(Wheel)*Counter;
	else if(Wheel.equals("50"))
	Score += Integer.parseInt(Wheel)*Counter;
	else if(Wheel.equals("100"))
	Score += Integer.parseInt(Wheel)*Counter;
	else if(Wheel.equals("250"))
	Score += Integer.parseInt(Wheel)*Counter;
	else if(Wheel.equals("500"))
	Score += Integer.parseInt(Wheel)*Counter;
	else if(Wheel.equals("1000"))
	Score += Integer.parseInt(Wheel)*Counter;
	else if(Wheel.equals("Double Money"))
	{
       if(Counter>=1)
	   Score *=2;
	}
	else{
	Score = 0;
	}

    return QuestionQueue;
}
static String Wheel()

	
	
{
	//Spinning the wheel and returning the coming value.
	int Wheel =RandomNumber(8);
	if(Wheel==0)
	return "10";
	else if(Wheel==1)
	return "50";
	else if(Wheel==2)
	return "100";
	else if(Wheel==3)
	return "250";
	else if(Wheel==4)
	return "500";
	else if(Wheel==5)
	return "1000";
	else if(Wheel==6)
	return "Double Money";
	else
	return "Bankrupt";
	
	


	
	
}
static void Letters()
{   //Pushing letters to the our letter stack.
	for (int index = 90; index > 64; index--) {
		Letters.push((char)index);		
	}
}
static Stack Sort(Stack UnSortedStack)
{
	//Sorting string stacks and returning sorted stack.
 Stack OrderedStack= new Stack(UnSortedStack.size());
 while (!UnSortedStack.isEmpty())
    {

        String temp = String.valueOf(UnSortedStack.peek());
        UnSortedStack.pop();

        while (!OrderedStack.isEmpty() && String.valueOf(OrderedStack.peek()).compareTo(temp)<0)
        {

            UnSortedStack.push(OrderedStack.peek());
            OrderedStack.pop();
        }
 
        OrderedStack.push(temp);
    }
	return OrderedStack;

}
static Stack Reading(String txt)

{
	//Reading files,putting and returning them in the stack.
  int counter=0;
	try 
	{      
	File file = new File(txt);
	Scanner count=new Scanner(file);
	while(count.hasNextLine())
	{
    String line=count.nextLine();
	counter++;
	}
  int size=counter;
  counter=0;
  Stack ReadedStack = new Stack(size);
  count.close();
  Scanner push =new Scanner(file);
  while(push.hasNextLine())
	{
	String line=push.nextLine();
	ReadedStack.push(line);
	}
	push.close();
	
	return ReadedStack;
	} catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
       
	      e.printStackTrace();
		  return null;
	    }
	
}
static void SplitAndPlacementForHighScore(Stack PlayersAndScores,int Score)
{
	//Splitting highscore table and putting them to right stacks.
	int size =PlayersAndScores.size();
	Players = new Stack(size+1);
	Scores = new Stack(size+1);
	OrderedPlayers = new Stack(size+1);
	OrderedScores = new Stack(size+1);
	Players.push("You");
	Scores.push(Score);
 for (int index = 0; index < size; index++) {
	 String [] Splitting =String.valueOf(PlayersAndScores.pop()).split(" ");
	 
	 Players.push(Splitting[0]);
	 Scores.push(Splitting[1]);
 }
     NumberSort();
}
static void NumberSort()
{
	//Sorting scores and players,and pushing them to their stacks.
	while (!Scores.isEmpty())
	   {
		   
		   int tempscore = Integer.parseInt(Scores.peek().toString());
		   String tempplayer = Players.peek().toString();
		   Scores.pop();
		   Players.pop();
		   while (!OrderedScores.isEmpty() && Integer.parseInt(OrderedScores.peek().toString())>tempscore)
		   {
               
			   Scores.push(OrderedScores.peek());
			   Players.push(OrderedPlayers.peek());
			   OrderedScores.pop();
			   OrderedPlayers.pop();
		   }
	
		   OrderedScores.push(tempscore);
		   OrderedPlayers.push(tempplayer);
		
	   }
}
static int RandomNumber(int range)

{
	// Create random number and return it.
	Random Random = new Random();
	return Random.nextInt(range);

}
static void Competition()
{
	//Choosing a random country.
	int RandomNumber =RandomNumber(OrderedCountries.size());
	
	Stack SearchArray = new Stack(OrderedCountries.size());
	String SelectedCountry;
	for (int index = 0; index < RandomNumber+1; index++) {
		SearchArray.push(OrderedCountries.pop());
	}
	SelectedCountry=String.valueOf(OrderedCountries.pop());
	for (int index = 0; index < RandomNumber; index++) {
		OrderedCountries.push(SearchArray.pop());
	}
	CircularQueue QuestionQueue = new CircularQueue(SelectedCountry.length());
	
	//Filling our stack which seen in the screen with "-".
	for (int index = 0; index < SelectedCountry.length(); index++) {
		QuestionQueue.enqueue("-");
	}
	while(true){
	System.out.println("Randomly generated number: "+RandomNumber);
	System.out.println(SelectedCountry);
	System.out.print("Word:  ");
	for (int index = 0; index < SelectedCountry.length(); index++) {
		System.out.print(QuestionQueue.peek()+" ");
		QuestionQueue.enqueue(QuestionQueue.dequeue());
		
	}
	System.out.print("          Step:   "+Step +"          Score:   "+Score+"          ");
	Step++;
	int size =Letters.size();
	Stack TempLetters = new Stack(size);
	//Choosing random guess.
	int GuessIndex=0;
	if(size>0){
	GuessIndex = RandomNumber(size);
	}
	String Guess="";
	
	for (int index = 0; index < size; index++) {
		System.out.print(Letters.peek());
		TempLetters.push(Letters.pop());	
	}
	for (int index = 0; index < size; index++) {
		if(GuessIndex==index)
		Guess= String.valueOf(TempLetters.pop());
		else
		Letters.push(TempLetters.pop());	
	
}
    //Calling method to spin wheel.
	String Wheel = Wheel();
	try{Thread.sleep(1000);}
	catch(InterruptedException e)
	{System.out.println(e);} 
	
	CircularQueue tempQueue = new CircularQueue(QuestionQueue.size());
	while(!tempQueue.isEmpty()){
		QuestionQueue.enqueue(tempQueue.dequeue());
	   }
	   if(IsGameOver){
	   System.out.println("");
	   System.out.println("");
	   break;
	   }
	   //Calling our method to comparing guess with stack and calculating points.
	   QuestionQueue=QuestionAndScoreCalculation(QuestionQueue, Guess, Wheel, SelectedCountry);
	   System.out.println("");
	   System.out.println("Wheel:  "+Wheel);
	   System.out.println("Guess:  "+Guess);
	   System.out.println("");
	
}

}

    public static void main(String[] args) {
	  Letters();
      UnOrderedCountries=Reading("Countries.txt");
	  OrderedCountries=Sort(UnOrderedCountries);
	  PlayersAndScores = Reading("HighScoreTable.txt");
	  Competition();	  
	  SplitAndPlacementForHighScore(PlayersAndScores,Score);
	  //Writing highscore table.
	  System.out.println("You Win $"+Score+" !!!");
	  System.out.println();
	  System.out.println("Highscore Table");
	  System.out.println();
	  for (int index = 0; index < 10 ; index++) {
		  System.out.print(OrderedPlayers.pop()+" "+OrderedScores.pop());
		  System.out.println();
	  }
	 
	  
	

      
      
      
    }
  }