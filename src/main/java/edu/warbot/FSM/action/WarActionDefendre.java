package edu.warbot.FSM.action;

import edu.warbot.FSM.WarFSMMessage;
import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.agents.WarRocketLauncher;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.projectiles.WarRocket;
import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.communications.WarMessage;
import edu.warbot.tools.geometry.CoordPolar;

import javax.swing.*;
import java.util.ArrayList;

public class WarActionDefendre extends WarAction<WarRocketLauncherBrain> {
	
	CoordPolar coordBase;
	
	public WarActionDefendre(WarRocketLauncherBrain brain) {
		super(brain);
		JOptionPane.showMessageDialog(null, "Attention l'action ChercherBase n'a pas étée vérifiée et risque de ne pas fonctionner", "Waring not terminated action", JOptionPane.INFORMATION_MESSAGE);
	}

	public String executeAction(){
		
		//si j'ai pas de missile et que je recharge pas, je recharge
		if(!getAgent().isReloaded() && !getAgent().isReloading()){
			return WarRocketLauncher.ACTION_RELOAD;
		}
		
		ArrayList<WarAgentPercept> perceptEnemy = getAgent().getPerceptsEnemiesByType(WarAgentType.WarRocketLauncher);

		// si j'ai un enemy dans mon percept
		if(perceptEnemy != null & perceptEnemy.size() > 0){
			
			// si j'ai rechargé
			if(getAgent().isReloaded()){
				getAgent().setHeading(perceptEnemy.get(0).getAngle());
				return WarRocketLauncher.ACTION_FIRE;
				
			}else{// si j'ai pas rechargé
				if(perceptEnemy.get(0).getDistance() > WarRocket.EXPLOSION_RADIUS + WarRocketLauncher.SPEED + 1){
					getAgent().setHeading(perceptEnemy.get(0).getAngle());
					return MovableWarAgent.ACTION_MOVE;
				}else{
					return MovableWarAgent.ACTION_IDLE;
				}
				
			}
			
		}else{//Si j'ai pas d'enemy dans mon percept
			
			WarMessage m = getMessageFromBase();
			
			if(m != null)
				getAgent().setHeading(m.getAngle());
			
			return MovableWarAgent.ACTION_MOVE;

		}
		
	}
	
	private WarMessage getMessageFromBase() {
		for (WarMessage m : getAgent().getMessages()) {
			if(m.getSenderType().equals(WarAgentType.WarBase))
				return m;
		}
		
		getAgent().broadcastMessageToAgentType(WarAgentType.WarBase, WarFSMMessage.whereAreYou, "");
		return null;
	}

	@Override
	public void actionWillBegin() {
		super.actionWillBegin();
	}
	
}
