package net.kenji.woh.api.basegameassets.skills;

import com.p1nero.invincible.api.skill.ComboNode;
import net.kenji.woh.api.basegameassets.condition.CooldownCounterCondition;
import net.kenji.woh.gameasset.skills.combos.ShotogatanaCombos;
import org.jline.utils.Log;
import yesman.epicfight.data.conditions.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseComboBuilder {
    protected static final List<Supplier<?>> DEFERRED_SETUP = new ArrayList<>();
    public static void initializeComboSkills(){
        DEFERRED_SETUP.forEach(Supplier::get);
        DEFERRED_SETUP.clear();
    };
    public static class ComboNodeWrapper{

        public final ComboNode[] combos;



        public ComboNodeWrapper(ComboNode... combos){
            /*for(int i = 0; i < combos.length; i++){
                if(combos[i] == null) continue;
                ComboNode currentCombo = combos[i];
                switch (i){
                    case 0: upCombo = currentCombo;
                    case 1: downCombo = currentCombo;
                    case 2: leftCombo = currentCombo;
                    case 3: rightCombo = currentCombo;
                    case 4: jumpCombo = currentCombo;
                    case 5: dashCombo = currentCombo;
                }
            }*/
          this.combos = combos;
        }
    }

    public static ComboNode createMovementCombo(ComboNode currentCombo, ComboNode nextCombo, ShotogatanaCombos.ComboNodeWrapper combos){
        ComboNode decision = ComboNode.create();

        for(ComboNode comboNode : combos.combos) {
            if(comboNode != null)
                decision.addConditionNode(comboNode);
        }
        if(nextCombo != null)
            decision.addConditionNode(nextCombo);
        currentCombo.key1(decision);

        return decision;
    }
    public static void collectConditions(ComboNode node) {
        for (Condition condition : node.getConditions()) {
            if (condition instanceof CooldownCounterCondition iCooldown) {
                CooldownCounterCondition.conditions.add(iCooldown);
                Log.info("added Condition!");
            }
        }

        for (ComboNode child : node.getChildren()) {
            collectConditions(child);
        }
    }
}
