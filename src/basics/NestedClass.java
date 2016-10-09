package basics;

public class NestedClass {
	
	String s;
	
	class InnerClass {
		void testMethod() {
			s = "Set from InnerClass";
		}
	}
	/*
	 * As with class methods and variables, a static nested class is associated with its outer class. 
	 * And like static class methods, a static nested class cannot refer directly to instance variables
	 * or methods defined in its enclosing class: it can use them only through an object reference.
	 * 
	 * Note: A static nested class interacts with the instance members of its outer class (and other classes)
	 * just like any other top-level class. In effect, a static nested class is behaviorally a top-level class
	 * that has been nested in another top-level class for packaging convenience.
	 */
	static class StaticInnerClass {
		void testMethod() {
			//s = "Set from StaticInnerClass";// Syntax Error: Cannot make a static reference to the non-static field s
		}
	}
	
	protected InnerClass1 ic;
	 
    public NestedClass() {
    ic = new InnerClass1();
    }
 
    public void displayStrings() {
    System.out.println(ic.getString() + ".");
    System.out.println(ic.getAnotherString() + ".");
    }
 
    static public void main(String[] args) {
    	NestedClass c1 = new NestedClass();
    c1.displayStrings();
    }
 
    protected class InnerClass1 {
    public String getString() {
        return "InnerClass1: getString invoked";
    }
 
    public String getAnotherString() {
        return "InnerClass1: getAnotherString invoked";
    }
    }
}
