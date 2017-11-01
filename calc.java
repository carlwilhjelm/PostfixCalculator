import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;


/**
*PURPOSE:   Create a calculator from scratch which performs operations on valid equations using postfix notation.
*           This program primarily uses switches and if then statements to execute the conversion of the equation
*           into its postfix form from prefix and then iterates using many conditional statements to obtain a final
*           value. Also allows user to include one variable in the equation, but this must be "X".
*
*ALGORITHMS:   Algorithms for this program primarily depend on arrays and stacks. A helper class "Objects" has been
*              created to help in the operations of the class according to java perscriptions and prohibitions for
*              overloading the operators. In many instances for loops are used to iterate over the values of the Arrays.
*
*DATA STRUCTURES: This program primarily depends on the Array and the Stack. In order to complete the postfix calculations
*                 both are required and must be used in tandem. Particularly in satisfying the requirements of doing the 
*                 calculations true to postfix notation.
*
*HOW TO USE THE PROGRAM:   The program comes with a driver class calcDriver which provides the user with a prompt to
*                          input the desired equation. This is shown back to the user and then they are promped to input
*                          a value for a variable if necessary. Any errors in the input should be recognized and messages
*                          describing the errors will be provided to the user. 
*
*/

/**
*Postcondition: this class provides all the logic to performing the work described in the description
*                 it provides and calculates a postfix notation equation, given a prefix notation and
*                 describes errors where they exist. 
*/
public class calc {
   Scanner scan;
   Stack<String> operators; //operator stack
   //Stack<Integer> operands; //operand stack
   Operator ops; //mostly pointless helper class
   
   /**
   *Postcondition: constructor initializes a new instance of the scanner class and operator class
   */
   public calc(){
      scan = new Scanner(System.in);
      operators = new Stack<>();
      ops = new Operator();
   }
   
   /**
   *Precondition: isValidInfix requires a String[] as a parameter which is expected to be proved by the
   *              following method, toArray()
   *Postcondition: provides a boolean result for valid or not and messages to user when invalid    
   */
   public boolean isValidInfix(String[] eq){
   //Assumes eq has already been run through toArray()
      int rPar = 0;
      int lPar = 0;
      int intCount = 0;
      int opCount = 0;
      //String[] eq = toArray(eqIn);
               
      for (int i = 0; i < eq.length; i++) {
      
         //check if float
         if (eq[i].equals(".")){
            System.out.println("Error in expression!! Cannot accept floating point numbers.");
            return false;
         }
      
         //check if an int or operator or variable x
         if (!eq[i].matches("\\d+") && !ops.is(eq[i]) && !eq[i].equals("x")) {
            System.out.println("Error in expression!! Invalid argument.");
            return false;
         }
         
         //if int increment ints
         if (eq[i].matches("\\d+") || eq[i].equals("x")) {
            intCount++;
         }
         
         //if op increment ops
         if (ops.notParenth(eq[i])) {
            opCount++;
         }
         
         //increment if left or right parentheses
         else if (eq[i].equals(")")) {
            rPar++;
            //if right parenth is preceeded by an operator 
            //or does  not come after a left parenth return false 
            if(i == 0 || ops.notParenth(eq[i-1]) || rPar > lPar) {
               System.out.println("Error in expression!! No matching left parentheses for a right parentheses.");
               return false;
            }
         }
         else if (eq[i].equals("(")) {
            lPar++;
         }
         //after first index check if two operators in a row with exception for right and left parenth
         else if (i > 0 && ops.notParenth(eq[i]) && (ops.notParenth(eq[i-1]))){
            System.out.println("Error in expression!!"
                              +" The " + eq[i] + " operator cannot be preceeded by a "
                              + eq[i-1] + " operator.");
            return false;
         }
         
         else if (i>0 && ops.notParenth(eq[i]) && eq[i-1].equals(")")){
            System.out.println("Error in expression!! No operator between operand and left parentheses.");
            return false;
         }
         
         //after first index check if two integers in a row
         else if (i > 0 && eq[i].matches("\\d+") && 
                  (eq[i-1].matches("\\d+") || eq[i-1].equals(")"))) {
            System.out.println("Error in expression!! No operator between operands.");
         
            return false;
         }
         
      }
      
      return rPar == lPar && intCount > 1 && opCount > 0 && opCount < intCount;
   }
   
   /**
   *Precondition: to array requires a String as a parameter which is expected to be proved by the
   *              user and may or may not have errors
   *Postcondition: provides a string[] result   
   */
   public String[] toArray(String origIn){
      String[] result = new String[origIn.length()];
      int resultIndex = 0;
      String dbl = "";
      char[] orig = origIn.toCharArray();
      for (int i = 0; i < origIn.length(); i++) {
      
         //if char is a digit append characters to the number 
         if (Character.isDigit(orig[i])){
            dbl += orig[i] + "";
         }
         
         //when a space is found, add the number preceeding it
         else if (orig[i] == ' ') {
            if (!dbl.isEmpty()) {
               result[resultIndex] = dbl;
               dbl = "";
               resultIndex++;
            }
         }
         
         //when an operator is found add the preceeding number and the operator to the string array
         else if (ops.is(orig[i])) {
            if (!dbl.isEmpty()) {
               result[resultIndex] = dbl;
               dbl = "";
               resultIndex++;
            }
            result[resultIndex] = orig[i] + "";
            resultIndex++;
            
         }
         // if anything else is included add it as a separate string
         else {
            if (!dbl.isEmpty()) {
               result[resultIndex] = dbl;
               dbl = "";
               resultIndex++;
            }
            result[resultIndex] = orig[i] + "";
            resultIndex++;
         }
         
      }
      //add last integer
      if (!dbl.isEmpty()) {
         result[resultIndex] = dbl;
         resultIndex++;
      }
      result = Arrays.copyOf(result, resultIndex);
      return result;
   }
   
   /**
   *Precondition: infixToPost requires a String[] as a parameter which is expected to be proved by the
   *              toArray() method and is expected to be valid
   *Postcondition: provides a string[] result for an equation in postfix notation   
   */
   public String[] infixToPost(String[] infixArrayIn) {
      //Assumes valid infixArrayIn
      String[] result = new String[infixArrayIn.length];
      int resultIndex = 0;
      for(String str: infixArrayIn) {
      
      //if the current string is a digit
         if (str.matches("\\d+") || str.equals("x")) {
            result[resultIndex] = str;
            resultIndex++;
         }
         //if the current string is an operator
         else if (ops.notParenth(str)) {
            while (!operators.isEmpty() && ops.preceeds(str, operators.peek())) {
               if (operators.peek().equals("(")){
                  break;
               }
               result[resultIndex] = operators.pop();
               resultIndex++;
            }
            operators.push(str);
         }
         
         else if (ops.isParenth(str)) {
            if (str.equals(")")) {
               while (!operators.peek().equals("(")) {
                  result[resultIndex] = operators.pop();
                  resultIndex++;
               }
               operators.pop();
            }
            else {
               operators.push(str);
            }
         }
         
         /*
            
            //if the stack is not empty
            //while the current operator is less than or equal to the one on the stack 
            //pop the element on the stack
         while (!operators.isEmpty() && ops.preceeds(str, operators.peek())){
            if (operators.peek().equals("(")){
               break;
            }
            result[resultIndex] = operators.pop() + "";
            resultIndex++;
         }
         if (!str.equals(")")) {
            operators.add(str);
         }
          */  
         
      }
      while (!operators.isEmpty()) {
         result[resultIndex] = operators.pop() + "";
         resultIndex++;
      }
      result = Arrays.copyOf(result, resultIndex);
      return result;      }
   
   /**
   *Postcondition:   provides boolean if variable X exists
   */
   public boolean hasVar(String eq) {
      if (eq.contains("x")) {
         return true;
      }
      return false;
   }

   /**
   *Precondition: postfixCalc requires a String[] as a parameter which is expected to be proved by the
   *              infixToPost() method 
   *Postcondition: provides an int result for an equation in postfix notation   
   */
   public int postfixCalc(String[] postfixIn) {
      Stack<Integer> operands = new Stack<>();
      int result;
      String[] postfixOut = Arrays.copyOf(postfixIn, postfixIn.length -1);
     
      for (String str: postfixIn) { 
         if (str.matches("\\d+")) {
            operands.push(Integer.parseInt(str));
         }
         else {
            char ch = str.charAt(0);
            switch (ch){
               case('+'):
                  operands.push(ops.addition(operands.pop(), operands.pop()));
                  break;
            
               case('-'):
                  operands.push(ops.subtraction(operands.pop(), operands.pop()));
                  break;
               case('*'):
                  operands.push(ops.multiplication(operands.pop(), operands.pop()));
                  break;
            
               case('/'):
                  operands.push(ops.division(operands.pop(), operands.pop()));
                  break; 
               case('%'):
                  operands.push(ops.modulo(operands.pop(), operands.pop()));
                  break;        
            }
         }
      }
      return operands.pop();
   }
   
   /**
   *Postcondition: provides an string result for any String[] in this class  
   */
   public String toString(String[] array){
      String result = "";
   
      for (String str: array){
         result += str + " ";
      }
   
      return result;
   }
}