package designpatterns.command;

import java.util.ArrayList;
import java.util.List;

public class RemoteControl {
	List<Command> commands = new ArrayList<Command>();
	
	public RemoteControl(int num){
		NoCommand noCommand = new NoCommand();
		for(int i=0; i <= num; i++)
			commands.add(noCommand);
	}
	
	public void setCommand(int slot, Command command) {
		commands.set(slot, command);
	}
	
	public void invokeCommand(int slot) {
		commands.get(slot).execute();
	}
	
	public void undoCommand(int slot) {
		commands.get(slot).undo();
	}

}
