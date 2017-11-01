import java.util.Arrays;
import java.util.Scanner;
/**
*Helper class takes operands as inputs as strings and chars and outputs various methods to help calc class.
*/

public class Operator {
   private static final char[] allOps = {'+','-','*','/','%','(',')'};
   private static final char[] notPar = {'+','-','*','/','%'};
   
   
   public Operator(){
   }
   
   /**
   *Postcondition: Takes a char as a parameter to return a bool value for if the input char is an operand
   */
   public boolean is(char chIn) {
      for (char ch: allOps) {
         if (chIn == ch){
            return true;
         }
      }
      return false;
   }
   
   /**
   *Postcondition: Takes a string as a param to return a bool for if the input string is an operand
   */
   public boolean is(String strIn) {
      for (char ch: allOps) {
         if (strIn.equals(ch + "")){
            return true;
         }
      }
      return false;
   }
   
   /**
   *Postcondition: takes two chars to determine precidence between two operators
   */
   public boolean preceeds(char a, char b) {
      int first = 0;
      int second = 0;
   
      switch (a) {
         case('+'):
            first = 1;
            break;
         case('-'):
            first = 1;
            break;
         case('*'):
            first = 2;
            break;
         case('/'):
            first = 2;
            break;
         case('%'):
            first = 2;
            break;
         case('('):
            first = 3;
            break;
         case(')'):
            first = 3;
            break;
         default:
            System.out.println("Problem with char a in class Operator");
      }
      switch (b) {
         case('+'):
            second = 1;
            break;
         case('-'):
            second = 1;
            break;
         case('*'):
            second = 2;
            break;
         case('/'):
            second = 2;
            break;
         case('%'):
            second = 2;
            break;
         case('('):
            second = 3;
            break;
         case(')'):
            second = 3;
            break;
         default:
            System.out.println("Problem with char b in class Operator");
      }
   
      return first <= second;
   }

   /**
   *Postcondition: takes two strings to determine precidence between two operators
   */
   public boolean preceeds(String a, String b) {
      int first = 0;
      int second = 0;
   
      switch (a.charAt(0)) {
         case('+'):
            first = 1;
            break;
         case('-'):
            first = 1;
            break;
         case('*'):
            first = 2;
            break;
         case('/'):
            first = 2;
            break;
         case('%'):
            first = 2;
            break;
         case('('):
            first = 3;
            break;
         case(')'):
            first = 3;
            break;
         default:
            System.out.println("Problem with char a in class Operator");
      }
      switch (b.charAt(0)) {
         case('+'):
            second = 1;
            break;
         case('-'):
            second = 1;
            break;
         case('*'):
            second = 2;
            break;
         case('/'):
            second = 2;
            break;
         case('%'):
            second = 2;
            break;
         case('('):
            second = 3;
            break;
         case(')'):
            second = 3;
            break;
         default:
            System.out.println("Problem with char b in class Operator");
      }
   
      return first <= second;
   }

   /**
   *Postcondition: takes a string to determine if value is a parentheses
   */
   public boolean isParenth(String x){
      if (x.equals(")") || x.equals("(")){
         return true;
      }
   
      return false;
   }
   
   /**
   *Postcondition: takes a char to determine if value is a parentheses
   */
   public boolean isParenth(char x) {
      if (x == ')' || x == '(') {
         return true;
      }
      return false;
   }
   
   /**
   *Postcondition: takes a string to determine if value is NOT a parentheses
   */
   public boolean notParenth(String str) {
   
      for (char op: notPar) {
         if (str.equals(op + "")) {
            return true;
         }
      }
      return false;
   }
   
   /**
   *Postcondition: takes a char to determine if value is NOT a parentheses
   */
   public boolean notParenth(char ch) {
   
      for (char op: notPar) {
         if (ch == op) {
            return true;
         }
      }
      return false;
   }


   /**
   *Precondition: takes two integer values to perform addition
   *Postcondition: returns the value as an integer
   */
   public int addition(int a, int b){
      return a + b;
   }
   
   /**
   *Precondition: takes two integer values to perform subtraction
   *Postcondition: returns the value as an integer
   */
   public int subtraction(int a, int b){
      return a - b;
   }
   
   /**
   *Precondition: takes two integer values to perform multiplication
   *Postcondition: returns the value as an integer
   */
   public int multiplication(int a, int b) {
      return a * b;
   }
   
   /**
   *Precondition: takes two integer values to perform division
   *Postcondition: returns the value as an integer
   */
   public int division(int a, int b) {
      return a / b;
   }
   
   /**
   *Precondition: takes two integer values to perform modulo
   *Postcondition: returns the value as an integer
   */
   public int modulo(int a, int b) {
      return a % b;
   }
   
   /**
   *Postcondition: main method runs through helper method in precise order to provide consistent output
   *                 consistent with desired results as stated above. 
   */
   public static void main(String[] args) {
      //edgeCases();
      calc C = new calc();
      Scanner scan = new Scanner(System.in);
      boolean quit = false;
      boolean hasVar = false;
      
      while(!quit){
         System.out.print("Enter infix expression: ");
         hasVar = false;
         String infix = scan.nextLine();
         String[] postfix = new String[0];
         int x;
         
         if (C.hasVar(infix)){
            hasVar = true;
         }
         
         String[] infixArr = C.toArray(infix);
      
         if (C.isValidInfix(infixArr)) {
            postfix = C.infixToPost(infixArr);
            System.out.println("Converted expression: " + C.toString(postfix));
         
         
            if (hasVar) {
               while(true){
                  System.out.print("\nEnter value of x: ");
                  String input = scan.next();
                  if (input.equals("q")){
                     quit = true;
                     break;
                  }
                  
                  String[] postfixTemp = Arrays.copyOf(postfix, postfix.length);               
                  for (int i = 0; i < postfix.length; i++){
                     if (postfixTemp[i].equals("x")){
                        postfixTemp[i] = input;
                     }
                  }
                  System.out.println("Answer to expression: " + C.postfixCalc(postfixTemp));
               }
            }
            
            else {
               System.out.println("\nAnswer to expression: " + C.postfixCalc(postfix) + "\n");
            }
         }
      }
   }

}