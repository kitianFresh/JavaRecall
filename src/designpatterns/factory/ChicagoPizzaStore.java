package designpatterns.factory;

public class ChicagoPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String item) {
		if ("cheese".equals(item)) {
			return new ChicagoStyleCheesePizza();
		} else if ("veggie".equals(item)) {
			return new ChicagoStyleVeggiePizza();
		} else return null;
	}

}
