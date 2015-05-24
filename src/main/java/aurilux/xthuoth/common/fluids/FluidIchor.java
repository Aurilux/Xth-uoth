package aurilux.xthuoth.common.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * This class was created by <Aurilux>. It's distributed as part of the Xth'uoth Mod.
 * <p/>
 * Xth'uoth is Open Source and distributed under the GNU Lesser General Public License v3.0
 * (https://www.gnu.org/licenses/lgpl.html)
 * <p/>
 * File Created @ [22 May 2015]
 */
public class FluidIchor extends Fluid {
    public FluidIchor() {
        super("ichor");
        FluidRegistry.registerFluid(this);
    }
}