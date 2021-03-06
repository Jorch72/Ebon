package vazkii.ebon.common;

import updatemanager.common.ModConverter;
import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.EnumVazkiiMods;
import vazkii.codebase.common.IOUtils;
import vazkii.codebase.common.mod_Vazcore;
import vazkii.ebon.api.EbonAPIRegistry;
import vazkii.ebon.client.EbonModClientTickHandler;
import vazkii.ebon.client.EbonModKeyHandler;
import vazkii.ebon.client.RenderTormentedSoul;
import vazkii.ebon.client.RenderVoidInsect;
import vazkii.ebon.common.block.TileEntityVaseOfSouls;
import vazkii.ebon.common.block.TileEntityWard;
import vazkii.ebon.common.item.armor.ArmorEffectDebilitation;
import vazkii.ebon.common.item.armor.ArmorEffectFrost;
import vazkii.ebon.common.item.armor.ArmorEffectMeteorPlumetting;
import vazkii.ebon.common.item.armor.ArmorEffectNature;
import vazkii.ebon.common.item.armor.ArmorEffectRegeneration;
import vazkii.ebon.common.item.armor.ArmorEffectRespiration;
import vazkii.ebon.common.item.armor.ArmorEffectRevitalization;
import vazkii.ebon.common.item.armor.ArmorEffectThorns;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EntityBlaze;
import net.minecraft.src.EntityCaveSpider;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityMagmaCube;
import net.minecraft.src.EntityMooshroom;
import net.minecraft.src.EntityOcelot;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySilverfish;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.Potion;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "ebon_Vz", name = "The Ebon Mod", version = "by Vazkii. Version [4.0.3] for 1.4.4/5")
@NetworkMod(channels = { "ebon_Vz", "ebon1_Vz", "ebon2_Vz", "ebon3_Vz", "ebon4_Vz" }, packetHandler = EbonModPacketHandler.class, clientSideRequired = true)
public class mod_Ebon {

	@PreInit
	public void onPreInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EbonModHooks());
	}

	@Init
	public void onInit(FMLInitializationEvent event) {
		mod_Vazcore.loadedVzMods.add(EnumVazkiiMods.EBON.getAcronym());
		CommonUtils.registerNewToolMaterial("EBON", 3, 1561, 8.0F, 3, 22);
		CommonUtils.registerNewArmorMaterial("EBON", 0, new int[] { 2, 6, 5, 2 }, 25);
		new EbonModConfig(IOUtils.getConfigFile(EnumVazkiiMods.EBON));
		new EbonModUpdateHandler(ModConverter.getMod(getClass()));
		TickRegistry.registerTickHandler(new EbonModClientTickHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new EbonModTickHandler(), Side.SERVER);
		TickRegistry.registerTickHandler(new EbonModTickHandler(), Side.CLIENT);
		KeyBindingRegistry.registerKeyBinding(new EbonModKeyHandler());

		Enchantment.enchantmentsList[17] = Enchantment.enchantmentsList[20] = null;
		new EnchantmentSmiteOverride(17, 5, 1);
		new EnchantmentFireAspectOverride(20, 2);

		OreDictionary.registerOre("corpseDust", corpseDust);
		OreDictionary.registerOre("undeadEssence", undeadEssence);
		OreDictionary.registerOre("ebonGem", ebonGem);
		OreDictionary.registerOre("ebonCoal", ebonCoal);
		OreDictionary.registerOre("ebonGlowdust", ebonGlowdust);
		OreDictionary.registerOre("ebonGlowstone", ebonGlowstone);
		OreDictionary.registerOre("quicksand", quicksand);
		OreDictionary.registerOre("soul", soul);
		OreDictionary.registerOre("demonicSoul", demonicSoul);
		OreDictionary.registerOre("bloodGem", bloodGem);
		OreDictionary.registerOre("soulGrindstone", soulGrindstone);
		OreDictionary.registerOre("bloodPowder", bloodPowder);
		OreDictionary.registerOre("ebonCloth", ebonCloth);
		OreDictionary.registerOre("gemOfDespair", gemOfDespair);

		LanguageRegistry.addName(corpseDust, "Corpse Dust");
		LanguageRegistry.addName(undeadEssence, "Undead Essence");
		LanguageRegistry.addName(undeadEssenceConcentrated, "Concentrated Undead Essence");
		LanguageRegistry.addName(ebonGem, "Ebon Gem");
		LanguageRegistry.addName(ebonBroadsword, "Ebon Broadsword");
		LanguageRegistry.addName(ebonPickaxe, "Ebon Pickaxe");
		LanguageRegistry.addName(ebonSpade, "Ebon Spade");
		LanguageRegistry.addName(ebonHatchet, "Ebon Hatchet");
		LanguageRegistry.addName(darknessImbiber, "Darkness Imbiber");
		LanguageRegistry.addName(ebonCoal, "Ebon Coal");
		LanguageRegistry.addName(ebonGlowdust, "Ebon Glowdust");
		LanguageRegistry.addName(ebonApple, "Ebon Apple");
		LanguageRegistry.addName(soul, "Soul");
		LanguageRegistry.addName(soulPowder, "Soul Powder");
		LanguageRegistry.addName(staffShard, "Ebon Staff Shard");
		LanguageRegistry.addName(ebonStaff, "Inert Ebon Staff");
		LanguageRegistry.addName(ebonStaffCharged, "Ebon Staff");
		LanguageRegistry.addName(orbOfSouls, "Orb Of Souls");
		LanguageRegistry.addName(orbOfSoulsCharged, "Charged Orb Of Souls");
		LanguageRegistry.addName(staffOfSouls, "Inert Staff of Souls");
		LanguageRegistry.addName(staffOfSoulsCharged, "Staff Of Souls");
		LanguageRegistry.addName(demonicSoul, "Demonic Soul");
		LanguageRegistry.addName(bloodGem, "Blood Gem");
		LanguageRegistry.addName(ebonScepter, "Ebon Scepter");
		LanguageRegistry.addName(soulGrindstone, "Soul Grindstone");
		LanguageRegistry.addName(wandOfRetrieval, "Wand of Retrieval");
		LanguageRegistry.addName(bloodPowder, "Blood Powder");
		LanguageRegistry.addName(bloodSeeds, "Blood Seeds");
		LanguageRegistry.addName(bloodLeaf, "Blood Leaf");
		LanguageRegistry.addName(necromancerLexicon, "Necromancer's Lexicon");
		LanguageRegistry.addName(ebonScepterInfinity, "Infinity Scepter");
		LanguageRegistry.addName(ebonScepterZero, "Zero Scepter");
		LanguageRegistry.addName(ebonScepterVoid, "Void Scepter");
		LanguageRegistry.addName(ebonHood, "Ebon Hood");
		LanguageRegistry.addName(ebonRobeTop, "Ebon Robe Top");
		LanguageRegistry.addName(ebonRobeBottom, "Ebon Robe Bottom");
		LanguageRegistry.addName(ebonShoes, "Ebon Shoes");
		LanguageRegistry.addName(soulStone, "Soul Stone");
		LanguageRegistry.addName(ebonCloth, "Ebon Cloth");
		LanguageRegistry.addName(plusiumCharm, "Plusium Charm");
		LanguageRegistry.addName(miniumCharm, "Minium Charm");
		LanguageRegistry.addName(gemOfDespair, "Gem of Despair");
		LanguageRegistry.addName(bottleOfDarkness, "Bottle of Darkness");
		LanguageRegistry.addName(wandOfImprisionment, "Wand of Imprisionment");
		LanguageRegistry.addName(altarBlueprint, "Ancient Scroll");
		LanguageRegistry.addName(vaseOfSoulsItem, "Vase of Souls");

		LanguageRegistry.addName(ebonGlowstone, "Ebon Glowstone");
		LanguageRegistry.addName(ebonGemBlock, "Ebon Gem Block");
		LanguageRegistry.addName(bloodGemBlock, "Blood Gem Block");
		LanguageRegistry.addName(quicksand, "Quicksand");
		LanguageRegistry.addName(ebonObsidian, "Ebon Obsidian");
		LanguageRegistry.addName(ebonStone, "Ebon Stone");
		LanguageRegistry.addName(ebonTorch, "Ebon Torch");

		LanguageRegistry.instance().addStringLocalization("enchantment.venomTouch", "Venom Touch");
		LanguageRegistry.instance().addStringLocalization("entity.TormentedSoul.name", "Tormented Soul");
		LanguageRegistry.instance().addStringLocalization("entity.DemonicSoul.name", "Demonic Soul");

		GameRegistry.registerFuelHandler(new EbonModFuels());
		GameRegistry.registerCraftingHandler(new EbonModCraftingHandler());

		GameRegistry.addRecipe(new ItemStack(undeadEssence), "CCC", "BBB", "GGG", Character.valueOf('C'), corpseDust, Character.valueOf('B'), Item.bone, Character.valueOf('G'), Item.gunpowder);
		GameRegistry.addShapelessRecipe(new ItemStack(undeadEssenceConcentrated), undeadEssence, undeadEssence, undeadEssence, undeadEssence);
		GameRegistry.addRecipe(new ItemStack(ebonGem), " E ", "EDE", " E ", Character.valueOf('E'), undeadEssence, Character.valueOf('D'), Item.diamond);
		GameRegistry.addRecipe(new ItemStack(ebonBroadsword), "E", "E", "B", Character.valueOf('E'), ebonGem, Character.valueOf('B'), Item.bone);
		GameRegistry.addRecipe(new ItemStack(ebonPickaxe), "EEE", " B ", " B ", Character.valueOf('E'), ebonGem, Character.valueOf('B'), Item.bone);
		GameRegistry.addRecipe(new ItemStack(ebonSpade), "E", "B", "B", Character.valueOf('E'), ebonGem, Character.valueOf('B'), Item.bone);
		GameRegistry.addRecipe(new ItemStack(ebonHatchet), "EE", "EB", " B", Character.valueOf('E'), ebonGem, Character.valueOf('B'), Item.bone);
		GameRegistry.addRecipe(new ItemStack(ebonHatchet), "EE", "BE", "B ", Character.valueOf('E'), ebonGem, Character.valueOf('B'), Item.bone);
		GameRegistry.addRecipe(new ItemStack(darknessImbiber), "E E", " G ", " G ", Character.valueOf('E'), ebonGem, Character.valueOf('G'), Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(ebonCoal), " E ", "ECE", " E ", Character.valueOf('E'), undeadEssence, Character.valueOf('C'), Item.coal);
		GameRegistry.addShapelessRecipe(new ItemStack(ebonGlowdust), undeadEssence, Item.lightStoneDust);
		GameRegistry.addRecipe(new ItemStack(ebonApple), "EEE", "EAE", "EEE", Character.valueOf('E'), ebonGemBlock, Character.valueOf('A'), Item.appleRed);
		GameRegistry.addShapelessRecipe(new ItemStack(soulPowder), soul, soul, soul, soulGrindstone);
		GameRegistry.addShapelessRecipe(new ItemStack(Block.slowSand), soul, Block.sand);
		GameRegistry.addRecipe(new ItemStack(ebonStaff), "  S", " W ", "G  ", Character.valueOf('S'), staffShard, Character.valueOf('W'), Item.stick, Character.valueOf('G'), ebonGem);
		GameRegistry.addRecipe(new ItemStack(ebonStaffCharged), "CCC", "CSC", "CCC", Character.valueOf('C'), corpseDust, Character.valueOf('S'), ebonStaff);
		GameRegistry.addRecipe(new ItemStack(orbOfSouls), "SEG", "EPE", "GES", Character.valueOf('G'), ebonGem, Character.valueOf('P'), Item.eyeOfEnder, Character.valueOf('E'), undeadEssence, Character.valueOf('S'), soul);
		GameRegistry.addRecipe(new ItemStack(orbOfSouls), "GES", "EPE", "SEG", Character.valueOf('G'), ebonGem, Character.valueOf('P'), Item.eyeOfEnder, Character.valueOf('E'), undeadEssence, Character.valueOf('S'), soul);
		GameRegistry.addRecipe(new ItemStack(staffOfSouls), " US", " RU", "O  ", Character.valueOf('S'), staffShard, Character.valueOf('U'), soul, Character.valueOf('R'), Item.blazeRod, Character.valueOf('O'), orbOfSoulsCharged);
		GameRegistry.addRecipe(new ItemStack(staffOfSoulsCharged), "UUU", "USU", "UUU", Character.valueOf('U'), soul, Character.valueOf('S'), staffOfSouls);
		GameRegistry.addRecipe(new ItemStack(bloodGem), " S ", "SGS", " S ", Character.valueOf('G'), ebonGem, Character.valueOf('S'), demonicSoul);
		GameRegistry.addRecipe(new ItemStack(ebonScepter), " SG", " BS", "E  ", Character.valueOf('G'), bloodGem, Character.valueOf('S'), demonicSoul, Character.valueOf('E'), ebonGem, Character.valueOf('B'), Item.blazeRod);
		GameRegistry.addRecipe(new ItemStack(soulGrindstone), "  R", "SSS", "RRR", Character.valueOf('R'), Block.stone, Character.valueOf('S'), soul);
		GameRegistry.addShapelessRecipe(new ItemStack(bloodPowder, 2), soulGrindstone, bloodGem);
		GameRegistry.addRecipe(new ItemStack(bloodSeeds, 8), "SSS", "SPS", "SSS", Character.valueOf('S'), Item.seeds, Character.valueOf('P'), bloodPowder);
		GameRegistry.addShapelessRecipe(new ItemStack(soul), soulGrindstone, bloodLeaf);
		GameRegistry.addRecipe(new ItemStack(necromancerLexicon), "PEP", "EBE", "PEP", Character.valueOf('P'), bloodPowder, Character.valueOf('E'), Item.writableBook, Character.valueOf('E'), undeadEssence);
		GameRegistry.addRecipe(new ItemStack(necromancerLexicon), "EPE", "PBP", "EPE", Character.valueOf('P'), bloodPowder, Character.valueOf('E'), Item.writableBook, Character.valueOf('E'), undeadEssence);
		GameRegistry.addRecipe(new ItemStack(ebonScepterInfinity), "EME", "ESE", "EEE", Character.valueOf('E'), undeadEssence, Character.valueOf('M'), Item.bone, Character.valueOf('S'), ebonScepter);
		GameRegistry.addRecipe(new ItemStack(ebonScepterZero), "EME", "ESE", "EEE", Character.valueOf('E'), undeadEssence, Character.valueOf('M'), Item.gunpowder, Character.valueOf('S'), ebonScepter);
		GameRegistry.addRecipe(new ItemStack(ebonScepterVoid), "EME", "ESE", "EEE", Character.valueOf('E'), undeadEssence, Character.valueOf('M'), corpseDust, Character.valueOf('S'), ebonScepter);
		GameRegistry.addRecipe(new ItemStack(wandOfRetrieval), "  B", " S ", "R  ", Character.valueOf('B'), bloodGem, Character.valueOf('S'), soulStone, Character.valueOf('R'), Item.blazeRod);
		GameRegistry.addRecipe(new ItemStack(wandOfImprisionment), "  D", " S ", "B  ", Character.valueOf('D'), gemOfDespair, Character.valueOf('S'), soulStone, Character.valueOf('B'), Item.blazeRod);
		GameRegistry.addRecipe(new ItemStack(vaseOfSoulsItem, 2), "CSC", "CPC", "CLC", Character.valueOf('C'), Block.blockClay, Character.valueOf('S'), soulStone, Character.valueOf('P'), bloodPowder, Character.valueOf('L'), Item.cauldron);
		GameRegistry.addRecipe(new ItemStack(plusiumCharm), "PSS", "BMS", "BBP", Character.valueOf('P'), bloodPowder, Character.valueOf('S'), Item.silk, Character.valueOf('B'), bottleOfDarkness, Character.valueOf('M'), ebonGlowstone);
		GameRegistry.addRecipe(new ItemStack(miniumCharm), "PSS", "BMS", "BBP", Character.valueOf('P'), bloodPowder, Character.valueOf('S'), Item.silk, Character.valueOf('B'), bottleOfDarkness, Character.valueOf('M'), ebonObsidian);
		GameRegistry.addRecipe(new ItemStack(ebonCloth, 4), "SES", "ECE", "SES", Character.valueOf('S'), demonicSoul, Character.valueOf('E'), undeadEssence, Character.valueOf('C'), new ItemStack(Block.cloth, -1));
		GameRegistry.addRecipe(new ItemStack(ebonCloth, 4), "ESE", "SCS", "ESE", Character.valueOf('S'), demonicSoul, Character.valueOf('E'), undeadEssence, Character.valueOf('C'), new ItemStack(Block.cloth, -1));
		GameRegistry.addRecipe(new ItemStack(soulStone), "ELE", "LOL", "ELE", Character.valueOf('E'), undeadEssence, Character.valueOf('L'), bloodLeaf, Character.valueOf('O'), Block.obsidian);
		GameRegistry.addRecipe(new ItemStack(soulStone), "LEL", "EOE", "LEL", Character.valueOf('E'), undeadEssence, Character.valueOf('L'), bloodLeaf, Character.valueOf('O'), Block.obsidian);
		GameRegistry.addRecipe(new ItemStack(ebonHood), "CCC", "CSC", Character.valueOf('C'), ebonCloth, Character.valueOf('S'), soulStone);
		GameRegistry.addRecipe(new ItemStack(ebonRobeTop), "C C", "CSC", "CCC", Character.valueOf('C'), ebonCloth, Character.valueOf('S'), soulStone);
		GameRegistry.addRecipe(new ItemStack(ebonRobeBottom), "CCC", "CSC", "C C", Character.valueOf('C'), ebonCloth, Character.valueOf('S'), soulStone);
		GameRegistry.addRecipe(new ItemStack(ebonShoes), "C C", "CSC", Character.valueOf('C'), ebonCloth, Character.valueOf('S'), soulStone);
		GameRegistry.addRecipe(new ItemStack(ebonHood), " S ", "SIS", " S ", Character.valueOf('S'), soul, Character.valueOf('I'), ebonHood);
		GameRegistry.addRecipe(new ItemStack(ebonRobeTop), " S ", "SIS", " S ", Character.valueOf('S'), soul, Character.valueOf('I'), ebonRobeTop);
		GameRegistry.addRecipe(new ItemStack(ebonRobeBottom), " S ", "SIS", " S ", Character.valueOf('S'), soul, Character.valueOf('I'), ebonRobeBottom);
		GameRegistry.addRecipe(new ItemStack(ebonShoes), " S ", "SIS", " S ", Character.valueOf('S'), soul, Character.valueOf('I'), ebonShoes);
		GameRegistry.addRecipe(new ItemStack(Block.web), "S S", " S ", "S S", Character.valueOf('S'), Item.silk);
		GameRegistry.addShapelessRecipe(new ItemStack(Item.lightStoneDust, 4), soulGrindstone, Block.glowStone);
		GameRegistry.addShapelessRecipe(new ItemStack(ebonGlowdust, 4), soulGrindstone, ebonGlowstone);
		GameRegistry.addRecipe(new ItemStack(ebonGlowstone), "DD", "DD", Character.valueOf('D'), ebonGlowdust);
		GameRegistry.addRecipe(new ItemStack(ebonGlowstone), " E ", "EGE", " E ", Character.valueOf('E'), undeadEssence, Character.valueOf('G'), Block.glowStone);
		GameRegistry.addRecipe(new ItemStack(ebonGemBlock), "GGG", "GGG", "GGG", Character.valueOf('G'), ebonGem);
		GameRegistry.addShapelessRecipe(new ItemStack(ebonGem, 9), ebonGemBlock);
		GameRegistry.addRecipe(new ItemStack(bloodGemBlock), "GGG", "GGG", "GGG", Character.valueOf('G'), bloodGem);
		GameRegistry.addShapelessRecipe(new ItemStack(bloodGem, 9), bloodGemBlock);
		GameRegistry.addRecipe(new ItemStack(quicksand, 2), " E ", "ESE", " E ", Character.valueOf('E'), undeadEssence, Character.valueOf('S'), Block.sand);
		GameRegistry.addRecipe(new ItemStack(ebonObsidian), " E ", "EOE", " E ", Character.valueOf('E'), undeadEssence, Character.valueOf('O'), Block.obsidian);
		GameRegistry.addRecipe(new ItemStack(ebonStone), " E ", "EME", " E ", Character.valueOf('E'), undeadEssence, Character.valueOf('M'), Block.cobblestoneMossy);
		GameRegistry.addRecipe(new ItemStack(ebonTorch, 2), "C", "B", Character.valueOf('C'), ebonCoal, Character.valueOf('B'), Item.bone);

		GameRegistry.registerTileEntity(TileEntityWard.class, "EbonWard");
		GameRegistry.registerTileEntity(TileEntityVaseOfSouls.class, "EbonVaseOfSouls");

		EntityRegistry.registerGlobalEntityID(EntityTormentedSoul.class, "TormentedSoul", EntityRegistry.findGlobalUniqueEntityId(), 0x191919, 0xB3B3B3);
		EntityRegistry.registerGlobalEntityID(EntityDemonicSoul.class, "DemonicSoul", EntityRegistry.findGlobalUniqueEntityId(), 0x191919, 0x230B0B);
		EntityRegistry.registerGlobalEntityID(EntityVoidInsect.class, "VoidInsect", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.addSpawn("TormentedSoul", 24, 1, 5, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.hell });
		RenderingRegistry.registerEntityRenderingHandler(EntityTormentedSoul.class, new RenderTormentedSoul(new ModelBiped()));
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidInsect.class, new RenderVoidInsect());

		EbonAPIRegistry.registerSimpleTransmutation(Block.stone.blockID, Block.netherrack.blockID);
		EbonAPIRegistry.registerSimpleTransmutation(Block.cobblestone.blockID, Block.netherrack.blockID);
		EbonAPIRegistry.registerSimpleTransmutation(Block.dirt.blockID, Block.slowSand.blockID);
		EbonAPIRegistry.registerSimpleTransmutation(Block.grass.blockID, Block.slowSand.blockID);
		EbonAPIRegistry.registerSimpleTransmutation(Block.mycelium.blockID, Block.slowSand.blockID);
		EbonAPIRegistry.registerSimpleTransmutation(Block.gravel.blockID, Block.slowSand.blockID);
		EbonAPIRegistry.registerSimpleTransmutation(Block.glass.blockID, Block.glowStone.blockID);
		EbonAPIRegistry.registerSimpleTransmutation(Block.brick.blockID, Block.netherBrick.blockID);

		EbonAPIRegistry.registerSimpleTransfiguration(EntityChicken.class, "Silverfish");
		EbonAPIRegistry.registerSimpleTransfiguration(EntitySilverfish.class, "Chicken");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityCow.class, "MushroomCow");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityMooshroom.class, "Cow");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityPigZombie.class, "Pig");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityPig.class, "PigZombie");
		EbonAPIRegistry.registerSimpleTransfiguration(EntitySheep.class, "Skeleton");
		EbonAPIRegistry.registerSimpleTransfiguration(EntitySkeleton.class, "Sheep");
		EbonAPIRegistry.registerSimpleTransfiguration(EntitySpider.class, "CaveSpider");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityCaveSpider.class, "Spider");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityBlaze.class, "Enderman");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityEnderman.class, "Blaze");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityWolf.class, "Ozelot");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityOcelot.class, "Wolf");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityMagmaCube.class, "Slime");
		EbonAPIRegistry.registerSimpleTransfiguration(EntitySlime.class, "LavaSlime");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityCreeper.class, "Ghast");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityGhast.class, "Creeper");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityZombie.class, "Squid");
		EbonAPIRegistry.registerSimpleTransfiguration(EntitySquid.class, "Zombie");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityTormentedSoul.class, "DemonicSoul");
		EbonAPIRegistry.registerSimpleTransfiguration(EntityDemonicSoul.class, "TormentedSoul");

		EbonAPIRegistry.blacklistSpawner("Villager");
		EbonAPIRegistry.blacklistSpawner("VillagerGolem");
		EbonAPIRegistry.blacklistSpawner("TormentedSoul");
		EbonAPIRegistry.blacklistSpawner("DemonicSoul");
		EbonAPIRegistry.blacklistSpawner("Slime");
		EbonAPIRegistry.blacklistSpawner("SnowMan");
		EbonAPIRegistry.blacklistSpawner("MushroomCow");
		EbonAPIRegistry.blacklistSpawner("EbonSpecter");

		EbonAPIRegistry.registerArmorEffect(new ArmorEffectDebilitation());
		EbonAPIRegistry.registerArmorEffect(new ArmorEffectFrost());
		EbonAPIRegistry.registerArmorEffect(new ArmorEffectMeteorPlumetting());
		EbonAPIRegistry.registerArmorEffect(new ArmorEffectNature());
		EbonAPIRegistry.registerArmorEffect(new ArmorEffectRegeneration());
		EbonAPIRegistry.registerArmorEffect(new ArmorEffectRespiration());
		EbonAPIRegistry.registerArmorEffect(new ArmorEffectRevitalization());
		EbonAPIRegistry.registerArmorEffect(new ArmorEffectThorns());
	}

	@ServerStarted
	public void onServerStarted(FMLServerStartedEvent event) {
		new CommandDarkness().register();
	}

	public static Potion magicalExhaustion;

	public static Enchantment venomTouch;

	public static Item corpseDust;
	public static Item undeadEssence;
	public static Item undeadEssenceConcentrated;
	public static Item ebonGem;
	public static Item ebonBroadsword;
	public static Item ebonPickaxe;
	public static Item ebonSpade;
	public static Item ebonHatchet;
	public static Item darknessIcon;
	public static Item darknessImbiber;
	public static Item ebonCoal;
	public static Item ebonGlowdust;
	public static Item ebonApple;
	public static Item soul;
	public static Item soulPowder;
	public static Item staffShard;
	public static Item ebonStaff;
	public static Item ebonStaffCharged;
	public static Item orbOfSouls;
	public static Item orbOfSoulsCharged;
	public static Item staffOfSouls;
	public static Item staffOfSoulsCharged;
	public static Item demonicSoul;
	public static Item bloodGem;
	public static Item ebonScepter;
	public static Item soulGrindstone;
	public static Item wandOfRetrieval;
	public static Item bloodPowder;
	public static Item bloodSeeds;
	public static Item bloodLeaf;
	public static Item necromancerLexicon;
	public static Item ebonScepterInfinity;
	public static Item ebonScepterZero;
	public static Item ebonScepterVoid;
	public static Item ebonHood;
	public static Item ebonRobeTop;
	public static Item ebonRobeBottom;
	public static Item ebonShoes;
	public static Item soulStone;
	public static Item ebonCloth;
	public static Item plusiumCharm;
	public static Item miniumCharm;
	public static Item gemOfDespair;
	public static Item bottleOfDarkness;
	public static Item wandOfImprisionment;
	public static Item altarBlueprint;
	public static Item vaseOfSoulsItem;

	public static Block ebonGlowstone;
	public static Block ebonGemBlock;
	public static Block bloodGemBlock;
	public static Block quicksand;
	public static Block ebonObsidian;
	public static Block ebonStone;
	public static Block ebonTorch;
	public static Block bloodLeafCrops;
	public static Block vaseOfSouls;

}
