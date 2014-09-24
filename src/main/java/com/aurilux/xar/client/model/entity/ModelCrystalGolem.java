package com.aurilux.xar.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ModelCrystalGolem extends ModelBase {
	private WavefrontObject model;
	
	public ModelCrystalGolem() {
		//model = (WavefrontObject) AdvancedModelLoader.loadModel(XARModInfo.ASSETS_DIR + "crystalGolem.obj");
		//I think this gets all the separate meshes i.e. legs and other individual body parts
		//model.groupObjects
	}

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     *
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        float f6 = par3 * (float)Math.PI * -0.1F;
        int i;

        for (i = 0; i < 4; ++i)
        {
            this.blazeSticks[i].rotationPointY = -2.0F + MathHelper.cos(((float)(i * 2) + par3) * 0.25F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f6) * 9.0F;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f6) * 9.0F;
            ++f6;
        }

        f6 = ((float)Math.PI / 4F) + par3 * (float)Math.PI * 0.03F;

        for (i = 4; i < 8; ++i)
        {
            this.blazeSticks[i].rotationPointY = 2.0F + MathHelper.cos(((float)(i * 2) + par3) * 0.25F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f6) * 7.0F;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f6) * 7.0F;
            ++f6;
        }

        f6 = 0.47123894F + par3 * (float)Math.PI * -0.05F;

        for (i = 8; i < 12; ++i)
        {
            this.blazeSticks[i].rotationPointY = 11.0F + MathHelper.cos(((float)i * 1.5F + par3) * 0.5F);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(f6) * 5.0F;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(f6) * 5.0F;
            ++f6;
        }

        this.blazeHead.rotateAngleY = par4 / (180F / (float)Math.PI);
        this.blazeHead.rotateAngleX = par5 / (180F / (float)Math.PI);
    }*/
}
