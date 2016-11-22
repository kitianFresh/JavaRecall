package designpatterns.factory;

public class NYPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String item) {
		if ("cheese".equals(item)) {
			return new NYStyleCheesePizza();
		} else if ("veggie".equals(item)) {
			return new NYStyleVeggiePizza();
		} else return null;
	}

}
