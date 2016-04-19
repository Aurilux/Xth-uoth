package aurilux.xar.research;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ResearchPage.PageType;

public class XARResearchItem extends ResearchItem {
    public XARResearchItem(String key, String category) {
        super(key, category);
    }

    public XARResearchItem(String key, String category, AspectList tags, int col, int row, int complex, ResourceLocation icon) {
        super(key, category, tags, col, row, complex, icon);
    }

    public XARResearchItem(String key, String category, AspectList tags, int col, int row, int complex, ItemStack icon) {
        super(key, category, tags, col, row, complex, icon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getName() {
        return StatCollector.translateToLocal("xarresearch.name." + key);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getText() {
        return StatCollector.translateToLocal("xarresearch.lore." + key);
    }

    @Override
    public ResearchItem setPages(ResearchPage... pages) {
        for (ResearchPage page : pages) {
            if (page.type == PageType.TEXT) {
                page.text = "xarresearch.page." + key + "." + page.text;
            }

            //if our page type is 'INFUSION_CRAFTING' ensure that this page only displays when we have unlocked
            //infusion crafting. We use 'parentsHidden' here because we are adding this research to our own tab(s)
            //so there is no need to try to draw the line that attaches most other research
            if (page.type == PageType.INFUSION_CRAFTING) {
                //add the infusion research to its list of 'parentsHidden' if it has none already
                if (parentsHidden == null || parentsHidden.length == 0) {
                    parentsHidden = new String[]{"INFUSION"};
                }
                //otherwise add it to its 'parentsHidden'
                else {
                    String[] newParents = new String[parentsHidden.length + 1];
                    newParents[0] = "INFUSION";
                    for (int i = 0; i < parentsHidden.length; i++) {
                        newParents[i + 1] = parentsHidden[i];
                    }
                    parentsHidden = newParents;
                }
            }
        }
        return super.setPages(pages);
    }
}