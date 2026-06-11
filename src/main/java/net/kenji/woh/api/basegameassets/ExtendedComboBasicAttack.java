package net.kenji.woh.api.basegameassets;

import com.p1nero.invincible.api.skill.ComboNode;
import com.p1nero.invincible.skill.ComboBasicAttack;

public class ExtendedComboBasicAttack extends ComboBasicAttack {

    private final ComboNode root;

    public ExtendedComboBasicAttack(Builder builder, ComboNode root) {
        super(builder);
        this.root = root;
    }

    public ComboNode getRoot() {
        return root;
    }
}
