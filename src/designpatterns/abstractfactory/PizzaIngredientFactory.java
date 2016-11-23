package designpatterns.abstractfactory;

public interface PizzaIngredientFactory {
	public Cheese createCheese();
	public Veggies[] createVeggies();

}
