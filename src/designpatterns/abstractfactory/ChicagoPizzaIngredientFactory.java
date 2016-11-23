package designpatterns.abstractfactory;

public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {

	@Override
	public Cheese createCheese() {
		
		return new MozzarsllaCheese();
	}

	@Override
	public Veggies[] createVeggies() {
		Veggies veggies[] = {new Garlic(), new RedPepper()};
		return veggies;
	}

}
