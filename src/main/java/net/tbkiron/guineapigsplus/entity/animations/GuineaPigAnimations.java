package net.tbkiron.guineapigsplus.entity.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuineaPigAnimations {

	public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(0.6f).looping()
			.addAnimation("front_left_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.15f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.45f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_right_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.15f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.45f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("back_left_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.15f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.45f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("back_right_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.15f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.45f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition STAND_FOUR_TO_TWO = AnimationDefinition.Builder.withLength(0.5f)
			.addAnimation("head",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(62.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 2f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(40f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 2f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(37.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("back_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("back_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 11f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition STAND_TWO_TO_FOUR = AnimationDefinition.Builder.withLength(0.5f)
			.addAnimation("head",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(62.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 2f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(40f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 2f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(37.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("back_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("back_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(32.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 11f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition STAND_TO_SIT = AnimationDefinition.Builder.withLength(0.125f)
			.addAnimation("head",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition SIT_TO_STAND = AnimationDefinition.Builder.withLength(0.125f)
			.addAnimation("head",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_left_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("front_right_leg",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.POSITION,
		new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
								  new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
	AnimationChannel.Interpolations.LINEAR))).build();
}
