import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import java.util.*;


    public class CalculatorModel {
        private ArrayList<String> current;
        private ScriptEngine solver;
        private int size;

        public CalculatorModel()
        {
            ScriptEngineManager mgr = new ScriptEngineManager();
            solver = mgr.getEngineByName("JavaScript");
            current = new ArrayList<String>();
            size = 0;
        }

        public void negative() {
            current.add("(-1)*");
            size++;
        }

        public void delete()
        {
            if (current.size() > 0) {
                String temp = current.get(size-1);
                if (isNum(temp)) {
                    if (temp.length()>1) {
                        current.set(size-1, temp.substring(0,temp.length()-1));
                    } else {
                        current.remove(size-1);
                        size--;
                    }
                }
                else if (temp.charAt(0)==',') {
                    current.remove(size-1);
                    int i = 2;
                    while (!current.get(size-i).equals("Math.pow(")) {
                        i++;
                    }
                    current.remove(size-i);
                    size-=2;
                }

                else {
                    current.remove(size-1);
                    size--;
                }
            }
        }


        public String[] performAction(String action) {
            String[] finalEquation = new String[2];

            if (action.equals("Enter")) {
                return this.evaluate();
            }

            else if (action.equals("+")) {
                this.plus();
            }
            else if (action.equals("-")) {
                this.minus();
            }
            else if (action.equals("*")) {
                this.times();
            }
            else if (action.equals("/")) {
                this.divide();
            }
            else if (action.equals("(-)")) {
                this.negative();
            }

            else if (action.equals("(")) {
                this.openParen();
            }
            else if (action.equals(")")) {
                this.closeParen();
            }

            else if (action.equals("sin()")) {
                this.sin();
            }
            else if (action.equals("cos()")) {
                this.cos();
            }
            else if (action.equals("tan()")) {
                this.tan();
            }
            else if (action.equals("pi")) {
                this.pi();
            }

            else if (action.equals("ln()")) {
                this.ln();
            }
            else if (action.equals("e")) {
                this.e();
            }

            else if (action.equals("x^2")) {
                this.square();
            }
            else if (action.equals("sqrt")) {
                this.sqrt();
            }
            else if (action.equals("^")) {
                this.power();
            }

            else if (action.equals("Delete")) {
                this.delete();
            }
            else if (action.equals("Clear")) {
                this.clear();
            }
            else if (action.equals("<html>"+"Clear"+"<br>"+"All"+"<html>")) {
                this.clear();
            }
            else if (action.equals("<html>"+"Clear"+"<br>"+"Graph"+"<html>")) {
                this.clear();
            }
            else if (action.equals(".")) {
                this.decimal();
            }
            else if (action.matches("[0-9]")) {
                this.number(action);
            }
            else if (action.matches("x")) {
                this.number(action);
            }

            finalEquation[0]=equationToNiceForm(this.copyEquation());
            return finalEquation;
        }


        public void number(String num) {
            if (size>0) {
                String temp = current.get(size-1);
                if (isNum(temp)) {
                    current.set(size-1, temp+num);
                } else {
                    current.add(num);
                    size++;
                }
            } else {
                current.add(num);
                size++;
            }
        }

        public void decimal() {
            if (size>0) {
                String temp = current.get(size-1);
                if (isNum(temp)) {
                    current.set(size-1, temp+".");
                } else {
                    current.add(".");
                    size++;
                }
            } else {
                current.add(".");
                size++;
            }
        }

        public void plus() {
            current.add("+");
            size++;
        }
        public void minus() {
            current.add("-");
            size++;
        }
        public void times() {
            current.add("*");
            size++;
        }
        public void divide() {
            current.add("/");
            size++;
        }

        public void sin() {
            current.add("Math.sin(");
            size++;
        }
        public void cos() {
            current.add("Math.cos(");
            size++;
        }
        public void tan() {
            current.add("Math.tan(");
            size++;
        }
        public void pi() {
            current.add("Math.PI");
            size++;
        }

        public void ln() {
            current.add("Math.log(");
            size++;
        }
        public void e() {
            current.add("Math.E");
            size++;
        }

        public void x() {
            current.add("x");
            size++;
        }

        public void sqrt() {
            current.add("Math.sqrt(");
            size++;
        }

        public void square()
        {
            if (size>0)
            {
                String temp = current.get(size-1);
                Stack<String> tempStack = new Stack<String>();
                if (isNum(temp) || temp.equals("x")) {
                    current.add(size-1, "Math.pow(");
                    current.add(",2)");
                    size+=2;
                } else if (temp.equals(")")) {
                    tempStack.push(")");
                    int i = 2;
                    while (!tempStack.empty()) {
                        String temp2 = current.get(size-i);
                        if (temp2.equals(")")) {
                            tempStack.push(")");
                        } else if (temp2.equals("(")) {
                            tempStack.pop();
                        } else if (temp2.matches("Math.+[(]")) {
                            tempStack.pop();
                        }
                        i ++;
                    }
                    current.add(size-i, "Math.pow(");
                    current.add(",2)");
                    size+=2;
                } else if (temp.matches("Math.(E|(PI))")) {
                    current.add(size-1, "Math.pow(");
                    current.add(",2)");
                    size+=2;
                }
            }
        }

        public void power() {
            if (size>0) {
                String temp = current.get(size-1);
                Stack<String> tempStack = new Stack<String>();
                if (isNum(temp) || temp.equals("x")) {
                    current.add(size-1, "Math.pow(");
                    current.add(",");
                    size+=2;
                } else if (temp.equals(")")) {
                    tempStack.push(")");
                    int i = 2;
                    while (!tempStack.empty()) {
                        String temp2 = current.get(size-i);
                        if (temp2.equals(")")) {
                            tempStack.push(")");
                        } else if (temp2.equals("(")) {
                            tempStack.pop();
                        } else if (temp2.matches("Math.+[(]")) {
                            tempStack.pop();
                        }
                        i ++;
                    }
                    i--;
                    current.add(size-i, "Math.pow(");
                    current.add(",");
                    size+=2;
                } else if (temp.matches("Math.(E|(PI))")) {
                    current.add(size-1, "Math.pow(");
                    current.add(",");
                    size+=2;
                }
            }
        }

        public ArrayList<String> copyEquation() {
            return (ArrayList<String>) current.clone();
        }

        private String parenthesesChecker(String checkedEq) {
            String withParens = new String(checkedEq);
            Stack<String> parenStack = new Stack<String>();
            for(int i=0; i<checkedEq.length(); i++) {
                if (withParens.charAt(i) == '(') {
                    parenStack.push("off cliff");
                }
                if (withParens.charAt(i) == ')' && !parenStack.empty()) {
                    parenStack.pop();
                }
            }
            while (!parenStack.empty()) {
                withParens += ")";
                parenStack.pop();
            }
            return withParens;
        }

        public String[] evaluate() {
            String[] evaluatedEquation = new String[2];

            String displayableEquation = equationToNiceForm(copyEquation());
            evaluatedEquation[0] = displayableEquation;
            String readableEquation = javascriptEquation();
            String fixedParen = parenthesesChecker(readableEquation);
            String withRounding = "Math.round(1000000*(" + fixedParen + "))/1000000";
            String tempSolution = "";

            try {
                tempSolution = solver.eval(withRounding).toString();
            } catch (Exception e) {
                tempSolution = "Error";
            }
            evaluatedEquation[1] = tempSolution;

            current = new ArrayList<String>();
            size=0;
            return evaluatedEquation;
        }

        public String[] evaluateGraph() {
            String[] solutionArray = new String[600];
            String readableEquation = javascriptEquation();
            String fixedParen = parenthesesChecker(readableEquation);
            String scaledEquation = "30*(" + fixedParen + ")";

            for (int i =-300; i<300; i++) {
                double scaleFactor = i*1/30.0;

                String graphedEq = replaceX(scaledEquation, Double.toString(scaleFactor));
                String tempSolution = "";
                try {
                    tempSolution = solver.eval(graphedEq).toString();
                } catch (Exception e) {
                    tempSolution = "0";
                }
                solutionArray[i+300] = tempSolution;
            }

            current = new ArrayList<String>();
            size=0;
            return solutionArray;
        }


        private String replaceX(String equation, String num){
            String output = new String(equation);

            for (int i=0;i<output.length();i++){
                if (output.charAt(i) == 'x'){
                    String firstPart = output.substring(0, i);
                    String secondPart = output.substring(i+1);
                    output = "";
                    output = output.concat(firstPart);
                    output = output.concat(num);
                    output = output.concat(secondPart);
                }
            }
            return output;
        }

        private boolean isNum(String nm) {
            if (nm.matches("[0-9]+.?[0-9]*")) {
                return true;
            }
            return false;
        }

        private String javascriptEquation() {
            String currentEquation = "";
            for (int i = 0; i<size; i++) {
                if (i<size-1) {
                    if (isNum(current.get(i)) && current.get(i+1).matches("Math.+")) {
                        current.add(i+1, "*");
                        size++;
                    }
                }
                currentEquation += current.get(i);
            }
            return currentEquation;
        }

        private String equationToNiceForm(ArrayList<String> eq) {
            String currentEquation = "";
            for (int i = 0; i<eq.size(); i++) {
                if (i<eq.size()-1) {
                    if (isNum(eq.get(i)) && eq.get(i+1).matches("Math.+")) {
                        eq.add(i+1, "*");
                    }
                } if (eq.get(i).equals("Math.pow(")) {
                    eq.remove(i);
                } if (eq.get(i).matches("Math.+")) {
                    String replace = eq.get(i).substring(5);
                    eq.set(i, replace);
                } if (eq.get(i).equals(",")) {
                    eq.set(i, "^(");
                } if (eq.get(i).equals(",2)")) {
                    eq.set(i, "^2");
                } if (eq.get(i).equals("(-1)*")) {
                    eq.set(i, "(-)");
                }
                currentEquation += eq.get(i);
            }
            return currentEquation;
        }

        public void openParen() {
            current.add("(");
            size++;
        }
        public void closeParen() {
            current.add(")");
            size++;
        }

        public void clear() {
            current = new ArrayList<String>();
            size = 0;
        }

    }