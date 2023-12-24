package net.tbkiron.guineapigsplus.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import net.tbkiron.guineapigsplus.entity.ModEntities;
import net.tbkiron.guineapigsplus.entity.variant.GuineaPigVariant;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

public class GuineaPigEntity extends TamableAnimal {


    public final AnimationState sitToStandAnimationState = new AnimationState();
    public final AnimationState standToSitAnimationState = new AnimationState();
    public final AnimationState fourToTwoAnimationState = new AnimationState();
    public final AnimationState twoToFourAnimationState = new AnimationState();

    private static final int SIT_DURATION_TICKS = 3;
    private static final int STAND_DURATION_TICKS = 7;
    private int sit_state_cooldown = 0;
    private int stand_state_cooldown = 0;

    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> STAND_TO_SIT_ANIM =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> SIT_TO_STAND_ANIM =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> STANDING_ON_TWO =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> FOUR_TO_TWO_ANIM =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> TWO_TO_FOUR_ANIM =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.INT);


    @Nullable
    private PlutoAvoidEntityGoal<Player> plutoAvoidPlayersGoal;


    private static final EntityDataAccessor<Boolean> DATA_TRUSTING = SynchedEntityData.defineId(GuineaPigEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }



    private void setupAnimationStates(){
        if(this.entityData.get(STANDING_ON_TWO)){
            if(this.isInFourToTwoAnim() && this.stand_state_cooldown <= 0){
                this.stand_state_cooldown = STAND_DURATION_TICKS;
                this.twoToFourAnimationState.stop();
                this.fourToTwoAnimationState.start(this.tickCount);
            } else {
                this.stand_state_cooldown--;
                if(this.stand_state_cooldown == 0){
                    setFourToTwoAnim(false);
                }
            }
        } else {
            if(this.isInTwoToFourAnim() && this.stand_state_cooldown <= 0){
                this.stand_state_cooldown = STAND_DURATION_TICKS;
                this.fourToTwoAnimationState.stop();
                this.twoToFourAnimationState.start(this.tickCount);
            } else {
                this.stand_state_cooldown--;
                if(this.stand_state_cooldown == 0){
                    setTwoToFourAnim(false);
                }
            }
        }

        if(this.entityData.get(SITTING)){
            if(this.isInStandToSitAnim() && this.sit_state_cooldown <= 0){
                this.sit_state_cooldown = SIT_DURATION_TICKS;
                this.sitToStandAnimationState.stop();
                this.standToSitAnimationState.start(this.tickCount);
            } else {
                this.sit_state_cooldown--;
                if(this.sit_state_cooldown == 0){
                    setStandToSitAnim(false);
                }
            }
        } else {
            if(this.isInSitToStandAnim() && this.sit_state_cooldown <= 0){
                this.sit_state_cooldown = SIT_DURATION_TICKS;
                this.standToSitAnimationState.stop();
                this.sitToStandAnimationState.start(this.tickCount);
            } else {
                this.sit_state_cooldown--;
                if(this.sit_state_cooldown == 0){
                    setSitToStandAnim(false);
                }
            }
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(!this.isSitting() && !this.isStandingOnTwo() && (this.sit_state_cooldown == 0) && (this.stand_state_cooldown == 0)){
            f = Math.min(pPartialTick * 6F, 1F);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    public GuineaPigEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.reassessTrustingGoals();
    }


    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(9, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(11, new GuineaPigStandIdlyGoal(this));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    protected void addVariantSpesificGoals(){
        if(this.getVariant() == GuineaPigVariant.PLUTO){
            this.goalSelector.addGoal(6, new PlutoTemptGoal(this, 0.6D, Ingredient.of(Items.GRASS, Items.GOLDEN_CARROT), true));
        } else {
            this.goalSelector.addGoal(6, new TemptGoal(this, 1.2D, Ingredient.of(Items.GRASS, Items.GOLDEN_CARROT), false));
        }
        reassessTrustingGoals();
    }

    protected void reassessTrustingGoals() {
        if (this.plutoAvoidPlayersGoal == null) {
            this.plutoAvoidPlayersGoal = new PlutoAvoidEntityGoal<>(this, Player.class, 10.0F, 2.2D, 2.2D);
        }

        this.goalSelector.removeGoal(this.plutoAvoidPlayersGoal);
        System.out.println("Doesn't trust" + this.isTrusting() + " " + this.entityData.get(DATA_TRUSTING)+ " " + getTypeVariant());
        if (!this.isTrusting()) {

            this.goalSelector.addGoal(4, this.plutoAvoidPlayersGoal);
        }

    }

    public static AttributeSupplier.Builder createAttributes(){
        return TamableAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.GUINEA_PIG.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.GRASS);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.FOX_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        Item itemForTaming = Items.GOLDEN_CARROT;

        if(this.isFood(itemStack)){
            int i = this.getAge();
            if(!this.level().isClientSide() && i == 0 && this.canFallInLove()){
                if(this.random.nextInt(6) == 0){
                    this.setTrusting(true);
                }
            }
        } else if(item == itemForTaming && !isTame()){
            if(this.level().isClientSide){
                return InteractionResult.CONSUME;
            } else {
                if(!player.getAbilities().instabuild){
                    itemStack.shrink(1);
                }

                if(!this.level().isClientSide){
                    if(!ForgeEventFactory.onAnimalTame(this, player) && this.random.nextInt(3) == 0){
                        this.setTrusting(true);
                        super.tame(player);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        setSitting(true);
                        this.spawnTrustingParticles(true);
                        this.level().broadcastEntityEvent(this, (byte) 11);
                    } else {
                        this.spawnTrustingParticles(false);
                        this.level().broadcastEntityEvent(this, (byte) 10);
                    }
                }


                return InteractionResult.SUCCESS;
            }
        }

        if(isTame() && !this.level().isClientSide && hand == InteractionHand.MAIN_HAND && this.isOwnedBy(player)){
            if(!this.isStandingOnTwo() && item == Items.AIR){
                setSitting(!isSitting());
            }

            return InteractionResult.SUCCESS;
        }

        if(itemStack.getItem() == itemForTaming){
            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }

    private void spawnTrustingParticles(boolean pIsTrusted) {
        ParticleOptions particleoptions = ParticleTypes.HEART;
        if (!pIsTrusted) {
            particleoptions = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setSitting(pCompound.getBoolean("isSitting"));
        setSitToStandAnim(pCompound.getBoolean("isInSitToStandAnim"));
        setStandToSitAnim(pCompound.getBoolean("isInStandToSitAnim"));
        setStandOnTwo(pCompound.getBoolean("isStandingOnTwo"));
        setFourToTwoAnim(pCompound.getBoolean("isInFourToTwoAnim"));
        setTwoToFourAnim(pCompound.getBoolean("isInTwoToFourAnim"));
        this.entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
        this.setTrusting(pCompound.getBoolean("Trusting"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isSitting", this.isSitting());
        pCompound.putBoolean("isInSitToStandAnim", this.isInSitToStandAnim());
        pCompound.putBoolean("isInStandToSitAnim", this.isInStandToSitAnim());
        pCompound.putBoolean("isStandingOnTwo", this.isStandingOnTwo());
        pCompound.putBoolean("isInTwoToFourAnim", this.isInTwoToFourAnim());
        pCompound.putBoolean("isInFourToTwoAnim", this.isInFourToTwoAnim());
        pCompound.putInt("Variant", this.getTypeVariant());
        pCompound.putBoolean("Trusting", this.isTrusting());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(STANDING_ON_TWO, false);
        this.entityData.define(SIT_TO_STAND_ANIM, false);
        this.entityData.define(STAND_TO_SIT_ANIM, false);
        this.entityData.define(FOUR_TO_TWO_ANIM, false);
        this.entityData.define(TWO_TO_FOUR_ANIM, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(DATA_TRUSTING, false);

    }

    public void setStandOnTwo(boolean standOnTwo){
        if(this.entityData.get(STANDING_ON_TWO) != standOnTwo){
            this.entityData.set(STANDING_ON_TWO, standOnTwo);
            setFourToTwoAnim(standOnTwo);
            setTwoToFourAnim(!standOnTwo);
        }
    }

    public boolean isStandingOnTwo(){
        return this.entityData.get(STANDING_ON_TWO);
    }

    public boolean isInTwoToFourAnim(){
        return this.entityData.get(TWO_TO_FOUR_ANIM);
    }

    public void setTwoToFourAnim(boolean twoToFourAnim){
        this.entityData.set(TWO_TO_FOUR_ANIM, twoToFourAnim);
    }

    public boolean isInFourToTwoAnim(){
        return this.entityData.get(FOUR_TO_TWO_ANIM);
    }

    public void setFourToTwoAnim(boolean fourToTwoAnim){
        this.entityData.set(FOUR_TO_TWO_ANIM, fourToTwoAnim);
    }

    public void setSitting(boolean sitting){
        if(this.entityData.get(SITTING) != sitting){
            setStandOnTwo(false);
            this.entityData.set(SITTING, sitting);
            setSitToStandAnim(!sitting);
            setStandToSitAnim(sitting);
            this.setOrderedToSit(sitting);
        }

    }

    public boolean isInSitToStandAnim(){
        return this.entityData.get(SIT_TO_STAND_ANIM);
    }

    public void setSitToStandAnim(boolean sitToStandAnim){
        this.entityData.set(SIT_TO_STAND_ANIM, sitToStandAnim);
    }

    public boolean isInStandToSitAnim(){
        return this.entityData.get(STAND_TO_SIT_ANIM);
    }

    public void setStandToSitAnim(boolean standToSitAnim){
        this.entityData.set(STAND_TO_SIT_ANIM, standToSitAnim);
    }


    public boolean isSitting(){
        return this.entityData.get(SITTING);
    }

    @Override
    public Team getTeam() {
        return super.getTeam();
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return true;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor slAccessor, DifficultyInstance difficultyInstance,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData,
                                        @Nullable CompoundTag tag){
        GuineaPigVariant variant = Util.getRandom(GuineaPigVariant.values(), this.random);
        setVariant(variant);
        addVariantSpesificGoals();
        return super.finalizeSpawn(slAccessor, difficultyInstance, spawnType, spawnGroupData, tag);
    }

    public GuineaPigVariant getVariant(){
        return GuineaPigVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant(){
        //System.out.println("get: " + this.entityData.get(DATA_ID_TYPE_VARIANT));
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(GuineaPigVariant variant){
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
        System.out.println(variant + " " + this.entityData.get(DATA_ID_TYPE_VARIANT));
    }

    boolean isTrusting() {
        if(getVariant() == GuineaPigVariant.FREDAG){
            return true;
        } else {
            return this.entityData.get(DATA_TRUSTING);
        }

    }

    public boolean isSteppingCarefully() {
        return this.isCrouching() || super.isSteppingCarefully();
    }

    private void setTrusting(boolean pTrusting) {
        this.entityData.set(DATA_TRUSTING, pTrusting);
        this.reassessTrustingGoals();
    }

    static class PlutoAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final GuineaPigEntity guineaPig;

        public PlutoAvoidEntityGoal(GuineaPigEntity pPluto, Class<T> pEntityClassToAvoid, float pMaxDist, double pWalkSpeedModifier, double pSprintSpeedModifier) {
            super(pPluto, pEntityClassToAvoid, pMaxDist, pWalkSpeedModifier, pSprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
            this.guineaPig = pPluto;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.guineaPig.isTrusting() && super.canUse();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return !this.guineaPig.isTrusting() && super.canContinueToUse();
        }
    }

    static class PlutoTemptGoal extends TemptGoal {
        private final GuineaPigEntity guineaPig;

        public PlutoTemptGoal(GuineaPigEntity guineaPig, double pSpeedModifier, Ingredient pItems, boolean pCanScare) {
            super(guineaPig, pSpeedModifier, pItems, pCanScare);
            this.guineaPig = guineaPig;
        }

        protected boolean canScare() {
            return super.canScare() && !this.guineaPig.isTrusting();
        }
    }

    static class GuineaPigStandIdlyGoal extends Goal {
        private final GuineaPigEntity guineaPig;
        private double relX;
        private double relZ;
        private int idleTime;

        public GuineaPigStandIdlyGoal(GuineaPigEntity guineaPig) {
            this.guineaPig = guineaPig;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK, Flag.TARGET));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return (this.guineaPig.getRandom().nextFloat() < 0.002F) && !this.guineaPig.isSitting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return (this.idleTime >= 0 && !this.guineaPig.isSitting());
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            double d0 = (Math.PI * 2D) * this.guineaPig.getRandom().nextDouble();
            this.relX = Math.cos(d0);
            this.relZ = Math.sin(d0);
            this.guineaPig.setStandOnTwo(true);
            this.idleTime = 40 + this.guineaPig.getRandom().nextInt(120);
            this.guineaPig.getNavigation().stop();
        }

        public void stop() {
            this.guineaPig.setStandOnTwo(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.idleTime;
            this.guineaPig.getLookControl().setLookAt(this.guineaPig.getX() + this.relX, this.guineaPig.getEyeY(), this.guineaPig.getZ() + this.relZ);
        }
    }


}
