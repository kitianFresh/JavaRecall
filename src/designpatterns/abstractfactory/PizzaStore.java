package designpatterns.abstractfactory;

public abstract class PizzaStore {
	
	Pizza orderPizza(String type) {
		Pizza pizza;
		
		pizza = createPizza(type);
		
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		
		return pizza;
		
	}
	
	/**
	 * 工厂方法
	 * abstract Product factoryMethod(String type);
	 */
	protected abstract  Pizza createPizza(String type);
	
}
