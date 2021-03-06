package edu.warbot.FSM.condition;

import edu.warbot.FSMEditor.settings.GenericConditionSettings;
import edu.warbot.brains.WarBrain;

public class WarConditionTimeOut<BrainType extends WarBrain> extends WarCondition<BrainType>{
	
	Integer timeOut;

	Integer currentTime = 0;
	
	public WarConditionTimeOut(String name, BrainType brain,
			GenericConditionSettings conditionSettings){
		super(name, brain, conditionSettings);
		
		if(conditionSettings.Tik_number != null)
			this.timeOut = conditionSettings.Tik_number;
		else
			this.timeOut = 1;
	}

	@Override
	public void conditionWillBegin() {
		this.currentTime = 0;
	}
	
	@Override
	public boolean isValide() {
		currentTime++;
		if(currentTime > timeOut)
			return true;
		else
			return false;
			
	}

}
