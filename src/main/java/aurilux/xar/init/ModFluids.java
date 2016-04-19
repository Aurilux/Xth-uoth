package aurilux.xar.init;

import aurilux.xar.fluid.FluidIchor;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
	public static Fluid ichor;
	
	public static void init() {
		ichor = new FluidIchor("ichor");
		
		FluidRegistry.registerFluid(ichor);
	}
}