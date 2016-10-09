package basics;

class Horse {
    public String identifyMyself() {
        return "I am a horse.";
    }
}
interface Flyer {
    default public String identifyMyself() {
        return "I am able to fly.";
    }
}
interface Mythical {
    default public String identifyMyself() {
        return "I am a mythical creature.";
    }
}
/*
 * Instance methods are preferred over interface default methods.
 */
class Pegasus extends Horse implements Flyer, Mythical {}



interface Animal {
    default public String identifyMyself() {
        return "I am an animal.";
    }
}
interface EggLayer extends Animal {
    default public String identifyMyself() {
        return "I am able to lay eggs.";
    }
}
interface FireBreather extends Animal { }
/*
 * Methods that are already overridden by other candidates are ignored. 
 * This circumstance can arise when supertypes share a common ancestor.
 */
class Dragon implements EggLayer, FireBreather {}



interface OperateCar {
    // ...
    default public int startEngine(String key) {
		return 0;
        // Implementation
    }
}
interface FlyCar {
    // ...
    default public int startEngine(String key) {
		return 1;
        // Implementation
    }
}
/*
 * If two or more independently defined default methods conflict, or a default method conflicts with an abstract method, 
 * then the Java compiler produces a compiler error. You must explicitly override the supertype methods.
 */
class FlyingCar implements OperateCar, FlyCar {

	@Override
	public int startEngine(String key) {
		// TODO Auto-generated method stub
		return FlyCar.super.startEngine(key);
	}
}


public class DragonOrPegasus {
	 public static void main(String... args) {
	        Pegasus pegasus = new Pegasus();
	        System.out.println(pegasus.identifyMyself());// "I am a horse."
	        
	        Dragon dragon = new Dragon();
	        System.out.println(dragon.identifyMyself());// "I am able to lay eggs."
	    }
}
