package vazkii.ebon.common.item;

import vazkii.ebon.common.EbonModReference;
import vazkii.ebon.common.mod_Ebon;

import net.minecraft.src.ItemSeeds;

public class ItemBloodSeeds extends ItemSeeds {

	public ItemBloodSeeds(int par1) {
		super(par1, mod_Ebon.bloodLeafCrops.blockID, mod_Ebon.quicksand.blockID);
	}

	@Override
	public String getTextureFile() {
		return EbonModReference.SPRITESHEET_PATH;
	}

}
