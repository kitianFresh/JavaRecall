package designpatterns.abstractfactory;

public class ChicagoPizzaStore extends PizzaStore {
	Pizza pizza = null;
	PizzaIngredientFactory ingredientFactory = new ChicagoPizzaIngredientFactory();
	@Override
	protected Pizza createPizza(String item) {
		if ("cheese".equals(item)) {
			pizza =  new CheesePizza(ingredientFactory);
			pizza.setName("Chicago Sytle Cheese Pizza");
			
		} else if ("veggie".equals(item)) {
			pizza =  new VeggiePizza(ingredientFactory);
			pizza.setName("Chicago Style Veggie Pizza");
		}
		return pizza;
	}

}
