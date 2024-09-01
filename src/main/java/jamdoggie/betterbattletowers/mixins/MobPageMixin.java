package jamdoggie.betterbattletowers.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import jamdoggie.betterbattletowers.entity.EntityGolem;
import net.minecraft.client.gui.guidebook.mobs.MobPage;
import net.minecraft.core.entity.EntityLiving;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MobPage.class, remap = false)
public abstract class MobPageMixin {

	@Shadow
	@Final
	private EntityLiving example;

	@Inject(method = "renderMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Lighting;enableLight()V"))
	private void offset(int x, int y, int mouseX, int mouseY, float partialTicks, CallbackInfo ci, @Local(ordinal = 1) float heightFactor, @Local(ordinal = 4) LocalFloatRef f6){
		//Guidebook entity mouse tracking fix
		if(this.example instanceof EntityGolem) f6.set(f6.get() + 2 * heightFactor);
	}
}
