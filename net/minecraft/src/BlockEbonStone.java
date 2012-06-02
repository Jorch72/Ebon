// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.forge.ITextureProvider;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, AxisAlignedBB, 
//            Entity

public class BlockEbonStone extends Block implements ITextureProvider
{

    protected BlockEbonStone(int i, int j)
    {
        super(i, j, Material.rock);
        f = 0.0625F;
        setTickRandomly(true);
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        for(int l = 0; l < 12; l++)
        {
            float f = (float)i + random.nextFloat();
            float f1 = (float)j + random.nextFloat() * 0.5F + 0.5F;
            float f2 = (float)k + random.nextFloat();
            world.spawnParticle("townaura", f, f1+0.233333333, f2, 0.0D, 0.0D, 0.0D);
        }

    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f, (float)(i + 1) - f, (float)(j + 1) - f, (float)(k + 1) - f);
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, EntityLiving entity)
    {
        entity.attackEntityFrom(DamageSource.cactus, 1);
    }
    
	public String getTextureFile() {
		return "/vazkii/ebonmod/sprites.png";
	}

    float f;
}