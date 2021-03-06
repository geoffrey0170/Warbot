package edu.warbot.FSM.action;

import edu.warbot.FSMEditor.settings.EnumMessage;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.communications.WarMessage;

import java.util.ArrayList;

/**
 * Va chercher de la nouriture
 */
public class WarActionChercherNouriture<BrainType extends WarBrain & Movable> extends WarAction<BrainType> {
	
	
	public WarActionChercherNouriture(BrainType brain) {
		super(brain);
	}

	public String executeAction(){
		
		if(getAgent().isBagFull()){
			getAgent().setDebugString("ActionChercherNourriture : bag full");
			if(getAgent().isBlocked())
				getAgent().setRandomHeading();
			return MovableWarAgent.ACTION_MOVE;
		}
		
		if(getAgent().isBlocked())
			getAgent().setRandomHeading();

		ArrayList<WarAgentPercept> percepts = getAgent().getPercepts();

		ArrayList<WarAgentPercept> foodPercepts = new ArrayList<>();
		
		for (WarAgentPercept p : percepts) {
			if(p.getType().equals(WarAgentType.WarFood))
				foodPercepts.add(p);
		}
		
		//Si il ny a pas de nouriture dans le percept
		if(foodPercepts.size() == 0){
			
			getAgent().setDebugString("ActionChercherNourriture : seek food");
			WarMessage m = getMessageAboutFood();
			if(m != null){
				getAgent().setDebugString("ActionChercherNourriture : msg about food");
				getAgent().setHeading(m.getAngle());
			}
			
			//Sinon je vais tout droit
			return MovableWarAgent.ACTION_MOVE;
				
		}else{//Si il y a de la nouriture
			getAgent().setDebugString("ActionChercherNourriture : find food");
            WarAgentPercept foodP = foodPercepts.get(0); //le 0 est le plus proche normalement
			
			//si il y a beaucoup de nourriture je previens mes alliés
			if(foodPercepts.size() > 1)
				getAgent().broadcastMessageToAgentType(WarAgentType.WarExplorer, EnumMessage.food_here.name(), "");
			
			if(foodP.getDistance() > ControllableWarAgent.MAX_DISTANCE_GIVE){
				getAgent().setHeading(foodP.getAngle());
				return MovableWarAgent.ACTION_MOVE;
			}else{
				return MovableWarAgent.ACTION_TAKE;
			}
		}
		
	}

	private WarMessage getMessageAboutFood() {
		for (WarMessage m : getAgent().getMessages()) {
			if(m.getMessage().equals(EnumMessage.food_here.name()))
				return m;
		}
		return null;
	}

	@Override
	public void actionWillBegin() {
		super.actionWillBegin();
		getAgent().setHeading(getAgent().getHeading() + 180);
	}

}
