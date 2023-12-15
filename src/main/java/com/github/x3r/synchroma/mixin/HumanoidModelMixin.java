package com.github.x3r.synchroma.mixin;

import com.github.x3r.synchroma.client.cutscene.ClientCutsceneManager;
import com.github.x3r.synchroma.client.screen.SurgeonScreen;
import com.github.x3r.synchroma.common.block.multiblock.ControllerBlock;
import com.github.x3r.synchroma.common.entity.RideableEntity;
import com.github.x3r.synchroma.common.registry.EntityRegistry;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IScreenHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightLeg;

    @Shadow @Final public ModelPart leftLeg;

    @Shadow @Final public ModelPart body;

    @Shadow @Final public ModelPart head;

    @Inject(method = "setupAnim", at = @At("TAIL"))
    public void setupAnim(LivingEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
//        if(pEntity instanceof Player player && player.getVehicle() != null && player.getVehicle() instanceof RideableEntity) {
//            this.rightArm.xRot += (0);
//            this.leftArm.xRot += (0);
//            this.rightLeg.xRot = (float) -(Math.PI/2);
//            this.rightLeg.yRot = 0;
//            this.rightLeg.zRot = 0;
//            this.leftLeg.xRot = (float) -(Math.PI/2);
//            this.leftLeg.yRot = 0;
//            this.leftLeg.zRot = 0;
//            this.body.xRot = (float) -(Math.PI/4);
//            this.body.y = 6;
//            this.body.z = 8;
//            this.head.y = 5;
//            this.head.z = 8;
//            this.rightArm.y = 7;
//            this.rightArm.z = 8;
//            this.leftArm.y = 7;
//            this.leftArm.z = 8;
//            pEntity.setXRot((float) (-Math.PI/4));
//        }
    }

}
