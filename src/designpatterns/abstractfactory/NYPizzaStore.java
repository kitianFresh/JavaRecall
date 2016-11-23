package designpatterns.abstractfactory;

public class NYPizzaStore extends PizzaStore {
	Pizza pizza = null;
	PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();
	@Override
	protected Pizza createPizza(String item) {
		if ("cheese".equals(item)) {
			pizza =  new CheesePizza(ingredientFactory);
			pizza.setName("New York Sytle Cheese Pizza");
			
		} else if ("veggie".equals(item)) {
			pizza =  new VeggiePizza(ingredientFactory);
			pizza.setName("New York Style Veggie Pizza");
		}
		return pizza;
	}
}
