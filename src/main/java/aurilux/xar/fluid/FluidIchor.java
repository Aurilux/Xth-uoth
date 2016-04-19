package aurilux.xar.fluid;

import net.minecraftforge.fluids.Fluid;

public class FluidIchor extends Fluid {
	public FluidIchor(String fluidName) {
		super(fluidName);
        this.setViscosity(6000);
	}
}