package apps;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Stack;

public class ExpressionEvaluator 
{
    
    private static final Pattern UNSIGNED_DOUBLE = Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][-+]?\\d+)?.*?");
    private static final Pattern CHARACTER = Pattern.compile("\\S.*?");
    private static Scanner scanner;

    public static String toPostfix (String expression)
    {
        Stack<String> stack = new Stack<String>();
        scanner = new Scanner(expression);
        String postfix = "";
        String temp;

        while (scanner.hasNext())
        {
            if (scanner.hasNext(UNSIGNED_DOUBLE))
            {
                postfix += scanner.findInLine(UNSIGNED_DOUBLE) + " ";
            }
            else
            {
                temp = scanner.findInLine(CHARACTER);
                switch (temp)
                {
                    case "(":
                    stack.push(temp);
                        break;
                    case ")":
                        while (!stack.peek().equals("("))
                        {
                            postfix += stack.pop() + " ";
                        }
                        stack.pop();
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        while (!stack.empty() 
                            && !stack.peek().equals("(")
                            && higherPrecedence(temp.charAt(0), stack.peek().charAt(0)))
                        {
                            postfix += stack.pop() + " ";
                        }
                        stack.push(temp);
                }
            }
        }
        while (!stack.empty())
        {
            postfix += stack.pop() + " ";
        }
        return postfix.trim();
    }


    public static double evaluate (String postfixExpression)
    {
        double left;
        double right;
        Stack<Double> stack = new Stack<Double>();
        String[] postfix = postfixExpression.split(" ");

        for (String s : postfix)
        {
            if (isNumeric(s))
            {
                stack.push(Double.parseDouble(s));
            }
            else
            {
                right = stack.pop();
                left = stack.pop();
                switch (s) {
                    case "+":
                        stack.push(left + right);
                        break;
                    case "-":
                    stack.push(left - right);
                        break;
                    case "*":
                        stack.push(left * right);
                        break;
                    case "/":
                        stack.push(left / right);
                        break;    
                }
            }
        }
        if (stack.size() > 1)
        {
            return Double.NaN;
        }
        return stack.pop();
    }

    private static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }

    private static boolean higherPrecedence (char current, char top)
    {
        return precedence(top) >= precedence(current);
    }

    private static int precedence (char eval)
    {
        switch (eval)
        {
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0;
        }
    }
}
