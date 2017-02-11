package designpatterns.command;

public class CommandTest {
	public static void main(String[] args) {
		Light livingLight = new Light("Living Light");
		Light kitchenLight = new Light("Kitchen Light");
		
		Command livingLightOnCommand = new LightOnCommand(livingLight);
		Command kitchenLightOnCommand = new LightOnCommand(kitchenLight);
		
		RemoteControl remoteControl = new RemoteControl(6);
		remoteControl.setCommand(0, livingLightOnCommand);
		remoteControl.setCommand(1, kitchenLightOnCommand);
		
		remoteControl.invokeCommand(0);
		remoteControl.invokeCommand(1);
		remoteControl.undoCommand(1);
	}
}
