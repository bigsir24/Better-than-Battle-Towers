package jamdoggie.betterbattletowers;

import jamdoggie.betterbattletowers.entity.EntityGolem;
import jamdoggie.betterbattletowers.entity.render.RenderGolem;
import jamdoggie.betterbattletowers.worldgen.WorldGenTower;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Random;


public class BetterBattleTowers implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientModInitializer
{
    public static final String MOD_ID = "betterbattletowers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static BetterBattleTowers instance;

	private int towercount;
	public static int rarity;
	private final int DEFAULT_RARITY = 12;

	public BetterBattleTowers()
	{
		instance = this;
	}

	@Override
    public void onInitialize() {
        LOGGER.info("Better than Battle Towers initialized.");
		towercount = 200;
		rarity = 3;

		EntityHelper.Core.createEntity(EntityGolem.class, 101, "TowerGolem");


    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void onInitializeClient()
	{
		EntityHelper.Client.assignEntityRenderer(EntityGolem.class, new RenderGolem());
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golem1.ogg");
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golem3.ogg");
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golemawaken.ogg");
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golemdeath.ogg");
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golemhurt2.ogg");
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golemhurt3.ogg");
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golemspecial1.ogg");
		SoundHelper.Client.addSound(BetterBattleTowers.MOD_ID, "golemspecial2.ogg");
	}

	public void GenerateSurface(World world, Random random, int chunkX, int chunkZ)
	{
		if(towercount >= rarity * 100)
		{
			if(random.nextInt(2) == 0)
			{
				int k = chunkX + random.nextInt(16) + 8;
				int l = random.nextInt(16) + 64;
				int i1 = chunkZ + random.nextInt(16) + 8;

				if((new WorldGenTower()).generate(world, random, k, l, i1))
				{
					towercount = 0;
				}
			}
		}
		else
		{
			towercount++;
		}
	}
}
