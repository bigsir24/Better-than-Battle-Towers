package jamdoggie.betterbattletowers.mixins;

import jamdoggie.betterbattletowers.BetterBattleTowers;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundPool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SoundManager.class, remap = false)
public abstract class SoundManagerMixin
{
	@Shadow
	private static void loadModAudio(String s, SoundPool soundpool)
	{
	}

	@Shadow
	@Final
	private SoundPool soundPoolSounds;

	@Inject(method = "loadSoundSettings",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/sound/SoundManager;loadModAudio(Ljava/lang/String;Lnet/minecraft/client/sound/SoundPool;)V",
			ordinal = 0,
			shift = At.Shift.BEFORE))
	private void loadSoundSettings(GameSettings gamesettings, CallbackInfo ci)
	{
		loadModAudio("assets/" + BetterBattleTowers.MOD_ID + "/sound", soundPoolSounds);
	}
}
