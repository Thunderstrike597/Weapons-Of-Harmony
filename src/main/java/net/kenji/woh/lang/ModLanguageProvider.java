package net.kenji.woh.lang;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.interfaces.ITranslatableItem;
import net.kenji.woh.api.interfaces.ITranslatableSkill;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.registry.WohItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import yesman.epicfight.skill.Skill;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, WeaponsOfHarmony.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        WohItems.ITEMS.getEntries().forEach(item -> {
            ResourceLocation loc = item.getId();
            if(loc != null) {
                String name = loc.getPath();
                add(item.get(), toEnglishName(item.get(), name));

            }
        });
        WohItems.ITEMS.getEntries().forEach(item -> {
            ResourceLocation loc = item.getId();
            if(loc != null) {
                add(item.get().getDescriptionId(), toEnglishTooltip(item.get()));
            }
        });
        WohSkills.skills.forEach(skill -> {
            String skillName = skill.getTranslationKey();

            String skillTooltip = skill.getTranslationKey() + ".tooltip";
            String skillTooltipExtra = skill.getTranslationKey() + ".tooltip.extra";

            if(skill instanceof ITranslatableSkill translatableSkill) {
                String name = translatableSkill.getSkillName();
                String tooltip = translatableSkill.getSkillTooltip();
                String tooltipExtra = translatableSkill.getSkillTooltipExtra();
                if(!name.isEmpty())
                    add(skillName, name);
                if(!tooltip.isEmpty())
                    add(skillTooltip, tooltip);
                if(!tooltipExtra.isEmpty()){
                    add(skillTooltipExtra, tooltipExtra);
                }
            }
        });
    }
    private String toEnglishName(Item item, String path) {
        String[] parts = path.split("_");
        StringBuilder builder = new StringBuilder();

        if(item instanceof ITranslatableItem translatableItem){
            if(translatableItem.getItemName() != null && !translatableItem.getItemName().isEmpty()){
                return translatableItem.getItemName();
            }
        }

        for (String part : parts) {
            builder.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1))
                    .append(" ");
        }

        return builder.toString().trim();
    }
    private String toEnglishTooltip(Item item) {
        if(item instanceof ITranslatableItem translatableItem){
            if(translatableItem.getItemName() != null && !translatableItem.getItemName().isEmpty()){
                return translatableItem.getItemTooltip();
            }
        }
        return "";
    }
}