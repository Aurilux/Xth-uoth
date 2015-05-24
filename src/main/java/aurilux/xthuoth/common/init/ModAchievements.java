package aurilux.xthuoth.common.init;

import aurilux.xthuoth.common.core.Xthuoth;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

/**
 * This class was created by <Aurilux>. It's distributed as part of the Xth'uoth Mod.
 * <p/>
 * Xth'uoth is Open Source and distributed under the GNU Lesser General Public License v3.0
 * (https://www.gnu.org/licenses/lgpl.html)
 * <p/>
 * File Created @ [23 Mar 2015]
 */
public class ModAchievements {
    public static final Achievement findPrysmal = new Achievement("achievement.xthuoth.findPrysmal", "xthuoth.findPrysmal",
            0, 0, new ItemStack(ModItems.itemResource, 1, 0), null);
    public static final Achievement riftTravel = new Achievement("achievement.xthuoth.riftTravel", "xthuoth.riftTravel",
            3, 0, new ItemStack(ModItems.riftCatalyst), findPrysmal);
    public static final Achievement aberrationSlayer = new Achievement("achievement.xthuoth.aberrationSlayer",
            "xthuoth.aberrationSlayer", 3, -2, new ItemStack(ModItems.wightbulbPod), riftTravel);

    public static void init() {
        AchievementPage page = new AchievementPage(Xthuoth.MOD_ID, findPrysmal, riftTravel, aberrationSlayer);
        AchievementPage.registerAchievementPage(page);
    }
}